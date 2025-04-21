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

    // ... other methods

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
