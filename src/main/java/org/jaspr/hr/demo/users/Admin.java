package org.jaspr.hr.demo.users;

public class Admin extends User {
    private int age;
    private int adminID;

    public Admin(String name, int age, int adminID, String email, String password){
        super(name, email, password);
        this.age = age;
        this.adminID = adminID;
    }

    @Override
    public String getRole() {
        return "Parent";
    }

    public int getAge() {
        return age;
    }

    public int getAdminID(){
        return adminID;
    }
}
