import org.jaspr.hr.demo.SqliteUserDAO;
import org.jaspr.hr.demo.users.Admin;
import org.jaspr.hr.demo.users.Parent;
import org.jaspr.hr.demo.users.Student;
import org.jaspr.hr.demo.users.Teacher;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;

public class UserRegistrationTests {
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
    public void testValidStudentRegistration() throws SQLException {
        // Create a valid student object
        Student newStudent = new Student("John Doe", 20, 12345, "john@example.com", "password123");
        assertDoesNotThrow(() -> userDAO.addStudent(newStudent)); // Assert that no exception is thrown
    }

    @Test
    public void testInvalidEmailFormatStudent() {
        Student newStudent = new Student("John Doe", 20, 12345, "invalid-email", "password123");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addStudent(newStudent));
        assertEquals("Invalid email format.", thrown.getMessage());
    }

    @Test
    public void testInvalidStudentID() {
        Student newStudent = new Student("John Doe", 20, 123, "john@example.com", "password123");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addStudent(newStudent));
        assertEquals("Student ID must be at least 5 digits.", thrown.getMessage());
    }

    @Test
    public void testDuplicateStudentID() throws SQLException {
        Student newStudent1 = new Student("John Doe", 20, 12345, "john@example.com", "password123");
        userDAO.addStudent(newStudent1);

        Student newStudent2 = new Student("Jane Doe", 21, 12345, "jane@example.com", "password123");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addStudent(newStudent2));
        assertEquals("Student ID already exists.", thrown.getMessage());
    }

    @Test
    public void testInvalidAgeStudent() {
        Student newStudent = new Student("John Doe", -1, 12345, "john@example.com", "password123");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addStudent(newStudent));
        assertEquals("Enter a valid age (0–120).", thrown.getMessage());
    }

    @Test
    public void testEmptyFieldsStudent() {
        Student newStudent = new Student("", 20, 12345, "", "");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addStudent(newStudent));
        assertEquals("Name, email, and password cannot be empty.", thrown.getMessage());
    }

    // Tests for Teacher Registration
    @Test
    public void testValidTeacherRegistration() throws SQLException {
        Teacher newTeacher = new Teacher("Alice Smith", 30, 12345, "alice@example.com", "password123");
        assertDoesNotThrow(() -> userDAO.addTeacher(newTeacher));
    }

    @Test
    public void testInvalidTeacherID() {
        Teacher newTeacher = new Teacher("Alice Smith", 30, 123, "alice@example.com", "password123");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addTeacher(newTeacher));
        assertEquals("Teacher ID must be at least 5 digits.", thrown.getMessage());
    }

    @Test
    public void testDuplicateTeacherID() throws SQLException {
        Teacher newTeacher1 = new Teacher("Alice Smith", 30, 12345, "alice@example.com", "password123");
        userDAO.addTeacher(newTeacher1);

        Teacher newTeacher2 = new Teacher("Bob Johnson", 40, 12345, "bob@example.com", "password123");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addTeacher(newTeacher2));
        assertEquals("Teacher ID already exists.", thrown.getMessage());
    }

    @Test
    public void testEmptyFieldsTeacher() {
        Teacher newTeacher = new Teacher("", 30, 12345, "", "");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addTeacher(newTeacher));
        assertEquals("Name, email, and password cannot be empty.", thrown.getMessage());
    }

    // Tests for Parent Registration
    @Test
    public void testValidParentRegistration() throws SQLException {
        Parent newParent = new Parent("David Wilson", "Chris Wilson", 12345, "david@example.com", "password123");
        assertDoesNotThrow(() -> userDAO.addParent(newParent));
    }

    @Test
    public void testInvalidChildID() {
        Parent newParent = new Parent("David Wilson", "Chris Wilson", 99999, "david@example.com", "password123");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addParent(newParent));
        assertEquals("Child ID does not exist.", thrown.getMessage());
    }

    @Test
    public void testEmptyFieldsParent() {
        Parent newParent = new Parent("", "Chris Wilson", 12345, "", "");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addParent(newParent));
        assertEquals("Name, email, and password cannot be empty.", thrown.getMessage());
    }

    // Tests for Admin Registration
    @Test
    public void testValidAdminRegistration() throws SQLException {
        Admin newAdmin = new Admin("Eve Lee", 35, 12345, "eve@example.com", "password123");
        assertDoesNotThrow(() -> userDAO.addAdmin(newAdmin));
    }

    @Test
    public void testInvalidAdminID() {
        Admin newAdmin = new Admin("Eve Lee", 35, 123, "eve@example.com", "password123");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addAdmin(newAdmin));
        assertEquals("Admin ID must be at least 5 digits.", thrown.getMessage());
    }

    @Test
    public void testDuplicateAdminID() throws SQLException {
        Admin newAdmin1 = new Admin("Eve Lee", 35, 12345, "eve@example.com", "password123");
        userDAO.addAdmin(newAdmin1);

        Admin newAdmin2 = new Admin("James Green", 40, 12345, "james@example.com", "password123");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addAdmin(newAdmin2));
        assertEquals("Admin ID already exists.", thrown.getMessage());
    }

    @Test
    public void testEmptyFieldsAdmin() {
        Admin newAdmin = new Admin("", 35, 12345, "", "");
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addAdmin(newAdmin));
        assertEquals("Name, email, and password cannot be empty.", thrown.getMessage());
    }

    // Tests for ID Validation (Student, Teacher, Admin)
    @Test
    public void testCheckExistingStudentID() throws SQLException {
        Student newStudent = new Student("John Doe", 20, 12345, "john@example.com", "password123");
        userDAO.addStudent(newStudent);
        assertTrue(userDAO.isStudentIDExists(12345));
    }

    @Test
    public void testCheckNonExistingStudentID() throws SQLException {
        assertFalse(userDAO.isStudentIDExists(99999));
    }

    @Test
    public void testCheckExistingTeacherID() throws SQLException {
        Teacher newTeacher = new Teacher("Alice Smith", 30, 12345, "alice@example.com", "password123");
        userDAO.addTeacher(newTeacher);
        assertTrue(userDAO.isTeacherIDExists(12345));
    }

    @Test
    public void testCheckNonExistingTeacherID() throws SQLException {
        assertFalse(userDAO.isTeacherIDExists(99999));
    }

    @Test
    public void testCheckExistingAdminID() throws SQLException {
        Admin newAdmin = new Admin("Eve Lee", 35, 12345, "eve@example.com", "password123");
        userDAO.addAdmin(newAdmin);
        assertTrue(userDAO.isAdminIDExists(12345));
    }

    @Test
    public void testCheckNonExistingAdminID() throws SQLException {
        assertFalse(userDAO.isAdminIDExists(99999));
    }

    @AfterAll
    public static void cleanup() throws SQLException {
        connection.close();  // Close the connection after all tests are done
    }
}
