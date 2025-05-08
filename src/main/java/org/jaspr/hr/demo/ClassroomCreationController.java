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
        int ClassroomNumber = Integer.parseInt(classroomNumber.getText().trim());
        int ClassroomCapacity = Integer.parseInt(classroomCapacity.getText().trim());

        // Creates new classroom object
        Classroom classroom = new Classroom(ClassroomNumber, ClassroomCapacity);
        userDAO.createClassroom(ClassroomNumber, ClassroomCapacity);

        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "admin-classroom-view.fxml");

    }



    }


