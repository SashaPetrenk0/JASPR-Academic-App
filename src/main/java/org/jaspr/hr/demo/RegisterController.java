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
    private ImageView successIcon;





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
        private void onSubmitClicked(){
            String role = roleComboBox.getValue();
            String name, email, password;
            int age = 0;

            switch (role){
                case "Student" -> {
                    name = nameFieldStudent.getText();
                    email = emailFieldStudent.getText();
                    password = passwordFieldStudent.getText();
                    age = Integer.parseInt(ageFieldStudent.getText().trim());
                    int studentID = Integer.parseInt(studentIDField.getText().trim());

                    String salt = PasswordUtility.generateSalt();
                    String hashedPassword = PasswordUtility.hashPassword(password, salt);

                    Student newStudent = new Student(name, age, studentID, email);
                    userDAO.addStudent(newStudent, hashedPassword, salt);
                    successfulSignUpLabelStudent.setText("Successful Student Registration! Welcome " + name + "!");
                    successfulSignUpLabelStudent.setVisible(true);
                    submitButtonStudent.setDisable(true);
                    
                }
                case "Teacher" -> {
                    name = nameFieldTeacher.getText();
                    email = emailFieldTeacher.getText();
                    password = passwordFieldTeacher.getText();
                    age = Integer.parseInt(ageFieldTeacher.getText().trim());
                    int teacherID = Integer.parseInt(teacherIDField.getText().trim());

                    String salt = PasswordUtility.generateSalt();
                    String hashedPassword = PasswordUtility.hashPassword(password, salt);

                    Teacher newTeacher = new Teacher(name, age, teacherID, email);
                    userDAO.addTeacher(newTeacher, hashedPassword, salt);
                    successfulSignUpLabelTeacher.setText("Successful Teacher Registration! Welcome " + name + "!");
                    successfulSignUpLabelTeacher.setVisible(true);
                    submitButtonTeacher.setVisible(false);
                    submitButtonTeacher.setManaged(false);

                    successfulSignUpLabelTeacher.setVisible(true);
                    successfulSignUpLabelTeacher.setManaged(true);

                    successIcon.setVisible(true);
                    successIcon.setManaged(true);


                }
                case "Parent" -> {
                    name = nameFieldParent.getText();
                    String child = childNameField.getText();
                    int childID = Integer.parseInt(childIDField.getText().trim());
                    email = emailFieldParent.getText();
                    password = passwordFieldParent.getText();

                    String salt = PasswordUtility.generateSalt();
                    String hashedPassword = PasswordUtility.hashPassword(password, salt);

                    Parent newParent = new Parent(name, child, childID, email);
                    userDAO.addParent(newParent, hashedPassword, salt);
                    successfulSignUpLabelParent.setText("Successful Parent Registration! Welcome " + name + "!");
                    successfulSignUpLabelParent.setVisible(true);
                    submitButtonParent.setVisible(false);
                    submitButtonParent.setManaged(false);

                    successfulSignUpLabelParent.setVisible(true);
                    successfulSignUpLabelParent.setManaged(true);

                    successIcon3.setVisible(true);
                    successIcon3.setManaged(true);



                }
                case "Admin" -> {
                    name = nameFieldAdmin.getText();
                    email = emailFieldAdmin.getText();
                    password = passwordFieldAdmin.getText();
                    age = Integer.parseInt(ageFieldAdmin.getText().trim());
                    int adminID = Integer.parseInt(adminIDField.getText().trim());

                    String salt = PasswordUtility.generateSalt();
                    String hashedPassword = PasswordUtility.hashPassword(password, salt);

                    Admin newAdmin = new Admin(name, age, adminID, email);
                    userDAO.addAdmin(newAdmin, hashedPassword, salt);
                    successfulSignUpLabelAdmin.setText("Successful Administrator Registration! Welcome " + name + "!");
                    successfulSignUpLabelAdmin.setVisible(true);
                    submitButtonAdmin.setVisible(false);
                    submitButtonAdmin.setManaged(false);

                    successfulSignUpLabelAdmin.setVisible(true);
                    successfulSignUpLabelAdmin.setManaged(true);

                    successIcon4.setVisible(true);
                    successIcon4.setManaged(true);

                    // TODO: Error handling for incorrect user inputs
                }

            }



        }

    @FXML private void returnToHomePage() throws IOException {
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "hello-view.fxml");

    }


}


