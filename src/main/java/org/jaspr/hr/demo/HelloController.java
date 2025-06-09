package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for the initial welcome screen of the AcademIQ application.
 * Handles logic for enabling/disabling login, and routing to sign-up or login screens.
 */
public class HelloController {
    @FXML
    private Button signUpButton;
    @FXML
    private Button loginButton;

    /** DAO for user related queries*/
    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    /**
     * Called automatically by JavaFX after the FXML has been loaded.
     * Disables the login button if no users are registered.
     */
    @FXML
    public void initialize() {
        if (!userDAO.hasAnyRegisteredUsers()) {
            // Disable login if no users exist
            loginButton.setDisable(true);
        } else {
            // Enable login if users are present
            loginButton.setDisable(false);
        }
    }

    /**
     * Handles the login button click event.
     * Switches the scene to the login screen.
     * @throws IOException if the login FXML file cannot be loaded
     */
    @FXML
    protected void onLoginButtonClick() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "login-view.fxml");
    }

    /**
     * Handles the sign-up button click event.
     * Switches the scene to the register screen.
     * @throws IOException if the register FXML file cannot be loaded
     */
    @FXML
    protected void onSignUpButtonClick() throws IOException {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "register-view.fxml");
    }



}
