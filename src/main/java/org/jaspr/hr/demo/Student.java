package org.jaspr.hr.demo;

public class Student {
    private final String name;
    private final int age;
    private final String email;
    private final String password;

    public Student(String name, int age, String email, String password){
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
