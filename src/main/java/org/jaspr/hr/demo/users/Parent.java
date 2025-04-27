package org.jaspr.hr.demo.users;

public class Parent extends User {
    private String childName;
    private int childID;

    public Parent(String name, String childName, int childID, String email, String password){
        super(name, email, password);
        this.childName = childName;
        this.childID = childID;
    }

    @Override
    public String getRole() {
        return "Parent";
    }

    public String getChildName() {
        return childName;
    }

    public int getChildID(){
        return childID;
    }
}
