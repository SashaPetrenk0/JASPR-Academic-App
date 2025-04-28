package org.jaspr.hr.demo;

public class Teacher extends User{
    private int age;
    private int teacherID;

    public Teacher(String name, int age, int teacherID, String email, String password, String salt){
        super(name, email, password,salt);
        this.age = age;
        this.teacherID = teacherID;
    }

    @Override
    public String getRole() {
        return "Teacher";
    }

    public int getAge() {
        return age;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public int getTeacherID(){
        return teacherID;
    }

}
