package org.jaspr.hr.demo;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    @FXML
    private Button profileButton;

    @FXML
    private Button ClassRoomCreation;

    @FXML
    private Button AssignButton;

    @FXML
    private Button logoutButton;

    User user = UserSession.getInstance().getCurrentUser();

    @FXML
    protected void onCreateClassroomClick() throws IOException {
        Stage stage = (Stage) ClassRoomCreation.getScene().getWindow();
        SceneChanger.changeScene(stage, "classroom-creation-view.fxml");
    }

    @FXML
    protected void onAssignUsersClick() throws IOException{
        Stage stage = (Stage) AssignButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "assign-users-to-classroom.fxml");
    }

    @FXML
    public void onProfileClick() throws IOException {
        Stage stage = (Stage) profileButton.getScene().getWindow();

        // Load the profile-view.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jaspr/hr/demo/profile-view.fxml"));
        Parent root = loader.load();  // This will return javafx.scene.Parent

        // Get the ProfileController
        ProfileController profileController = loader.getController();

        // Pass the current user to the controller
        profileController.setCurrentUser(user);

        // Change the scene
        stage.setScene(new Scene(root, SceneChanger.WIDTH, SceneChanger.HEIGHT));
        stage.show();
    }

    @FXML
    private void onLogoutClicked(){
        UserSession.getInstance().clearSession();
        System.out.println("User logged out successfully");

        Stage stage = (Stage) logoutButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "hello-view.fxml");
    }
}
