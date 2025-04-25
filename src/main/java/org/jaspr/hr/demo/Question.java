package org.jaspr.hr.demo;

public class Question {
    private  String question;
    private  String[] choices;
    private  int correctAnswerIndex;

    public Question(String question, String[] choices, int correctAnswerIndex) {
        this.question = question;
        this.choices = choices;
        this.correctAnswerIndex = correctAnswerIndex;
    }

    // Getters for question text, options, and correct answer index
    public String getQuestion() {
        return question;
    }
    public String[] getChoices() {
        return choices;
    }
    public int getCorrectAnswerIndex() {
        return correctAnswerIndex;
    }

    public void setCorrectAnswerIndex(int index){
        correctAnswerIndex = index;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setChoices(String[] choices) {
        this.choices = choices;
    }
}
