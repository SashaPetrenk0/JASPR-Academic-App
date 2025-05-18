package org.jaspr.hr.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    @FXML
    private ListView allResults;

    @FXML
    private PieChart pieChart;

    @FXML
    private ListView questionResults;
    Student student = (Student) user;

    private Map<String, Quiz> quizTitleToQuizMap;

    public void initialize(){
        if ("Student".equals(role) && user instanceof Student) {
            Student student = (Student) user;
            List<Quiz> userCreatedQuizzes = quizDAO.getAllQuizzes(student);
            List<Quiz> assignedQuizzes = quizDAO.getQuizzesForStudent(student.getStudentID());
            List<String> quizTitles = new ArrayList<>();
            quizTitles.add("All quizzes");
            quizTitleToQuizMap = new HashMap<>();
            for (Quiz quiz : assignedQuizzes) {
                String title = quiz.getTitle() + " - assigned by teacher";
                quizTitles.add(title);
                quizTitleToQuizMap.put(title, quiz);
            }

            for (Quiz quiz : userCreatedQuizzes) {
                String title = quiz.getTitle() + " - created by you";
                quizTitles.add(title);
                quizTitleToQuizMap.put(title, quiz);
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
        if (quiz.equals("All quizzes")){
            System.out.print("hello");
            showOnlyResults(allResultsBox);

            allResults.setItems(FXCollections.observableArrayList(resultsDAO.getResultsByQuiz(student.getStudentID())));

        }
        else{
            showOnlyResults(specificResultsBox);

            if(quizTitleToQuizMap.containsKey(quiz)){
                Quiz selectedQuiz = quizTitleToQuizMap.get(quiz);
                int quizID = selectedQuiz.getId();
                questionResults.setItems(FXCollections.observableArrayList(resultsDAO.getResultsByQuestion(quizID, student.getStudentID())));
                int grade = resultsDAO.getGrade(quizID, student.getStudentID());
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Correct", grade ),
                        new PieChart.Data("Incorrect", 100-grade)

                );

                pieChart.setData(pieChartData);
                pieChart.setTitle("Percentage of correct answers");

            }

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
