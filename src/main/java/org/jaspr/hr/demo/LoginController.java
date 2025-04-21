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
    private Label loginIncorrectError;
    @FXML
    private Button LoginButton;

    @FXML
    private void onLoginClicked(){
        String email = loginEmailField.getText().trim();
        String password = loginPasswordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()){
            loginEmptyError.setText("Please enter both an email and a password");
            loginEmptyError.setVisible(true);
            return;
        }
        // Calls authentication method
        String role = userDAO.Authenticate(email, password);

        // If no matches
        if (role == null){
            loginIncorrectError.setText("Login information incorrect. Please try again.");
            loginIncorrectError.setVisible(true);
            return;
        }
        // If "Student" role returned
        if ("Student".equals(role)){
            System.out.println("Student successfully logged in");
            // Change scene to student dashboard
            // TODO: Whoever is doing the dasboard pages uncomment below and replace INSERT FXML HERE with student dasboard
//            Stage stage = (Stage) LoginButton.getScene().getWindow();
//            SceneChanger.changeScene(stage, "INSERT FXML FILE HERE e.g.student-dashboard-view");
        }

        // If "Teacher" role returned
        if ("Teacher".equals(role)){
            System.out.println("Teacher successfully logged in");
            // Change scene to student dashboard
            // TODO: Whoever is doing the dasboard pages uncomment below and replace INSERT FXML HERE with teacher dashboard
//            Stage stage = (Stage) LoginButton.getScene().getWindow();
//            SceneChanger.changeScene(stage, "INSERT FXML FILE HERE e.g.teacher-dashboard-view");
        }

        // If "Parent" role returned
        if ("Parent".equals(role)){
            System.out.println("Parent successfully logged in");
            // Change scene to student dashboard
            // TODO: Whoever is doing the dasboard pages uncomment below and replace INSERT FXML HERE with parent dashboard
//            Stage stage = (Stage) LoginButton.getScene().getWindow();
//            SceneChanger.changeScene(stage, "INSERT FXML FILE HERE e.g.parent-dashboard-view");
        }

        // If "Admin" role returned
        if ("Admin".equals(role)){
            // Change scene to student dashboard
            System.out.println("Admin successfully logged in");
            // TODO: Whoever is doing the dasboard pages uncomment below and replace INSERT FXML HERE with admin dashboard
//            Stage stage = (Stage) LoginButton.getScene().getWindow();
//            SceneChanger.changeScene(stage, "INSERT FXML FILE HERE e.g.admin-dashboard-view");
        }

    }

}
