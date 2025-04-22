package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.io.IOException;
public class TempHome {

    @FXML
    private Label personalisedGreeting;

    @FXML
    private Button createQuiz;

    private Teacher teacher;

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;

        // Personalized greeting
        System.out.println("methid cAKKED");
        personalisedGreeting.setText("Hi, " + teacher.getName() + "!");
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
