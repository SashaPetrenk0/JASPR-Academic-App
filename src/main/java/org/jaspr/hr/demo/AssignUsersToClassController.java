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
    private Button returnToPrevious;

    @FXML
    private Label statusLabel;

    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    private final ToggleGroup teacherToggleGroup = new ToggleGroup();

    @FXML
    private void initialize() {

        // Loading classrooms from the DAO
        ObservableList<Classroom> classrooms = userDAO.getUpdatedClassrooms();


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

        if (selectedClassroom == null) {
            statusLabel.setText("Please select a classroom");
        }

        RadioButton selectedTeacherRadioButton = (RadioButton) teacherToggleGroup.getSelectedToggle();
        Teacher selectedTeacher = null;
        if (selectedTeacherRadioButton != null) {
            selectedTeacher = (Teacher) selectedTeacherRadioButton.getUserData();
        }
        // Get selected students
        List<CheckBox> checkboxes = new ArrayList<>();
        for (Node node : studentVBox.getChildren()) {
            if (node instanceof CheckBox) {
                checkboxes.add((CheckBox) node); // Add CheckBox to the list
            }
        }

        List<Student> selectedStudents = new ArrayList<>();
        for (CheckBox checkBox : checkboxes) {
            if (checkBox.isSelected()) {
                selectedStudents.add((Student) checkBox.getUserData());
            }
        }

        // At least one of teacher or student must be selected
        if (selectedTeacher == null && selectedStudents.isEmpty()) {
            statusLabel.setText("Please select at least one teacher or student to assign");
            return;
        }

        if (selectedTeacher != null) {
            Integer existingTeacherID = userDAO.getAssignedTeacherId(selectedClassroom.getClassRoomNumber());
            if (existingTeacherID != null && existingTeacherID != 0) {
                statusLabel.setText("This classroom already has a teacher assigned.");
                return;
            }
        }

        // Call the DAO to assign the users (teacher and students) to the classroom
        boolean assignmentSuccess = userDAO.assignUsers(selectedClassroom, selectedTeacher, selectedStudents);

        // Display success message if assignment was successful
        if (assignmentSuccess) {
            statusLabel.setStyle("-fx-text-fill: green;");
            statusLabel.setText("Users successfully assigned to classroom.");

            teacherToggleGroup.selectToggle(null);

            for (CheckBox checkBox : checkboxes) {
                checkBox.setSelected(false);
            }
            Stage stage = (Stage) assignUsers.getScene().getWindow();
            SceneChanger.changeScene(stage, "admin-classroom-view.fxml");

        } else {
            statusLabel.setStyle("-fx-text-fill: red;");
            statusLabel.setText("An error occurred while assigning users.");
        }
    }
        @FXML
        private void returnToClassroom() throws IOException {
            Stage stage = (Stage) returnToPrevious.getScene().getWindow();
            SceneChanger.changeScene(stage, "admin-classroom-view.fxml");
        }


    }

