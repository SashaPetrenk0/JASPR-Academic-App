package org.jaspr.hr.demo.model.EducationDeleteFolderEventually;

import org.jaspr.hr.demo.model.usersDeleteFolderEventually.Student;
import java.util.List;

public class Classroom {
    private int id;
    private String name;
    private List<Student> students;

    // Constructor
    public Classroom(int id, String name, List<Student> students) {
        this.id = id;
        this.name = name;
        this.students = students;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    // toString() for easy representation
    @Override
    public String toString() {
        return "Classroom{id=" + id + ", name='" + name + "', students=" + students + "}";
    }
}

