package org.jaspr.hr.demo;

/**
 * A simple model class that inherits form the user class, representing a teacher with a name, age, admin id, and  email.
 */

public class Teacher extends User {
    private int teacherID;

    /**
     * Constructs a new Teacher with the specified  name, age, email and teacher ID where name and email are inherited from the user class
     * @param name The name of the teacher
     * @param age The age of the teacher
     * @param teacherID The unique identifier of the teacher
     * @param email The email of the teacher
     */

    public Teacher(String name, int age, int teacherID, String email){
        super(name, age, email);
        this.teacherID = teacherID;
    }

    @Override
    public String getRole() {
        return "Teacher";
    }

    @Override
    public int getAge() {
        return super.getAge();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public int getTeacherID(){
        return teacherID;
    }

    public void setTeacherID(int teacherID) {
        this.teacherID = teacherID;
    }

}
