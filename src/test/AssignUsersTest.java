import org.jaspr.hr.demo.SqliteUserDAO;
import org.jaspr.hr.demo.Admin;
import org.jaspr.hr.demo.Parent;
import org.jaspr.hr.demo.Student;
import org.jaspr.hr.demo.Teacher;
import org.jaspr.hr.demo.Classroom;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignUsersTest {
    private static Connection connection;
    private static SqliteUserDAO userDAO;


    @BeforeAll
    public static void setup() throws SQLException {
        // Set up a connection to your test database
        connection = DriverManager.getConnection("jdbc:sqlite:users.db");
        // Initialize the userDAO with the connection
        userDAO = new SqliteUserDAO();  // Ensure you pass the connection to the DAO
    }

    @BeforeEach
    public void beginTransaction() throws SQLException {
        // Start a new transaction before each test
        Statement stmt = connection.createStatement();
        stmt.execute("DELETE FROM studentClassroom");
        stmt.execute("DELETE FROM students");
        stmt.execute("DELETE FROM teachers");
        stmt.execute("DELETE FROM classrooms");
        connection.setAutoCommit(false);  // Begin transaction
    }

    @AfterEach
    public void rollbackTransaction() throws SQLException {
        // Rollback the transaction after each test to ensure no changes persist
        connection.rollback();
    }
    @Test
    public void testAssignTeacherToClassroom() throws SQLException {
        // Step 1: Create and insert a classroom
        userDAO.createClassroom(101, 30);
        Classroom classroom = userDAO.getClassroomByNumber(101); // You'll need to implement this method

        // Step 2: Create and insert a teacher
        Teacher testTeacher = new Teacher("Ms. Smith", 35, 1001, "smith@example.com");
        userDAO.addTeacher(testTeacher, "testPassword", "testSalt");

        // Step 3: Assign teacher to classroom (no students)
        assertDoesNotThrow(() -> userDAO.assignUsers(classroom, testTeacher, new ArrayList<>()));
    }
    @Test
    public void testAssignStudentToClassroom() throws SQLException {
        // Create a sample classroom and student
        Classroom classroom = new Classroom(100, 30); // classroom_number = 100, capacity = 30
        userDAO.createClassroom(100, 30);

        Student student = new Student("Test Student",  12, 12345,"test@student.com");
        userDAO.addStudent(student, "testPwd", "testSalt");

        // Assign student to classroom
        List<Student> students = new ArrayList<>();
        students.add(student);
        userDAO.assignUsers(classroom, null, students); // Only student, no teacher

        // Fetch the updated classroom and check if student was added
        Classroom result = userDAO.getClassroomByNumberForStudents(100);
        assertNotNull(result);
        List<Student> assignedStudents = result.getStudents();  // Ensure this is loaded in DAO
        assertEquals(1, assignedStudents.size());
        assertEquals(student.getName(), assignedStudents.get(0).getName());
    }



    @AfterAll
    public static void cleanup() throws SQLException {
        connection.close();  // Close the connection after all tests are done
    }
}
