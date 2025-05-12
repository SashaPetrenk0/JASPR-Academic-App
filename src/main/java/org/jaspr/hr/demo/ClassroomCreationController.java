package org.jaspr.hr.demo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.io.IOException;
import java.sql.PreparedStatement;

import javafx.fxml.FXML;

import javax.xml.transform.Result;

public class ClassroomCreationController {
    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private TextField classroomNumber;

    @FXML
    private TextField classroomCapacity;

    @FXML
    private Button returnToPrevious;

    @FXML
    private Label statusLabel;


    @FXML
    private void initialize() {
        // Loading classrooms from the DAO
        ObservableList<Classroom> classrooms = userDAO.getUpdatedClassrooms();
    }


    @FXML
    private void returnToClassrooms() throws IOException{
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "admin-classroom-view.fxml");
    }

    @FXML
    private void createClassroom(){

        String numberText = classroomNumber.getText().trim();
        String capacityText = classroomCapacity.getText().trim();
        // If either text field is empty
        if (numberText.isEmpty() || capacityText.isEmpty()) {
            statusLabel.setText("Please fill in both the classroom number and capacity.");
            return;
        }

        int ClassroomNumber;
        int ClassroomCapacity;

        // If inputted values are not integers
        try{
            ClassroomNumber = Integer.parseInt(numberText);
            ClassroomCapacity = Integer.parseInt(capacityText);
        } catch (NumberFormatException e){
            statusLabel.setText("Please enter valid numbers for both fields");
            return;
        }
        // If inputted classroom capacity exceeds 40 students
        if(ClassroomCapacity > 40){
            statusLabel.setText("Classroom capacity cannot exceed 40 students");
            return;
        }

        if (userDAO.classroomNumberExists(ClassroomNumber)) {
            statusLabel.setText("A classroom with this number already exists.");
            return;
        }

        boolean created = userDAO.createClassroom(ClassroomNumber, ClassroomCapacity);
        // Creates new classroom object
        Classroom classroom = new Classroom(ClassroomNumber, ClassroomCapacity);
        if(created){
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Classroom created successfully!");

            Stage stage = (Stage) returnToPrevious.getScene().getWindow();
            SceneChanger.changeScene(stage, "admin-classroom-view.fxml");
        }
        else{
            statusLabel.setText("Failed to create classroom. Please try again.");
        }



    }



    }


