package org.jaspr.hr.demo;

public class Parent extends User{
    private String childName;
    private int childID;

    public Parent(String name, String childName, int childID, String email, String password, String salt){
        super(name, email, password, salt);
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
