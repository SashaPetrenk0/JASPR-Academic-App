package org.jaspr.hr.demo;

import javafx.scene.Parent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class QuizAssignmentDetailsController {

    @FXML
    private ListView<CheckBox> classroomListView;

    @FXML
    private Label titleLabel;
    @FXML
    private Label topicLabel;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label numQuestionsLabel;

    @FXML
    private Button returnToAssignment;

    @FXML
    private Label StatusMessage;

    private Quiz quiz;
    private List<Classroom> teacherClassrooms;
    private Teacher currentTeacher;

    private final SqliteUserDAO userDAO = new SqliteUserDAO();
    private final SqliteQuizDAO quizDAO = new SqliteQuizDAO();




    public void setQuiz(Quiz quiz, Teacher teacher){
        this.currentTeacher = teacher;
        this.quiz = quiz;

        titleLabel.setText(quiz.getTitle());
        topicLabel.setText("Topic: " + quiz.getTopic());
        descriptionLabel.setText("Description: " + quiz.getDescription());
        numQuestionsLabel.setText("Number of Questions: " + quiz.getNumOfQuestions());

        loadClassrooms();
    }

    private void loadClassrooms(){
        List<Integer> classroomNumbers = userDAO.getClassroomNumberForTeacher(currentTeacher.getTeacherID());

        for(Integer number : classroomNumbers){
            CheckBox cb = new CheckBox("Classroom" + number);
            cb.setUserData(number);
            classroomListView.getItems().add(cb);
        }
    }



    @FXML
    private void handleAssignClicked() {
        boolean atLeastOneAssigned = false;
        for (CheckBox cb : classroomListView.getItems()) {
            if (cb.isSelected()) {
                int classroomNumber = (int) cb.getUserData();
                int id = quiz.getId();
                quizDAO.assignQuizToClassroom(id, classroomNumber);
                atLeastOneAssigned = true;
            }
        }
        if (!atLeastOneAssigned){
            System.out.println("No classroms selected");
        }
    }

    @FXML
    private void returnToAssignment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/jaspr/hr/demo/quiz-assignment-view.fxml"));
        Parent root = loader.load();

        QuizAssignmentController controller = loader.getController();
        controller.setTeacher(currentTeacher);  // pass the teacher again

        Stage stage = (Stage) returnToAssignment.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
