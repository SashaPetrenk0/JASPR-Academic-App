package org.jaspr.hr.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SqliteUserDAO implements IUserDAO {
    private Connection connection;


    public SqliteUserDAO() {
        connection = SqliteConnection.getInstance();

        //TODO: Consider removing these Alter queries as I only really need to call them once

        try {
            // Check if connection is not null
            if (connection != null) {
                // Alter the table to add missing column if necessary
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("ALTER TABLE students ADD COLUMN classroom_number INTEGER;");
                } catch (SQLException e) {
                    // SQLite will throw an error if the column already exists; ignore that error
                    if (!e.getMessage().contains("duplicate column name")) {
                        e.printStackTrace();
                    }
                }// Alter the classrooms table to add teacherID column if necessary
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("ALTER TABLE classrooms ADD COLUMN teacherID INTEGER;");
                } catch (SQLException e) {
                    // SQLite will throw an error if the column already exists; ignore that error
                    if (!e.getMessage().contains("duplicate column name")) {
                        e.printStackTrace();
                    }
                }
                // Now create tables (if they don't exist)
                createStudentTable();
                createTeacherTable();
                createParentTable();
                createAdminTable();
                createClassroomTable();
                createStudentClassroom();


            } else {
                System.out.println("Database connection failed!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createStudentTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS students ("
                    + "name STRING NOT NULL,"
                    + "age INTEGER NOT NULL,"
                    + "studentID INTEGER NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL,"
                    + "classroom_number INTEGER, "
                    + "FOREIGN KEY (classroom_number) REFERENCES classrooms(classroom_number) ON DELETE SET NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTeacherTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS teachers ("
                    + "name STRING NOT NULL,"
                    + "age INTEGER NOT NULL,"
                    + "teacherID INTEGER NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createParentTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS parents ("
                    + "name STRING NOT NULL,"
                    + "childName STRING NOT NULL,"
                    + "childID INTEGER NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createAdminTable() {
        // Create table if not exists
        try {
            Statement statement = connection.createStatement();
            String query = "CREATE TABLE IF NOT EXISTS admins ("
                    + "name STRING NOT NULL,"
                    + "age INTEGER NOT NULL,"
                    + "adminID INTEGER NOT NULL,"
                    + "email VARCHAR NOT NULL,"
                    + "password VARCHAR NOT NULL"
                    + ")";
            statement.execute(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void addStudent(Student student) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO students (name, age, studentID, email, password)" +
                    "VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setInt(3, student.getStudentID());
            statement.setString(4, student.getEmail());
            statement.setString(5, student.getPassword());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTeacher(Teacher teacher) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO teachers (name, age, teacherID, email, password)" +
                    "VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, teacher.getName());
            statement.setInt(2, teacher.getAge());
            statement.setInt(3, teacher.getTeacherID());
            statement.setString(4, teacher.getEmail());
            statement.setString(5, teacher.getPassword());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAdmin(Admin admin) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO admins (name, age, adminID, email, password)" +
                    "VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, admin.getName());
            statement.setInt(2, admin.getAge());
            statement.setInt(3, admin.getAdminID());
            statement.setString(4, admin.getEmail());
            statement.setString(5, admin.getPassword());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addParent(Parent parent) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO parents (name, childID, childName, email, password)" +
                    "VALUES (?, ?, ?, ?, ?)");
            statement.setString(1, parent.getName());
            statement.setInt(2, parent.getChildID());
            statement.setString(3, parent.getChildName());
            statement.setString(4, parent.getEmail());
            statement.setString(5, parent.getPassword());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }

    // Pull all attributes of a specific student for Display Details functionality in profile
    @Override
    public Student getStudent(int studentID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE studentID = ?");
            statement.setInt(1, studentID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Student(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        studentID,
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Pull all attributes of a specific teacher for Display Details functionality in profile
    @Override
    public Teacher getTeacher(int teacherID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM teachers WHERE teacherID = ?");
            statement.setInt(1, teacherID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Teacher(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        teacherID,
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Pull all attributes of a specific admin for Display Details functionality in profile
    @Override
    public Admin getAdmin(int adminID) {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM admins WHERE adminID = ?");
            statement.setInt(1, adminID);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Admin(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        adminID,
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Teacher getLoggedInTeacher(String email, String password){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM teachers WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Teacher(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("teacherID"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
            //TODO: Error Handling
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //TODO: merge into getLoggedInUser
    @Override
    public Student getLoggedInStudent(String email, String password){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE email = ? AND password = ?");
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Student(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("studentID"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
            }
            //TODO: Error Handling
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String Authenticate(String email, String password) {
        // Check authentication information against all four user tables
        String[] tables = {"students", "teachers", "parents", "admins"};
        for (String table : tables) {
            try {
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE email = ? AND password = ?");
                statement.setString(1, email);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    switch (table) {
                        case "students":
                            return "Student";
                        case "teachers":
                            return "Teacher";
                        case "parents":
                            return "Parent";
                        case "admins":
                            return "Admin";
                    }
                }
                //TODO: Error Handling
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    public void changePassword(String email, String oldPassword, String newPassword, String role) {
        String tableName = "";

        switch (role) {
            case "Student":
                tableName = "students";
                break;
            case "Teacher":
                tableName = "teachers";
                break;
            case "Admin":
                tableName = "admins";
                break;
            case "Parent":
                tableName = "parent";
                break;
            default:
                System.out.println("Invalid role provided.");
                return;
        }


        try {
            PreparedStatement statement = connection.prepareStatement("UPDATE " + tableName + " SET password = ?, WHERE email = ? AND password = ?");
            statement.setString(1, newPassword);
            statement.setString(2, email);
            statement.setString(3, oldPassword);
            //TODO: Error Handling
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // For use when viewing student names in lists of quiz results etc.
    @Override
    public List<String> getAllStudentNames() {
        List<String> studentNames = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT name FROM students");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                studentNames.add(name);
            }
            //TODO: Error Handling
        } catch (Exception e) {
            e.printStackTrace();
        }
        return studentNames;
    }

    // For use when a teacher is viewing their class list and details
    @Override
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int studentID = resultSet.getInt("studentID");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                Student student = new Student(name, age, studentID, email, password);
                students.add(student);
            }
            //TODO: Error Handling
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    public ObservableList<Teacher> getAllTeachers() {
        ObservableList<Teacher> teachers = FXCollections.observableArrayList();
        String query = "SELECT * FROM teachers";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                teachers.add(new Teacher(
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getInt("teacherID"),
                        rs.getString("email"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teachers;
    }


    // For database checking
    public void printAllStudents() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students");
            ResultSet resultSet = statement.executeQuery();

            System.out.println("---- All Students ----");
            while (resultSet.next()) {
                System.out.println("Name: " + resultSet.getString("name") +
                        ", Age: " + resultSet.getInt("age") +
                        ", ID: " + resultSet.getInt("studentID") +
                        ", Email: " + resultSet.getString("email"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // For database checking
    public void clearStudentsTable() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM students");
            statement.executeUpdate();
            System.out.println("All student records deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // For database checking
    public void clearTeacherTable() {
        try {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM teachers");
            statement.executeUpdate();
            System.out.println("All teacher records deleted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Classroom> getAllClassrooms() {
        ObservableList<Classroom> classrooms = FXCollections.observableArrayList();

        try {
            String query = "SELECT classroom_number, capacity FROM classrooms";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int classroomNumber = resultSet.getInt("classroom_number");
                int capacity = resultSet.getInt("capacity");

                // Placeholder values for numStudents/numTeachers
                classrooms.add(new Classroom(classroomNumber, capacity, 0, 0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classrooms;
    }

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



    public boolean assignUsers(Classroom selectedClassroom, Teacher selectedTeacher, List<Student> selectedStudents) {
        try {
            // Start a transaction
            connection.setAutoCommit(false);

            if (selectedTeacher != null) {
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

}





