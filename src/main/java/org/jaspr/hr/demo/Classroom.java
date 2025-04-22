package org.jaspr.hr.demo;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
    private int classRoomNumber;
    private Teacher teacher;
    private List<Student> students;

    public Classroom(int classRoomNumber, Teacher Teacher){
        this.classRoomNumber = classRoomNumber;
        this.teacher = teacher;
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