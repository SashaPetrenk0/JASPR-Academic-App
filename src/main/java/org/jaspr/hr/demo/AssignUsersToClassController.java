package org.jaspr.hr.demo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.util.StringConverter;

import javax.xml.transform.Result;

public class AssignUsersToClassController {

    @FXML
    private ComboBox<Classroom> classroomComboBox;

    @FXML
    private VBox teacherRadioList;

    @FXML
    private VBox studentVBox;

    @FXML
    private Button assignUsers;

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

    private final ToggleGroup teacherToggleGroup = new ToggleGroup();

    @FXML
    private void initialize() {

        System.out.println("AssignUsersToClassController initialized");
        classroomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("classRoomNumber"));
        classroomCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("classRoomCapacity"));
        numStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("numStudents"));
        numTeachersColumn.setCellValueFactory(new PropertyValueFactory<>("numTeachers"));

        // Loading classrooms from the DAO
        ObservableList<Classroom> classrooms = userDAO.getAllClassrooms();
        classroomTable.setItems(classrooms);

        // Set classrooms in ComboBox
        classroomComboBox.setItems(classrooms);  // Use a StringConverter to control what is displayed in the ComboBox
        classroomComboBox.setCellFactory(param -> new ListCell<Classroom>() {
            @Override
            protected void updateItem(Classroom item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getClassRoomNumber() + " - " + item.getClassRoomCapacity() + " seats");
                } else {
                    setText(null);
                }
            }
        });

        classroomComboBox.setConverter(new StringConverter<Classroom>() {
            @Override
            public String toString(Classroom classroom) {
                if (classroom == null) {
                    return null;
                } else {
                    return classroom.getClassRoomNumber() + " - " + classroom.getClassRoomCapacity() + " seats";
                }
            }

            @Override
            public Classroom fromString(String string) {
                return null;  // Not needed for this case
            }
        });
        // Get all teachers
        ObservableList<Teacher> teachers = userDAO.getAllTeachers();
        System.out.println("Number of teachers loaded: " + teachers.size());

        for (Teacher teacher : teachers) {
            RadioButton rb = new RadioButton(teacher.getName());
            rb.setUserData(teacher);
            rb.setToggleGroup(teacherToggleGroup);
            teacherRadioList.getChildren().add(rb);
        }

        List<Student> students = userDAO.getAllStudents(); // userDAO = your SqliteUserDAO instance

        for (Student student : students) {
            CheckBox checkBox = new CheckBox(student.getName() + " (ID: " + student.getStudentID() + ")");
            checkBox.setUserData(student); // store full student object if you need it later
            studentVBox.getChildren().add(checkBox);
            System.out.println(student.getName());
        }
    }

    @FXML
    private void assignUsers() {
        Classroom selectedClassroom = classroomComboBox.getValue();

        RadioButton selectedTeacherRadioButton = (RadioButton) teacherToggleGroup.getSelectedToggle();
        Teacher selectedTeacher = (Teacher) selectedTeacherRadioButton.getUserData();

        // Get selected students
        List<CheckBox> checkboxes = new ArrayList<>();
        for (Node node : studentVBox.getChildren()) {
            if (node instanceof CheckBox) {
                checkboxes.add((CheckBox) node); // Add CheckBox to the list
            }
        }

        // List to store selected students
        List<Student> selectedStudents = new ArrayList<>();
        for (CheckBox checkBox : checkboxes) {
            if (checkBox.isSelected()) {
                selectedStudents.add((Student) checkBox.getUserData()); // Get the student object associated with the CheckBox
            }
        }


        // Call the DAO to assign the users (teacher and students) to the classroom
        boolean assignmentSuccess = userDAO.assignUsers(selectedClassroom, selectedTeacher, selectedStudents);

        // Display success message if assignment was successful
        if (assignmentSuccess) {
            // You could print to the console, or use an Alert box to notify the user
            System.out.println("Teacher and students have been successfully assigned to classroom");
            // Update the table view to reflect the changes
            ObservableList<Classroom> updatedClassrooms = userDAO.getUpdatedClassrooms();
            classroomTable.setItems(updatedClassrooms); // Set updated classrooms to the table
        }
    }

        @FXML
        private void returnToAdmin () throws IOException {
            Stage stage = (Stage) returnToPrevious.getScene().getWindow();
            SceneChanger.changeScene(stage, "admin-dashboard-view.fxml");
        }


    }

