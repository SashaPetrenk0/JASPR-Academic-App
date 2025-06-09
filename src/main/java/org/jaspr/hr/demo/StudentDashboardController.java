package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.io.IOException;

import javafx.scene.Parent;

/**
 * Class used to control the UI for the student dashboard and handle passing data to further controllers
 */
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
    private ListView<Quiz> createdQuizzesLists;

    @FXML
    private Button viewAnalytics;

    @FXML
    private ListView<Quiz> assignedQuizzesList;

    @FXML
    private Button logoutButton;

    /**
     * Method that is run automatically when this page is opened
     */
    @FXML
    public void initialize() {
        //verify that the logged in user is a student
        if ("Student".equals(role) && user instanceof Student) {
            Student student = (Student) user;
            personalisedGreeting.setText("Hi, " + student.getName() + "!");
            //populate listView with quizzes created by the student
            createdQuizzesLists.setItems(FXCollections.observableArrayList(quizDAO.getAllQuizzes(student)));
            createdQuizzesLists.setCellFactory(listView -> new ListCell<>() {
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

            //populate second listView with quizzes assigned to the logged in student by a teacher
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


            createdQuizzesLists.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) { // double-click
                    Quiz selectedQuiz = createdQuizzesLists.getSelectionModel().getSelectedItem();
                    if (selectedQuiz != null) {

                        openTakeQuiz(selectedQuiz.getTitle(), quizDAO.getQuestions(selectedQuiz.getId()),selectedQuiz.getId(), student.getStudentID());

                    }
                }
            });

            assignedQuizzesList.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) { // double-click
                    Quiz selectedQuiz = assignedQuizzesList.getSelectionModel().getSelectedItem();
                    if (selectedQuiz != null) {

                        openTakeQuiz(selectedQuiz.getTitle(), quizDAO.getQuestions(selectedQuiz.getId()),selectedQuiz.getId(), student.getStudentID());

                    }
                }
            });


        }
    }

    /**
     * Pass data to the take quiz controller and change the scene
     * @param title the title of the selected quiz
     * @param questions array of question objects corresponding to the selected quiz
     * @param quizID id of the selected quiz
     * @param studentID the id of the student who is currently logged in
     */

    @FXML
        private void openTakeQuiz (String title, Question[]questions, int quizID, int studentID ){
            try {
                Stage currentStage = (Stage) createdQuizzesLists.getScene().getWindow();
                // Load new window
                FXMLLoader loader = new FXMLLoader(getClass().getResource("take-quiz-view.fxml"));
                Parent root = loader.load();

                // Pass data to next controller
                TakeQuizController controller = loader.getController();

                //pass the quiz title to the take quiz page
                controller.loadTitle(title);
                //pass the question array
                controller.setQuestions(questions);
               //pass the student id and quiz id so this can be used for results
                controller.getInfo(studentID,quizID);

                //change the scene
                Stage stage = new Stage();

                stage.setWidth(currentStage.getWidth());
                stage.setHeight(currentStage.getHeight());

                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }


    /**
     * Method to change the scene to the create quiz fxml file, actioned when the create quiz button is clicked.
     */
    @FXML
        protected void onAdd () throws IOException {
            //get current scene from button
            Stage stage = (Stage) createQuiz.getScene().getWindow();
            //change the scene
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-quiz-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
            stage.setScene(scene);
        }

    /**
     * Method to change the scene when the view results button is clicked
     */
    @FXML
        private void onViewResults() {
            Stage stage = (Stage) viewAnalytics.getScene().getWindow();
            SceneChanger.changeScene(stage, "student-view-results.fxml");
        }


    /**
     * Method to show the starting page when the user presses the logout button
     */
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

    /**
     * Pass the current user to the profile fxml page and change the scene
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
}






