package org.jaspr.hr.demo;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;

import java.awt.*;
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
        for (CheckBox cb : classroomListView.getItems()) {
            if (cb.isSelected()) {
                int classroomNumber = (int) cb.getUserData();
                int id = quiz.getId();
                quizDAO.assignQuizToClassroom(id, classroomNumber);
            }
        }
    }
}
