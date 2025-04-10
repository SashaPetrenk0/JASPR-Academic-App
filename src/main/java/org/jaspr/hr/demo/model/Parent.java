package org.jaspr.hr.demo.model;

public class Parent extends User {
    private String childName;

    // Constructor
    public Parent(int id, String username, String password, String role, String childName) {
        super(id, username, password, role); // Call parent constructor
        this.childName = childName;
    }

    // Getter and Setter
    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    // Override toString() to add child details
    @Override
    public String toString() {
        return super.toString() + ", childName='" + childName + "'";
    }
}

