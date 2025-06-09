package org.jaspr.hr.demo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private Button returnToPrevious;

    @FXML
    private ComboBox resultDropdown;
    @FXML
    private VBox allResultsBox;

    @FXML
    private VBox specificResultsBox;

    @FXML
    private TableView<Map<String, Object>> allResults;
    @FXML
    private TableColumn<Map<String, Object>, String> titleColumn;

    @FXML
    private TableColumn<Map<String, Object>, Integer> gradeColumn;

    @FXML
    private TableView<Map<String, Object>> questionResults;
    @FXML
    private TableColumn<Map<String, Object>, String> questionColumn;
    @FXML
    private TableColumn<Map<String, Object>, String> answerColumn;

    @FXML
    private TableColumn<Map<String, Object>, Integer> questionGradeColumn;

    @FXML
    private PieChart pieChart;

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
            // Cell value for title
            titleColumn.setCellValueFactory(cellData -> {
                Object value = cellData.getValue().get("title");
                return new SimpleStringProperty(value != null ? value.toString() : "");
            });

            // Cell value for grade
            gradeColumn.setCellValueFactory(cellData -> {
                Object value = cellData.getValue().get("grade");
                return new SimpleIntegerProperty(value != null ? (Integer) value : 0).asObject();
            });


            List<Map<String, Object>> results = resultsDAO.getResultsByQuiz(student.getStudentID());
            allResults.setItems(FXCollections.observableArrayList(results));

        }
        else{
            showOnlyResults(specificResultsBox);

            if(quizTitleToQuizMap.containsKey(quiz)){
                Quiz selectedQuiz = quizTitleToQuizMap.get(quiz);
                int quizID = selectedQuiz.getId();
                // Cell value for title
                questionColumn.setCellValueFactory(cellData -> {
                    Object value = cellData.getValue().get("question");
                    return new SimpleStringProperty(value != null ? value.toString() : "");
                });
                answerColumn.setCellValueFactory(cellData -> {
                    Object value = cellData.getValue().get("answer");
                    return new SimpleStringProperty(value != null ? value.toString() : "");
                });

                // Cell value for grade
                questionGradeColumn.setCellValueFactory(cellData -> {
                    Object value = cellData.getValue().get("grade");
                    return new SimpleIntegerProperty(value != null ? (Integer) value : 0).asObject();
                });

                questionResults.setItems(FXCollections.observableArrayList(resultsDAO.getResultsByQuestion(quizID, student.getStudentID())));

                int grade = resultsDAO.getGrade(quizID, student.getStudentID());
                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                        new PieChart.Data("Correct", grade ),
                        new PieChart.Data("Incorrect", 100-grade)

                );

                pieChart.setData(pieChartData);
                pieChart.setTitle("Percentage of correct answers from your latest attempt");

            }

        }
    }

    /**
     * Method to only show the section of the page that has been selected
     * @param boxToShow the VBox element that is chosen to be shown
     */
    private void showOnlyResults(VBox boxToShow) {

        //hide all quiz results section
        allResultsBox.setVisible(false);
        allResultsBox.setManaged(false);
        //hide specific quiz results section
        specificResultsBox.setVisible(false);
        specificResultsBox.setManaged(false);
        //show the box that has been selected
        boxToShow.setVisible(true);
        boxToShow.setManaged(true);
    }

    @FXML
    private void returnToStudentDashboard() throws IOException {
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "student-dashboard-view.fxml");
    }
}
