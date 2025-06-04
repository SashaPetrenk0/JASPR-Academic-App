package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;


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


    @FXML
    private Button returnToPrevious;

    private int studentId;
    private int quizId;

    private QuizHelper quizHelper;

    private final SqliteResultsDAO resultsDAO = new SqliteResultsDAO();
    private final User user = UserSession.getInstance().getCurrentUser();
    private final Student student = (Student) user;

    @FXML
    public void loadTitle(String title){
        quizTitle.setText(title);

    }

    @FXML
    public void setQuestions(Question[] questions){
        //this.questions = questions;
        quizHelper = new QuizHelper(questions);
        loadQuestion();

    }

    @FXML
    public void getInfo(int student, int quiz_id){
        this.studentId = student;
        this.quizId = quiz_id;

    }

    @FXML
    public void loadQuestion(){
        if (quizHelper.getTotalQuestions() == 0) {
            System.out.println("Questions not yet set — skipping load.");
            return;
        }
        System.out.println(quizHelper.getCurrentQuestion());
        questionLabel.setText(quizHelper.getCurrentQuestion().getQuestion());
        optionA.setText(quizHelper.getCurrentQuestion().getOptionA());
        optionB.setText(quizHelper.getCurrentQuestion().getOptionB());
        optionC.setText(quizHelper.getCurrentQuestion().getOptionC());
        optionD.setText(quizHelper.getCurrentQuestion().getOptionD());
    }

    public void nextQuestion() {
        if(quizHelper.nextQuestion()){
            //refresh the display with the next question in the array
            loadQuestion();
            optionA.setDisable(false);
            optionB.setDisable(false);
            optionC.setDisable(false);
            optionD.setDisable(false);
        } else {
            System.out.println("Quiz finished!");
            System.out.println("correct" + quizHelper.getCorrectCount());
            System.out.println("incorrect" + quizHelper.getIncorrectCount());
            double grade = ((double) quizHelper.getCorrectCount() / quizHelper.getTotalQuestions()) * 100;
            questionLabel.setText("Quiz Finished! You scored"+ quizHelper.getCorrectCount() + "/"+ quizHelper.getTotalQuestions());
            System.out.print(studentId);
            System.out.print(quizId);
            resultsDAO.addQuizResult(quizId, studentId, grade);

        }
    }

    @FXML
    private void onReturn(){
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "student-dashboard-view.fxml");
    }


    @FXML
    public void optionAclicked() {
        optionA.setDisable(true);
        if (quizHelper.checkAnswer("A")) {
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,1);
        } else{
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,0);
        }


    }

    @FXML
    public void optionBclicked() {
        optionB.setDisable(true);
        if (quizHelper.checkAnswer("B")) {
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,1);
        } else{
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,0);
        }

    }
    @FXML
    public void optionCclicked() {
        optionC.setDisable(true);
        if (quizHelper.checkAnswer("C")) {
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,1);
        } else{
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,0);
        }

    }
    @FXML
    public void optionDclicked() {
        optionD.setDisable(true);
        if (quizHelper.checkAnswer("D")) {
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,1);
        } else{
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,0);
        }

    }
}
