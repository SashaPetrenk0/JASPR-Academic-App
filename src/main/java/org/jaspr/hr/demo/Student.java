package org.jaspr.hr.demo;

public class Student extends User{
    private int age;
    private int studentID;

    public Student(String name, int age, int studentID, String email){
        super(name, email);
        this.age = age;
        this.studentID = studentID;
    }

    @Override
    public String getRole(){
        return "Student";
    }

    public int getStudentID() {
        return studentID;
    }

    public int getAge() {
        return age;
    }

}
