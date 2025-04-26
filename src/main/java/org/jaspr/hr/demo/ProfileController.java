package org.jaspr.hr.demo;

import javafx.fxml.FXML;
package org.jaspr.hr.demo;
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
            loadTeacherProfile(admin);
        }
        if (user instanceof Parent){
            Parent parent = (Parent) user;
            loadTeacherProfile(parent);
        }
    }






}
