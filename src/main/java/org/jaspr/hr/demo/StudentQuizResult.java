package org.jaspr.hr.demo;

public class StudentQuizResult {
    private final String student;
    private final int grade;

    public StudentQuizResult(String student, int grade){
        this.student = student;
        this.grade = grade;
    }

    public String getStudent(){
        return student;
    }
    public int getGrade(){
        return grade;
    }
}
