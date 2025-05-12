package org.jaspr.hr.demo;

import java.sql.Connection;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.*;
        import java.util.ArrayList;
import java.util.List;


public class SqliteClassroomDAO implements IClassroomDAO {
    private Connection connection;


    public SqliteClassroomDAO() {
        connection = SqliteConnection.getInstance();
        createClassroomTable();
        createStudentClassroom();

    }

    private void createClassroomTable() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS classrooms ("
                    + "classroom_number INTEGER PRIMARY KEY, "
                    + "capacity INTEGER NOT NULL, "
                    + "teacherID INTEGER, "
                    + "FOREIGN KEY (teacherID) REFERENCES teachers(teacherID)"
                    + ")";
            statement.execute((query));
            System.out.println("Classroom table created successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createClassroom(int classroomNumber, int capacity) {

        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO classrooms (classroom_number, capacity) VALUES (?, ?)");
            statement.setInt(1, classroomNumber);
            statement.setInt(2, capacity);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void createStudentClassroom() {
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS studentClassroom ("
                    + "studentID INTEGER, "
                    + "classroom_number INTEGER, "
                    + "PRIMARY KEY (studentID, classroom_number), "
                    + "FOREIGN KEY (studentID) REFERENCES students(studentID),"
                    + "FOREIGN KEY (classroom_number) REFERENCES classrooms(classroom_number)"
                    + ")";
            statement.execute((query));
            System.out.println("student classroom table is created");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ObservableList<Classroom> getUpdatedClassrooms() {
        ObservableList<Classroom> classrooms = FXCollections.observableArrayList();

        String sql = "SELECT c.classroom_number, " +
                "       c.capacity, " +
                "       (SELECT COUNT(*) FROM studentClassroom sc WHERE sc.classroom_number = c.classroom_number) AS num_students, " +
                "       CASE WHEN c.teacherID IS NOT NULL THEN 1 ELSE 0 END AS num_teachers " +
                "  FROM classrooms c";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int number = rs.getInt("classroom_number");
                int capacity = rs.getInt("capacity");
                int numStudents = rs.getInt("num_students");
                int numTeachers = rs.getInt("num_teachers");

                classrooms.add(new Classroom(number, capacity, numStudents, numTeachers));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classrooms;
    }
    @Override
    public boolean classroomNumberExists(int classroomNumber) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM classrooms WHERE classroom_number = ?");
            statement.setInt(1, classroomNumber);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // returns true if a record exists
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean assignUsers(Classroom selectedClassroom, Teacher selectedTeacher, List<Student> selectedStudents) {
        try {
            // Start a transaction
            connection.setAutoCommit(false);

            if (selectedTeacher != null) {
                // Check if the classroom already has a teacher assigned
                String checkTeacherQuery = "SELECT teacherID FROM classrooms WHERE classroom_number = ?";
                try (PreparedStatement checkTeacherStmt = connection.prepareStatement(checkTeacherQuery)) {
                    checkTeacherStmt.setInt(1, selectedClassroom.getClassRoomNumber());
                    try (ResultSet rs = checkTeacherStmt.executeQuery()) {
                        if (rs.next()) {
                            int currentTeacherID = rs.getInt("teacherID");
                            // If the classroom already has a teacher assigned, don't update
                            if (currentTeacherID != 0) {
                                System.out.println("This classroom already has a teacher assigned.");
                                connection.commit();
                                return false; // Teacher assignment skipped
                            }
                        }
                    }
                }

                // If no teacher is assigned, proceed with updating the teacher
                String teacherUpdateQuery = "UPDATE classrooms SET teacherID = ? WHERE classroom_number = ?";
                try (PreparedStatement teacherStatement = connection.prepareStatement(teacherUpdateQuery)) {
                    teacherStatement.setInt(1, selectedTeacher.getTeacherID());
                    teacherStatement.setInt(2, selectedClassroom.getClassRoomNumber());
                    teacherStatement.executeUpdate();
                }
            }

            // Assign students (many-to-many, so insert into join table)
            if (selectedStudents != null && !selectedStudents.isEmpty()) {
                String insertQuery = "INSERT OR IGNORE INTO studentClassroom (studentID, classroom_number) VALUES (?, ?)";
                try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                    for (Student student : selectedStudents) {
                        insertStmt.setInt(1, student.getStudentID());
                        insertStmt.setInt(2, selectedClassroom.getClassRoomNumber());
                        insertStmt.addBatch();
                    }
                    insertStmt.executeBatch(); // Execute all inserts at once
                }
            }

            // Commit the transaction
            connection.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Integer getAssignedTeacherId(int classroomNumber) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT teacherID FROM classrooms WHERE classroom_number = ?");
            stmt.setInt(1, classroomNumber);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("teacherID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Integer> getClassroomNumbersForStudent(int studentID) {
        List<Integer> classroomNumbers = new ArrayList<>();

        String query = "SELECT classroom_number FROM studentClassroom WHERE studentID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int classroomNumber = resultSet.getInt("classroom_number");
                classroomNumbers.add(classroomNumber);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classroomNumbers;
    }

    @Override
    public List<Integer> getClassroomNumberForTeacher(int teacherID) {
        List<Integer> classroomNumbers = new ArrayList<>();

        String query = "SELECT classroom_number FROM classrooms WHERE teacherID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teacherID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                classroomNumbers.add(resultSet.getInt("classroom_number"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classroomNumbers;
    }

}

