package org.jaspr.hr.demo.model.EducationDeleteFolderEventually;

public class QuizQuestion {
    private int id;
    private String questionText;
    private String answer;

    // Constructor
    public QuizQuestion(int id, String questionText, String answer) {
        this.id = id;
        this.questionText = questionText;
        this.answer = answer;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // toString() for easy representation
    @Override
    public String toString() {
        return "QuizQuestion{id=" + id + ", questionText='" + questionText + "', answer='" + answer + "'}";
    }
}

