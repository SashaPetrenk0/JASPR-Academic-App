package org.jaspr.hr.demo.model.EducationDeleteFolderEventually;

import java.util.List;

public class Quiz {
    private int id;
    private String title;
    private List<QuizQuestion> questions;
    private Classroom classroom;

    // Constructor
    public Quiz(int id, String title, List<QuizQuestion> questions, Classroom classroom) {
        this.id = id;
        this.title = title;
        this.questions = questions;
        this.classroom = classroom;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuizQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuizQuestion> questions) {
        this.questions = questions;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    // toString() for easy representation
    @Override
    public String toString() {
        return "Quiz{id=" + id + ", title='" + title + "', classroom=" + classroom + "}";
    }
}

