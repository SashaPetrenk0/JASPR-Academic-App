package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

import javafx.fxml.FXML;
import javafx.util.StringConverter;

import javax.xml.transform.Result;

public class ProfileController {

    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private Label nameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label idLabel;

    @FXML
    private Label emailLabel;


    @FXML
    private Label passwordLabel;

    @FXML
    private Label enrollmentLabel;

    // Either student, teacher, admin, parent
    private Object currentUser;

    public void setCurrentUser(Object user){
        this.currentUser = user;

        if (user instanceof Student){
            Student student = (Student) user;
            loadStudentProfile(student);
        }
        else if (user instanceof Teacher){
            Teacher teacher = (Teacher) user;
            loadTeacherProfile(teacher);
        }
        if (user instanceof Admin){
            Admin admin = (Admin) user;
            loadAdminProfile(admin);
        }
        if (user instanceof Parent){
            Parent parent = (Parent) user;
            loadParentProfile(parent);
        }
    }

    private void loadStudentProfile(Student student){
        nameLabel.setText("Name: " + student.getName());
        ageLabel.setText("Age: " + student.getAge());
        idLabel.setText("ID: " + student.getStudentID());
        emailLabel.setText("Email: " + student.getEmail());
        //TODO: Hash the password, press a button to unhash it
        passwordLabel.setText("Password: " + student.getPassword());

        List<Integer> classroomNumbers = userDAO.getClassroomNumbersForStudent(student.getStudentID());

        StringBuilder classroomText = new StringBuilder();
        for (Integer number : classroomNumbers) {
            classroomText.append("Classroom: ").append(number).append("\n");
        }
        enrollmentLabel.setText(classroomText.toString());
        enrollmentLabel.setVisible(true);
    }

    private void loadTeacherProfile(Teacher teacher){
        nameLabel.setText("Name: " + teacher.getName());
        ageLabel.setText("Age: " + teacher.getAge());
        idLabel.setText("ID: " + teacher.getTeacherID());
        emailLabel.setText("Email: " + teacher.getEmail());
        //TODO: Hash the password, press a button to unhash it
        passwordLabel.setText("Password: " + teacher.getPassword());
        enrollmentLabel.setText("Classroom: " + teacher.getClass());
        enrollmentLabel.setVisible(true);
    }

    private void loadAdminProfile(Admin admin){
        nameLabel.setText("Name: " + admin.getName());
        ageLabel.setText("Age: " + admin.getAge());
        idLabel.setText("ID: " + admin.getAdminID());
        emailLabel.setText("Email: " + admin.getEmail());
        //TODO: Hash the password, press a button to unhash it
        passwordLabel.setText("Password: " + admin.getPassword());
        enrollmentLabel.setText("Classroom: " + admin.getClass());
        enrollmentLabel.setVisible(true);
    }

    private void loadParentProfile(Parent parent){
        nameLabel.setText("Name: " + parent.getName());
        ageLabel.setText("Child Name " + parent.getChildName());
        idLabel.setText("ID: " + parent.getChildID());
        emailLabel.setText("Email: " + parent.getEmail());
        //TODO: Hash the password, press a button to unhash it
        passwordLabel.setText("Password: " + parent.getPassword());
        enrollmentLabel.setText("Classroom: " + parent.getClass());
        enrollmentLabel.setVisible(true);
    }






}
