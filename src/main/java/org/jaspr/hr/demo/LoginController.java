package org.jaspr.hr.demo;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private TextField loginEmailField;
    @FXML
    private TextField loginPasswordField;
    @FXML
    private Label loginEmptyError;

    @FXML
    private void onLoginClicked(){
        String email = loginEmailField.getText().trim();
        String password = loginPasswordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()){
            loginEmptyError.setText("Please enter both an email and a password");
            loginEmptyError.setVisible(true);
            return;
        }

        String role = userDAO.Authenticate(email, password);

    }

}
