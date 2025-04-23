package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateQuizController {
    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();


    User user = UserSession.getInstance().getCurrentUser();
    String role = UserSession.getInstance().getRole();

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


    }

    @FXML private void returnToPage() throws IOException {
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "temp-home-view.fxml");

    }








}
