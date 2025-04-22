package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
public class TempHome {
    User user = UserSession.getInstance().getCurrentUser();
    String role = UserSession.getInstance().getRole();

    @FXML
    private Label personalisedGreeting;

    @FXML
    private Button createQuiz;



    public void setTeacher() {

        // Personalized greeting
        System.out.println("methid cAKKED");

        if ("Teacher".equals(role) && user instanceof Teacher){
            Teacher teacher = (Teacher) user;
            personalisedGreeting.setText("Hi, " + teacher.getName() + "!");

            System.out.println(teacher.getName() + teacher.getTeacherID());


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

}
