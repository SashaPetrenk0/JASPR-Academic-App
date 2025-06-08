package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import java.io.IOException;
import javafx.scene.Parent;

/**
 * Controller class for handling user password changes.
 * Supports multiple user roles including Student, Teacher, and Admin.
 */
public class ChangePassword {

    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private TextField emailField;

    @FXML
    private TextField currentPasswordField;

    @FXML
    private TextField newPasswordField;

    @FXML
    private Label ChangePwdError;

    @FXML
    private Button ReturnButton;

    private Object currentUser;

    /**
     * Sets the current logged-in user object
     * @param user the current user object (Student, Teacher, Admin)
     */
    public void setCurrentUser(Object user) {
        this.currentUser = user;

    }

    /**
     * Determines the role of the current user.
     * @return a string representing the user's role or null if unknown
     */
    public String getRole() {
        if (currentUser instanceof Student) {
            return "Student";
        } else if (currentUser instanceof Teacher) {
            return "Teacher";
        } else if (currentUser instanceof Admin) {
            return "Admin";
        } else if (currentUser instanceof Parent) {
            return "Parent";
        }
        return null;
    }

    /**
     * Handles the password change action triggered by the user.
     * Validates input, hashes passwords with salt, and attempts to update the password in the database.
     * Displays relevant success or error messages.
     */
    public void onChangePasswordClicked() {
        String role = getRole();
        String email = emailField.getText();
        String currentPwd = currentPasswordField.getText();
        String newPwd = newPasswordField.getText();

        String user_salt = userDAO.getSalt(email);
        String hashedCurredPwd = PasswordUtility.hashPassword(currentPwd, user_salt);
        String hashedNewPwd = PasswordUtility.hashPassword(newPwd, user_salt);

        // Validate input fields
        if (email == null || email.isEmpty() || currentPwd == null || currentPwd.isEmpty() || newPwd == null || newPwd.isEmpty()) {
            ChangePwdError.setText("Please fill all fields.");
            ChangePwdError.setVisible(true);
        }
        if (user_salt == null) {
            ChangePwdError.setText("Email is incorrect or does not exist.");
            ChangePwdError.setVisible(true);
            return;
        }

        // Add inputted fields to database
        boolean success = userDAO.changePassword(email, hashedCurredPwd, hashedNewPwd, role);

        if (success) {
            // After successful password change, fetch the updated user data
            refreshCurrentUser();
            ChangePwdError.setText("Password Successfully Changed!");
            ChangePwdError.setVisible(true);
        } else {
            ChangePwdError.setText("Error changing password. Please confirm your details are correct.");
            ChangePwdError.setVisible(true);
        }

    }

    /**
     * Refreshes the currentUser object by fetching the latest user data from the database, based on role
     */
    private void refreshCurrentUser() {
        String role = getRole();

        // Fetch updated student info
        if ("Student".equals(role)) {
            Student student = (Student) currentUser;
            student = userDAO.getStudent(student.getStudentID());
            setCurrentUser(student);
        }
        // Fetch updated teacher info
        else if ("Teacher".equals(role)) {
            Teacher teacher = (Teacher) currentUser;
            teacher = userDAO.getTeacher(teacher.getTeacherID());
            setCurrentUser(teacher);
        }
        // Fetch updated admin info
        else if ("Admin".equals(role)) {
            Admin admin = (Admin) currentUser;
            admin = userDAO.getAdmin(admin.getAdminID());
            setCurrentUser(admin);
        } else {
            ChangePwdError.setText("Unknown role.");
            ChangePwdError.setVisible(true);
        }
    }

    /**
     * Handles the return button action, navigating the user back to the profile view.
     * @throws IOException if fxml fails to load
     */
    @FXML
    private void onReturnClicked() throws IOException {

        String role = getRole();
        if ("Student".equals(role) || "Teacher".equals(role)|| "Admin".equals(role)){

            // Load the profile-view.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jaspr/hr/demo/profile-view.fxml"));
            javafx.scene.Parent root = loader.load();  // This will return javafx.scene.Parent

            // Get the ProfileController
            ProfileController profileController = loader.getController();

            // Pass the current user to the controller
            profileController.setCurrentUser(currentUser);
            Stage stage = (Stage) ReturnButton.getScene().getWindow();
            stage.setScene(new Scene(root, SceneChanger.WIDTH, SceneChanger.HEIGHT));
            stage.show();
        }
    }
}



