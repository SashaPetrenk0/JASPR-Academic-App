package org.jaspr.hr.demo;

public class Teacher extends User{
    private int age;
    private int staffID;

    public Teacher(String name, int age, int staffID, String email, String password){
        super(name, email, password);
        this.age = age;
        this.staffID = staffID;
    }

    @Override
    public String getRole() {
        return "Teacher";
    }

    public int getAge() {
        return age;
    }

    public int getStaffID(){
        return staffID;
    }

}
