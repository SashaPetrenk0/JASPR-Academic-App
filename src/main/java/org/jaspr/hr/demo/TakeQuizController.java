package org.jaspr.hr.demo;

import javafx.fxml.FXML;
import javafx.scene.control.*;


public class TakeQuizController {
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
        loadQuestion();

    }

    @FXML
    public void loadQuestion(){
        questionLabel.setText(questions[questionIndex].getQuestion());
        String[] choices = questions[questionIndex].getChoices();
        optionA.setText(choices[0]);
        optionB.setText(choices[1]);
        optionC.setText(choices[2]);
        optionD.setText(choices[3]);
    }

    public void nextQuestion() {
        if (questionIndex < questions.length - 1) {
            questionIndex++;
            loadQuestion();  // refresh display with new question
        } else {
            // Optional: Show results or end the quiz
            System.out.println("Quiz finished!");
            System.out.println("correct" + correctAnswerCount);
            System.out.println("incorrect" + incorrectAnswerCount);
        }
    }

    @FXML
    public void leaveQuiz() {
    }

    @FXML
    private boolean checkAnswer(int answer){
        int correctAnswer = questions[questionIndex].getCorrectAnswerIndex();

        if (answer == correctAnswer){
            System.out.println("correct");
            return true;

        }else{
            System.out.println("wrong");
            return false;
        }

    }

    @FXML
    public void optionAclicked() {
        if (checkAnswer(0)) {
            correctAnswerCount ++;
        } else{
            incorrectAnswerCount ++;
        }
        questionIndex++;

    }

    @FXML
    public void optionBclicked() {
        if (checkAnswer(1)) {
            correctAnswerCount ++;
        } else{
            incorrectAnswerCount ++;
        }
        questionIndex++;
    }
    @FXML
    public void optionCclicked() {
        if (checkAnswer(2)) {
            correctAnswerCount ++;
        } else{
            incorrectAnswerCount ++;
        }
        questionIndex++;
    }
    @FXML
    public void optionDclicked() {
        if (checkAnswer(3)) {
            correctAnswerCount ++;
        } else{
            incorrectAnswerCount ++;
        }
        questionIndex++;
    }
}
