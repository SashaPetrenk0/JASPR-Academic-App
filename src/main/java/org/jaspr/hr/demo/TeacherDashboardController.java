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
    public void initialize() {
        if ("Teacher".equals(role) && user instanceof Teacher){
            Teacher teacher = (Teacher) user;
            quizLists.setItems(FXCollections.observableArrayList(quizDAO.getAllQuizzes(teacher)));
            personalisedGreeting.setText("Hi, " + teacher.getName() + "!");
            System.out.println(teacher.getName() + teacher.getTeacherID());
        }
    }

    private Object currentUser;

    public void setCurrentUser(Object user){
        this.currentUser = user;
        if (user instanceof Teacher){
            Teacher teacher = (Teacher) user;
        }
    }
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
    /// go to create quiz page
    @FXML
    protected void onAdd() throws IOException {
        Stage stage = (Stage) createQuiz.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-quiz-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onLogoutClicked(){
        UserSession.getInstance().clearSession();
        System.out.println("User logged out successfully");

        Stage stage = (Stage) logoutButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "hello-view.fxml");
    }

    @FXML
    private void onAssignQuizzesClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jaspr/hr/demo/quiz-assignment-view.fxml"));
        Parent root = loader.load();

        QuizAssignmentController controller = loader.getController();
        if (user instanceof Teacher) {
            controller.setTeacher((Teacher) user);
        }

        Stage stage = (Stage) assignQuizzes.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

}


