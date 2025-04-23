package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;





import java.io.IOException;

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
    private void onCreateQuiz() {

        String title = titleField.getText();
        String desc = descriptionField.getText();
        String topic = topicField.getText();
        int length = Integer.parseInt(lengthField.getText().trim());
        int author = 0;



        if ("Teacher".equals(role) && user instanceof Teacher){
            Teacher teacher = (Teacher) user;
            author = teacher.getTeacherID();
        }

        Quiz newQuiz = new Quiz(title, desc, topic, length, author);
        quizDAO.addQuiz(newQuiz);
        successMessage.setText("Quiz " + title + " created successfully! Yay :)");
        successMessage.setVisible(true);
        createQuiz.setDisable(true);
        //TODO: create error handling for if from is not complete


    }

    @FXML private void returnToPage() throws IOException {
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "temp-home-view.fxml");

    }

    //trying ai implementation
    public class SimplestCompletion {
        public static void main(String[] args) {

            String apiURL = "http://127.0.0.1:11434/api/generate/";
            String model = "llama3.2";
            String prompt = "Write 2 multiple choice questions about chemistry shown in a java array";

            OllamaResponseFetcher fetcher = new OllamaResponseFetcher(apiURL);

            OllamaResponse response = fetcher.fetchOllamaResponse(model, prompt);

            System.out.println("======================================================");
            System.out.print("You asked: ");
            System.out.println(prompt);
            System.out.println("======================================================");
            System.out.print("Ollama says: ");
            System.out.println(response.getResponse());
            System.out.println("======================================================");
        }
    }








}
