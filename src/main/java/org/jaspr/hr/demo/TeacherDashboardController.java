package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.FXCollections;

import java.io.IOException;

/**
 * Class used to control UI interactions from the teacher dashboard fxml page
 */
public class TeacherDashboardController {
    User user = UserSession.getInstance().getCurrentUser();
    String role = UserSession.getInstance().getRole();

    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();

    @FXML
    private Button profileButton;

    @FXML
    private Label personalisedGreeting;

    @FXML
    private Button createQuiz;

    @FXML
    private ListView quizLists;

    @FXML
    private Button logoutButton;

    @FXML
    private Button assignQuizzes;

    @FXML
    private Button analytics;

    /**
     * Set personalised greeting when this page is opened
     */
    @FXML
    public void initialize() {
        if ("Teacher".equals(role) && user instanceof Teacher){
            //use the current logged-in user to get the name to show
            Teacher teacher = (Teacher) user;
            //show all quizzes made by the current teacher in a list view
            quizLists.setItems(FXCollections.observableArrayList(quizDAO.getAllQuizzes(teacher)));
            personalisedGreeting.setText(teacher.getName() + "'s Dashboard");
        }
    }

    //set the current user
    private Object currentUser;

    public void setCurrentUser(Object user){
        this.currentUser = user;
        if (user instanceof Teacher){
            Teacher teacher = (Teacher) user;
        }
    }

    /**
     *When the profile button is clicked, changes the scene to show the profile fxml page
     */
    @FXML
    public void onProfileClick() throws IOException {
        Stage stage = (Stage) profileButton.getScene().getWindow();

        // Load the profile-view.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jaspr/hr/demo/profile-view.fxml"));
        Parent root = loader.load();  // This will return javafx.scene.Parent

        // Get the ProfileController
        ProfileController profileController = loader.getController();
        // Pass the current user (updated) to the ProfileController
        profileController.setCurrentUser(user);

        // Change the scene
        stage.setScene(new Scene(root, SceneChanger.WIDTH, SceneChanger.HEIGHT));
        stage.show();
    }

    //TODO: Use scene changer here:

    /**
     *When the create quiz button is clicked, change the scene to the create quiz page.
     */
    @FXML
    protected void onAdd() throws IOException {
        Stage stage = (Stage) createQuiz.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-quiz-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    /**
     * When the logout button is pressed, change the scene to the opening page
     */
    @FXML
    private void onLogoutClicked(){
        UserSession.getInstance().clearSession();
        System.out.println("User logged out successfully");

        Stage stage = (Stage) logoutButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "hello-view.fxml");
    }


    /**
     * WHen the assign quiz button is clicked, change the scene to the assign quiz page
     */
    @FXML
    private void onAssignQuizzesClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jaspr/hr/demo/quiz-assignment-view.fxml"));
        Parent root = loader.load();

        QuizAssignmentController controller = loader.getController();
        //pass the current teacher to the next controller
        if (user instanceof Teacher) {
            controller.setTeacher((Teacher) user);
        }

        Stage stage = (Stage) assignQuizzes.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    /**
     * When the view analytics button is clicked, change the scene to the classroom analytics page
     */
    @FXML
    private void onAnalytics() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jaspr/hr/demo/teacher-view-results.fxml"));
        Parent root = loader.load();

        TeacherViewResultsController controller = loader.getController();
        //pass the current teacher to the next controller as an object
        if (user instanceof Teacher) {
            controller.setTeacher((Teacher) user);
        }

        Stage stage = (Stage) assignQuizzes.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

}


