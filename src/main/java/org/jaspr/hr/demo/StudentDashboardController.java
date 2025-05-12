package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.io.IOException;
import java.util.List;

import javafx.scene.Parent;


public class StudentDashboardController {
    private final User user = UserSession.getInstance().getCurrentUser();
    private final String role = UserSession.getInstance().getRole();

    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();

    @FXML
    private Button profileButton;

    @FXML
    private Label personalisedGreeting;

    @FXML
    private Button createQuiz;

    @FXML
    private ListView<Quiz> quizLists;

    @FXML
    private Button takeQuiz;

    @FXML
    private ListView<Quiz> assignedQuizzesList;

    @FXML
    private Button logoutButton;


    @FXML
    public void initialize() {
        if ("Student".equals(role) && user instanceof Student) {
            Student student = (Student) user;
            personalisedGreeting.setText("Hi, " + student.getName() + "!");
            quizLists.setItems(FXCollections.observableArrayList(quizDAO.getAllQuizzes(student)));
            assignedQuizzesList.setItems(FXCollections.observableArrayList(quizDAO.getQuizzesForStudent(student.getStudentID())));

            assignedQuizzesList.setCellFactory(param -> new ListCell<Quiz>() {
                @Override
                protected void updateItem(Quiz quiz, boolean empty) {
                    super.updateItem(quiz, empty);
                    if (empty || quiz == null) {
                        setText(null);
                    } else {
                        setText(quiz.getTitle());
                    }
                }
            });
            quizLists.setCellFactory(listView -> new ListCell<>() {
                @Override
                protected void updateItem(Quiz quiz, boolean empty) {
                    super.updateItem(quiz, empty);
                    if (empty || quiz == null) {
                        setText(null);
                    } else {
                        setText(quiz.getTitle());
                    }
                }
            });



            quizLists.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) { // double-click
                    Quiz selectedQuiz = quizLists.getSelectionModel().getSelectedItem();
                    if (selectedQuiz != null) {

                        openTakeQuiz(selectedQuiz.getTitle(), quizDAO.getQuestions(selectedQuiz.getId()));

                    }
                }
            });


        }
    }

        @FXML
        private void openTakeQuiz (String title, Question[]questions){
            try {
                Stage currentStage = (Stage) quizLists.getScene().getWindow();
                // Load new window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("take-quiz-view.fxml"));
                Parent root = loader.load();

                // Pass data to next controller
                TakeQuizController controller = loader.getController();

                controller.loadTitle(title);
                controller.setQuestions(questions);

                Stage stage = new Stage();

                stage.setWidth(currentStage.getWidth());
                stage.setHeight(currentStage.getHeight());

                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        /// go to create quiz page
        @FXML
        protected void onAdd () throws IOException {
            Stage stage = (Stage) createQuiz.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-quiz-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            stage.setScene(scene);
        }
        @FXML
        private void onTakeQuiz () throws IOException {
            Stage stage = (Stage) takeQuiz.getScene().getWindow();
            SceneChanger.changeScene(stage, "take-quiz-view.fxml");
        }

    @FXML
    private void onLogoutClicked() {
        UserSession.getInstance().clearSession();
        System.out.println("User logged out successfully");

        Stage stage = (Stage) logoutButton.getScene().getWindow();
        SceneChanger.changeScene(stage, "hello-view.fxml");
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

        // Pass the current user to the controller
        profileController.setCurrentUser(user);

        // Change the scene
        stage.setScene(new Scene(root, SceneChanger.WIDTH, SceneChanger.HEIGHT));
        stage.show();
    }
}






