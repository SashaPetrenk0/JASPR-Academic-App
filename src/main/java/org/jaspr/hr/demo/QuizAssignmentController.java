package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.List;
import javafx.scene.Parent;


public class QuizAssignmentController {

    @FXML
    private ListView<Quiz> quizListView;

    // Store retrieved quizzes
    private List<Quiz> quizzes;

//    private Teacher currentTeacher;

    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();

    public void setTeacher(Teacher teacher){
        quizzes = quizDAO.getAllQuizzes(teacher);
        quizListView.getItems().setAll(quizzes);
//        this.currentTeacher = teacher;
    }

    @FXML
    private void handleQuizClicked(MouseEvent event) throws IOException {
        if(event.getClickCount() == 1){
            Quiz selectedQuiz = quizListView.getSelectionModel().getSelectedItem();
            if (selectedQuiz != null){
                goToQuiz(selectedQuiz);
            }
        }
    }

    private void goToQuiz(Quiz quiz) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jaspr/hr/demo/quiz-assignment-details.fxml"));
        Parent root = loader.load();

        QuizAssignmentDetailsController controller = loader.getController();
        controller.setQuizID(quiz); // <--- Pass quiz object

        Stage stage = (Stage) quizListView.getScene().getWindow();
        stage.setScene(new Scene(root));

    }
}
