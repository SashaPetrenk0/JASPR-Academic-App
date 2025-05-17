package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentViewResultsController {
    private final User user = UserSession.getInstance().getCurrentUser();
    private final String role = UserSession.getInstance().getRole();

    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();
    private final SqliteResultsDAO resultsDAO = new SqliteResultsDAO();

    @FXML
    private ComboBox resultDropdown;
    @FXML
    private VBox allResultsBox;

    @FXML
    private VBox specificResultsBox;


    Student student = (Student) user;



    public void initialize(){
        if ("Student".equals(role) && user instanceof Student) {
            Student student = (Student) user;
            List<Quiz> userCreatedQuizzes = quizDAO.getAllQuizzes(student);
            List<Quiz> assignedQuizzes = quizDAO.getQuizzesForStudent(student.getStudentID());
            List<String> quizTitles = new ArrayList<>();
            quizTitles.add("All quizzes");
            for (Quiz quiz : assignedQuizzes) {
                quizTitles.add(quiz.getTitle() + "-assigned by teacher");
            }
            for (Quiz quiz : userCreatedQuizzes) {
                quizTitles.add(quiz.getTitle() + "-created by you");
            }

            resultDropdown.setItems(FXCollections.observableArrayList(quizTitles));

        }
    }

    @FXML
    private void onQuizSelected() {

        // First hide everything
        allResultsBox.setVisible(false);
        specificResultsBox.setVisible(false);

        // Then show the selected one
        String quiz = (String) resultDropdown.getValue();
        if (quiz == "All quizzes"){
            showOnlyResults(allResultsBox);
            List<Map<String, Integer>> quizResults = resultsDAO.getResultsByQuiz(student.getStudentID());
        }
        else{
            showOnlyResults(specificResultsBox);
        }
    }

    private void showOnlyResults(VBox boxToShow) {

        allResultsBox.setVisible(false);
        allResultsBox.setManaged(false);

        specificResultsBox.setVisible(false);
        specificResultsBox.setManaged(false);

        boxToShow.setVisible(true);
        boxToShow.setManaged(true);
    }
}
