package org.jaspr.hr.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The controller for the Admin Classroom Management Menu
 * Handles initialisation of assignment table and navigation to classroom creation and assigning actions
 */
public class AdminClassroomController {

    @FXML
    private Button AssignButton;

    @FXML
    private Button ClassRoomCreation;

    @FXML
    private Button returnToDashboard;

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


    /**
     * Data Access Object for user-related queries
     */
    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    /**
     * Data Access Object for classroom related queries
     */
    private final SqliteClassroomDAO classroomDAO = new SqliteClassroomDAO();

    /**
     * Initialises the controller after FXML elements are loaded
     * Sets up table columns and loads classroom data into the table
     */
    @FXML
    private void initialize() {
        setUpTableColumns();
        loadClassrooms();
    }

    /**
     * Configures the TableView columns with property bindings
     */
    private void setUpTableColumns(){
        classroomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("classRoomNumber"));
        classroomCapacityColumn.setCellValueFactory(new PropertyValueFactory<>("classRoomCapacity"));
        numStudentsColumn.setCellValueFactory(new PropertyValueFactory<>("numStudents"));
        numTeachersColumn.setCellValueFactory(new PropertyValueFactory<>("numTeachers"));
    }

    /**
     * Loads classroom data from the database and dispays it in the Table View
     */
    private void loadClassrooms(){
        // Loading classrooms from the DAO
        ObservableList<Classroom> classrooms = classroomDAO.getUpdatedClassrooms();
        classroomTable.setItems(classrooms);
    }

    /**
     * Navigates to the classroom creation view when the ClassRoomCreation button is clicked
     * @throws IOException if the fxml file cannot be loaded
     */
    @FXML
    private void onCreateClassroomClick() throws IOException {
        Stage stage = (Stage) ClassRoomCreation.getScene().getWindow();
        SceneChanger.changeScene(stage, "classroom-creation-view.fxml");
    }

    /**
     * Navigates to the assignment view when the AssignButton button is clicked
     * @throws IOException when fxml file cannot be loaded
     */
    @FXML
    private void onAssignUsersClick() throws IOException{
        Stage stage = (Stage) AssignButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "assign-users-to-classroom.fxml");
    }

    /**
     * Navigates back to the admin dashboard view when returnToDashboard button is cicked
     * @throws IOException if the fxml file cannot be loaded
     */
    @FXML
    private void returnToDashboard() throws IOException{
        Stage stage = (Stage) returnToDashboard.getScene().getWindow();
        SceneChanger.changeScene(stage, "admin-dashboard-view.fxml");
    }
}
