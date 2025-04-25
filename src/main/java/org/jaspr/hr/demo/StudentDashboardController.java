package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.io.IOException;


public class StudentDashboardController {
    User user = UserSession.getInstance().getCurrentUser();
    String role = UserSession.getInstance().getRole();

    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();

    @FXML
    private Label personalisedGreeting;

    @FXML
    private Button createQuiz;

    @FXML
    private ListView quizLists;

    @FXML
    private Button takeQuiz;

    @FXML
    public void initialize() {


        if ("Student".equals(role) && user instanceof Student){
            Student student = (Student) user;
            personalisedGreeting.setText("Hi, " + student.getName() + "!");
            quizLists.setItems(FXCollections.observableArrayList(quizDAO.getAllQuizzes(student)));
        }

    }




    /// go to create quiz page
    @FXML
    protected void onAdd() throws IOException {
        Stage stage = (Stage) createQuiz.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-quiz-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    protected void onTakeQuiz() throws IOException {
        Stage stage = (Stage)takeQuiz.getScene().getWindow();
        SceneChanger.changeScene(stage, "take-quiz-view.fxml");
    }


}


