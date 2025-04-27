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
import org.w3c.dom.Text;

import javax.xml.transform.Result;

public class ChangePassword {

    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private Label ChangePwdError;

    private Object currentUser;

    public void setCurrentUser(Object user) {
        this.currentUser = user;
    }

    public String getRole(){
        if (currentUser instanceof Student) {
            return "Student";
        } else if (currentUser instanceof Teacher) {
            return "Teacher";
        } else if (currentUser instanceof Admin) {
            return "Admin";
        } else if (currentUser instanceof Parent) {
            return "Parent";
        }
        return null;
    }


    public void onChangePasswordClicked() {
        String email = emailField.getText();
        String currentPwd = currentPasswordField.getText();
        String newPwd = newPasswordField.getText();
        String role = getRole();

        if (emailField == null || currentPwd == null || newPwd == null){
            ChangePwdError.setText("Please fill all fields.");
        }
        else{
            boolean success = userDAO.changePassword(email, currentPwd, newPwd, role);

            if (success){
                ChangePwdError.setText("Password Successfully Changed!");
            }
            else{
                ChangePwdError.setText("Error changing password. Please confirm your details are correct.");
            }
        }



    }
}
