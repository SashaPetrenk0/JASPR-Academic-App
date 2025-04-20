package org.jaspr.hr.demo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private VBox studentForm, teacherForm, parentForm, adminForm;

    @FXML
    private TextField nameFieldStudent;
    @FXML
    private TextField ageFieldStudent;
    @FXML
    private TextField studentIDField;
    @FXML
    private TextField emailFieldStudent;
    @FXML
    private TextField passwordFieldStudent;

    @FXML
    private TextField nameFieldTeacher;
    @FXML
    private TextField ageFieldTeacher;
    @FXML
    private TextField teacherIDField;
    @FXML
    private TextField emailFieldTeacher;
    @FXML
    private TextField passwordFieldTeacher;

    @FXML
    private TextField nameFieldParent;
    @FXML
    private TextField childNameField;
    @FXML
    private TextField childIDField;
    @FXML
    private TextField emailFieldParent;
    @FXML
    private TextField passwordFieldParent;

    @FXML
    private TextField nameFieldAdmin;
    @FXML
    private TextField ageFieldAdmin;
    @FXML
    private TextField adminIDField;
    @FXML
    private TextField emailFieldAdmin;
    @FXML
    private TextField passwordFieldAdmin;

    @FXML
    private Label successfulSignUpLabelStudent;
    @FXML
    private Label successfulSignUpLabelTeacher;
    @FXML
    private Label successfulSignUpLabelParent;
    @FXML
    private Label successfulSignUpLabelAdmin;


    private Student newStudent;
    private Teacher newTeacher;
    private Parent newParent;
    private Admin newAdmin;

    public void initialize(){
        roleComboBox.setItems(FXCollections.observableArrayList("Student", "Teacher", "Parent", "Admin"));
    }

    @FXML
    private void onRoleSelected(){
        String role = roleComboBox.getValue();
        studentForm.setVisible(false);
        teacherForm.setVisible(false);
        parentForm.setVisible(false);
        adminForm.setVisible(false);

        switch(role){
            case "Student" -> studentForm.setVisible(true);
            case "Teacher" -> studentForm.setVisible(true);
            case "Parent" -> studentForm.setVisible(true);
            case "Admin" -> studentForm.setVisible(true);
        }
        String selectedRole = roleComboBox.getValue();

    }

        @FXML
        private void onSubmitClicked(){
            String role = roleComboBox.getValue();
            String name, email, password;
            int age = 0;

            switch (role){
                case "Student" -> {
                    name = nameFieldStudent.getText();
                    email = emailFieldStudent.getText();
                    password = passwordFieldStudent.getText();
                    age = Integer.parseInt(ageFieldStudent.getText().trim());
                    int studentID = Integer.parseInt(studentIDField.getText().trim());

                    Student newStudent = new Student(name, age, studentID, email, password);
                    successfulSignUpLabelStudent.setText("Successful Student Registration! Welcome " + name + "!");



                }
                case "Teacher" -> {
                    name = nameFieldTeacher.getText();
                    email = emailFieldTeacher.getText();
                    password = passwordFieldTeacher.getText();
                    age = Integer.parseInt(ageFieldTeacher.getText().trim());
                    int teacherID = Integer.parseInt(teacherIDField.getText().trim());

                    Teacher newTeacher = new Teacher(name, age, teacherID, email, password);
                    successfulSignUpLabelTeacher.setText("Successful Teacher Registration! Welcome " + name + "!");


                }
                case "Parent" -> {
                    name = nameFieldParent.getText();
                    String child = childNameField.getText();
                    int childID = Integer.parseInt(childIDField.getText().trim());
                    email = emailFieldParent.getText();
                    password = passwordFieldParent.getText();

                    Parent newParent = new Parent(name, child, childID, email, password);
                    successfulSignUpLabelParent.setText("Successful Parent Registration! Welcome " + name + "!");



                }
                case "Admin" -> {
                    name = nameFieldAdmin.getText();
                    email = emailFieldAdmin.getText();
                    password = passwordFieldAdmin.getText();
                    age = Integer.parseInt(ageFieldAdmin.getText().trim());
                    int adminID = Integer.parseInt(adminIDField.getText().trim());

                    Admin newAdmin = new Admin(name, age, adminID, email, password);
                    successfulSignUpLabelAdmin.setText("Successful Administrator Registration! Welcome " + name + "!");

//                    successfulSignUpLabel.setVisible(true);

                    // TODO: Error handling for incorrect user inputs
                }
            }



        }


    }


