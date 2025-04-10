package org.jaspr.hr.demo.model.EducationDeleteFolderEventually;

import org.jaspr.hr.demo.model.usersDeleteFolderEventually.Student;

public class QuizResult {
    private int id;
    private Student student;
    private Quiz quiz;
    private int score;

    // Constructor
    public QuizResult(int id, Student student, Quiz quiz, int score) {
        this.id = id;
        this.student = student;
        this.quiz = quiz;
        this.score = score;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    // toString() for easy representation
    @Override
    public String toString() {
        return "QuizResult{id=" + id + ", student=" + student + ", quiz=" + quiz + ", score=" + score + "}";
    }
}
