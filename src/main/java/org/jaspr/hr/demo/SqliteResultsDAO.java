package org.jaspr.hr.demo;

import java.sql.Connection;
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
    public void addQuestionResult(int studentID, int quizID, int questionID, int grade) {

    }
    @Override
    public void addQuizResult(int studentID, int quizID, int questionID, int grade) {

    }

    @Override
    public void getResultsByQuestion(int questionId, int correct) {

    }

    @Override
    public void getResultsByQuiz(int quizId) {

    }
}
