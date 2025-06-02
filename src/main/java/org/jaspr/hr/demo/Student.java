package org.jaspr.hr.demo;

public class Student extends User {
    private int age;
    private int studentID;

    public Student (){
        super();
    }

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

    public void setAge(int age) {
        this.age = age;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

}
