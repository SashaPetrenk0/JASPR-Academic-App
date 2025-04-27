package org.jaspr.hr.demo;

import org.jaspr.hr.demo.users.Admin;
import org.jaspr.hr.demo.users.Parent;
import org.jaspr.hr.demo.users.Student;
import org.jaspr.hr.demo.users.Teacher;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SqliteUserDAO implements IUserDAO {
    private Connection connection;

    public SqliteUserDAO() {
        connection = SqliteConnection.getInstance();
        createStudentTable();
        createTeacherTable();
        createParentTable();
        createAdminTable();
    }

    private void createStudentTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS students ("
                    + "name STRING NOT NULL,"
                    + "age INTEGER NOT NULL,"
                    + "studentID INTEGER NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTeacherTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS teachers ("
                    + "name STRING NOT NULL,"
                    + "age INTEGER NOT NULL,"
                    + "teacherID INTEGER NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createParentTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS parents ("
                    + "name STRING NOT NULL,"
                    + "childName STRING NOT NULL,"
                    + "childID INTEGER NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createAdminTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS admins ("
                    + "name STRING NOT NULL,"
                    + "age INTEGER NOT NULL,"
                    + "adminID INTEGER NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isUserExists(String email) {
        try {
            // SQL query to check if a user with the provided email exists
            PreparedStatement stmt = connection.prepareStatement("SELECT 1 FROM students WHERE email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            // If the result set has any rows, the user already exists
            return rs.next();  // Returns true if there's a match, false otherwise
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // In case of error, assume user does not exist
        }
    }

    public boolean isStudentIDExists(int studentID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM students WHERE studentID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // if count > 0, studentID exists
                }
            }
        }
        return false; // default: ID does not exist
    }

    public boolean isTeacherIDExists(int teacherID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM teachers WHERE teacherID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, teacherID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // if count > 0, teacherID exists
                }
            }
        }
        return false; // default: ID does not exist
    }

    private boolean isChildIDExists(int studentID) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM students WHERE studentID = ?");
        stmt.setInt(1, studentID);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    private boolean isAdminIDExists(int adminID) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM admins WHERE adminID = ?");
        stmt.setInt(1, adminID);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }


    @Override
    public void addStudent(Student students) throws SQLException, IllegalArgumentException {
        if (isUserExists(students.getEmail())) {
            throw new IllegalArgumentException("Email already in use.");
        }
        if (isStudentIDExists(students.getStudentID())) {
            throw new IllegalArgumentException("Student ID already exists.");
        }

        // Insert new student into the database
        PreparedStatement stmt = connection.prepareStatement("INSERT INTO students (name, age, studentID, email, password) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, students.getName());
        stmt.setInt(2, students.getAge());
        stmt.setInt(3, students.getStudentID());
        stmt.setString(4, students.getEmail());
        stmt.setString(5, students.getPassword());
        stmt.executeUpdate();
    }

    @Override
    public void addTeacher(Teacher teacher) throws SQLException, IllegalArgumentException {
        if (isUserExists(teacher.getEmail())) {
            throw new IllegalArgumentException("Email already in use.");
        }
        if (isTeacherIDExists(teacher.getTeacherID())) {
            throw new IllegalArgumentException("Teacher ID already exists.");
        }

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO teachers (name, age, teacherID, email, password) VALUES (?, ?, ?, ?, ?)");
        stmt.setString(1, teacher.getName());
        stmt.setInt(2, teacher.getAge());
        stmt.setInt(3, teacher.getTeacherID());
        stmt.setString(4, teacher.getEmail());
        stmt.setString(5, teacher.getPassword());
        stmt.executeUpdate();
    }

    @Override
    public void addAdmin(Admin admin) throws SQLException, IllegalArgumentException {
        if (isUserExists(admin.getEmail())) {
            throw new IllegalArgumentException("Email already in use.");
        }
        if (isAdminIDExists(admin.getAdminID())) {
            throw new IllegalArgumentException("Admin ID already exists.");
        }

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO admins (name, age, adminID, email, password) VALUES (?, ?, ?, ?, ?)"
        );
        stmt.setString(1, admin.getName());
        stmt.setInt(2, admin.getAge());
        stmt.setInt(3, admin.getAdminID());
        stmt.setString(4, admin.getEmail());
        stmt.setString(5, admin.getPassword());
        stmt.executeUpdate();
    }

    @Override
    public void addParent(Parent parent) throws SQLException, IllegalArgumentException {
        if (isUserExists(parent.getEmail())) {
            throw new IllegalArgumentException("Email already in use.");
        }
        if (isStudentIDExists(parent.getChildID())) {  // Assuming child's ID must be unique among students
            throw new IllegalArgumentException("Child ID already linked to another account.");
        }

        PreparedStatement stmt = connection.prepareStatement(
                "INSERT INTO parents (name, childName, childID, email, password) VALUES (?, ?, ?, ?, ?)"
        );
        stmt.setString(1, parent.getName());
        stmt.setString(2, parent.getChildName());
        stmt.setInt(3, parent.getChildID());
        stmt.setString(4, parent.getEmail());
        stmt.setString(5, parent.getPassword());
        stmt.executeUpdate();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Pull all attributes of a specific student for Display Details functionality in profile
    @Override
    public Student getStudent(int studentID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE studentID = ?");
            statement.setInt(1, studentID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Student(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        studentID,
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Pull all attributes of a specific teacher for Display Details functionality in profile
    @Override
    public Teacher getTeacher(int teacherID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM teachers WHERE teacherID = ?");
            statement.setInt(1, teacherID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Teacher(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        teacherID,
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Pull all attributes of a specific admin for Display Details functionality in profile
    @Override
    public Admin getAdmin(int adminID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM admins WHERE adminID = ?");
            statement.setInt(1, adminID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Admin(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        adminID,
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Teacher getLoggedInTeacher(String email, String password){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM teachers WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Teacher(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("teacherID"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
            //TODO: Error Handling
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String Authenticate(String email, String password) {
        // Check authentication information against all four user tables
        String[] tables = {"students", "teachers", "parents", "admins"};
        for (String table : tables) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE email = ? AND password = ?");
                statement.setString(1, email);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    switch (table) {
                        case "students":
                            return "Student";
                        case "teachers":
                            return "Teacher";
                        case "parents":
                            return "Parent";
                        case "admins":
                            return "Admin";
                    }
                }
                //TODO: Error Handling
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public void changePassword(String email, String oldPassword, String newPassword, String role) {
        String tableName = "";

        switch (role){
            case "Student":
                tableName = "students";
                break;
            case "Teacher":
                tableName = "teachers";
                break;
            case "Admin":
                tableName = "admin";
                break;
            case "Parent":
                tableName = "parent";
                break;
            default:
                System.out.println("Invalid role provided.");
                return;
        }


        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE " + tableName + " SET password = ?, WHERE email = ? AND password = ?");
            statement.setString(1, newPassword);
            statement.setString(2, email);
            statement.setString(3, oldPassword);
            //TODO: Error Handling
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // For use when viewing student names in lists of quiz results etc.
    @Override
    public List<String> getAllStudentNames() {
        List<String> studentNames = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM students");
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String name = resultSet.getString("name");
                studentNames.add(name);
            }
            //TODO: Error Handling
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentNames;
    }

    // For use when a teacher is viewing their class list and details
    @Override
    public List<Student> getAllStudents(){
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int studentID = resultSet.getInt("studentID");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                Student student = new Student(name, age, studentID, email, password);
                students.add(student);
            }
            //TODO: Error Handling
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve students", e);
        }
        return students;
    }

    // For database checking
    public void printAllStudents() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("---- All Students ----");
            while (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("name") +
                        ", Age: " + resultSet.getInt("age") +
                        ", ID: " + resultSet.getInt("studentID") +
                        ", Email: " + resultSet.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // For database checking
    public void clearStudentsTable() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM students");
            statement.executeUpdate();
            System.out.println("All student records deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean hasAnyStudents() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT 1 FROM students LIMIT 1");
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasAnyTeachers() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT 1 FROM teachers LIMIT 1");
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasAnyParents() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT 1 FROM parents LIMIT 1");
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasAnyAdmins() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT 1 FROM admins LIMIT 1");
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasAnyRegisteredUsers() {
        return hasAnyStudents()
                || hasAnyTeachers()
                || hasAnyParents()
                || hasAnyAdmins();
    }



}

