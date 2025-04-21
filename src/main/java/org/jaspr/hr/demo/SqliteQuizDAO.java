package org.jaspr.hr.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SqliteQuizDAO implements IQuizDAO {
    private Connection connection;

    public SqliteQuizDAO() {
        connection = SqliteConnection.getInstance();
        createQuizTable();

    }
    private void createQuizTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS quizzes ("
                    + "id INTEGER PRIMARY KEY AUTOINCREMENT"
                    + "title STRING NOT NULL,"
                    + "description STRING NOT NULL,"
                    + "topic STRING NOT NULL,"
                    + "numOfQuestions INTEGER NOT NULL,"
                    + "author INT,"
                    + ")";
            statement.execute(query);
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
    public Quiz getQuiz(Quiz quiz) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM quizzes WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Quiz(
                        id,
                        resultSet.getString("title"),
                        resultSet.getInt("description"),
                        resultSet.getInt("topic"),
                        resultSet.getInt("numOfQuestions"),
                        resultSet.getInt("author")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void addQuiz(Quiz quiz) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO quizzes (title, description, topic, numOfQuestions, author)" +
                    "VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, quiz.getTitle());
            statement.setString(2, quiz.getDescription());
            statement.setString(3, quiz.getTopic());
            statement.setInt(4, quiz.getNumOfQuestions());
            statement.setInt(5, quiz.getAuthor());

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void editQuiz(String email, String oldPassword, String newPassword, String role) {
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

    @Override
    public List<Quiz> getAllQuizzes(Teacher teacher) {

        List<Quiz> quizzes = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM quizzes WHERE author = ?");
            statement.setInt(1, teacher.getTeacherID());
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                Quiz quiz = new Quiz(
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("topic"),
                        resultSet.getInt("numOfQuestions"),
                        resultSet.getInt("author")
                );


                quizzes.add(quiz);
            }
            //TODO: Error Handling
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quizzes;

    }

    @Override
    public List<Quiz> getAllQuizzes(Student student) {
        return List.of();
    }

    @Override
    public List<Quiz> getAllQuizzes(Teacher teacher, Student student) {

        return List.of();
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




}

