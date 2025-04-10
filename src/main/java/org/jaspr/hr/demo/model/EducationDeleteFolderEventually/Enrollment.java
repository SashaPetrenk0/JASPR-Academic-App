package org.jaspr.hr.demo.model.EducationDeleteFolderEventually;

import org.jaspr.hr.demo.model.usersDeleteFolderEventually.Student;

public class Enrollment {
    private Student student;
    private Classroom classroom;

    // Constructor
    public Enrollment(Student student, Classroom classroom) {
        this.student = student;
        this.classroom = classroom;
    }

    // Getters and Setters
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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
        return "Enrollment{student=" + student + ", classroom=" + classroom + "}";
    }
}

