package org.jaspr.hr.demo;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller class for creating new classrooms.
 * Handles form input validation and integrates with SqliteClassroomDAO to persist classrooms.
 */
public class ClassroomCreationController {

    /** Data Access Object for classroom related queries */
    private final SqliteClassroomDAO classroomDAO = new SqliteClassroomDAO();

    @FXML
    private TextField classroomNumber;

    @FXML
    private TextField classroomCapacity;

    @FXML
    private Button returnToPrevious;

    @FXML
    private Label statusLabel;

    /**
     * Navigates back to the admin classroom overview screen.
     * @throws IOException if the FXML file cannot be loaded
     */
    @FXML
    private void returnToClassrooms() throws IOException{
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "admin-classroom-view.fxml");
    }

    @FXML
    private void createClassroom() {
        String numberText = classroomNumber.getText().trim();
        String capacityText = classroomCapacity.getText().trim();

        if (!validateRequiredFields(numberText, capacityText)) return;

        Integer classroomNumberValue = parseNumber(numberText, "classroom number");
        Integer classroomCapacityValue = parseNumber(capacityText, "classroom capacity");

        if (classroomNumberValue == null || classroomCapacityValue == null) return;

        if (!validateClassroomConstraints(classroomNumberValue, classroomCapacityValue)) return;

        if (createAndSaveClassroom(classroomNumberValue, classroomCapacityValue)) {
            showSuccessAndRedirect();
        } else {
            statusLabel.setText("Failed to create classroom. Please try again.");
        }
    }

    private boolean validateRequiredFields(String numberText, String capacityText) {
        if (numberText.isEmpty() || capacityText.isEmpty()) {
            statusLabel.setText("Please fill in both the classroom number and capacity.");
            return false;
        }
        return true;
    }

    private Integer parseNumber(String text, String fieldName) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a valid number for " + fieldName + ".");
            return null;
        }
    }

    private boolean validateClassroomConstraints(int number, int capacity) {
        if (capacity > 40) {
            statusLabel.setText("Classroom capacity cannot exceed 40 students.");
            return false;
        }
        if (classroomDAO.classroomNumberExists(number)) {
            statusLabel.setText("A classroom with this number already exists.");
            return false;
        }
        return true;
    }

    private boolean createAndSaveClassroom(int number, int capacity) {
        return classroomDAO.createClassroom(number, capacity);
    }

    private void showSuccessAndRedirect() {
        statusLabel.setStyle("-fx-text-fill: green;");
        statusLabel.setText("Classroom created successfully!");
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "admin-classroom-view.fxml");
    }


}


