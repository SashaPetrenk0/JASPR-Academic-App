package org.jaspr.hr.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller for creating quizzes by teachers or students.
 * It handles form inputs, validates data, constructs prompts, and
 * processes responses using the Ollama LLM to auto-generate quiz content.
 */
public class CreateQuizController {

    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();
    private final User user = UserSession.getInstance().getCurrentUser();
    private final String role = UserSession.getInstance().getRole();

    @FXML
    private Button createQuiz;
    @FXML
    private Label successMessage;

    @FXML
    private TextField titleField;
    @FXML
    private TextField descriptionField;
    @FXML
    private TextField topicField;
    @FXML
    private TextField lengthField;

    @FXML
    private Button returnToPrevious;

    @FXML
    private Label errorLabel;

    @FXML
    private VBox descriptionSection;

    @FXML
    private Button nextButton;

    @FXML
    private VBox initialQuizFields;


    /**
     * Handles the "Create Quiz" button click event.
     * Validates inputs, builds a quiz prompt, generates questions from AI,
     * and saves the quiz and its questions to the database.
     */
    @FXML
    private void onCreateQuiz() {
        // Extract and format user input
        String title = titleField.getText().trim();
        String desc = descriptionField.getText().trim();
        String topic = topicField.getText().trim();
        String duration = lengthField.getText().trim();

        // Validate all fields are filled
        if(title.isEmpty() || topic.isEmpty() || desc.isEmpty() || duration.isEmpty()){
            errorLabel.setText("Please fill out all fields.");
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            return;
        }

        // Check if length is a number
        int length;
        try{
            length = Integer.parseInt(duration);
        } catch (NumberFormatException e){
            errorLabel.setText("Number of Questions must be a number.");
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            return;
        }

        if(length > 20){
            errorLabel.setText("Number of Questions must not exceed 20.");
            errorLabel.setVisible(true);
            errorLabel.setManaged(true);
            return;
        }
        // Determine author ID based on user role
        int author = 0;

        // Construct the quiz generation prompt for Ollama using fxml inputs
        String prompt = "Write " + length + " multiple choice questions about "+desc+" with 4 options, A, B, C and D in the format; 'Question:' 'Question text' '?' - new line - 'Question letter' ')' repeated for A, B, C, D - new line - 'Answer:' 'Question letter'')'. Do not under any circumstances deviate from this format in any way and do not forget to write the answer";

        //if the logged-in user is a teacher, use the getTeacherID method
        if ("Teacher".equals(role) && user instanceof Teacher){
            Teacher teacher = (Teacher) user;
            author = teacher.getTeacherID();
        }else if ("Student".equals(role) && user instanceof Student) {
            Student student = (Student) user;
            author = student.getStudentID();
        }

        // Create a new Quiz object
        Quiz newQuiz = new Quiz(title, desc, topic, length, author);

        // LLM response handler class
        class MyResponseListener implements ResponseListener {
            @Override
            public void onResponseReceived(OllamaResponse response) {
                String input = response.getResponse();
                System.out.print(input);

                // Regex pattern to match the LLM-generated questions
                Pattern fullQuestionPattern = Pattern.compile(
                        "Question\\s*(?::|\\d+:|\\*\\*\\d+\\*\\*)\\s*" +       // Match "Question:", "Question N:", or "**Question N**"
                                "(.*?)\\s*" +                                         // Question text
                                "A\\)\\s*(.*?)\\s*" +                                 // Option A
                                "B\\)\\s*(.*?)\\s*" +                                 // Option B
                                "C\\)\\s*(.*?)\\s*" +                                 // Option C
                                "D\\)\\s*(.*?)\\s*" +                                 // Option D

                                "(?:\\*\\*?\\s*Answer\\s*:?|Answer\\s*:?)+\\s*([A-D])\\)?" // Match "**Answer: C)", "Answer: C)", or "Answer : C)"

                );

                Matcher matcher = fullQuestionPattern.matcher(input);
                List<Question> questionList = new ArrayList<>();

                // Extract questions from the matched input
                while (matcher.find()) {
                    String qText = matcher.group(1).trim();
                    String a = matcher.group(2).trim();
                    String b = matcher.group(3).trim();
                    String c = matcher.group(4).trim();
                    String d = matcher.group(5).trim();
                    String correct = matcher.group(6).trim();

                    questionList.add(new Question(qText, a, b, c, d, correct));
                }

                Question[] questions = questionList.toArray(new Question[0]);
                System.out.print("question length" + questions.length);

                // Save questions to quiz
                newQuiz.setQuestions(questions);

                for (Question question : questions) {
                    quizDAO.addQuestion(question, newQuiz);

                }
            }
        }
        // Trigger the asynchronous fetch from Ollama
        String apiURL = "http://127.0.0.1:11434/api/generate";
        String model = "llama3.2";
        OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);
        fetcher.fetchAsynchronousOllamaResponse(model, prompt, new MyResponseListener());

        // Save the initial quiz entry (without questions)
        quizDAO.addQuiz(newQuiz);
        successMessage.setText("Quiz " + title + " has been created successfully!");
        successMessage.setVisible(true);
        successMessage.setManaged(true);
        createQuiz.setDisable(true);
        //TODO: create error handling for if from is not complete
    }

    /**
     * Handles the "Next" button to transition between form steps.
     * Reveals the description input section after basic quiz info is entered.
     * @param event the ActionEvent triggered by clicking the "Next" button
     */
    @FXML
    private void onNextPressed(ActionEvent event) {

        initialQuizFields.setVisible(false);
//        initialQuizFields.setManaged(false);

        // Show the description section
        descriptionSection.setVisible(true);
        descriptionSection.setManaged(true);

        // Hide "Next" button
        nextButton.setVisible(false);
        nextButton.setManaged(false);

        // Show "Create Quiz" button
        createQuiz.setVisible(true);
        createQuiz.setManaged(true);
    }


    /**
     * Navigates the user back to their respective dashboard view based on their role.
     * If the user is a Teacher, it loads the teacher dashboard.
     * If the user is a Student, it loads the student dashboard.
     * @throws IOException if the FXML file for the scene cannot be loaded
     */
    @FXML private void returnToPage() throws IOException {
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        if ("Teacher".equals(role) && user instanceof Teacher){
            SceneChanger.changeScene(stage, "teacher-dashboard-view.fxml");

        }else if ("Student".equals(role) && user instanceof Student) {
            SceneChanger.changeScene(stage, "student-dashboard-view.fxml");
        }

    }









}
