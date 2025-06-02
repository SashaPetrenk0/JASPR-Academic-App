package org.jaspr.hr.demo;

public class Parent extends User {
    private String childName;
    private int childID;

    public Parent (){
        super();
    }

    public Parent(String name, String childName, int childID, String email){
        super(name, email);
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

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public void setChildID(int childID) {
        this.childID = childID;
    }
}
