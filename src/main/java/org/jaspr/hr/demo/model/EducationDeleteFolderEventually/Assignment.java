package org.jaspr.hr.demo.model.EducationDeleteFolderEventually;

import org.jaspr.hr.demo.model.usersDeleteFolderEventually.Student;

public class Assignment {
    private int id;
    private String title;
    private Classroom classroom;
    private Student student;

    // Constructor for class-wide assignment
    public Assignment(int id, String title, Classroom classroom) {
        this.id = id;
        this.title = title;
        this.classroom = classroom;
    }

    // Constructor for student-specific assignment
    public Assignment(int id, String title, Student student) {
        this.id = id;
        this.title = title;
        this.student = student;
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

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    // toString() for easy representation
    @Override
    public String toString() {
        return "Assignment{id=" + id + ", title='" + title + "', classroom=" + classroom + ", student=" + student + "}";
    }
}
