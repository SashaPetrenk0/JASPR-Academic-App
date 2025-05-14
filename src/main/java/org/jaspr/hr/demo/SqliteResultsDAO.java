package org.jaspr.hr.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

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
                    "FOREIGN KEY (student_id) REFERENCES questions(question_id)," +
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
                    "student_id INTEGER NOT NULL"+
                    "grade INTEGER NOT NULL,"+
                    "FOREIGN KEY (quiz_id) REFERENCES quizzes(id)," +
                    "FOREIGN KEY (student_id) REFERENCES questions(question_id)," +
                    ");";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void addQuestionResult(Student student, Quiz quiz, Question question, int grade) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO questionResults (quiz_id, question_id, student_id, grade)" +
                    "VALUES (?,?, ?, ?)");
            statement.setInt(1, quiz.getId());
            statement.setInt(1, question.getId());
            statement.setInt(1, student.getStudentID());
            statement.setInt(1, grade);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public void addQuizResult(Student student, Quiz quiz, int grade) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO quizResults (quiz_id, student_id, grade)" +
                    "VALUES (?,?, ?)");
            statement.setInt(1, quiz.getId());
            statement.setInt(1, student.getStudentID());
            statement.setInt(1, grade);

            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getResultsByQuestion(int questionId, int correct) {

    }

    @Override
    public void getResultsByQuiz(int quizId) {

    }
}
