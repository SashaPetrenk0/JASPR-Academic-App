package org.jaspr.hr.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SqliteQuizDAO implements IQuizDAO {
    private Connection connection;

    public SqliteQuizDAO() {
        connection = SqliteConnection.getInstance();
        createQuizTable();
        createQuestionTable();
        createQuizAssignmentsTable();

    }
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


    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Pull all attributes of a specific student for Display Details functionality in profile
    @Override
    public Quiz getQuiz(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM quizzes WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Quiz(

                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("topic"),
                        resultSet.getInt("numOfQuestions"),
                        resultSet.getInt("author")

                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO: separate the question stuff into a separate interface?
    @Override
    public Question[] getQuestions(int id) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM questions WHERE id = ?");
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            List<Question> questionList = new ArrayList<>();



            while (resultSet.next()) {
                questionList.add(new Question(
                        resultSet.getString("question"),
                        resultSet.getString("optionA"),
                        resultSet.getString("optionB"),
                        resultSet.getString("optionC"),
                        resultSet.getString("optionD"),
                        resultSet.getString("answer")
                ));
            }

            System.out.println("Questions loaded: " + questionList.size()); // Debug
            return questionList.toArray(new Question[0]);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void addQuestion(Question question, Quiz quiz) {
        try{
            PreparedStatement statement = connection.prepareStatement("INSERT INTO questions (id, question, optionA, optionB, optionC, optionD, answer)" +
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
                    //set id of quiz to the autoincremented id
                    question.setId(generatedId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
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


    @Override
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

    @Override
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

    @Override
    public List<Quiz> getAllQuizzes(Teacher teacher, Student student) {

        return List.of();
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






