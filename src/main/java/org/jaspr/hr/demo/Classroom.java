package org.jaspr.hr.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for a classroom with a unique number, capacity, assigned teacher, and list of students.
 */
public class Classroom {
    private int classRoomNumber;
    private int classRoomCapacity;
    private int numStudents;
    private int numTeachers;
    private Teacher teacher;
    private List<Student> students;

    /**
     * Constructs a Classroom for initial setup (without assigned teacher/students).
     * @param classRoomNumber the unique classroom number
     * @param classRoomCapacity the maximum number of students that can be assigned to this classroom
     */
    public Classroom(int classRoomNumber, int classRoomCapacity){
        this.classRoomNumber = classRoomNumber;
        this.classRoomCapacity = classRoomCapacity;
        this.students = new ArrayList<>();
    }

    /**
     * Constructs a Classroom with populated student and teacher counts
     * @param classRoomNumber the unique classroom number
     * @param classRoomCapacity the maximum number of students that can be assigned to this classroom
     * @param numStudents number of students currently assigned
     * @param numTeachers number of teachers currently assigned
     */
    public Classroom (int classRoomNumber, int classRoomCapacity, int numStudents, int numTeachers){
        this.classRoomNumber = classRoomNumber;
        this.classRoomCapacity = classRoomCapacity;
        this.numStudents = numStudents;
        this.numTeachers = numTeachers;
    }

    /**
     * Returns the classroom number
     * @return classroom number as integer
     */
    public int getClassRoomNumber(){
        return classRoomNumber;
    }

    /**
     * Returns the classroom capacity
     * @return classroom capacity as integer
     */
    public int getClassRoomCapacity(){
        return classRoomCapacity;
    }

    /**
     * Returns the number of students in the classroom
     * @return integer number of students
     */
    public int getNumStudents(){
        return numStudents;
    }

    /**
     * Returns the number of teachers assigned to the classroom
     * @return integer number of teachers
     */
    public int getNumTeachers(){
        return numTeachers;
    }

    /**
     * Returns the teacher assigned to this classroom
     * @return Teacher object
     */
    public Teacher getTeacher(){
        return teacher;
    }

    /**
     * Sets the teacher assigned to this classroom
     * @param teacher to be assigned to this classroom
     */
    public void setTeacher(Teacher teacher){
        this.teacher=teacher;
    }

    /**
     * Returns the students assigned to this classroom
     * @return the list of students as student objects
     */
    public List<Student> getStudents(){
        return students;
    }

    /**
     * Adds a student to the list of students in the classroom
     * @param student to be added to the classroom
     */
    public void addStudent(Student student){
        students.add(student);
    }

    /**
     * Removes a student to the list of students in the classroom
     * @param student to be removed to the classroom
     */
    public void deleteStudent(Student student){
        students.remove(student);
    }






}