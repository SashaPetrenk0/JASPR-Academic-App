package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

        //TODO: decide on maximum number of questions
        if(length > 20){
            errorLabel.setText("Number of Questions must not exceed 20.");
            return;
        }

        int author = 0;
        String prompt = "Write " + length + " multiple choice questions about "+desc+" with 4 options, A, B, C and D";

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
                System.out.print("Ollama says: ");
                System.out.print(input);
                Pattern fullQuestionPattern = Pattern.compile(
                        "(?:\\*\\*Question \\d+\\*\\*|Question \\d+:)\\s*" +   // Match "**Question N**" or "Question N:"
                                "(.*?)\\s*" +                                         // Question text
                                "A\\)\\s*(.*?)\\s*" +                                 // Option A
                                "B\\)\\s*(.*?)\\s*" +                                 // Option B
                                "C\\)\\s*(.*?)\\s*" +                                 // Option C
                                "D\\)\\s*(.*?)\\s*" +                                 // Option D
                                "(?:\\*\\*Answer:|Answer:)\\s*([A-D])\\)"             // Match "**Answer: C)" or "Answer: C)"
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
                System.out.print(questions.length);

                newQuiz.setQuestions(questions);
                for (int i = 0; i < questions.length; i++) {
                    quizDAO.addQuestion(questions[i],newQuiz);
                }
            }
        }

        String apiURL = "http://127.0.0.1:11434/api/generate";
        String model = "llama3.2"; //replace with the model YOU are using
        OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);
        fetcher.fetchAsynchronousOllamaResponse(model, prompt, new MyResponseListener());

        quizDAO.addQuiz(newQuiz);
        successMessage.setText("Quiz " + title + " created successfully! Yay :)");
        successMessage.setVisible(true);
        createQuiz.setDisable(true);
        //TODO: create error handling for if from is not complete



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
