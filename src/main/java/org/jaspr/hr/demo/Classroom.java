package org.jaspr.hr.demo;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    private int classRoomNumber;
    private int classRoomCapacity;
    private Teacher teacher;
    private List<Student> students;

    public Classroom(int classRoomNumber, int classRoomCapacity){
        this.classRoomNumber = classRoomNumber;
        this.classRoomCapacity = classRoomCapacity;
        this.students = new ArrayList<>();
    }
    // getters
    public int getClassRoomNumber(){
        return classRoomNumber;
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