package org.jaspr.hr.demo;

public class Question {
    int id;
    private  String question;
    private  String optionA;
    private  String optionB;
    private  String optionC;
    private  String optionD;
    private  String correctAnswer;

    public Question(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.question = question;
        this.optionA =  optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }
    public Question(int id, String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.id = id;
        this.question = question;
        this.optionA =  optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }

    // Getters for question text, options, and correct answer index
    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setCorrectAnswer(String answer){
        correctAnswer = answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }
    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }
}
