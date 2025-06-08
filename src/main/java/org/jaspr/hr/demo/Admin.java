package org.jaspr.hr.demo;

/**
 * A simple model class that inherits form the User class
 * Includes additional admin-specific attributes such as age and admin ID
 */
public class Admin extends User {
    private int age;
    private int adminID;

    /**
     * Constructs a new Admin with the specified  name, age, email and admin ID where name and email are inherited from the user super class
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

    /**
     * Overrides abstract method from abstract superclass User
     * Returns the role of this user
     * @return A string of the role "Admin"
     */
    @Override
    public String getRole() {
        return "Admin";
    }

    /**
     * Returns the age of the admin
     * @return age as an integer
     */
    public int getAge() {
        return age;
    }

    /**
     * Returns the unique identifier of the admin
     * @return ID as an integer
     */
    public int getAdminID(){
        return adminID;
    }

    /**
     * Sets the age of the admin
     * @param age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Sets the unique identifier of the admin
     * @param adminID
     */
    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }
}
