package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.List;

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

    @FXML
    private Button changePwd;

    @FXML
    private Button ReturnButton;

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
        else if (user instanceof Admin){
            Admin admin = (Admin) user;
            loadAdminProfile(admin);
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

        if (classroomNumbers == null || classroomNumbers.isEmpty()){
            enrollmentLabel.setText("No classroom assigned");
        }
        else {
            StringBuilder classroomText = new StringBuilder();
            for (Integer number : classroomNumbers) {
                classroomText.append("Classroom: ").append(number).append("\n");
            }
            enrollmentLabel.setText(classroomText.toString());
        }
        enrollmentLabel.setVisible(true);
    }

    private void loadTeacherProfile(Teacher teacher){
        nameLabel.setText("Name: " + teacher.getName());
        ageLabel.setText("Age: " + teacher.getAge());
        idLabel.setText("ID: " + teacher.getTeacherID());
        emailLabel.setText("Email: " + teacher.getEmail());
        //TODO: Hash the password, press a button to unhash it
        passwordLabel.setText("Password: " + teacher.getPassword());

        Integer classroomNumber = userDAO.getClassroomNumberForTeacher(teacher.getTeacherID());
        if (classroomNumber != null) {
            enrollmentLabel.setText("Classroom: " + classroomNumber);
        } else {
            enrollmentLabel.setText("No classroom assigned");
        }
        enrollmentLabel.setVisible(true);
    }

    private void loadAdminProfile(Admin admin){
        nameLabel.setText("Name: " + admin.getName());
        ageLabel.setText("Age: " + admin.getAge());
        idLabel.setText("ID: " + admin.getAdminID());
        emailLabel.setText("Email: " + admin.getEmail());
        //TODO: Hash the password, press a button to unhash it
        passwordLabel.setText("Password: " + admin.getPassword());
    }

    @FXML
    private void onChangePwdClicked(){
        try {
            Stage stage = (Stage) changePwd.getScene().getWindow(); // only once

            FXMLLoader loader = new FXMLLoader(getClass().getResource("change-password.fxml"));
            Parent root = loader.load();
            ChangePassword controller = loader.getController();
            controller.setCurrentUser(currentUser); // pass the logged in user

            // Set scene with specific width and height
            Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT); // width = 600, height = 400 (example)
            stage.setScene(scene); // just reuse the stage
            stage.show();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }



    @FXML
    private void onReturnClicked() {
        Stage stage = (Stage) ReturnButton.getScene().getWindow();
        String role = UserSession.getInstance().getRole();

        if ("Student".equals(role)) {
            // Reload the student data and show updated profile
            Student student = (Student) currentUser;
            student = userDAO.getStudent(student.getStudentID()); // Refresh student info
            setCurrentUser(student); // Set updated student info

            // Navigate to the student profile or dashboard
            SceneChanger.changeScene(stage, "student-dashboard-view.fxml");
        }
        // Add similar logic for Teacher and Admin
    }
}
