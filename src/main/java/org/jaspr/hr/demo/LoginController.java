package org.jaspr.hr.demo;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
    private Button ReturnButton;


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
            Student loggedInStudent = userDAO.getLoggedInStudent(email, password);
            UserSession.getInstance().setCurrentUser(loggedInStudent, role);
            // Change scene to student dashboard
            // TODO: Whoever is doing the dasboard pages uncomment below and replace INSERT FXML HERE with student dasboard
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            SceneChanger.changeScene(stage, "student-dashboard-view.fxml");
        }

        // If "Teacher" role returned
        if ("Teacher".equals(role)){
            System.out.println("Teacher successfully logged in");
            Teacher loggedInTeacher = userDAO.getLoggedInTeacher(email, password);
            UserSession.getInstance().setCurrentUser(loggedInTeacher, role);
            // Change scene to student dashboard
            // TODO: Whoever is doing the dasboard pages uncomment below and replace INSERT FXML HERE with teacher dashboard
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            SceneChanger.changeScene(stage, "teacher-dashboard-view.fxml");






        }

        // If "Parent" role returned
        if ("Parent".equals(role)){
            System.out.println("Parent successfully logged in");
            // Change scene to student dashboard
            // TODO: Whoever is doing the dasboard pages uncomment below and replace INSERT FXML HERE with parent dashboard
//            Stage stage = (Stage) LoginButton.getScene().getWindow();
//            SceneChanger.changeScene(stage, "INSERT FXML FILE HERE e.g.parent-dashboard-view.fxml");
        }

        // If "Admin" role returned
        if ("Admin".equals(role)){
            Admin loggedInAdmin = userDAO.getLoggedInAdmin(email, password);
            UserSession.getInstance().setCurrentUser(loggedInAdmin, role);
            // Change scene to student dashboard
            System.out.println("Admin successfully logged in");
            // TODO: Whoever is doing the dasboard pages uncomment below and replace INSERT FXML HERE with admin dashboard
            Stage stage = (Stage) LoginButton.getScene().getWindow();
            SceneChanger.changeScene(stage, "admin-dashboard-view.fxml");
        }

    }

    @FXML
    private void onReturnClicked() {
        Stage stage = (Stage) ReturnButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "hello-view.fxml");
    }


}
