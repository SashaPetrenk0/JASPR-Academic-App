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
import javafx.scene.Parent;


public class StudentDashboardController {
    User user = UserSession.getInstance().getCurrentUser();
    String role = UserSession.getInstance().getRole();

    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();

    @FXML
    private Label personalisedGreeting;

    @FXML
    private Button createQuiz;

    @FXML
    private ListView<Quiz> quizLists;

    @FXML
    private Button takeQuiz;

    @FXML
    public void initialize() {


        if ("Student".equals(role) && user instanceof Student){
            Student student = (Student) user;
            personalisedGreeting.setText("Hi, " + student.getName() + "!");
            quizLists.setItems(FXCollections.observableArrayList(quizDAO.getAllQuizzes(student)));
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
                        openTakeQuiz(selectedQuiz.getTitle(), selectedQuiz.getId());
                    }
                }
            });


        }

    }

    @FXML
    private void openTakeQuiz(String title, int id) {
        try {
            Stage currentStage = (Stage) quizLists.getScene().getWindow();
            // Load new window
            FXMLLoader loader = new FXMLLoader(getClass().getResource("take-quiz-view.fxml"));
            Parent root = loader.load();

            // Pass data to next controller
            TakeQuizController controller = loader.getController();
            controller.setSelectedQuiz(id);
            controller.loadTitle(title);

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
    protected void onAdd() throws IOException {
        Stage stage = (Stage) createQuiz.getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("create-quiz-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), HelloApplication.WIDTH, HelloApplication.HEIGHT);
        stage.setScene(scene);
    }

    @FXML
    private void onTakeQuiz() throws IOException {
        Stage stage = (Stage) takeQuiz.getScene().getWindow();
        SceneChanger.changeScene(stage,"take-quiz-view.fxml");
    }


}


