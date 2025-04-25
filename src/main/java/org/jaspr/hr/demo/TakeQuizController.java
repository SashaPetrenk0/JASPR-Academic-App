package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class TakeQuizController {
    @FXML
    private Label question;
    @FXML
    private Label optionA;
    @FXML
    private Label optionB;
    @FXML
    private Label optionC;
    @FXML
    private Label optionD;



    //sample questions for testing purposes
    Question[] questions = {
            new Question("What is the capital of France?", new String[]{"Berlin", "Madrid", "Paris", "Rome"}, 2),
            new Question("Which planet is known as the Red Planet?", new String[]{"Earth", "Mars", "Jupiter", "Saturn"}, 1),
            new Question("Who wrote 'Hamlet'?", new String[]{"Charles Dickens", "William Shakespeare", "Jane Austen", "Mark Twain"}, 1),
            new Question("What is the largest ocean on Earth?", new String[]{"Atlantic", "Indian", "Pacific", "Arctic"}, 2),
            new Question("Which element has the chemical symbol 'O'?", new String[]{"Oxygen", "Gold", "Iron", "Hydrogen"}, 0)
    };

    int correctAnswerCount = 0;
    int incorrectAnswerCount = 0;
    int questionIndex = 0;

    @FXML
    public void initialize(){
        question.setText(questions[questionIndex].getQuestion());
        String[] choices = questions[questionIndex].getChoices();
        optionA.setText(choices[0]);
        optionB.setText(choices[1]);
        optionC.setText(choices[2]);
        optionD.setText(choices[3]);

    }
    @FXML
    public void leaveQuiz() {
    }

    @FXML
    private boolean checkAnswer(String answer){

        return false;
    }

    @FXML
    public void optionAclicked() {
        if (checkAnswer(optionA.getText())) {
            correctAnswerCount ++;
        } else{
            incorrectAnswerCount ++;
        }




    }

    @FXML
    public void optionBclicked() {


    }
    @FXML
    public void optionCclicked() {


    }
    @FXML
    public void optionDclicked() {


    }
}
