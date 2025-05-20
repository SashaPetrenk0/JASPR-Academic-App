package org.jaspr.hr.demo;

import javafx.beans.binding.ObjectBinding;

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
            statement.setInt(2, questionID);
            statement.setInt(3, studentID);
            statement.setInt(4, grade);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void addQuizResult(int quizID, int studentID, double grade) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO quizResults (quiz_id, student_id, grade)" +
                    "VALUES (?,?,?)");
            statement.setInt(1, quizID);
            statement.setInt(2, studentID);
            statement.setInt(3, (int) grade);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Map<String, Object>> getResultsByQuestion(int quizID, int studentID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT questions.question, questionResults.grade FROM questionResults INNER JOIN questions ON questionResults.question_id=questions.question_id WHERE questionResults.quiz_id = ? AND questionResults.student_id = ?");
            statement.setInt(1, quizID);
            statement.setInt(2, studentID);
            ResultSet resultSet = statement.executeQuery();
            List<Map<String, Object>> results = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> result = new HashMap<>();
                result.put("question", resultSet.getString("question"));
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
    public Integer getGrade (int quizID, int studentID) {
      
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT grade FROM quizResults WHERE quiz_id = ? AND student_id = ? ORDER BY quiz_result_id DESC LIMIT 1;");
            statement.setInt(1, quizID);
            statement.setInt(2, studentID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("grade");
            } else {
                return null; // No grade found
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
      return null;
        
    }

    @Override
    public List<Map<String, Object>> getResultsByQuiz(int studentID) {
        System.out.print("method calles");
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT quizzes.title, quizResults.grade FROM quizResults INNER JOIN quizzes ON quizResults.quiz_id=quizzes.id WHERE quizResults.student_id = ?;");
            statement.setInt(1, studentID);
            ResultSet resultSet = statement.executeQuery();
            List<Map<String, Object>> results = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> result = new HashMap<>();
                result.put("title", resultSet.getString("title"));
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
