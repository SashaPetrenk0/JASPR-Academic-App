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

    private QuizManager quizManager;

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
        quizManager = new QuizManager(questions);
        loadQuestion();

    }

    @FXML
    public void getInfo(int student, int quiz_id){
        this.studentId = student;
        this.quizId = quiz_id;

    }

    @FXML
    public void loadQuestion(){
        if (quizManager.getTotalQuestions() == 0) {
            System.out.println("Questions not yet set — skipping load.");
            return;
        }
        System.out.println(quizManager.getCurrentQuestion());
        questionLabel.setText(quizManager.getCurrentQuestion().getQuestion());
        optionA.setText(quizManager.getCurrentQuestion().getOptionA());
        optionB.setText(quizManager.getCurrentQuestion().getOptionB());
        optionC.setText(quizManager.getCurrentQuestion().getOptionC());
        optionD.setText(quizManager.getCurrentQuestion().getOptionD());
    }

    public void nextQuestion() {
        if(quizManager.nextQuestion()){
            //refresh the display with the next question in the array
            loadQuestion();
            optionA.setDisable(false);
            optionB.setDisable(false);
            optionC.setDisable(false);
            optionD.setDisable(false);
        } else {
            System.out.println("Quiz finished!");
            System.out.println("correct" + quizManager.getCorrectCount());
            System.out.println("incorrect" + quizManager.getIncorrectCount());
            double grade = ((double) quizManager.getCorrectCount() / quizManager.getTotalQuestions()) * 100;
            questionLabel.setText("Quiz Finished! You scored"+ quizManager.getCorrectCount() + "/"+quizManager.getTotalQuestions());
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
        if (quizManager.checkAnswer("A")) {
            quizManager.recordAnswerResult(quizId,quizManager.getCurrentQuestion(),student,1);
        } else{
            quizManager.recordAnswerResult(quizId,quizManager.getCurrentQuestion(),student,0);
        }


    }

    @FXML
    public void optionBclicked() {
        optionB.setDisable(true);
        if (quizManager.checkAnswer("B")) {
            quizManager.recordAnswerResult(quizId,quizManager.getCurrentQuestion(),student,1);
        } else{
            quizManager.recordAnswerResult(quizId,quizManager.getCurrentQuestion(),student,0);
        }

    }
    @FXML
    public void optionCclicked() {
        optionC.setDisable(true);
        if (quizManager.checkAnswer("C")) {
            quizManager.recordAnswerResult(quizId,quizManager.getCurrentQuestion(),student,1);
        } else{
            quizManager.recordAnswerResult(quizId,quizManager.getCurrentQuestion(),student,0);
        }

    }
    @FXML
    public void optionDclicked() {
        optionD.setDisable(true);
        if (quizManager.checkAnswer("D")) {
            quizManager.recordAnswerResult(quizId,quizManager.getCurrentQuestion(),student,1);
        } else{
            quizManager.recordAnswerResult(quizId,quizManager.getCurrentQuestion(),student,0);
        }

    }
}
