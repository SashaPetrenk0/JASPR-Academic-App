package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Controller class for handling login interactions for all user roles.
 * Supports authentication via the SqliteUserDAO and role-based redirection.
 */
public class LoginController {

    /** DAO for all user related queries */
    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private TextField loginEmailField;
    @FXML
    private TextField loginPasswordField;
    @FXML
    private Label loginIncorrectError;
    @FXML
    private Button LoginButton;
    @FXML
    private Button ReturnButton;

    /**
     * Initializes the login form fields and sets styles for error labels.
     */
    @FXML
    private void initialize() {
        loginEmailField.setFocusTraversable(false);
        loginPasswordField.setFocusTraversable(false);
        loginIncorrectError.setStyle("-fx-font-family: 'Telugu Sangam MN'; -fx-font-size: 12px; -fx-text-fill: #d9534f;");
    }

    /**
     * Handles login button click. Validates input, hashes password, authenticates role,
     * and redirects to the appropriate dashboard view.
     */
    @FXML
    private void onLoginClicked(){

        String email = loginEmailField.getText().trim();
        String password = loginPasswordField.getText().trim();
        String user_salt = userDAO.getSalt(email);

        // Error Handle: Enter both fields
        if (email.isEmpty() || password.isEmpty()){
            showError(loginIncorrectError, "Please enter both a valid Email and a Password");
            return;
        }


        // Error Handle: Email not found
        if (user_salt == null) {
            showError(loginIncorrectError, "Login information incorrect. Please try again.");
            return;
        }
        String hashedPwd = PasswordUtility.hashPassword(password, user_salt);


        // Calls authentication method and determines role of successfully logged in user
        String role = userDAO.Authenticate(email, hashedPwd);

        // If no matches for role
        if (role == null){
            showError(loginIncorrectError, "Login information incorrect. Please try again.");
            return;
        }

        // Handle successful login based on user role
        handleLoginSuccess(role, email, hashedPwd);

    }

    /**
     * Shows the specified error message in the given label.
     * @param label the label to display the error
     * @param message the error message to display
     */
    private void showError(Label label, String message) {
        label.setText(message);
        label.setVisible(true);
    }

    /**
     * Handles login success by creating user sessions and redirecting
     * to the appropriate dashboard based on the role.
     * @param role the role returned from authentication
     * @param email the user's email
     * @param hashedPassword the hashed password used for login
     */
    private void handleLoginSuccess(String role, String email, String hashedPassword) {
        Stage stage = (Stage) LoginButton.getScene().getWindow();

        switch (role) {
            case "Student":
                Student loggedInStudent = userDAO.getLoggedInStudent(email, hashedPassword);
                UserSession.getInstance().setCurrentUser(loggedInStudent, role);
                SceneChanger.changeScene(stage, "student-dashboard-view.fxml");
                break;

            case "Teacher":
                Teacher loggedInTeacher = userDAO.getLoggedInTeacher(email, hashedPassword);
                UserSession.getInstance().setCurrentUser(loggedInTeacher, role);
                System.out.println("Teacher successfully logged in");
                SceneChanger.changeScene(stage, "teacher-dashboard-view.fxml");
                break;

            case "Admin":
                Admin loggedInAdmin = userDAO.getLoggedInAdmin(email, hashedPassword);
                UserSession.getInstance().setCurrentUser(loggedInAdmin, role);
                System.out.println("Admin successfully logged in");
                SceneChanger.changeScene(stage, "admin-dashboard-view.fxml");
                break;

            default:
                System.err.println("Unhandled role: " + role);
                showError(loginIncorrectError, "Unrecognized user role.");
                break;
        }
    }

    /**
     * Handles return button click. Navigates back to the welcome screen.
     */
    @FXML
    private void onReturnClicked() {
        Stage stage = (Stage) ReturnButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "hello-view.fxml");
    }

}
