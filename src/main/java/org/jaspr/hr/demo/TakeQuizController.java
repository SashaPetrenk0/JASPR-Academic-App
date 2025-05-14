package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class TakeQuizController {
    @FXML
    private Label quizTitle;
    @FXML
    private Label questionLabel;
    @FXML
    private Button optionA;
    @FXML
    private Button optionB;
    @FXML
    private Button optionC;
    @FXML
    private Button optionD;



    private Question[] questions;


    int correctAnswerCount = 0;
    int incorrectAnswerCount = 0;
    int questionIndex = 0;
    private int studentId;
    private int quizId;


    private final SqliteResultsDAO resultsDAO = new SqliteResultsDAO();

    @FXML
    public void loadTitle(String title){
        quizTitle.setText(title);

    }

    @FXML
    public void setQuestions(Question[] questions){
        this.questions = questions;
        System.out.println(questions.length);
        loadQuestion();

    }

    @FXML
    public void getInfo(int student, int quiz_id){
        this.studentId = student;
        this.quizId = quiz_id;

    }






    @FXML
    public void loadQuestion(){
        if (questions == null || questions.length == 0) {
            System.out.println("Questions not yet set — skipping load.");
            return;
        }
        System.out.println(questionIndex);
        questionLabel.setText(questions[questionIndex].getQuestion());
        optionA.setText(questions[questionIndex].getOptionA());
        optionB.setText(questions[questionIndex].getOptionB());
        optionC.setText(questions[questionIndex].getOptionC());
        optionD.setText(questions[questionIndex].getOptionD());
    }

    public void nextQuestion() {
        questionIndex++;
        if (questionIndex < questions.length) {
            loadQuestion();// refresh display with new question
            optionA.setDisable(false);
            optionB.setDisable(false);
            optionC.setDisable(false);
            optionD.setDisable(false);
        } else {
            System.out.println("Quiz finished!");
            System.out.println("correct" + correctAnswerCount);
            System.out.println("incorrect" + incorrectAnswerCount);
            int grade = correctAnswerCount/questions.length;
            questionLabel.setText("Quiz FInished! You scored"+ correctAnswerCount + "/"+questions.length);

            resultsDAO.addQuizResult(studentId, quizId, grade);

        }
    }

    @FXML
    public void leaveQuiz() {
    }

    @FXML
    private boolean checkAnswer(String answer){
        String correctAnswer = questions[questionIndex].getCorrectAnswer();

        if (answer.equals(correctAnswer)){
            System.out.println("correct");
            return true;

        }else{
            System.out.println("wrong");
            return false;
        }

    }

    @FXML
    public void optionAclicked() {
        optionA.setDisable(true);
        if (checkAnswer("A")) {
            correctAnswerCount ++;
        } else{
            incorrectAnswerCount ++;
        }


    }

    @FXML
    public void optionBclicked() {
        optionB.setDisable(true);
        if (checkAnswer("B")) {
            correctAnswerCount ++;
        } else{
            incorrectAnswerCount ++;
        }

    }
    @FXML
    public void optionCclicked() {
        optionC.setDisable(true);
        if (checkAnswer("C")) {
            correctAnswerCount ++;
        } else{
            incorrectAnswerCount ++;
        }

    }
    @FXML
    public void optionDclicked() {
        optionD.setDisable(true);
        if (checkAnswer("D")) {
            correctAnswerCount ++;
        } else{
            incorrectAnswerCount ++;
        }

    }
}
