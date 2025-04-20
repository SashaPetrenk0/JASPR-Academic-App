package org.jaspr.hr.demo;

public class Teacher {
    private String name, email, password;
    private int age;
    private int staffID;

    public Teacher(String name, int age, int staffID, String email, String password){
        this.name = name;
        this.age = age;
        this.staffID = staffID;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getStaffID(){
        return staffID;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
