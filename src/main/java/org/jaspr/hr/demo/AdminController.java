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
    private Label personalisedGreeting;

    @FXML
    private Button classRoomButton;

    @FXML
    private Button AssignButton;

    @FXML
    private Button logoutButton;

    User user = UserSession.getInstance().getCurrentUser();


    private Object currentUser;

    public void setCurrentUser(Object user){
        this.currentUser = user;
        if (user instanceof Teacher){
            Teacher teacher = (Teacher) user;
            personalisedGreeting.setText(teacher.getName() + "'s Dashboard");
        } else if (user instanceof Admin){
            Admin admin = (Admin) user;
            personalisedGreeting.setText(admin.getName() + "'s Dashboard");
        }
    }

    @FXML
    protected void onClassroomClick() throws IOException {
        Stage stage = (Stage) classRoomButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "admin-classroom-view.fxml");
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
