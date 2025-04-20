package org.jaspr.hr.demo;

public class Admin implements User{
    private String name, email, password;
    private int age;
    private int adminID;

    public Admin(String name, int age, int adminID, String email, String password){
        this.name = name;
        this.age = age;
        this.adminID = adminID;
        this.email = email;
        this.password = password;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getAdminID(){
        return adminID;
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
