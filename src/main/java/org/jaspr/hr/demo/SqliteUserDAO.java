package org.jaspr.hr.demo;

import org.jaspr.hr.demo.model.Contact;

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
                    + "userID INTEGER PRIMARY KEY AUTOINCREMENT"
                    + "name STRING NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL,"
                    + "role VARCHAR NOT NULL,"
                    + "age INTEGER NOT NULL,"
                    + "studentID INTEGER NOT NULL,"
                    + "teacherID INTEGER NOT NULL,"
                    + "adminID INTEGER NOT NULL,"
                    + "childID INTEGER NOT NULL,"
                    + "childName STRING NOT NULL,"
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
                    "studentID, teacherID, adminID, childID, childName) " +
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



    @Override
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM contacts";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                Contact contact = new Contact(firstName, lastName, phone, email);
                contact.setId(id);
                contacts.add(contact);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contacts;
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

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

    @Override
    public List<Student> getAllStudents() {
        return List.of();
    }
}
