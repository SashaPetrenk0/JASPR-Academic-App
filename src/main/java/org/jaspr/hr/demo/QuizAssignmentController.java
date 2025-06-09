package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.List;
import javafx.scene.Parent;
//TODO: maybe split this up into a couple classes

/**
 * Controller class used to facilitate UI actions and to assign quizzes to classrooms
 */
public class QuizAssignmentController {

    @FXML
    private ListView<Quiz> quizListView;

    @FXML
    private Button returnToDashboard;

    // Store retrieved quizzes
    private List<Quiz> quizzes;

    private Teacher teacher;

    //use instance of quizDAO
    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();

    /**
     * Set the current teacher, populate the quiz listView with the quizzes made by the teacher.
     * @param teacher the logged in teacher
     */
    public void setTeacher(Teacher teacher){
        quizzes = quizDAO.getAllQuizObjects(teacher);
        quizListView.getItems().setAll(quizzes);
        this.teacher = teacher;

        quizListView.setCellFactory(lv -> new ListCell<Quiz>() {
            @Override
            protected void updateItem(Quiz quiz, boolean empty) {
                super.updateItem(quiz, empty);
                if (empty || quiz == null) {
                    setText(null);
                } else {
                    //if quizzes exist, display the title of each quiz in the listView
                    setText(quiz.getTitle());
                }
            }
        });


    }

    /**
     * Method to handle users clicking on the listView
     */
    @FXML
    private void handleQuizClicked(MouseEvent event) throws IOException {
        int SINGLE_CLICK = 1;
        if(event.getClickCount() == SINGLE_CLICK){
            Quiz selectedQuiz = quizListView.getSelectionModel().getSelectedItem();
            if (selectedQuiz != null){
                goToQuiz(selectedQuiz);
            }
        }
    }

    /**
     * Change the fxml page and pass the quiz object to that page
     * @param quiz selected quiz object from the listView
     * @throws IOException
     */
    private void goToQuiz(Quiz quiz) throws IOException {
        //get the next fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jaspr/hr/demo/quiz-assignment-details.fxml"));
        Parent root = loader.load();

        QuizAssignmentDetailsController controller = loader.getController();
        //pass the quiz and the current teacher to the next controller for the new fxml page
        controller.setQuiz(quiz, teacher);

        Stage stage = (Stage) quizListView.getScene().getWindow();
        stage.setScene(new Scene(root));

    }

    /**
     * Method to return to the teacher dashboard when the return button is pressed.
     */

    @FXML
    private void returnToDashboard() throws IOException {
        Stage stage = (Stage) returnToDashboard.getScene().getWindow();
        SceneChanger.changeScene(stage, "teacher-dashboard-view.fxml");
    }



}
