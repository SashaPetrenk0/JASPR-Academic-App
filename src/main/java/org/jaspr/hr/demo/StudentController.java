package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class StudentController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    private Student currentStudent;

    @FXML
    private void onSubmitClicked(){
        String name = nameField.getText();
        int age;
        age = Integer.parseInt(ageField.getText());
        String email = emailField.getText();
        String password = passwordField.getText();

        currentStudent = new Student(name, age, email, password);

        // TODO: Error handling for incorrect user inputs
    }
}
