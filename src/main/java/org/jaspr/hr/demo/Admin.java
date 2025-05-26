package org.jaspr.hr.demo;

/**
 * A simple model class that inherits form the user class, representing an admin with a name, age, admin id, and  email.
 */
public class Admin extends User {
    private int age;
    private int adminID;

    /**
     * Constructs a new Admin with the specified  name, age, email and admin ID where name and email are inherited from the user class
     * @param name The name of the admin
     * @param age The age of the admin
     * @param adminID The unique identifier of the admin
     * @param email The email of the admin
     */

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

    public void setAge(int age) {
        this.age = age;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }
}
