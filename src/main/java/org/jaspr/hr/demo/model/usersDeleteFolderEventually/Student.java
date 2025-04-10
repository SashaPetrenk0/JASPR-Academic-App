package org.jaspr.hr.demo.model.usersDeleteFolderEventually;

public class Student extends User {
    private String grade;

    // Constructor
    public Student(int id, String username, String password, String role, String grade) {
        super(id, username, password, role); // Call parent constructor
        this.grade = grade;
    }

    // Getter and Setter
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    // Override toString() to add grade details
    @Override
    public String toString() {
        return super.toString() + ", grade='" + grade + "'";
    }
}
