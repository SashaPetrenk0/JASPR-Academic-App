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



}
