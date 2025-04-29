package org.jaspr.hr.demo;

public class Admin extends User{
    private int age;
    private int adminID;

    public Admin(String name, int age, int adminID, String email){
        super(name, email);
        this.age = age;
        this.adminID = adminID;
    }

    @Override
    public String getRole() {
        return "Admin";
    }

    public int getAge() {
        return age;
    }

    public int getAdminID(){
        return adminID;
    }
}
