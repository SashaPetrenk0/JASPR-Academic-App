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


    @AfterAll
    public static void cleanup() throws SQLException {
        connection.close();  // Close the connection after all tests are done
    }
}
