
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.util.StringConverter;

import javax.xml.transform.Result;

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

    @FXML private VBox allResultsBox;
    @FXML private VBox specificResultsBox;
    @FXML private VBox selectionBox;

    @FXML private BarChart<String, Number> barChart;

    @FXML private TableView<StudentQuizResult> quizResultTable;
    @FXML private TableColumn<StudentQuizResult, String> studentColumn;
    @FXML private TableColumn<StudentQuizResult, String> gradeColumn;
    @FXML private TableColumn<StudentQuizResult, String> percentageColumn;


    @FXML
    private Label statusLabel;

    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();
    private final SqliteClassroomDAO classroomDAO = new SqliteClassroomDAO();
    private final SqliteResultsDAO resultsDAO = new SqliteResultsDAO();


    public void setTeacher(Teacher teacher) {
        this.currentTeacher = teacher;
        loadClassroomsForTeacher();
        loadQuizzesForTeacher();
    }
    private void loadClassroomsForTeacher() {
        if (currentTeacher == null) return;

        List<Integer> classroomNumbers = classroomDAO.getClassroomNumberForTeacher(currentTeacher.getTeacherID());
        ObservableList<Integer> observableClassroomNumbers = FXCollections.observableArrayList(classroomNumbers);
        classroomComboBox.setItems(observableClassroomNumbers);

        classroomComboBox.setConverter(new StringConverter<Integer>() {
            @Override
            public String toString(Integer classroomNumber) {
                return "Classroom " + classroomNumber;
            }

            @Override
            public Integer fromString(String string) {
                return null;
            }
        });
    }
    private void loadQuizzesForTeacher() {

        List<Quiz> quizzes = quizDAO.getAllQuizObjects(currentTeacher);

        Quiz allQuizzesOption = new Quiz(-1,"All quizzes", "Summary", "Represents All Quizzes", 0, 0);
        quizzes.add(0, allQuizzesOption); // Add the "All quizzes" option at the top
        quizSelection.setItems(FXCollections.observableArrayList(quizzes));

        quizSelection.setConverter(new StringConverter<Quiz>() {
            @Override
            public String toString(Quiz quiz) {
                if (quiz == null) return "";
                return quiz.getTitle();
            }

            @Override
            public Quiz fromString(String string) {
                return null; // not needed for this use case
            }
        });
    }

    @FXML
    private void generateAnalytics() {
        Quiz selectedQuiz = quizSelection.getValue();
        Integer selectedClassroom = classroomComboBox.getValue();

        if (selectedQuiz == null || selectedClassroom == null) {
            statusLabel.setText("Please select both a classroom and a quiz.");
            return;
        }

        // UI logic previously in onQuizSelected()
        if ("All quizzes".equals(selectedQuiz.getTitle())) {
            showOnlyResults(allResultsBox);
            selectionBox.setVisible(false);
            Map<Integer, Double> accuracyMap =
                    resultsDAO.getQuestionAccuracyForQuiz(selectedQuiz.getId(), selectedClassroom);

            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Accuracy per Question");

            for (Map.Entry<Integer, Double> entry : accuracyMap.entrySet()) {
                String questionLabel = "Q" + entry.getKey();
                double percent = entry.getValue();
                series.getData().add(new XYChart.Data<>(questionLabel, percent));
            }

            barChart.getData().clear();
            barChart.getData().add(series);
        } else {
            showOnlyResults(specificResultsBox);
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
        }

        int totalQuestions = resultsDAO.getTotalQuestionsForQuiz(selectedQuiz.getId());

        List<Map<String, String>> quizResults = resultsDAO.getStudentGradesForQuiz(selectedQuiz.getId(), selectedClassroom);
        ObservableList<StudentQuizResult> resultList = FXCollections.observableArrayList();

        for (Map<String, String> result : quizResults) {
            String studentName = result.get("student");
            int grade = Integer.parseInt(result.get("grade"));
            resultList.add(new StudentQuizResult(studentName, grade, totalQuestions));
        }

        quizResultTable.setItems(resultList);




    }
}



private void showOnlyResults(VBox boxToShow) {
    boxToShow.setVisible(true);
    boxToShow.setManaged(true);
}



@FXML
private void returnToTeacherDashboard() throws IOException {
    Stage stage = (Stage) returnToPrevious.getScene().getWindow();
    SceneChanger.changeScene(stage, "teacher-dashboard-view.fxml");
}

@FXML
private void returnToAnalyticsSelection() throws IOException {
    allResultsBox.setVisible(false);
    specificResultsBox.setVisible(false);
    selectionBox.setVisible(true);
}


}

