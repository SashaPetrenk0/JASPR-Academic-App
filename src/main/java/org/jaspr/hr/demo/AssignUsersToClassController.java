package org.jaspr.hr.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.StringConverter;

/**
 * Controller for assigning teachers and students to a selected classroom.
 * Loads all teachers and students from the database and allows the admin to assign them to a selected classroom.
 */
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

    /** Data Access Object for user related queries */
    private final SqliteUserDAO userDAO = new SqliteUserDAO();
    /** Data Access Object for classroom related queries */
    private final SqliteClassroomDAO classroomDAO = new SqliteClassroomDAO();

    private final ToggleGroup teacherToggleGroup = new ToggleGroup();

    /**
     * Initialises the controller after its root element has been processed
     * Loads classrooms, teachers, and students into their respective UI controls.
     */
    @FXML
    private void initialize() {
        loadClassrooms();
        loadTeachers();
        loadStudents();
    }

    /**
     * Loads available classrooms into the combo box and configures the display format.
     */
    private void loadClassrooms(){
        // Loading classrooms from the DAO
        ObservableList<Classroom> classrooms = classroomDAO.getUpdatedClassrooms();

        // Set classrooms in ComboBox
        classroomComboBox.setItems(classrooms);
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
                return null;
            }
        });
    }

    /**
     * Loads all teachers and displays them as radio buttons.
     */
    private void loadTeachers(){
        // Get all teachers
        ObservableList<Teacher> teachers = userDAO.getAllTeachers();
        System.out.println("Number of teachers loaded: " + teachers.size());

        for (Teacher teacher : teachers) {
            RadioButton rb = new RadioButton(teacher.getName());
            rb.setUserData(teacher);
            rb.setToggleGroup(teacherToggleGroup);
            teacherRadioList.getChildren().add(rb);
        }
    }

    /**
     * Loads all students and displays them as checkboxes.
     */
    private void loadStudents(){
        List<Student> students = userDAO.getAllStudents(); // userDAO = your SqliteUserDAO instance

        for (Student student : students) {
            CheckBox checkBox = new CheckBox(student.getName() + " (ID: " + student.getStudentID() + ")");
            checkBox.setUserData(student); // store full student object if you need it later
            studentVBox.getChildren().add(checkBox);
            System.out.println(student.getName());
        }
    }

    /**
     * Handles assignment of selected teacher and students to the selected classroom.
     * Shows appropriate messages if selection or assignment fails (error handling).
     */
    @FXML
    private void assignUsers() {
        Classroom selectedClassroom = classroomComboBox.getValue();

        // Error Handling: No classroom selected
        if (selectedClassroom == null) {
            statusLabel.setText("Please select a classroom");
            return;
        }

        // Retrieve selected teacher
        RadioButton selectedTeacherRadioButton = (RadioButton) teacherToggleGroup.getSelectedToggle();
        Teacher selectedTeacher = null;
        if (selectedTeacherRadioButton != null) {
            selectedTeacher = (Teacher) selectedTeacherRadioButton.getUserData();
        }
        // Collects checkboxes inside studentVBox
        List<CheckBox> checkboxes = new ArrayList<>();
        for (Node node : studentVBox.getChildren()) {
            if (node instanceof CheckBox) {
                checkboxes.add((CheckBox) node);
            }
        }

        // Retrieve selected students
        List<Student> selectedStudents = new ArrayList<>();
        for (CheckBox checkBox : checkboxes) {
            if (checkBox.isSelected()) {
                selectedStudents.add((Student) checkBox.getUserData());
            }
        }

        // Error Handling: At least one teacher or one student must be selected
        if (selectedTeacher == null && selectedStudents.isEmpty()) {
            statusLabel.setText("Please select at least one teacher or student to assign");
            return;
        }

        // Error Handling: Check that the classroom does not already have an assigned teacher
        if (selectedTeacher != null) {
            Integer existingTeacherID = classroomDAO.getAssignedTeacherId(selectedClassroom.getClassRoomNumber());
            if (existingTeacherID != null && existingTeacherID != 0) {
                statusLabel.setText("This classroom already has a teacher assigned.");
                return;
            }
        }

        // Call the DAO to assign the users (teacher and students) to the classroom
        boolean assignmentSuccess = classroomDAO.assignUsers(selectedClassroom, selectedTeacher, selectedStudents);

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

    /**
     * Returns to the admin classroom view without making any assignments.
     * @throws IOException if the fxml cannot be loaded
     */
        @FXML
        private void returnToClassroom() throws IOException {
            Stage stage = (Stage) returnToPrevious.getScene().getWindow();
            SceneChanger.changeScene(stage, "admin-classroom-view.fxml");
        }


    }

