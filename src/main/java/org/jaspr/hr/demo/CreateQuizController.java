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

public class CreateQuizController {
    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();



    User user = UserSession.getInstance().getCurrentUser();
    String role = UserSession.getInstance().getRole();

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



    @FXML
    private void onCreateQuiz() {

        String title = titleField.getText().trim();
        String desc = descriptionField.getText().trim();
        String topic = topicField.getText().trim();
        String duration = lengthField.getText().trim();

        if(title.isEmpty() || topic.isEmpty() || desc.isEmpty() || duration.isEmpty()){
            errorLabel.setText("Please fill out all fields.");
            return;
        }

        // Check if length is a number
        int length;
        try{
            length = Integer.parseInt(duration);
        } catch (NumberFormatException e){
            errorLabel.setText("Number of Questions must be a number.");
            return;
        }

        if(length > 20){
            errorLabel.setText("Number of Questions must not exceed 20.");
            return;
        }

        int author = 0;
        //prompt for the AI, taking data entered by the user in the fxml fields
        String prompt = "Write " + length + " multiple choice questions about "+desc+" with 4 options, A, B, C and D in the format; 'Question:' 'Question text' '?' - new line - 'Question letter' ')' repeated for A, B, C, D - new line - 'Answer:' 'Question letter'')'. Do not under any circumstances deviate from this format in any way and do not forget to write the answer";

        //if the logged-in user is a teacher, use the getTeacherID method
        if ("Teacher".equals(role) && user instanceof Teacher){
            Teacher teacher = (Teacher) user;
            author = teacher.getTeacherID();
        }else if ("Student".equals(role) && user instanceof Student) {
            Student student = (Student) user;
            author = student.getStudentID();
        }

//Quiz newQuiz = new Quiz(title, desc, topic, length, author, List.of(questions));
        Quiz newQuiz = new Quiz(title, desc, topic, length, author);

        class MyResponseListener implements ResponseListener {
            @Override
            public void onResponseReceived(OllamaResponse response) {
                String input = response.getResponse();
                System.out.print(input);
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

                newQuiz.setQuestions(questions);

                for (Question question : questions) {
                    quizDAO.addQuestion(question, newQuiz);

                }
            }
        }

        String apiURL = "http://127.0.0.1:11434/api/generate";
        String model = "llama3.2";
        OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);
        fetcher.fetchAsynchronousOllamaResponse(model, prompt, new MyResponseListener());

        quizDAO.addQuiz(newQuiz);
        successMessage.setText("Quiz " + title + " created successfully! Yay :)");
        successMessage.setVisible(true);
        createQuiz.setDisable(true);
        //TODO: create error handling for if from is not complete



    }

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


    @FXML private void returnToPage() throws IOException {
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        if ("Teacher".equals(role) && user instanceof Teacher){
            Teacher teacher = (Teacher) user;
            SceneChanger.changeScene(stage, "teacher-dashboard-view.fxml");

        }else if ("Student".equals(role) && user instanceof Student) {
            Student student = (Student) user;
            SceneChanger.changeScene(stage, "student-dashboard-view.fxml");
        }



    }









}
