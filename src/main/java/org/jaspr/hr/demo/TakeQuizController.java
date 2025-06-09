package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

/**
 * Class used to control the UI interactions for the 'taking quiz' page.
 */
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

    //initialise instances of helper classes and data access objects
    private QuizHelper quizHelper;
    private final SqliteResultsDAO resultsDAO = new SqliteResultsDAO();
    private final User user = UserSession.getInstance().getCurrentUser();
    private final Student student = (Student) user;

    /**
     * Method to display the title of the selected quiz
     * @param title passed from student dashboard
     */
    @FXML
    public void loadTitle(String title){
        quizTitle.setText(title);

    }

    /**
     * create quizHelper object with set of questions
     * @param questions array of question objects passed from the student dashboard
     */
    @FXML
    public void setQuestions(Question[] questions){
        quizHelper = new QuizHelper(questions);
        loadQuestion();

    }

    /**
     * set the student id and the quiz id to be used later
     * @param student the student id, passed from the student dashboard
     * @param quiz_id the quiz id, passed from the student dashboard
     */
    @FXML
    public void getInfo(int student, int quiz_id){
        this.studentId = student;
        this.quizId = quiz_id;

    }

    /**
     * Setting the text of UI labels to show questions
     */
    @FXML
    public void loadQuestion(){
        //error handling for slow ollama response
        if (quizHelper.getTotalQuestions() == 0) {
            System.out.println("Questions not yet set — skipping load.");
            return;
        }
        //display the question
        questionLabel.setText(quizHelper.getCurrentQuestion().getQuestion());
        //display the options
        optionA.setText(quizHelper.getCurrentQuestion().getOptionA());
        optionB.setText(quizHelper.getCurrentQuestion().getOptionB());
        optionC.setText(quizHelper.getCurrentQuestion().getOptionC());
        optionD.setText(quizHelper.getCurrentQuestion().getOptionD());
    }

    /**
     * refresh the display with the next question in the array
     */
    public void nextQuestion() {
        //increment the question index through quiz helper
        if(quizHelper.nextQuestion()){
            loadQuestion();
            optionA.setDisable(false);
            optionB.setDisable(false);
            optionC.setDisable(false);
            optionD.setDisable(false);
        } else {
           //if there is no next question, display results
            double grade = ((double) quizHelper.getCorrectCount() / quizHelper.getTotalQuestions()) * 100;
            questionLabel.setText("Quiz Finished! You scored"+ quizHelper.getCorrectCount() + "/"+ quizHelper.getTotalQuestions());
           //add the quiz result to the database
            resultsDAO.addQuizResult(quizId, studentId, grade);

        }
    }

    /**
     * Method to change scene back to the student dashboard when the return button is pressed
     */
    @FXML
    private void onReturn(){
        Stage stage = (Stage) returnToPrevious.getScene().getWindow();
        SceneChanger.changeScene(stage, "student-dashboard-view.fxml");
    }


    /**
     * When the A button is clicked, check the answer and add the results to the database
     */
    @FXML
    public void optionAclicked() {
        optionA.setDisable(true);
        if (quizHelper.checkAnswer("A")) {
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,1);
        } else{
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,0);
        }


    }

    /**
     * When the B button is clicked, check the answer and add the results to the database
     */
    @FXML
    public void optionBclicked() {
        optionB.setDisable(true);
        if (quizHelper.checkAnswer("B")) {
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,1);
        } else{
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,0);
        }

    }
    /**
     * When the C button is clicked, check the answer and add the results to the database
     */
    @FXML
    public void optionCclicked() {
        optionC.setDisable(true);
        if (quizHelper.checkAnswer("C")) {
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,1);
        } else{
            quizHelper.recordAnswerResult(quizId, quizHelper.getCurrentQuestion(),student,0);
        }

    }
    /**
     * When the D button is clicked, check the answer and add the results to the database
     */
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
