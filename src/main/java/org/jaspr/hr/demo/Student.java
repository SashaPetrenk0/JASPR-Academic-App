package org.jaspr.hr.demo;

public class Student implements User{
    private String name, email, password;
    private int age;
    private int studentID;

    public Student(String name, int age, int studentID, String email, String password){
        this.name = name;
        this.age = age;
        this.studentID = studentID;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getStudentID() {
        return studentID;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
