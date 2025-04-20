package org.jaspr.hr.demo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private VBox studentForm, teacherForm, parentForm, adminForm;

    public void initialise(){
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
