package org.jaspr.hr.demo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterController {

    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private VBox studentForm, teacherForm, parentForm, adminForm;

    @FXML
    private TextField nameFieldStudent;
    @FXML
    private TextField ageFieldStudent;
    @FXML
    private TextField studentIDField;
    @FXML
    private TextField emailFieldStudent;
    @FXML
    private TextField passwordFieldStudent;

    @FXML
    private TextField nameFieldTeacher;
    @FXML
    private TextField ageFieldTeacher;
    @FXML
    private TextField teacherIDField;
    @FXML
    private TextField emailFieldTeacher;
    @FXML
    private TextField passwordFieldTeacher;

    @FXML
    private TextField nameFieldParent;
    @FXML
    private TextField childNameField;
    @FXML
    private TextField childIDField;
    @FXML
    private TextField emailFieldParent;
    @FXML
    private TextField passwordFieldParent;

    @FXML
    private TextField nameFieldAdmin;
    @FXML
    private TextField ageFieldAdmin;
    @FXML
    private TextField adminIDField;
    @FXML
    private TextField emailFieldAdmin;
    @FXML
    private TextField passwordFieldAdmin;

    @FXML
    private Label successfulSignUpLabelStudent;
    @FXML
    private Label successfulSignUpLabelTeacher;
    @FXML
    private Label successfulSignUpLabelParent;
    @FXML
    private Label successfulSignUpLabelAdmin;

    @FXML
    private Button returnToPrevious;


    @FXML
    private Rectangle Rectangle1;
    @FXML
    private Rectangle Rectangle2;

    @FXML
    private Button submitButtonStudent;

    @FXML
    private Button submitButtonTeacher;

    @FXML
    private Button submitButtonParent;

    @FXML
    private Button submitButtonAdmin;

    @FXML
    private ImageView successIcon2;
    @FXML
    private ImageView successIcon1;
    @FXML
    private ImageView successIcon3;

    @FXML
    private ImageView successIcon4;





    private Student newStudent;
    private Teacher newTeacher;
    private Parent newParent;
    private Admin newAdmin;

    public void initialize(){
        roleComboBox.setItems(FXCollections.observableArrayList("Student", "Teacher", "Parent", "Admin"));
    }

    @FXML
    private void onRoleSelected() {
        // Hide decorative rectangles
        Rectangle1.setVisible(false);
        Rectangle2.setVisible(false);
        
        // First hide everything
        studentForm.setVisible(false);
        teacherForm.setVisible(false);
        parentForm.setVisible(false);
        adminForm.setVisible(false);

        // Then show the selected one
        String role = roleComboBox.getValue();
        switch(role){
            case "Student" -> showOnlyForm(studentForm);
            case "Teacher" -> showOnlyForm(teacherForm);
            case "Parent" -> showOnlyForm(parentForm);
            case "Admin" -> showOnlyForm(adminForm);
        }
    }

    private void showOnlyForm(VBox formToShow) {
        studentForm.setVisible(false);
        studentForm.setManaged(false);

        teacherForm.setVisible(false);
        teacherForm.setManaged(false);

        parentForm.setVisible(false);
        parentForm.setManaged(false);

        adminForm.setVisible(false);
        adminForm.setManaged(false);

        formToShow.setVisible(true);
        formToShow.setManaged(true);
    }


    @FXML
    private void onSubmitClicked() {
        String role = roleComboBox.getValue();
        String name, email, password;

        try {
            switch (role) {
                case "Student" -> {
                    name = nameFieldStudent.getText().trim();
                    email = emailFieldStudent.getText().trim();
                    password = passwordFieldStudent.getText().trim();

                    if (name.isEmpty() || email.isEmpty() || password.isEmpty())
                        throw new IllegalArgumentException("All fields must be filled out");

                    int age = parseIntField(ageFieldStudent.getText(), "Age must be a valid number");
                    int studentID = parseIntField(studentIDField.getText(), "Student ID must be a valid number");

                    String salt = PasswordUtility.generateSalt();
                    String hashedPassword = PasswordUtility.hashPassword(password, salt);

                    Student newStudent = new Student(name, age, studentID, email);
                    userDAO.addStudent(newStudent, hashedPassword, salt);
                    showSuccess(successfulSignUpLabelStudent, submitButtonStudent, successIcon1, name);
                }
                case "Teacher" -> {
                    name = nameFieldTeacher.getText().trim();
                    email = emailFieldTeacher.getText().trim();
                    password = passwordFieldTeacher.getText().trim();

                    if (name.isEmpty() || email.isEmpty() || password.isEmpty())
                        throw new IllegalArgumentException("All fields must be filled out");

                    int age = parseIntField(ageFieldTeacher.getText(), "Age must be a valid number");
                    int teacherID = parseIntField(teacherIDField.getText(), "Teacher ID must be a valid number");

                    String salt = PasswordUtility.generateSalt();
                    String hashedPassword = PasswordUtility.hashPassword(password, salt);

                    Teacher newTeacher = new Teacher(name, age, teacherID, email);
                    userDAO.addTeacher(newTeacher, hashedPassword, salt);
                    showSuccess(successfulSignUpLabelTeacher, submitButtonTeacher, successIcon2, name);
                }
                case "Parent" -> {
                    name = nameFieldParent.getText().trim();
                    String child = childNameField.getText().trim();
                    email = emailFieldParent.getText().trim();
                    password = passwordFieldParent.getText().trim();

                    if (name.isEmpty() || child.isEmpty() || email.isEmpty() || password.isEmpty())
                        throw new IllegalArgumentException("All fields must be filled out");

                    int childID = parseIntField(childIDField.getText(), "Child ID must be a valid number");

                    String salt = PasswordUtility.generateSalt();
                    String hashedPassword = PasswordUtility.hashPassword(password, salt);

                    Parent newParent = new Parent(name, child, childID, email);
                    userDAO.addParent(newParent, hashedPassword, salt);
                    showSuccess(successfulSignUpLabelParent, submitButtonParent, successIcon3, name);
                }
                case "Admin" -> {
                    name = nameFieldAdmin.getText().trim();
                    email = emailFieldAdmin.getText().trim();
                    password = passwordFieldAdmin.getText().trim();

                    if (name.isEmpty() || email.isEmpty() || password.isEmpty())
                        throw new IllegalArgumentException("All fields must be filled out");

                    int age = parseIntField(ageFieldAdmin.getText(), "Age must be a valid number");
                    int adminID = parseIntField(adminIDField.getText(), "Admin ID must be a valid number");

                    String salt = PasswordUtility.generateSalt();
                    String hashedPassword = PasswordUtility.hashPassword(password, salt);

                    Admin newAdmin = new Admin(name, age, adminID, email);
                    userDAO.addAdmin(newAdmin, hashedPassword, salt);
                    showSuccess(successfulSignUpLabelAdmin, submitButtonAdmin, successIcon4, name);
                }
                default -> throw new IllegalArgumentException("Role must be selected");
            }
        } catch (IllegalArgumentException ex) {
            // Optionally log or display this in a label or alert box
            System.err.println("Registration Error: " + ex.getMessage());
            throw ex; // Required for unit tests to pass!
        }
    }

    private int parseIntField(String value, String errorMessage) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void showSuccess(Label label, Button button, ImageView icon, String name) {
        label.setText("Successful Registration! Welcome " + name + "!");
        label.setVisible(true);
        label.setManaged(true);

        button.setVisible(false);
        button.setManaged(false);

        icon.setVisible(true);
        icon.setManaged(true);
    }



    @FXML private void returnToHomePage() throws IOException {
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "hello-view.fxml");

    }


}


