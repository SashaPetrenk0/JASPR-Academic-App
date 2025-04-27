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

    @FXML private Label teacherIDErrorLabel;
    @FXML private Label childIDErrorLabel;
    @FXML private Label adminIDErrorLabel;


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
            //int age = 0;

            switch (role){
                case "Student" -> {
                    resetErrorLabels();

                    name = nameFieldStudent.getText();
                    email = emailFieldStudent.getText();
                    password = passwordFieldStudent.getText();
                    String ageText = ageFieldStudent.getText().trim();
                    String studentIDText = studentIDField.getText().trim();

                    if (name.isEmpty() || email.isEmpty() || password.isEmpty() || ageText.isEmpty() || studentIDText.isEmpty()) {
                        showError("All fields must be filled out.", generalErrorLabel);
                        return;
                    }

                    if (!validateName(name) || !validateEmail(email) || !validatePassword(password)
                            || !validateAge(ageText) || !validateStudentID(studentIDText)) {
                        return;
                    }

                    try {
                        int age = Integer.parseInt(ageText);
                        int studentID = Integer.parseInt(studentIDText);

                        Student newStudent = new Student(name, age, studentID, email, password);
                        userDAO.addStudent(newStudent);

                        successfulSignUpLabelStudent.setText("Successful Student Registration! Welcome " + name + "!");
                        successfulSignUpLabelStudent.setVisible(true);
                    } catch (IllegalArgumentException e) {
                        showError(e.getMessage(), generalErrorLabel);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showError("Database error, please try again.", generalErrorLabel);
                    }
                }

                case "Teacher" -> {
                    resetErrorLabels();

                    name = nameFieldTeacher.getText();
                    email = emailFieldTeacher.getText();
                    password = passwordFieldTeacher.getText();
                    String ageText = ageFieldTeacher.getText().trim();
                    String teacherIDText = teacherIDField.getText().trim();

                    if (name.isEmpty() || email.isEmpty() || password.isEmpty() || ageText.isEmpty() || teacherIDText.isEmpty()) {
                        showError("All fields must be filled out.", generalErrorLabel);
                        return;
                    }

                    if (!validateName(name) || !validateEmail(email) || !validatePassword(password)
                            || !validateAge(ageText) || !validateTeacherID(teacherIDText)) {
                        return;
                    }

                    try {
                        int age = Integer.parseInt(ageText);
                        int teacherID = Integer.parseInt(teacherIDText);

                        Teacher newTeacher = new Teacher(name, age, teacherID, email, password);
                        userDAO.addTeacher(newTeacher);

                        successfulSignUpLabelTeacher.setText("Successful Teacher Registration! Welcome " + name + "!");
                        successfulSignUpLabelTeacher.setVisible(true);
                    } catch (IllegalArgumentException e) {
                        showError(e.getMessage(), generalErrorLabel);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showError("Database error, please try again.", generalErrorLabel);
                    }
                }


                case "Parent" -> {
                    resetErrorLabels();

                    name = nameFieldParent.getText();
                    String child = childNameField.getText();
                    String childIDText = childIDField.getText().trim();
                    email = emailFieldParent.getText();
                    password = passwordFieldParent.getText();

                    if (name.isEmpty() ||
                            child.isEmpty() ||
                            childIDText.isEmpty() ||
                            email.isEmpty() ||
                            password.isEmpty()) {
                        showError("All fields must be filled out.", generalErrorLabel);
                        return;
                    }

                    if (!validateName(name) ||
                            !validateName(child) ||
                            !validateEmail(email) ||
                            !validatePassword(password) ||
                            !validateStudentID(childIDText)) {
                        return;
                    }

                    try {
                        int childID = Integer.parseInt(childIDText);

                        Parent newParent = new Parent(name, child, childID, email, password);
                        userDAO.addParent(newParent);

                        successfulSignUpLabelParent.setText("Successful Parent Registration! Welcome " + name + "!");
                        successfulSignUpLabelParent.setVisible(true);
                    } catch (IllegalArgumentException e) {
                        showError(e.getMessage(), generalErrorLabel);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showError("Database error, please try again.", generalErrorLabel);
                    }
                }

                case "Admin" -> {
                    resetErrorLabels();

                    name = nameFieldAdmin.getText();
                    email = emailFieldAdmin.getText();
                    password = passwordFieldAdmin.getText();
                    String ageText = ageFieldAdmin.getText().trim();
                    String adminIDText = adminIDField.getText().trim();

                    if (name.isEmpty() ||
                            email.isEmpty() ||
                            password.isEmpty() ||
                            ageText.isEmpty() ||
                            adminIDText.isEmpty()) {
                        showError("All fields must be filled out.", generalErrorLabel);
                        return;
                    }

                    if (!validateName(name) ||
                            !validateEmail(email) ||
                            !validatePassword(password) ||
                            !validateAge(ageText) ||
                            !validateAdminID(adminIDText)) {
                        return;
                    }

                    try {
                        int age = Integer.parseInt(ageText);
                        int adminID = Integer.parseInt(adminIDText);

                        Admin newAdmin = new Admin(name, age, adminID, email, password);
                        userDAO.addAdmin(newAdmin);

                        successfulSignUpLabelAdmin.setText("Successful Administrator Registration! Welcome " + name + "!");
                        successfulSignUpLabelAdmin.setVisible(true);
                    } catch (IllegalArgumentException e) {
                        showError(e.getMessage(), generalErrorLabel);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        showError("Database error, please try again.", generalErrorLabel);
                    }
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
        nameErrorLabel.setVisible(false);
        ageErrorLabel.setVisible(false);
        studentIDErrorLabel.setVisible(false);
        teacherIDErrorLabel.setVisible(false);
        childIDErrorLabel.setVisible(false);
        adminIDErrorLabel.setVisible(false);
        emailErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);
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
            int id = Integer.parseInt(idText);
            if (idText.length() != 5) {
                showError("Student ID must be exactly 5 digits.", studentIDErrorLabel);
                return false;
            }
            studentIDErrorLabel.setVisible(false);
            return true;
        } catch (NumberFormatException e) {
            showError("Student ID must be a number.", studentIDErrorLabel);
            return false;
        }
    }

    private boolean validateTeacherID(String idText) {
        try {
            int id = Integer.parseInt(idText);
            if (idText.length() != 5) {
                showError("Teacher ID must be exactly 5 digits.", teacherIDErrorLabel);
                return false;
            }
            teacherIDErrorLabel.setVisible(false);
            return true;
        } catch (NumberFormatException e) {
            showError("Teacher ID must be a number.", teacherIDErrorLabel);
            return false;
        }
    }

    private boolean validateChildID(String idText) {
        try {
            int id = Integer.parseInt(idText);
            if (idText.length() != 5) {
                showError("Child ID must be exactly 5 digits.", childIDErrorLabel);
                return false;
            }
            childIDErrorLabel.setVisible(false);
            return true;
        } catch (NumberFormatException e) {
            showError("Child ID must be a number.", childIDErrorLabel);
            return false;
        }
    }

    private boolean validateAdminID(String idText) {
        try {
            int id = Integer.parseInt(idText);
            if (idText.length() != 5) {
                showError("Admin ID must be exactly 5 digits.", adminIDErrorLabel);
                return false;
            }
            adminIDErrorLabel.setVisible(false);
            return true;
        } catch (NumberFormatException e) {
            showError("Admin ID must be a number.", adminIDErrorLabel);
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


