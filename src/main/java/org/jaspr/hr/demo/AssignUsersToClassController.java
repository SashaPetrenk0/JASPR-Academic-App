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

public class AssignUsersToClassController {

    @FXML
    private VBox teacherRadioList;

    @FXML
    private ToggleGroup teacherToggleGroup;

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
    private Button returnToPrevious;

    private final SqliteUserDAO userDAO = new SqliteUserDAO();


    @FXML
    private void initialize() {

        classroomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("classRoomNumber"));
        classroomCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("classRoomCapacity"));
        numStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("numStudents"));
        numTeachersColumn.setCellValueFactory(new PropertyValueFactory<>("numTeachers"));

        // Loading classrooms from the DAO
        ObservableList<Classroom> classrooms = userDAO.getAllClassrooms();
        classroomTable.setItems(classrooms);

        // Get all teachers
        ObservableList<Teacher> teachers = userDAO.getAllTeachers();

        for (Teacher teacher : teachers){
            RadioButton rb = new RadioButton(teacher.getName());
            rb.setUserData(teacher);
            rb.setToggleGroup(teacherToggleGroup);
            teacherRadioList.getChildren().add(rb);
        }
    }

    @FXML
    private void returnToAdmin() throws IOException{
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "admin-dashboard-view.fxml");
    }


}
