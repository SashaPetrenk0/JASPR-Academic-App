package org.jaspr.hr.demo;

import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;

public class QuizAssignmentDetailsController {

    @FXML
    private ListView<String> classroomListView;

    private ObservableList<String> classroomNames = FXCollections.observableArrayList();
    private ObservableMap<String, BooleanProperty> selectedClassrooms = FXCollections.observableHashMap();

    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    private Teacher currentTeacher;

    public void setTeacher(Teacher teacher){
        this.currentTeacher = teacher;
    }

    private void loadClassrooms(){
        List<Integer> classrooms = userDAO.getClassroomNumberForTeacher(currentTeacher.getTeacherID());
    }
}
