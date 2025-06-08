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

    /**
     * Called when the "Create Classroom" button is clicked.
     * Coordinates validation, classroom creation, and success/failure feedback.
     */
    @FXML
    private void createClassroom() {
        String numberText = classroomNumber.getText().trim();
        String capacityText = classroomCapacity.getText().trim();

        // Check if required fields are empty
        if (!validateRequiredFields(numberText, capacityText)) return;

        // Parse input strings to integers
        Integer classroomNumberValue = parseNumber(numberText, "classroom number");
        Integer classroomCapacityValue = parseNumber(capacityText, "classroom capacity");

        // Abort if parsing failed
        if (classroomNumberValue == null || classroomCapacityValue == null) return;

        // Check classroom-specific business constraints
        if (!validateClassroomConstraints(classroomNumberValue, classroomCapacityValue)) return;

        // Attempt to create the classroom
        if (createAndSaveClassroom(classroomNumberValue, classroomCapacityValue)) {
            showSuccessAndRedirect();
        } else {
            statusLabel.setText("Failed to create classroom. Please try again.");
        }
    }

    /**
     * Checks if input fields are empty.
     * @param numberText the classroom number input
     * @param capacityText the classroom capacity input
     * @return true if both inputs are filled; false otherwise
     */
    private boolean validateRequiredFields(String numberText, String capacityText) {
        if (numberText.isEmpty() || capacityText.isEmpty()) {
            statusLabel.setText("Please fill in both the classroom number and capacity.");
            return false;
        }
        return true;
    }

    /**
     * Attempts to parse a string into an integer, showing an error if it fails.
     * @param text the input text to parse
     * @param fieldName the name of the field for error display
     * @return the parsed integer or null if parsing fails
     */
    private Integer parseNumber(String text, String fieldName) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            statusLabel.setText("Please enter a valid number for " + fieldName + ".");
            return null;
        }
    }

    /**
     * Applies business rules related to classroom creation (e.g. max size, uniqueness).
     * @param number the classroom number
     * @param capacity the classroom capacity
     * @return true if constraints are satisfied; false otherwise
     */
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

    /**
     * Persist the new classroom via the classroom DAO.
     * @param number the classroom number
     * @param capacity the classroom capacity
     * @return true if successful; false otherwise
     */
    private boolean createAndSaveClassroom(int number, int capacity) {
        return classroomDAO.createClassroom(number, capacity);
    }

    /**
     * Shows success feedback and navigates to the classroom overview screen.
     */
    private void showSuccessAndRedirect() {
        statusLabel.setStyle("-fx-text-fill: green;");
        statusLabel.setText("Classroom created successfully!");
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "admin-classroom-view.fxml");
    }


}


