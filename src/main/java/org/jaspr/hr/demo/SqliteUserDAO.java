package org.jaspr.hr.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO class for managing user data in an SQLite database.
 * Handles creation of user tables and CRUD operations for students, teachers, and admins.
 */
public class SqliteUserDAO {
    private final Connection connection;

    /**
     * Constructor initialises the SQLite connection and creates all required tables.
     */
    public SqliteUserDAO() {
        connection = SqliteConnection.getInstance();
                createStudentTable();
                createTeacherTable();
                createAdminTable();
    }

    /**
     * Creates the students table if it does not exist with name, age, studentID, email, hashed password, and salt columns.
     */
    private void createStudentTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS students ("
                    + "name STRING NOT NULL,"
                    + "age INTEGER NOT NULL,"
                    + "studentID INTEGER PRIMARY KEY NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "passwordHash VARCHAR NOT NULL,"
                    + "salt TEXT NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the teacher table if it does not exist with name, age, teacherID, email, hashed password, and salt columns.
     */
    private void createTeacherTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS teachers ("
                    + "name STRING NOT NULL,"
                    + "age INTEGER NOT NULL,"
                    + "teacherID INTEGER PRIMARY KEY NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "passwordHash VARCHAR NOT NULL,"
                    + "salt TEXT NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the admin table if it does not exist with name, age, adminID, email, hashed password, and salt columns.
     */
    private void createAdminTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS admins ("
                    + "name STRING NOT NULL,"
                    + "age INTEGER NOT NULL,"
                    + "adminID INTEGER PRIMARY KEY NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "passwordHash VARCHAR NOT NULL,"
                    + "salt TEXT NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a student into the database.
     * @param student the student object to add
     * @param password the hashed password
     * @param salt the salt used for hashing
     */
    public void addStudent(Student student, String password, String salt) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO students (name, age, studentID, email, passwordHash, salt)" +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setInt(3, student.getStudentID());
            statement.setString(4, student.getEmail());
            statement.setString(5, password);
            statement.setString(6, salt);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new teacher to the database.
     * @param teacher the teacher object to add
     * @param password the hashed password
     * @param salt the salt used for hashing
     */
    public void addTeacher(Teacher teacher, String password, String salt) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO teachers (name, age, teacherID, email, passwordHash, salt)" +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, teacher.getName());
            statement.setInt(2, teacher.getAge());
            statement.setInt(3, teacher.getTeacherID());
            statement.setString(4, teacher.getEmail());
            statement.setString(5, password);
            statement.setString(6, salt);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new admin to the database.
     * @param admin the admin object to add
     * @param password the hashed password
     * @param salt the salt used for hashing
     */
    public void addAdmin(Admin admin, String password, String salt) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO admins (name, age, adminID, email, passwordHash, salt)" +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, admin.getName());
            statement.setInt(2, admin.getAge());
            statement.setInt(3, admin.getAdminID());
            statement.setString(4, admin.getEmail());
            statement.setString(5, password);
            statement.setString(6, salt);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves a student by ID.
     * @param studentID the ID of the student
     * @return the matching Student object or null if not found
     */
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
                        resultSet.getString("email")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Retrieves a teacher by ID.
     * @param teacherID the ID of the teacher
     * @return the matching Teacher object or null if not found
     */
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
                        resultSet.getString("email")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Retrieves an admin by ID.
     * @param adminID the ID of the admin
     * @return the matching Admin object or null if not found
     */
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
                        resultSet.getString("email")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Authenticates and returns a user object (Student, Teacher, or Admin) based on role, email, and password.
     * @param email the user's email
     * @param password the hashed password
     * @param role the role of the user: "Student", "Teacher", or "Admin"
     * @return the matching user object as an Object (cast as needed), or null if not found
     */
    public Object getLoggedInUser(String email, String password, String role) {
        String tableName;
        String idColumn;

        switch (role) {
            case "Student":
                tableName = "students";
                idColumn = "studentID";
                break;
            case "Teacher":
                tableName = "teachers";
                idColumn = "teacherID";
                break;
            case "Admin":
                tableName = "admins";
                idColumn = "adminID";
                break;
            default:
                return null; // Invalid role
        }

        try {
            String query = "SELECT * FROM " + tableName + " WHERE email = ? AND passwordHash = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String name = rs.getString("name");
                int age = rs.getInt("age");
                int id = rs.getInt(idColumn);
                String userEmail = rs.getString("email");

                switch (role) {
                    case "Student":
                        return new Student(name, age, id, userEmail);
                    case "Teacher":
                        return new Teacher(name, age, id, userEmail);
                    case "Admin":
                        return new Admin(name, age, id, userEmail);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Authenticates provided email and password against database of registered users
     * @param email the user's email
     * @param password the hashed password
     * @return role "Student", "Teacher", or "Admin" if found, otherwise null
     */
    public String Authenticate(String email, String password) {
        // Check authentication information against all four user tables
        String[] tables = {"students", "teachers", "admins"};
        for (String table : tables) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE email = ? AND passwordHash = ?");
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


    /**
     * Retrieves the salt for a given user's email.
     * @param email the user's email
     * @return the salt string or null if not found
     */
    public String getSalt(String email) {
        String[] tables = {"students", "teachers", "admins", "parent"};

        try {
            for (String table : tables) {
                String sql = "SELECT salt FROM " + table + " WHERE email = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, email);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // Found the user
                    System.out.println("User found in table: " + table);
                    return resultSet.getString("salt");
                }
            }
            // If no user found
            System.out.println("No user found with email: " + email);
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Changes the password for a user after verifying the old password.
     * @param email the user's email
     * @param oldPassword the current hashed password
     * @param newPassword the new hashed password
     * @param role the user's role
     * @return true if the password was changed successfully, false otherwise
     */
    public boolean changePassword(String email, String oldPassword, String newPassword, String role) {
        String tableName = "";

        switch (role) {
            case "Student":
                tableName = "students";
                break;
            case "Teacher":
                tableName = "teachers";
                break;
            case "Admin":
                tableName = "admins";
                break;
            case "Parent":
                tableName = "parent";
                break;
            default:
                System.out.println("Invalid role provided.");
                return false;
        }

        try {
            // First check if the old password matches the one in the database
            String checkPasswordQuery = "SELECT passwordHash FROM " + tableName + " WHERE email = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkPasswordQuery);
            checkStatement.setString(1, email);

            ResultSet rs = checkStatement.executeQuery();

            if (rs.next()) {
                String currentPasswordInDb = rs.getString("passwordHash");

                if (currentPasswordInDb.equals(oldPassword)) {
                    // Password matches, now update it
                    String updateQuery = "UPDATE " + tableName + " SET passwordHash = ? WHERE email = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setString(1, newPassword);
                    updateStatement.setString(2, email);

                    int rowsUpdated = updateStatement.executeUpdate();
                    return rowsUpdated > 0;  // Ensure at least one row is updated
                } else {
                    System.out.println("Old password does not match.");
                    return false;
                }
            } else {
                System.out.println("Email not found.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Retrieves all students from the database.
     * @return a list of all Student objects
     */

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int studentID = resultSet.getInt("studentID");
                String email = resultSet.getString("email");
                String password = resultSet.getString("passwordHash");

                Student student = new Student(name, age, studentID, email);
                students.add(student);
            }
            //TODO: Error Handling
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }


    /**
     * Retrieves all teachers from the database.
     * @return an ObservableList of all Teacher objects
     */
    public ObservableList<Teacher> getAllTeachers() {
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        String query = "SELECT * FROM teachers";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getInt("teacherID"),
                        rs.getString("email")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }

    /**
     * Checks if there are any students in the database.
     * @return true if at least one student exists, false otherwise
     */
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

    /**
     * Checks if there are any teachers in the database.
     * @return true if at least one teacher exists, false otherwise
     */
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

    /**
     * Checks if there are any admins in the database.
     * @return true if at least one admin exists, false otherwise
     */
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

    /**
     * Checks if there are any users in the database.
     * @return true if any user exists, false otherwise
     */
    public boolean hasAnyRegisteredUsers() {
        return hasAnyStudents()
                || hasAnyTeachers()
                || hasAnyAdmins();
    }




}





