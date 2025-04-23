package org.jaspr.hr.demo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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


    private Student newStudent;
    private Teacher newTeacher;
    private Parent newParent;
    private Admin newAdmin;

    public void initialize(){
        roleComboBox.setItems(FXCollections.observableArrayList("Student", "Teacher", "Parent", "Admin"));
    }

    @FXML
    private void onRoleSelected() {
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
        private void onSubmitClicked(){
            String role = roleComboBox.getValue();
            String name, email, password;
            int age = 0;

            switch (role){
                case "Student" -> {
                    name = nameFieldStudent.getText();
                    email = emailFieldStudent.getText();
                    password = passwordFieldStudent.getText();

                    // Validation for empty fields
                    if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        showError("Please fill out all fields.");
                        return;
                    }

                    age = Integer.parseInt(ageFieldStudent.getText().trim());
                    int studentID = Integer.parseInt(studentIDField.getText().trim());

                    Student newStudent = new Student(name, age, studentID, email, password);
                    try {
                        userDAO.addStudent(newStudent);
                        showSuccess("Registration successful!");
                    } catch (IllegalArgumentException e) {
                        showError(e.getMessage());
                    } catch (SQLException e) {
                        showError("Database error, please try again.");
                    }
                    successfulSignUpLabelStudent.setText("Successful Student Registration! Welcome " + name + "!");
                    successfulSignUpLabelStudent.setVisible(true);



                }
                case "Teacher" -> {
                    name = nameFieldTeacher.getText();
                    email = emailFieldTeacher.getText();
                    password = passwordFieldTeacher.getText();
                    age = Integer.parseInt(ageFieldTeacher.getText().trim());
                    int teacherID = Integer.parseInt(teacherIDField.getText().trim());

                    Teacher newTeacher = new Teacher(name, age, teacherID, email, password);
                    userDAO.addTeacher(newTeacher);
                    successfulSignUpLabelTeacher.setText("Successful Teacher Registration! Welcome " + name + "!");
                    successfulSignUpLabelTeacher.setVisible(true);


                }
                case "Parent" -> {
                    name = nameFieldParent.getText();
                    String child = childNameField.getText();
                    int childID = Integer.parseInt(childIDField.getText().trim());
                    email = emailFieldParent.getText();
                    password = passwordFieldParent.getText();

                    Parent newParent = new Parent(name, child, childID, email, password);
                    userDAO.addParent(newParent);
                    successfulSignUpLabelParent.setText("Successful Parent Registration! Welcome " + name + "!");
                    successfulSignUpLabelParent.setVisible(true);



                }
                case "Admin" -> {
                    name = nameFieldAdmin.getText();
                    email = emailFieldAdmin.getText();
                    password = passwordFieldAdmin.getText();
                    age = Integer.parseInt(ageFieldAdmin.getText().trim());
                    int adminID = Integer.parseInt(adminIDField.getText().trim());

                    Admin newAdmin = new Admin(name, age, adminID, email, password);
                    userDAO.addAdmin(newAdmin);
                    successfulSignUpLabelAdmin.setText("Successful Administrator Registration! Welcome " + name + "!");
                    successfulSignUpLabelAdmin.setVisible(true);

                    // TODO: Error handling for incorrect user inputs
                }

            }



        }

    @FXML private void returnToHomePage() throws IOException {
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "hello-view.fxml");

    }

    // ---------- ERROR HANDLING CODE BELOW ----------
    @FXML
    private Label successLabel;  // This will display success messages
    @FXML
    private Label errorLabel;    // This will display error messages

    // Method to show success message
    private void showSuccess(String message) {
        successLabel.setText(message);                // Set the success message text
        successLabel.setStyle("-fx-text-fill: green;"); // Set the text color to green
        successLabel.setVisible(true);                // Make the success label visible

        // Optionally hide the error label if a success message is displayed
        errorLabel.setVisible(false);
    }

    // Method to show error message
    private void showError(String message) {
        errorLabel.setText(message);                // Set the error message text
        errorLabel.setStyle("-fx-text-fill: red;");  // Set the text color to red
        errorLabel.setVisible(true);                 // Make the error label visible

        // Optionally hide the success label if an error message is displayed
        successLabel.setVisible(false);
    }



}


