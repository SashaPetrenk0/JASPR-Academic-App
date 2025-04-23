package org.jaspr.hr.demo;

public class Question {
    private final String question;
    private final String[] choices;
    private final int correctAnswerIndex;

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

}
