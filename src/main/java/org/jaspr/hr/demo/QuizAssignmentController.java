package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.util.List;


public class QuizAssignmentController {

    @FXML
    private ListView<String> quizListView;

    private Teacher currentTeacher;

    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();

    public void setTeacher(Teacher teacher){
        this.currentTeacher = teacher;
    }

    private void loadQuizzes(){
        List<String> quizzes = quizDAO.getAllQuizzes(currentTeacher);
        quizListView.getItems().setAll(quizzes);
    }

    @FXML
    private void handleQuizClicked(MouseEvent event){
        if(event.getClickCount() == 1){
            String selectedQuiz = quizListView.getSelectionModel().getSelectedItem();
            if (selectedQuiz != null){
                goToQuiz(selectedQuiz);
            }
        }
    }

    private void goToQuiz(String quizTitle){
        Stage stage = (Stage) quizListView.getScene().getWindow();
        SceneChanger.changeScene(stage, "quiz-assignment-details.fxml");
    }
}
