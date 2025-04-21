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
    private Label greetingLabel;

    private Teacher teacher;

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;

        // Personalized greeting
        greetingLabel.setText("Hi, " + teacher.getTeacherID() + "!");
    }

}
