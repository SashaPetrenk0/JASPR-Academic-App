package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
public class StudentController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label successfulSignUpLabel;

    private Student currentStudent;

    @FXML
    private void onSubmitClicked(){
        String name = nameField.getText();

        String ageInput = ageField.getText().trim();
        int age;
        try {
            age = Integer.parseInt(ageInput);
        } catch (NumberFormatException e) {
            successfulSignUpLabel.setText("Age must be a valid number.");
            successfulSignUpLabel.setVisible(true);
            return;
        }

        String email = emailField.getText();
        String password = passwordField.getText();

        currentStudent = new Student(name, age, email, password);

        successfulSignUpLabel.setText("Successful Student Signup! Welcome " + name);
        successfulSignUpLabel.setVisible(true);

        // TODO: Error handling for incorrect user inputs
    }


}
