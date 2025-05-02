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

        try {
            if (connection != null) {
                // Add 'salt' column to each table if it doesn't exist
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("ALTER TABLE students ADD COLUMN passwordHash TEXT;");
                } catch (SQLException e) {
                    if (!e.getMessage().contains("duplicate column name")) {
                        e.printStackTrace();
                    }
                }

                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("ALTER TABLE teachers ADD COLUMN passwordHash TEXT;");
                } catch (SQLException e) {
                    if (!e.getMessage().contains("duplicate column name")) {
                        e.printStackTrace();
                    }
                }

                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("ALTER TABLE admins ADD COLUMN passwordHash TEXT;");
                } catch (SQLException e) {
                    if (!e.getMessage().contains("duplicate column name")) {
                        e.printStackTrace();
                    }
                }

                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("ALTER TABLE parents ADD COLUMN passwordHash TEXT;");
                } catch (SQLException e) {
                    if (!e.getMessage().contains("duplicate column name")) {
                        e.printStackTrace();
                    }
                }
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("ALTER TABLE students ADD COLUMN salt TEXT;");
                } catch (SQLException e) {
                    if (!e.getMessage().contains("duplicate column name")) {
                        e.printStackTrace();
                    }
                }
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("ALTER TABLE teachers ADD COLUMN salt TEXT;");
                } catch (SQLException e) {
                    if (!e.getMessage().contains("duplicate column name")) {
                        e.printStackTrace();
                    }
                }
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("ALTER TABLE admins ADD COLUMN salt TEXT;");
                } catch (SQLException e) {
                    if (!e.getMessage().contains("duplicate column name")) {
                        e.printStackTrace();
                    }
                }
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("ALTER TABLE parents ADD COLUMN salt TEXT;");
                } catch (SQLException e) {
                    if (!e.getMessage().contains("duplicate column name")) {
                        e.printStackTrace();
                    }
                }

                // Add teacherID to classrooms if needed
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("ALTER TABLE classrooms ADD COLUMN teacherID INTEGER;");
                } catch (SQLException e) {
                    if (!e.getMessage().contains("duplicate column name")) {
                        e.printStackTrace();
                    }
                }

                // Create all necessary tables
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
                    + "passwordHash VARCHAR NOT NULL,"
                    + "salt TEXT NOT NULL,"
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
                    + "passwordHash VARCHAR NOT NULL,"
                    + "salt TEXT NOT NULL"
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
                    + "passwordHash VARCHAR NOT NULL,"
                    + "salt TEXT NOT NULL"
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
                    + "passwordHash VARCHAR NOT NULL,"
                    + "salt TEXT NOT NULL"
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
    public void addStudent(Student student, String password, String salt) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO students (name, age, studentID, email, passwordHash, salt)" +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setInt(3, student.getStudentID());
            statement.setString(4, student.getEmail());
            statement.setString(5, password);
            statement.setString(6, salt);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addTeacher(Teacher teacher, String password, String salt) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO teachers (name, age, teacherID, email, passwordHash, salt)" +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, teacher.getName());
            statement.setInt(2, teacher.getAge());
            statement.setInt(3, teacher.getTeacherID());
            statement.setString(4, teacher.getEmail());
            statement.setString(5, password);
            statement.setString(6, salt);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addAdmin(Admin admin, String password, String salt) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO admins (name, age, adminID, email, passwordHash, salt)" +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, admin.getName());
            statement.setInt(2, admin.getAge());
            statement.setInt(3, admin.getAdminID());
            statement.setString(4, admin.getEmail());
            statement.setString(5, password);
            statement.setString(6, salt);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addParent(Parent parent, String password, String salt) {
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO parents (name, childID, childName, email, passwordHash, salt)" +
                    "VALUES (?, ?, ?, ?, ?, ?)");
            statement.setString(1, parent.getName());
            statement.setInt(2, parent.getChildID());
            statement.setString(3, parent.getChildName());
            statement.setString(4, parent.getEmail());
            statement.setString(5, password);
            statement.setString(6, salt);
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
                        resultSet.getString("email")
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
                        resultSet.getString("email")
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
                        resultSet.getString("email")
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM teachers WHERE email = ? AND passwordHash = ?");
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Teacher(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("teacherID"),
                        resultSet.getString("email")
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
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM students WHERE email = ? AND passwordHash = ?");
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Student(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("studentID"),
                        resultSet.getString("email")
                );
            }
            //TODO: Error Handling
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Admin getLoggedInAdmin(String email, String password){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM admins WHERE email = ? AND passwordHash = ?");
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Admin(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("adminID"),
                        resultSet.getString("email")
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
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE email = ? AND passwordHash = ?");
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
    public String getSalt(String email) {
        String[] tables = {"students", "teachers", "admins", "parent"};

        try {
            for (String table : tables) {
                String sql = "SELECT salt FROM " + table + " WHERE email = ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, email);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    // Found the user
                    System.out.println("User found in table: " + table);
                    return resultSet.getString("salt");
                }
                // else move to the next table
            }

            // If no user found
            System.out.println("No user found with email: " + email);
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


//
//    @Override
//    public String getSalt(String email, String role) {
//        String tableName = "";
//        String salt = null;
//
//        switch (role) {
//            case "Student":
//                tableName = "students";
//                break;
//            case "Teacher":
//                tableName = "teachers";
//                break;
//            case "Admin":
//                tableName = "admins";
//                break;
//            case "Parent":
//                tableName = "parent";
//                break;
//            default:
//                System.out.println("Invalid role provided.");
//        }
//
//        try {
//            PreparedStatement statement = connection.prepareStatement("SELECT salt FROM " + tableName + " WHERE email = ?");
//            statement.setString(1, email);
//
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                salt = resultSet.getString("salt");
//            }
//            return salt;
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//
//        }
//    }
//
//
//    public String getTable(String email) {
//        String[] tables = {"students", "teachers", "parents", "admins"};
//        for (String table : tables) {
//            try {
//                PreparedStatement statement = connection.prepareStatement("SELECT * FROM " + table + " WHERE email = ?");
//                statement.setString(1, email);
//                ResultSet resultSet = statement.executeQuery();
//                if (resultSet.next()) {
//                    return table;
//                }
//                //TODO: Error Handling
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }






    @Override
    public boolean changePassword(String email, String oldPassword, String newPassword, String role) {
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
                return false;
        }

        try {
            // First check if the old password matches the one in the database
            String checkPasswordQuery = "SELECT passwordHash FROM " + tableName + " WHERE email = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkPasswordQuery);
            checkStatement.setString(1, email);

            ResultSet rs = checkStatement.executeQuery();

            if (rs.next()) {
                String currentPasswordInDb = rs.getString("passwordHash");

                if (currentPasswordInDb.equals(oldPassword)) {
                    // Password matches, now update it
                    String updateQuery = "UPDATE " + tableName + " SET passwordHash = ? WHERE email = ?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
                    updateStatement.setString(1, newPassword);
                    updateStatement.setString(2, email);

                    int rowsUpdated = updateStatement.executeUpdate();
                    return rowsUpdated > 0;  // Ensure at least one row is updated
                } else {
                    System.out.println("Old password does not match.");
                    return false;
                }
            } else {
                System.out.println("Email not found.");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
                String password = resultSet.getString("passwordHash");

                Student student = new Student(name, age, studentID, email);
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
                        rs.getString("email")
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

    public Integer getClassroomNumberForTeacher(int teacherID) {
        Integer classroomNumber = null;

        String query = "SELECT classroom_number FROM classrooms WHERE teacherID = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teacherID);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                classroomNumber = resultSet.getInt("classroom_number");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return classroomNumber;
    }




}





