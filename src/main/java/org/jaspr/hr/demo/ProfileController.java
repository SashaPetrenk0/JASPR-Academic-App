package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ProfileController {

    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    @FXML
    private TextField nameLabel;

    @FXML
    private TextField ageLabel;

    @FXML
    private TextField idLabel;

    @FXML
    private TextField emailLabel;



    @FXML
    private Label enrollmentLabel;

    @FXML
    private Button changePwd;

    @FXML
    private Button ReturnButton;

    // Either student, teacher, admin, parent
    private Object currentUser;

    public void setCurrentUser(Object user) {
        this.currentUser = user;

        if (user instanceof Student) {
            Student student = (Student) user;
            loadStudentProfile(student);
        } else if (user instanceof Teacher) {
            Teacher teacher = (Teacher) user;
            loadTeacherProfile(teacher);
        } else if (user instanceof Admin) {
            Admin admin = (Admin) user;
            loadAdminProfile(admin);
        }
    }

    public String getRole() {
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

    private void loadStudentProfile(Student student) {
        nameLabel.setText("" + student.getName());
        ageLabel.setText("" + student.getAge());
        idLabel.setText("" + student.getStudentID());
        emailLabel.setText("" + student.getEmail());

        List<Integer> classroomNumbers = userDAO.getClassroomNumbersForStudent(student.getStudentID());

        if (classroomNumbers == null || classroomNumbers.isEmpty()) {
            enrollmentLabel.setText("No classroom assigned");
        } else {
            StringBuilder classroomText = new StringBuilder();
            for (Integer number : classroomNumbers) {
                classroomText.append("Classroom: ").append(number).append("\n");
            }
            enrollmentLabel.setText(classroomText.toString());
        }
        enrollmentLabel.setVisible(true);
    }

    private void loadTeacherProfile(Teacher teacher) {
        nameLabel.setText("" + teacher.getName());
        ageLabel.setText("" + teacher.getAge());
        idLabel.setText("" + teacher.getTeacherID());
        emailLabel.setText("" + teacher.getEmail());

        Integer classroomNumber = userDAO.getClassroomNumberForTeacher(teacher.getTeacherID());
        if (classroomNumber != null) {
            enrollmentLabel.setText("Classroom: " + classroomNumber);
        } else {
            enrollmentLabel.setText("No classroom assigned");
        }
        enrollmentLabel.setVisible(true);
    }

    private void loadAdminProfile(Admin admin) {
        nameLabel.setText("Name: " + admin.getName());
        ageLabel.setText("Age: " + admin.getAge());
        idLabel.setText("ID: " + admin.getAdminID());
        emailLabel.setText("Email: " + admin.getEmail());
    }

    @FXML
    private void onChangePwdClicked() {
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onReturnClicked() throws IOException {
        // Ensure currentUser is updated before navigating to the dashboard
        if (currentUser instanceof Student) {
            currentUser = userDAO.getStudent(((Student) currentUser).getStudentID());  // Reload student data
        } else if (currentUser instanceof Teacher) {
            currentUser = userDAO.getTeacher(((Teacher) currentUser).getTeacherID());  // Reload teacher data
        } else if (currentUser instanceof Admin) {
            currentUser = userDAO.getAdmin(((Admin) currentUser).getAdminID());  // Reload admin data
        }
        // Get the user's role and navigate accordingly
        String role = getRole();
        FXMLLoader loader = null;
        Parent root = null;

        if ("Student".equals(role)) {
            loader = new FXMLLoader(getClass().getResource("/org/jaspr/hr/demo/student-dashboard-view.fxml"));
            root = loader.load();
            StudentDashboardController controller = loader.getController();
            controller.setCurrentUser(currentUser);
        } else if ("Teacher".equals(role)) {
            loader = new FXMLLoader(getClass().getResource("/org/jaspr/hr/demo/teacher-dashboard-view.fxml"));
            root = loader.load();
            TeacherDashboardController controller = loader.getController();
            controller.setCurrentUser(currentUser);
        } else if ("Admin".equals(role)) {
            loader = new FXMLLoader(getClass().getResource("/org/jaspr/hr/demo/admin-dashboard-view.fxml"));
            root = loader.load();
            AdminController controller = loader.getController();
            controller.setCurrentUser(currentUser);
        }

        // Set the scene to the updated dashboard
        Stage stage = (Stage) ReturnButton.getScene().getWindow();
        stage.setScene(new Scene(root, SceneChanger.WIDTH, SceneChanger.HEIGHT));
        stage.show();
    }

}

