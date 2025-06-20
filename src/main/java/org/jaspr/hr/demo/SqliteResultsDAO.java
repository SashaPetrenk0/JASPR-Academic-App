package org.jaspr.hr.demo;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class to handle database interactions related to storing quiz and question results
 */
public class SqliteResultsDAO  {
    private final Connection connection;

    /**
     * Open database connection and create tables
     */
    public SqliteResultsDAO() {
        connection = SqliteConnection.getInstance();
        createQuestionResultsTable();
        createQuizResultsTable();
    }

    /**
     * Create the question results table if it doesn't already exist
     * Store a student's result for individual questions in this table
     */
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
                    "FOREIGN KEY (student_id) REFERENCES students(studentID)" +
                    ");";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the quiz results table is one doesn't exist to store the students' results for quizzes
     */
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
                    "FOREIGN KEY (student_id) REFERENCES students(studentID)" +
                    ");";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Add a question result to the database
     * @param quizID id of the quiz that the question belongs to
     * @param questionID id of the question being answered
     * @param studentID id of the student who answered the question
     * @param grade grade, 1 for correct or 0 for incorrect
     */
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

    /**
     * Add a quiz result to the database
     * @param quizID id of the quiz
     * @param studentID id of the student who took the quiz
     * @param grade percentage of correct answers
     */
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

    /**
     * Fetch the results for each question in a given quiz taken by a given student
     * @param quizID the id of the quiz that the questions belong to
     * @param studentID the id of the student who took the quiz
     * @return a list containing the question, the result and the correct answer
     */
    public List<Map<String, Object>> getResultsByQuestion(int quizID, int studentID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT questions.question, questionResults.grade, questions.answer FROM questionResults INNER JOIN questions ON questionResults.question_id=questions.question_id WHERE questionResults.quiz_id = ? AND questionResults.student_id = ?");
            statement.setInt(1, quizID);
            statement.setInt(2, studentID);
            ResultSet resultSet = statement.executeQuery();
            List<Map<String, Object>> results = new ArrayList<>();

            while (resultSet.next()) {
                Map<String, Object> result = new HashMap<>();
                result.put("question", resultSet.getString("question"));
                result.put("grade", resultSet.getInt("grade"));
                result.put("answer", resultSet.getString("answer"));
                results.add(result);
            }

            System.out.print(results);
            return results;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * Fetch only the grade for a quiz
     * @param quizID the id of the quiz that is being marked
     * @param studentID the id of the student who took the quiz
     * @return the grade that the student received for the quiz as an integer
     */
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

    /**
     * Fetch all the quiz results for a particular student
     * @param studentID the id of the student whose results are desired
     * @return a list of quiz titles and results for the student
     */
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

   //TODO: pragati can you comment this i dont really get the sql what is qr
    public Map<Integer, Double> getQuestionAccuracyForQuiz(int quizId, int classroomId) {
        Map<Integer, Double> accuracyMap = new HashMap<>();
        try {
            String query = """
            SELECT qr.question_id,
                   SUM(CASE WHEN qr.grade = 1 THEN 1 ELSE 0 END) * 1.0 / COUNT(*) * 100 AS percent_correct
            FROM questionResults qr
            JOIN studentClassroom cs ON cs.studentId = qr.student_id
            WHERE qr.quiz_id = ? AND cs.classroom_number = ?
            GROUP BY qr.question_id
        """;

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, quizId);
            statement.setInt(2, classroomId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int questionId = resultSet.getInt("question_id");
                double percentCorrect = resultSet.getDouble("percent_correct");
                accuracyMap.put(questionId, percentCorrect);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return accuracyMap;
    }

    // DAO method remains the same (grade stored as String in map)
    public List<Map<String, String>> getStudentGradesForQuiz(int quizId, int classroomNumber) {
        List<Map<String, String>> results = new ArrayList<>();
        String query = "SELECT s.name AS student_name, qr.grade " +
                "FROM quizResults qr " +
                "JOIN students s ON s.studentID = qr.student_id " +
                "JOIN studentClassroom sc ON s.studentID = sc.studentId " +
                "WHERE qr.quiz_id = ? AND sc.classroom_number = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quizId);
            statement.setInt(2, classroomNumber);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Map<String, String> row = new HashMap<>();
                row.put("student", rs.getString("student_name"));
                row.put("grade", String.valueOf(rs.getInt("grade")));  // store as String in map
                results.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }






}
