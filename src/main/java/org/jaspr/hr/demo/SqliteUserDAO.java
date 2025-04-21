package org.jaspr.hr.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SqliteUserDAO implements IUserDAO {
    private Connection connection;

    public SqliteUserDAO() {
        connection = SqliteConnection.getInstance();
        createTable();
    }

    private void createTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS users ("
                    + "userID INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name STRING NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL,"
                    + "role VARCHAR NOT NULL,"
                    + "age INTEGER,"
                    + "studentID INTEGER,"
                    + "teacherID INTEGER"
                    + "adminID INTEGER,"
                    + "childID INTEGER,"
                    + "childName STRING"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addUser(User user) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO users (name, email, password, role, age, " +
                    "studentID, teacherID, adminID, childID, childName)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole());

            statement.setNull(5, java.sql.Types.INTEGER); // age
            statement.setNull(6, java.sql.Types.INTEGER); // studentID
            statement.setNull(7, java.sql.Types.INTEGER); // teacherID
            statement.setNull(8, java.sql.Types.INTEGER); // adminID
            statement.setNull(9, java.sql.Types.VARCHAR); // childID
            statement.setNull(10, java.sql.Types.VARCHAR); // childName

            // Role specific values
            if (user instanceof Student student){
                statement.setInt(5, student.getAge());
                statement.setInt(6, student.getStudentID());
            } else if (user instanceof Teacher teacher){
                statement.setInt(5, teacher.getAge());
                statement.setInt(7, teacher.getTeacherID());
            } else if (user instanceof Admin admin){
                statement.setInt(5, admin.getAge());
                statement.setInt(8, admin.getAdminID());
            } else if (user instanceof Parent parent){
                statement.setInt(9, parent.getChildID());
                statement.setString(10, parent.getChildName());
            }

            statement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE role = 'Student' AND studentID = ?");
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE role = 'Teacher' AND teacherID = ?");
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE role = 'Admin' AND adminID = ?");
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
    public void changePassword(String email, String oldPassword, String newPassword) {
        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE users SET password = ?, WHERE email = ? AND password = ?");
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
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM users WHERE role = 'Student'");
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE role = 'Student'");
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
            e.printStackTrace();
        }
        return students;
    }

}
