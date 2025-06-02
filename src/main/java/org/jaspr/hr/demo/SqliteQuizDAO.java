package org.jaspr.hr.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for the Quiz Data Access Object that handles
 * the CRUD operations for the Quiz class with the database.
 */
public class SqliteQuizDAO {
    private final Connection connection;

    /**
     * Create all tables and get instance of database connection
     */
    public SqliteQuizDAO() {
        connection = SqliteConnection.getInstance();
        createQuizTable();
        createQuestionTable();
        createQuizAssignmentsTable();

    }

    /**
     * Create a table in the database to store quizzes
     */
    private void createQuizTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS quizzes (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title TEXT NOT NULL," +
                    "description TEXT NOT NULL," +
                    "topic TEXT NOT NULL," +
                    "numOfQuestions INTEGER NOT NULL," +
                    "author INTEGER" +
                    ");";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a table in the database to store questions
     */
    private void createQuestionTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS questions (" +
                    "question_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "id INTEGER NOT NULL,"+
                    "question TEXT NOT NULL," +
                    "optionA TEXT NOT NULL," +
                    "optionB TEXT NOT NULL," +
                    "optionC TEXT NOT NULL," +
                    "optionD TEXT NOT NULL," +
                    "answer TEXT NOT NULL," +
                    "FOREIGN KEY (id) REFERENCES quizzes(id)"+
                    ");";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a table in the database to store assignments of quizzes to classes
     */
    private void createQuizAssignmentsTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS quizAssignments (" +
                    "quiz_id INTEGER, " +
                    "classroom_number INTEGER, " +
                    "FOREIGN KEY (classroom_number) REFERENCES classrooms(classroom_number), " +
                    "FOREIGN KEY (quiz_id) REFERENCES quizzes(id)," +
                    "UNIQUE (quiz_id, classroom_number)" +
                    ");";
            statement.execute(query);
        } catch (SQLException e) {
            if (e.getMessage().contains("unique_quiz_classroom")) {
                System.out.println("This classroom has already been assigned to the quiz.");
            } else {
                e.printStackTrace();
            }
        }
    }

    // Pull all attributes of a specific student for Display Details functionality in profile

    /**
     * Retrieve all questions from the database that correspond to a given quiz id
     * @param id the quiz id that will be used to find correct questions
     * @return an array of question objects
     */

    public Question[] getQuestions(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM questions WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Question> questionList = new ArrayList<>();

            while (resultSet.next()) {
                questionList.add(new Question(
                        resultSet.getInt("question_id"),
                        resultSet.getString("question"),
                        resultSet.getString("optionA"),
                        resultSet.getString("optionB"),
                        resultSet.getString("optionC"),
                        resultSet.getString("optionD"),
                        resultSet.getString("answer")

                ));
            }

            System.out.println("Questions loaded: " + questionList.size());
            System.out.print("quizID " + questionList.getFirst().getId());// Debug
            return questionList.toArray(new Question[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Adds a new question to the database.
     * @param quiz The quiz that the question belongs to
     * @param question the question object
     */

    public void addQuestion(Question question, Quiz quiz) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO questions (id,question, optionA, optionB, optionC, optionD, answer)" +
                    "VALUES (?,?, ?, ?, ?, ?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            statement.setInt(1, quiz.getId());
            statement.setString(2, question.getQuestion());
            statement.setString(3, question.getOptionA());
            statement.setString(4, question.getOptionB());
            statement.setString(5, question.getOptionC());
            statement.setString(6, question.getOptionD());
            statement.setString(7, question.getCorrectAnswer());

            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    //set id of quiz to the auto incremented id
                    question.setId(generatedId);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * Adds a new quiz to the database.
     * @param quiz The quiz to add.
     */

    public void addQuiz(Quiz quiz) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO quizzes (title, description, topic, numOfQuestions, author)" +
                    "VALUES (?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, quiz.getTitle());
            statement.setString(2, quiz.getDescription());
            statement.setString(3, quiz.getTopic());
            statement.setInt(4, quiz.getNumOfQuestions());
            statement.setInt(5, quiz.getAuthor());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    System.out.println("Before insert: quiz id = " + quiz.getId());

                    quiz.setId(generatedId);
                    System.out.println("after insert: quiz id = " + quiz.getId());
                    System.out.println("Inserted quiz with ID: " + generatedId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Retrieves all quizzes created by a teacher from the database.
     * @param teacher teacher who owns the quizzes.
     * @return  string list of titles of quizzes created by the teacher
     */

    public List<String> getAllQuizzes(Teacher teacher) {

        List<String> quizzes = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM quizzes WHERE author = ?");
            statement.setInt(1, teacher.getTeacherID());
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String title = resultSet.getString("title");

                quizzes.add(title);
            }
            //TODO: Error Handling
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quizzes;

    }


    /**
     * Retrieves all quizzes created by a teacher from the database.
     * @param teacher teacher who owns the quizzes.
     * @return  object list all quizzes created by the teacher
     */
    public List<Quiz> getAllQuizObjects(Teacher teacher) {

        List<Quiz> quizzes = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM quizzes WHERE author = ?");
            statement.setInt(1, teacher.getTeacherID());
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                String topic = resultSet.getString("topic");
                int numOfQuestions = resultSet.getInt("numOfQuestions");
                int author = resultSet.getInt("author");

                quizzes.add(new Quiz(id, title, description, topic, numOfQuestions, author));
            }
            //TODO: Error Handling
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quizzes;

    }

    /**
     * Retrieves all quizzes created by a student from the database.
     * @param student student who created the quizzes.
     * @return  object list all quizzes created by the student
     */
    public List<Quiz> getAllQuizzes(Student student) {
        List<Quiz> quizzes = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM quizzes WHERE author = ?");
            statement.setInt(1, student.getStudentID());
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                String title = resultSet.getString("title");
                String desc  = resultSet.getString("description");
                String topic = resultSet.getString("topic");
                int numOfQuestions = resultSet.getInt("numOfQuestions");
                int author = resultSet.getInt("author");
                Quiz quiz = new Quiz(title,desc,topic,numOfQuestions,author);
                quiz.setId(resultSet.getInt("id"));
                quizzes.add(quiz);
            }
            //TODO: Error Handling
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quizzes;
    }

    public List<Quiz> getQuizzesForStudent(int studentID){
        List<Quiz> quizzes = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT q.*" +
                    "        FROM quizzes q" +
                    "        JOIN quizAssignments qa ON q.id = qa.quiz_id" +
                    "        JOIN studentClassroom sc ON qa.classroom_number = sc.classroom_number" +
                    "        WHERE sc.studentID = ?");
            statement.setInt(1, studentID);
            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
               Quiz quiz = new Quiz(
                       resultSet.getInt("id"),
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


    public String assignQuizToClassroom(int quiz_id, int classroom_number) {
        try {
            // First, check if the assignment already exists
            PreparedStatement checkStatement = connection.prepareStatement(
                    "SELECT * FROM quizAssignments WHERE quiz_id = ? AND classroom_number = ?"
            );
            checkStatement.setInt(1, quiz_id);
            checkStatement.setInt(2, classroom_number);
            ResultSet resultSet = checkStatement.executeQuery();

            // If the result set is not empty, it means the quiz is already assigned to the classroom
            if (resultSet.next()) {
                return "Quiz already assigned to classroom " + classroom_number;

            }

            // If the quiz is not assigned, proceed to insert
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO quizAssignments(quiz_id, classroom_number) VALUES (?, ?)"
            );
            insertStatement.setInt(1, quiz_id);
            insertStatement.setInt(2, classroom_number);
            insertStatement.executeUpdate();

            return "Quiz successfully assigned to classroom " + classroom_number;

        } catch (SQLException e) {
            e.printStackTrace();
            return "Error assigning quiz to classroom " + classroom_number;
        }
    }
}






