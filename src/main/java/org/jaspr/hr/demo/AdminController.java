package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for Admin Dashboard view
 * Handles navigation to profile, classroom management, and logout
 */
public class AdminController {

    @FXML
    private Button profileButton;

    @FXML
    private Button classRoomButton;

    @FXML
    private Button logoutButton;

    /** Currently logged-in user retrieved from the session */
    User user = UserSession.getInstance().getCurrentUser();

    // Initialise current user object
    private Object currentUser;

    /**
     * Set the current user for user related actions on this page
     * @param user to be set
     */
    public void setCurrentUser(Object user){
        this.currentUser = user;
        if (user instanceof Teacher){
            Teacher teacher = (Teacher) user;
        }
    }

    /**
     * Navigates to the classroom management menu using Scene Changer class
     * @throws IOException if fxml fails to load
     */
    @FXML
    private void onClassroomClick() throws IOException {
        Stage stage = (Stage) classRoomButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "admin-classroom-view.fxml");
    }


    /**
     * Navigates to the admin's profile view and passes the current user.
     * @throws IOException if FXML loading fails.
     */
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

    /**
     * Logs out the current user and returns to the login view.
     */
    @FXML
    private void onLogoutClicked(){
        UserSession.getInstance().clearSession();
        System.out.println("User logged out successfully");

        Stage stage = (Stage) logoutButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "hello-view.fxml");
    }
}
