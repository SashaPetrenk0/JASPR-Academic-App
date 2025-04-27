package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.io.IOException;
import javafx.scene.Parent;

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

    public void setCurrentUser(Object user) {
        this.currentUser = user;

    }

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


    public void onChangePasswordClicked() {
        String email = emailField.getText();
        String currentPwd = currentPasswordField.getText();
        String newPwd = newPasswordField.getText();
        String role = getRole();

        if (email == null || email.isEmpty() || currentPwd == null || currentPwd.isEmpty() || newPwd == null || newPwd.isEmpty()) {
            ChangePwdError.setText("Please fill all fields.");
            ChangePwdError.setVisible(true);
        } else {
            boolean success = userDAO.changePassword(email, currentPwd, newPwd, role);

            if (success) {
                // After successful password change, fetch the updated user data
                refreshCurrentUser();

                // Update currentUser immediately after password change
                ChangePwdError.setText("Password Successfully Changed!");
                ChangePwdError.setVisible(true);
            } else {
                ChangePwdError.setText("Error changing password. Please confirm your details are correct.");
                ChangePwdError.setVisible(true);
            }
        }
    }

    private void refreshCurrentUser() {
        String role = getRole();
        if ("Student".equals(role)) {
            Student student = (Student) currentUser;
            student = userDAO.getStudent(student.getStudentID()); // Fetch updated student info
            setCurrentUser(student); // Set the updated student info
        } else if ("Teacher".equals(role)) {
            Teacher teacher = (Teacher) currentUser;
            teacher = userDAO.getTeacher(teacher.getTeacherID()); // Fetch updated teacher info
            setCurrentUser(teacher); // Set the updated teacher info
        } else if ("Admin".equals(role)) {
            Admin admin = (Admin) currentUser;
            admin = userDAO.getAdmin(admin.getAdminID()); // Fetch updated admin info
            setCurrentUser(admin); // Set the updated admin info
        } else {
            // Fallback, handle unknown role
            ChangePwdError.setText("Unknown role.");
            ChangePwdError.setVisible(true);
        }
    }
    @FXML
    private void onReturnClicked() throws IOException {

        String role = getRole();
        if ("Student".equals(role)) {

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
//TODO: add this for teacher and admin
    }
}



