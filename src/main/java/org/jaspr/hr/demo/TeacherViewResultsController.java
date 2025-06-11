package org.jaspr.hr.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import javafx.util.StringConverter;


/**
 * Class to populate the results page that the teacher sees and to control the UI elements on this page
 */
public class TeacherViewResultsController {

    @FXML
    private ComboBox<Integer> classroomComboBox;

    @FXML
    private ComboBox<Quiz> quizSelection;

    @FXML
    private VBox teacherRadioList;

    @FXML
    private VBox studentVBox;

    @FXML
    private Button assignUsers;

    @FXML
    private Button returnToPrevious;

    private Teacher currentTeacher;

    @FXML private VBox specificResultsBox;
    @FXML private VBox selectionBox;

    @FXML private BarChart<String, Number> barChart;

    @FXML private TableView<StudentQuizResult> quizResultTable;
    @FXML private TableColumn<StudentQuizResult, String> studentColumn;
    @FXML private TableColumn<StudentQuizResult, String> gradeColumn;
    @FXML private TableColumn<StudentQuizResult, String> percentageColumn;

    @FXML private Label QuizTitle;

    @FXML private PieChart pieChart;

    @FXML private ListView ranking;


    @FXML
    private Label statusLabel;

    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();
    private final SqliteClassroomDAO classroomDAO = new SqliteClassroomDAO();
    private final SqliteResultsDAO resultsDAO = new SqliteResultsDAO();


    /**
     * Set the current teacher as the teacher who is logged in and display relevant data for the teacher
     * @param teacher teacher object passed from the dashboard
     */
    public void setTeacher(Teacher teacher) {
        this.currentTeacher = teacher;
        loadClassroomsForTeacher();
        loadQuizzesForTeacher();
    }

    /**
     * Display the classroom numbers in a comboBox
     */
    private void loadClassroomsForTeacher() {
        if (currentTeacher == null) return;

        //store all classroom numbers that the teacher is assigned to in a list
        List<Integer> classroomNumbers = classroomDAO.getClassroomNumberForTeacher(currentTeacher.getTeacherID());
        ObservableList<Integer> observableClassroomNumbers = FXCollections.observableArrayList(classroomNumbers);
        //display list of classrooms
        classroomComboBox.setItems(observableClassroomNumbers);

        classroomComboBox.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer classroomNumber) {
                if (classroomNumber == null) {
                    return null;
                } else {
                    return "Classroom " + classroomNumber;
                }
            }

            @Override
            public Integer fromString(String string) {
                return null;
            }
        });
    }

    /**
     * Display the quizzes that the teacher has created in a comboBox
     */
    private void loadQuizzesForTeacher() {

        List<Quiz> quizzes = quizDAO.getAllQuizObjects(currentTeacher);

        quizSelection.setItems(FXCollections.observableArrayList(quizzes));

        quizSelection.setConverter(new StringConverter<Quiz>() {
            //only show the quiz title
            @Override
            public String toString(Quiz quiz) {
                if (quiz == null) return "";
                return quiz.getTitle();
            }

            @Override
            public Quiz fromString(String string) {
                return null;
            }
        });
    }

    /**
     * Populate the bar graph, pie chart, and tables with data from the results tables accessed through resultsDAO
     */
    @FXML
    private void generateAnalytics() {
        Quiz selectedQuiz = quizSelection.getValue();
        Integer selectedClassroom = classroomComboBox.getValue();

        //error handling
        if (selectedQuiz == null || selectedClassroom == null) {
            statusLabel.setText("Please select both a classroom and a quiz.");
            return;
        }

            showOnlyResults(specificResultsBox);
            QuizTitle.setText(selectedQuiz.getTitle() + " Quiz Analytics");
            selectionBox.setVisible(false);
            Map<Integer, Double> accuracyMap =
                    resultsDAO.getQuestionAccuracyForQuiz(selectedQuiz.getId(), selectedClassroom);

            System.out.println("Selected quiz ID: " + selectedQuiz.getId());
            System.out.println("Selected classroom ID: " + selectedClassroom);
            System.out.println("Accuracy map size: " + accuracyMap.size());
            for (Map.Entry<Integer, Double> entry : accuracyMap.entrySet()) {
                System.out.println("Q" + entry.getKey() + ": " + entry.getValue() + "% correct");
            }

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Accuracy per Question");

            for (Map.Entry<Integer, Double> entry : accuracyMap.entrySet()) {
                String questionLabel = "Q" + entry.getKey();
                double percent = entry.getValue();
                series.getData().add(new XYChart.Data<>(questionLabel, percent));
            }

            barChart.getData().clear();
            barChart.getData().add(series);


            studentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getStudentName()));
            gradeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getScoreText()));
            percentageColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPercentageText()));


        int totalQuestions = quizDAO.getTotalQuestionsForQuiz(selectedQuiz.getId());

        List<Map<String, String>> quizResults = resultsDAO.getStudentGradesForQuiz(selectedQuiz.getId(), selectedClassroom);
        ObservableList<StudentQuizResult> resultList = FXCollections.observableArrayList();

        int scores = 0;
        for (Map<String, String> result : quizResults) {
            String studentName = result.get("student");
            int percentageGrade = Integer.parseInt(result.get("grade"));  // grade is percentage
            int score = (int) Math.round(percentageGrade * totalQuestions / 100.0);
            scores = score;
            resultList.add(new StudentQuizResult(studentName, score, totalQuestions));
        }

        quizResultTable.setItems(resultList);

        int totalIncorrect = totalQuestions - scores;

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Correct", scores),
                new PieChart.Data("Incorrect", totalIncorrect)
        );

        pieChart.setData(pieChartData);
        pieChart.setTitle("Class Performance on Quiz: " + selectedQuiz.getTitle());


        List<String> rankedStudentNames = resultList.stream()
                .sorted((r1, r2) -> Integer.compare(r2.getScore(), r1.getScore()))
                .map(StudentQuizResult::getStudentName)
                .collect(Collectors.toList());

// Add numbering (1. Student 1, 2. Student 2, etc.)
        ObservableList<String> rankedDisplayList = FXCollections.observableArrayList();
        for (int i = 0; i < rankedStudentNames.size(); i++) {
            rankedDisplayList.add((i + 1) + ". " + rankedStudentNames.get(i));
        }

        ranking.setItems(rankedDisplayList);

    }


    /**
     * Method to set which part of the page (VBox) should be visible
     * @param boxToShow the VBox that should be visible
     */
    private void showOnlyResults(VBox boxToShow) {
    boxToShow.setVisible(true);
    boxToShow.setManaged(true);
}


    /**
     * Method to return to the teacher dashboard when the return button is pressed
     */
    @FXML
private void returnToTeacherDashboard() throws IOException {
    Stage stage = (Stage) returnToPrevious.getScene().getWindow();
    SceneChanger.changeScene(stage, "teacher-dashboard-view.fxml");
}

    /**
     * Method to return to the selection menu on the analytics page
     */
    @FXML
private void returnToAnalyticsSelection() throws IOException {
    specificResultsBox.setVisible(false);
    selectionBox.setVisible(true);
}


}

