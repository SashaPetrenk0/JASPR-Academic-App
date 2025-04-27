package org.jaspr.hr.demo;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jaspr.hr.demo.users.Admin;
import org.jaspr.hr.demo.users.Parent;
import org.jaspr.hr.demo.users.Student;
import org.jaspr.hr.demo.users.Teacher;

import java.io.IOException;
import java.sql.SQLException;

public class RegisterController {

    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private VBox studentForm, teacherForm, parentForm, adminForm;


    @FXML
    private TextField nameFieldStudent, ageFieldStudent, studentIDField, emailFieldStudent, passwordFieldStudent;
    @FXML
    private TextField nameFieldTeacher, ageFieldTeacher, teacherIDField, emailFieldTeacher, passwordFieldTeacher;
    @FXML
    private TextField nameFieldParent, childNameField, childIDField, emailFieldParent, passwordFieldParent;
    @FXML
    private TextField nameFieldAdmin, ageFieldAdmin, adminIDField, emailFieldAdmin, passwordFieldAdmin;

    // Error Labels for each field (Student sign up)
    @FXML
    private Label nameErrorLabel, ageErrorLabel, studentIDErrorLabel, emailErrorLabel, passwordErrorLabel, generalErrorLabel;;

    @FXML
    private Label successfulSignUpLabelStudent, successfulSignUpLabelTeacher, successfulSignUpLabelParent, successfulSignUpLabelAdmin;
    @FXML
    private Button returnToPrevious;
    @FXML
    private Label errorLabel;  // For global success and error messages



    private Student newStudent;
    private Teacher newTeacher;
    private Parent newParent;
    private Admin newAdmin;

    public void initialize(){
        roleComboBox.setItems(FXCollections.observableArrayList("Student", "Teacher", "Parent", "Admin"));

        // Real-time validation: when focus is lost
        nameFieldStudent.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validateName(nameFieldStudent.getText());
        });

        ageFieldStudent.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validateAge(ageFieldStudent.getText());
        });

        studentIDField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validateStudentID(studentIDField.getText());
        });

        emailFieldStudent.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validateEmail(emailFieldStudent.getText());
        });

        passwordFieldStudent.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) validatePassword(passwordFieldStudent.getText());
        });
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

                    resetErrorLabels();

                    // Validation for empty fields
                    if (name.isEmpty()) {
                        showError("Name cannot be empty.", nameErrorLabel);
                        return;
                    }
                    if (email.isEmpty()) {
                        showError("Email cannot be empty.", emailErrorLabel);
                        return;
                    }
                    if (password.isEmpty()) {
                        showError("Password cannot be empty.", passwordErrorLabel);
                        return;
                    }

                    if (!validateName(name) ||
                            !validateEmail(email) ||
                            !validatePassword(password) ||
                            !validateAge(ageFieldStudent.getText()) ||
                            !validateStudentID(studentIDField.getText())) {
                        return; // stop if any validation fails
                    }

                    // Handle age input with validation and error handling
                    String ageText = ageFieldStudent.getText().trim();
                    if (ageText.isEmpty() || !ageText.matches("\\d+")) {  // Check if age is empty or contains non-numeric characters
                        showError("Please enter a valid age.", ageErrorLabel);
                        return;
                    }

                    try {
                        age = Integer.parseInt(ageText);  // Try parsing the age input
                    } catch (NumberFormatException e) {
                        showError("Invalid age input, please enter a valid number.", ageErrorLabel);
                        return;
                    }

                    try {
                        int studentID = Integer.parseInt(studentIDField.getText().trim());

                        // Create the new student object
                        Student newStudent = new Student(name, age, studentID, email, password);

                        // Add the student to the database
                        userDAO.addStudent(newStudent);

                        // Show success message
                        //showSuccess("Registration successful!");
                        successfulSignUpLabelStudent.setText("Successful Student Registration! Welcome " + name + "!");
                        successfulSignUpLabelStudent.setVisible(true);
                    } catch (IllegalArgumentException e) {
                        showError(e.getMessage(), generalErrorLabel);
                    } catch (SQLException e) {
                        e.printStackTrace();  // Add this line to see what actually failed
                        showError("Database error, please try again.", generalErrorLabel);
                    }
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

//    // Method to show success message
//    private void showSuccess(String message) {
//        successLabel.setText(message);                // Set the success message text
//        successLabel.setStyle("-fx-text-fill: green;"); // Set the text color to green
//        successLabel.setVisible(true);                // Make the success label visible
//
//        // Optionally hide the error label if a success message is displayed
//        errorLabel.setVisible(false);
//    }

    // Method to show error message
    private void showError(String message, Label fieldErrorLabel) {
        fieldErrorLabel.setText(message);
        fieldErrorLabel.setVisible(true);
    }

    // Reset all error labels
    private void resetErrorLabels() {
        generalErrorLabel.setVisible(false);
    }

    private boolean validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            showError("Name cannot be empty.", nameErrorLabel);
            return false;
        }
        nameErrorLabel.setVisible(false); // Hide the error if valid
        return true;
    }

    private boolean validateAge(String ageText) {
        try {
            int age = Integer.parseInt(ageText);
            if (age < 0 || age > 120) {
                showError("Enter a valid age (0–120).", ageErrorLabel);
                return false;
            }
            ageErrorLabel.setVisible(false); // Hide the error if valid
            return true;
        } catch (NumberFormatException e) {
            showError("Age must be a number.", ageErrorLabel);
            return false;
        }
    }

    private boolean validateStudentID(String idText) {
        try {
            Integer.parseInt(idText);
            studentIDErrorLabel.setVisible(false); // Hide the error if valid
            return true;
        } catch (NumberFormatException e) {
            showError("Student ID must be a number.", studentIDErrorLabel);
            return false;
        }
    }

    private boolean validateTeacherID(String idText) {
        try {
            Integer.parseInt(idText);
            return true;
        } catch (NumberFormatException e) {
            showError("Teacher ID must be a number.", generalErrorLabel);
            return false;
        }
    }

    private boolean validateAdminID(String idText) {
        try {
            Integer.parseInt(idText);
            return true;
        } catch (NumberFormatException e) {
            showError("Admin ID must be a number.", generalErrorLabel);
            return false;
        }
    }

    private boolean validateChildID(String idText) {
        try {
            Integer.parseInt(idText);
            return true;
        } catch (NumberFormatException e) {
            showError("Child ID must be a number.", generalErrorLabel);
            return false;
        }
    }


    private boolean validateEmail(String email) {
        if (email == null || !email.matches("^\\S+@\\S+\\.\\S+$")) {
            showError("Invalid email format.", emailErrorLabel);
            return false;
        }
        emailErrorLabel.setVisible(false); // Hide the error if valid
        return true;
    }

    private boolean validatePassword(String password) {
        if (password == null || password.length() < 6) {
            showError("Password must be at least 6 characters.", passwordErrorLabel);
            return false;
        }
        passwordErrorLabel.setVisible(false); // Hide the error if valid
        return true;
    }





}


