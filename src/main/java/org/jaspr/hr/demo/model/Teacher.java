package org.jaspr.hr.demo.model;

public class Teacher extends User {
    private String subject;

    // Constructor
    public Teacher(int id, String username, String password, String role, String subject) {
        super(id, username, password, role); // Call parent constructor
        this.subject = subject;
    }

    // Getter and Setter
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    // Override toString() to add subject details
    @Override
    public String toString() {
        return super.toString() + ", subject='" + subject + "'";
    }
}

