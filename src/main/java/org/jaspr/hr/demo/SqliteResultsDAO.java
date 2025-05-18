package org.jaspr.hr.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqliteResultsDAO implements IResultsDAO {
    private Connection connection;

    public SqliteResultsDAO() {
        connection = SqliteConnection.getInstance();
        createQuestionResultsTable();
        createQuizResultsTable();
    }
    private void createQuestionResultsTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS questionResults (" +
                    "result_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "quiz_id INTEGER NOT NULL,"+
                    "question_id INTEGER NOT NULL,"+
                    "student_id INTEGER NOT NULL,"+
                    "grade INTEGER NOT NULL,"+
                    "FOREIGN KEY (quiz_id) REFERENCES quizzes(id)," +
                    "FOREIGN KEY (question_id) REFERENCES questions(question_id)," +
                    "FOREIGN KEY (student_id) REFERENCES questions(question_id)" +
                    ");";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createQuizResultsTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS quizResults (" +
                    "quiz_result_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "quiz_id INTEGER NOT NULL,"+
                    "student_id INTEGER NOT NULL,"+
                    "grade INTEGER NOT NULL,"+
                    "FOREIGN KEY (quiz_id) REFERENCES quizzes(id)," +
                    "FOREIGN KEY (student_id) REFERENCES questions(question_id)" +
                    ");";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addQuestionResult(int quizID, int questionID, int studentID, int grade) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO questionResults (quiz_id, question_id, student_id, grade)" +
                    "VALUES (?,?, ?, ?)");
            statement.setInt(1, quizID );
            statement.setInt(1, questionID);
            statement.setInt(1, studentID);
            statement.setInt(1, grade);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void addQuizResult(int quizID, int studentID, int grade) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO quizResults (quiz_id, student_id, grade)" +
                    "VALUES (?,?,?)");
            statement.setInt(1, quizID);
            statement.setInt(2, studentID);
            statement.setInt(3, grade);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Map<String, Integer>> getResultsByQuestion(int quizID, int studentID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT question_id, grade FROM questionResults WHERE quiz_id = ? AND student_id = ?");
            statement.setInt(1, quizID);
            statement.setInt(2, studentID);
            ResultSet resultSet = statement.executeQuery();
            List<Map<String, Integer>> results = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Integer> result = new HashMap<>();
                result.put("question_id", resultSet.getInt("question_id"));
                result.put("grade", resultSet.getInt("grade"));
                results.add(result);
            }

            System.out.print(results);
            return results;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Map<String, Integer>> getResultsByQuiz(int studentID) {
        System.out.print("method calles");
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT quiz_id, grade FROM quizResults WHERE student_id = ?");
            statement.setInt(1, studentID);
            ResultSet resultSet = statement.executeQuery();
            List<Map<String, Integer>> results = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Integer> result = new HashMap<>();
                result.put("quiz_id", resultSet.getInt("quiz_id"));
                result.put("grade", resultSet.getInt("grade"));
                results.add(result);
            }

            System.out.print(results);
            return results;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;



    }
}
