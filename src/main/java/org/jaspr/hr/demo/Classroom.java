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





}