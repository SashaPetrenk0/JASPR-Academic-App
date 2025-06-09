package org.jaspr.hr.demo;

/**
 * A simple model class inheriting email and name from the user class, representing a student with an age and student id.
 */

public class Student extends User {
    private int studentID;

    /**
     * Constructs a new Student with the specified  name, age, email and student ID where name and email are inherited from the user class
     * @param name The name of the student
     * @param age The age of the student
     * @param studentID The unique identifier of the student
     * @param email The email of the student
     */

    public Student(String name, int age, int studentID, String email){
        super(name, age, email);
        this.studentID = studentID;
    }

    @Override
    public String getRole(){
        return "Student";
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

}
