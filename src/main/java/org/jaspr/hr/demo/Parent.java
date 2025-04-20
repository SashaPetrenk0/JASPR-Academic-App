package org.jaspr.hr.demo;

public class Parent implements User{
    private String name, email, password;
    private String studentName;
    private int studentID;

    public Parent(String name, String studentName, int studentID, String email, String password){
        this.name = name;
        this.studentName = studentName;
        this.studentID = studentID;
        this.email = email;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getStudentName() {
        return studentName;
    }

    public int getStudentID(){
        return studentID;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }


}
