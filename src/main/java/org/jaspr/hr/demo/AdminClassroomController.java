package org.jaspr.hr.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminClassroomController {

    @FXML
    private Button AssignButton;

    @FXML
    private Button ClassRoomCreation;

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

    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private void initialize() {
        classroomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("classRoomNumber"));
        classroomCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("classRoomCapacity"));
        numStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("numStudents"));
        numTeachersColumn.setCellValueFactory(new PropertyValueFactory<>("numTeachers"));

        // Loading classrooms from the DAO
        ObservableList<Classroom> classrooms = userDAO.getUpdatedClassrooms();
        classroomTable.setItems(classrooms);
    }

    @FXML
    protected void onCreateClassroomClick() throws IOException {
        Stage stage = (Stage) ClassRoomCreation.getScene().getWindow();
        SceneChanger.changeScene(stage, "classroom-creation-view.fxml");
    }

    @FXML
    protected void onAssignUsersClick() throws IOException{
        Stage stage = (Stage) AssignButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "assign-users-to-classroom.fxml");
    }
}
