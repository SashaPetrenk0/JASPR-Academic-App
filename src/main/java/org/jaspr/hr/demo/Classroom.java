package org.jaspr.hr.demo;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    private int classRoomNumber;
    private int classRoomCapacity;
    private int numStudents;
    private int numTeachers;
    private Teacher teacher;
    private List<Student> students;

    // Constructor used before any students or teachers have been assigned.
    public Classroom(int classRoomNumber, int classRoomCapacity){
        this.classRoomNumber = classRoomNumber;
        this.classRoomCapacity = classRoomCapacity;
        this.students = new ArrayList<>();
    }

    // Constructor ussed when pulling from database with populated student and teacher fields
    public Classroom (int classRoomNumber, int classRoomCapacity, int numStudents, int numTeachers){
        this.classRoomNumber = classRoomNumber;
        this.classRoomCapacity = classRoomCapacity;
        this.numStudents = numStudents;
        this.numTeachers = numTeachers;
    }

    // getters
    public int getClassRoomNumber(){
        return classRoomNumber;
    }

    public int getClassRoomCapacity(){
        return classRoomCapacity;
    }

    public int getNumStudents(){
        return numStudents;
    }

    public int getNumTeachers(){
        return numTeachers;
    }

    public Teacher getTeacher(){
        return teacher;
    }

    public List<Student> getStudents(){
        return students;
    }

    public void addStudent(Student student){
        students.add(student);
    }

    public void deleteStudent(Student student){
        students.remove(student);
    }






}