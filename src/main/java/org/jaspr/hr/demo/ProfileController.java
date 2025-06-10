package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

/**
 * Controller class responsible for displaying user profile data and handling UI interactions related to profile viewing
 */
public class ProfileController {

    /** DAO for user related queries */
    private final SqliteUserDAO userDAO = new SqliteUserDAO();

    /** DAO for classroom related queries */
    private final SqliteClassroomDAO classroomDAO = new SqliteClassroomDAO();

    @FXML
    private TextField nameLabel;

    @FXML
    private TextField ageLabel;

    @FXML
    private TextField idLabel;

    @FXML
    private TextField emailLabel;

    @FXML
    private Label nameLabel1;

    @FXML
    private Label enrollmentLabel;

    @FXML
    private TextField classroomField;

    @FXML
    private Button changePwd;

    @FXML
    private Button ReturnButton;

    // Either student, teacher, admin, parent
    private Object currentUser;

    /**
     * Sets the current user and loads the appropriate profile.
     * @param user the logged-in user
     */
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

    /**
     * Returns the role of the current user as a string.
     * @return the user's role
     */
    public String getRole() {
        if (currentUser instanceof Student) {
            return "Student";
        } else if (currentUser instanceof Teacher) {
            return "Teacher";
        } else if (currentUser instanceof Admin) {
            return "Admin";
        }
        return null;
    }

    /**
     * Fills shared profile fields for any role
     * @param name name of user
     * @param age age of user
     * @param id unique identifier of user (studentID, teacherID, or adminID)
     * @param email email of user
     */
    private void populateCommonFields(String name, int age, int id, String email) {
        nameLabel.setText(name);
        ageLabel.setText(String.valueOf(age));
        idLabel.setText(String.valueOf(id));
        emailLabel.setText(email);
    }

    /**
     * Loads student-specific profile details into the UI.
     * @param student the student whose profile is displayed
     */
    private void loadStudentProfile(Student student) {
        populateCommonFields(student.getName(), student.getAge(), student.getStudentID(), student.getEmail());
        nameLabel1.setText("Welcome, " + student.getName() + "!");

        List<Integer> classroomNumbers = classroomDAO.getClassroomNumbersForStudent(student.getStudentID());

        if (classroomNumbers != null && !classroomNumbers.isEmpty()) {
            String text = "Classroom: " + classroomNumbers.toString().replaceAll("[\\[\\]]", "");
            classroomField.setText(text);
        } else {
            classroomField.setText("No classroom assigned");
        }

        enrollmentLabel.setVisible(false);
    }


    /**
     * Loads teacher-specific profile details into the UI.
     * @param teacher the teacher whose profile is displayed
     */
    private void loadTeacherProfile(Teacher teacher) {
        populateCommonFields(teacher.getName(), teacher.getAge(), teacher.getTeacherID(), teacher.getEmail());
        nameLabel1.setText("Welcome, " + teacher.getName() + "!");

        List<Integer> classroomNumber = classroomDAO.getClassroomNumberForTeacher(teacher.getTeacherID());

        if (classroomNumber != null && !classroomNumber.isEmpty()) {
            String text = "Classroom: " + classroomNumber.toString().replaceAll("[\\[\\]]", ""); // e.g. "Classroom: 12"
            classroomField.setText(text);
        } else {
            classroomField.setText("No classroom assigned");
        }

        enrollmentLabel.setVisible(false);
    }

    /**
     * Loads admin-specific profile details into the UI.
     * @param admin the admin whose profile is displayed
     *
     */
    private void loadAdminProfile(Admin admin) {
        populateCommonFields(admin.getName(), admin.getAge(), admin.getAdminID(), admin.getEmail());
        nameLabel1.setText("Welcome, " + admin.getName() + "!");
    }


    /**
     * Called when the "Change Password" button is clicked.
     * Loads change password fxml page and passes the current user
     */
    @FXML
    private void onChangePwdClicked() {
        try {
            Stage stage = (Stage) changePwd.getScene().getWindow(); // only once

            FXMLLoader loader = new FXMLLoader(getClass().getResource("change-password.fxml"));
            Parent root = loader.load();
            ChangePassword controller = loader.getController();
            controller.setCurrentUser(currentUser); // pass the logged in user

            // Set scene with specific width and height
            Scene scene = new Scene(root, HelloApplication.WIDTH, HelloApplication.HEIGHT);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when the "Change Password" button is clicked.
     * Reloads user data and navigates to their respective dashboard.
     * @throws IOException if fxml cannot load
     */
    @FXML
    private void onReturnClicked() throws IOException {
        refreshCurrentUser();
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

    /**
     * Refreshes the current user by reloading their data from the database.
     */
    private void refreshCurrentUser() {
        if (currentUser instanceof Student s) {
            currentUser = userDAO.getStudent(s.getStudentID());
        } else if (currentUser instanceof Teacher t) {
            currentUser = userDAO.getTeacher(t.getTeacherID());
        } else if (currentUser instanceof Admin a) {
            currentUser = userDAO.getAdmin(a.getAdminID());
        }
    }

}

