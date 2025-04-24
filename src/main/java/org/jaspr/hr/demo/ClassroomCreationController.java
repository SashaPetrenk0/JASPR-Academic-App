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
    private TableColumn<Classroom, Integer> classroomNumberColumn;

    @FXML
    private TableColumn<Classroom, Integer> classroomCapacityColumn;

    @FXML
    private TableColumn<Classroom, Integer> numTeachersColumn;

    @FXML
    private TableColumn<Classroom, Integer> numStudentsColumn;

    @FXML
    private TableView<Classroom> classroomTable;

    @FXML
    private void initialize() {
        classroomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("classroomNumber"));
        classroomCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));
        numStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("numStudents"));
        numTeachersColumn.setCellValueFactory(new PropertyValueFactory<>("numTeachers"));

        // Loading classrooms from the DAO
        ObservableList<Classroom> classrooms = userDAO.getAllClassrooms();
        classroomTable.setItems(classrooms);
    }


    @FXML
    private void returnToAdmin() throws IOException{
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "admin-dashboard-view.fxml");
    }

    @FXML
    private void createClassroom(){
        int ClassroomNumber = Integer.parseInt(classroomNumber.getText().trim());
        int ClassroomCapacity = Integer.parseInt(classroomCapacity.getText().trim());

        // Creates new classroom object
        Classroom classroom = new Classroom(ClassroomNumber, ClassroomCapacity);

        // Adds classroom to database
        userDAO.createClassroom(ClassroomNumber, ClassroomCapacity);
    }



    }






}
