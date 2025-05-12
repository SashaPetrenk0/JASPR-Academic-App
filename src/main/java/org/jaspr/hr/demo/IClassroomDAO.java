package org.jaspr.hr.demo;

import javafx.collections.ObservableList;

import java.util.List;

public interface IClassroomDAO {
    public ObservableList<Classroom> getUpdatedClassrooms();
    public boolean classroomNumberExists(int classroomNumber);
    public boolean assignUsers(Classroom selectedClassroom, Teacher selectedTeacher, List<Student> selectedStudents);
    public Integer getAssignedTeacherId(int classroomNumber);
    public List<Integer> getClassroomNumbersForStudent(int studentID);
    public List<Integer> getClassroomNumberForTeacher(int teacherID);


}
