package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {


    @FXML
    private Button signUpButton;
    @FXML
    private Button loginButton;

    private final IUserDAO userDAO = new SqliteUserDAO();

    @FXML
    public void initialize() {
        if (!userDAO.hasAnyRegisteredUsers()) {
            loginButton.setDisable(true);   // Keep it disabled
        } else {
            loginButton.setDisable(false);  // Enable login
        }
    }

    @FXML
    protected void onLoginButtonClick() throws IOException {
        Stage stage = (Stage) loginButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "login-view.fxml");
    }

    @FXML
    protected void onSignUpButtonClick() throws IOException {
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "register-view.fxml");
    }



}
