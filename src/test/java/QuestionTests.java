//import org.jaspr.hr.demo.*;
//import org.junit.jupiter.api.*;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//import org.jaspr.hr.demo.SqliteUserDAO;
//import org.jaspr.hr.demo.Admin;
//import org.jaspr.hr.demo.Parent;
//import org.jaspr.hr.demo.Student;
//import org.jaspr.hr.demo.Teacher;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.util.List;
//
//
//public class QuestionTests {
//    private static Connection connection;
//    private static SqliteUserDAO userDAO;
//
//    private static Classroom classroom;
//    private static Student student;
//    private static Teacher teacher;
//    private static Admin admin;
//    private static Parent parent;
//
//    @BeforeEach
//    public void setup() throws SQLException {
//        // Set up a connection to your test database
//        connection = DriverManager.getConnection("jdbc:sqlite:users.db");
//        // Initialize the userDAO with the connection
//        userDAO = new SqliteUserDAO();  // Ensure you pass the connection to the DAO
//        classroom = new Classroom(101, 30);
//        student = new Student("John", 16, 12345, "john@gmail.com");
//        teacher = new Teacher("Steve", 35, 14321, "steve@gmail.com");
//        admin = new Admin("Alice", 40, 1001, "alice@gmail.com");
//        parent = new Parent("Sarah", "John", 12345, "sarah@gmail.com");
//    }
//
//    @BeforeEach
//    public void beginTransaction() throws SQLException {
//        // Start a new transaction before each test
//        connection.setAutoCommit(false);  // Begin transaction
//    }
//
//    @AfterEach
//    public void rollbackTransaction() throws SQLException {
//        // Rollback the transaction after each test to ensure no changes persist
//        connection.rollback();
//    }
//
////    @Test
////    public void testConstructor() {
////        String questionText  = "What is the capital of France?";
////        String[] choices = {"Berlin", "Paris", "Rome", "Madrid"};
////        int correctAnswerIndex = 2;
////
////        Question question = new Question("What is the capital of France?", "Berlin", "Paris", "Rome", "Madrid", "Paris");
////
////        assertEquals(questionText, question.getQuestion());
////        assertArrayEquals(choices, question.getChoices());
////        assertEquals(correctAnswerIndex, question.getCorrectAnswerIndex());
////    }
//
//    @Test
//    public void testGetChoices() {
//        String[] choices = {"Red", "Green", "Blue"};
//        Question question = new Question("What colour is the sky?", "Red", "Green", "Blue", "Purple", "Blue");
//
//        assertEquals("What colour is the sky?", question.getQuestion());
//        assertEquals("Red", question.getOptionA());
//        assertEquals("Green", question.getOptionB());
//        assertEquals("Blue", question.getOptionC());
//        assertEquals("Purple", question.getOptionD());
//        assertEquals("Blue", question.getCorrectAnswer());
//    }
//
//
//    @Test
//    public void testValidStudentRegistration() throws SQLException {
//        // Create a valid student object
//        Student newStudent = new Student("John Doe", 20, 12345, "john@example.com");
//        assertDoesNotThrow(() -> userDAO.addStudent(newStudent, "password123","239874387")); // Assert that no exception is thrown
//    }
//
//    @Test
//    public void testInvalidEmailFormatStudent() {
//        Student newStudent = new Student("John Doe", 20, 12345, "invalid-email");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addStudent(newStudent, "password123","239874387"));
//        assertEquals("Invalid email format.", thrown.getMessage());
//    }
//
//    @Test
//    public void testInvalidStudentID() {
//        Student newStudent = new Student("John Doe", 20, 123, "john@example.com");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addStudent(newStudent, "password123","239874387"));
//        assertEquals("Student ID must be at least 5 digits.", thrown.getMessage());
//    }
//
//    @Test
//    public void testDuplicateStudentID() throws SQLException {
//        Student newStudent1 = new Student("John Doe", 20, 12345, "john@example.com");
//        userDAO.addStudent(newStudent1, "password123","239874387");
//
//        Student newStudent2 = new Student("Jane Doe", 21, 12345, "jane@example.com");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addStudent(newStudent2, "password123","239874387"));
//        assertEquals("Student ID already exists.", thrown.getMessage());
//    }
//
//    @Test
//    public void testInvalidAgeStudent() {
//        Student newStudent = new Student("John Doe", -1, 12345, "john@example.com");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addStudent(newStudent, "password123","239874387"));
//        assertEquals("Enter a valid age (0–120).", thrown.getMessage());
//    }
//
//    @Test
//    public void testEmptyFieldsStudent() {
//        Student newStudent = new Student("", 20, 12345, "");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addStudent(newStudent, "password123","239874387"));
//        assertEquals("Name, email, and password cannot be empty.", thrown.getMessage());
//    }
//
//    // Tests for Teacher Registration
//    @Test
//    public void testValidTeacherRegistration() throws SQLException {
//        Teacher newTeacher = new Teacher("Alice Smith", 30, 12345, "alice@example.com");
//        assertDoesNotThrow(() -> userDAO.addTeacher(newTeacher, "password123","239874387"));
//    }
//
//    @Test
//    public void testInvalidTeacherID() {
//        Teacher newTeacher = new Teacher("Alice Smith", 30, 123, "alice@example.com");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addTeacher(newTeacher, "password123","239874387"));
//        assertEquals("Teacher ID must be at least 5 digits.", thrown.getMessage());
//    }
//
//    @Test
//    public void testDuplicateTeacherID() throws SQLException {
//        Teacher newTeacher1 = new Teacher("Alice Smith", 30, 12345, "alice@example.com");
//        userDAO.addTeacher(newTeacher1, "password123","239874387");
//
//        Teacher newTeacher2 = new Teacher("Bob Johnson", 40, 12345, "bob@example.com");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addTeacher(newTeacher2, "password123","239874387"));
//        assertEquals("Teacher ID already exists.", thrown.getMessage());
//    }
//
//    @Test
//    public void testEmptyFieldsTeacher() {
//        Teacher newTeacher = new Teacher("", 30, 12345, "");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addTeacher(newTeacher, "password123","239874387"));
//        assertEquals("Name, email, and password cannot be empty.", thrown.getMessage());
//    }
//
//    // Tests for Parent Registration
//    @Test
//    public void testValidParentRegistration() throws SQLException {
//        Parent newParent = new Parent("David Wilson", "Chris Wilson", 12345, "david@example.com");
//        assertDoesNotThrow(() -> userDAO.addParent(newParent, "password123","239874387"));
//    }
//
//    @Test
//    public void testInvalidChildID() {
//        Parent newParent = new Parent("David Wilson", "Chris Wilson", 99999, "david@example.com");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addParent(newParent, "password123","239874387"));
//        assertEquals("Child ID does not exist.", thrown.getMessage());
//    }
//
//    @Test
//    public void testEmptyFieldsParent() {
//        Parent newParent = new Parent("", "Chris Wilson", 12345, "");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addParent(newParent, "password123","239874387"));
//        assertEquals("Name, email, and password cannot be empty.", thrown.getMessage());
//    }
//
//    // Tests for Admin Registration
//    @Test
//    public void testValidAdminRegistration() throws SQLException {
//        Admin newAdmin = new Admin("Eve Lee", 35, 12345, "eve@example.com");
//        assertDoesNotThrow(() -> userDAO.addAdmin(newAdmin, "password123","239874387"));
//    }
//
//    @Test
//    public void testInvalidAdminID() {
//        Admin newAdmin = new Admin("Eve Lee", 35, 123, "eve@example.com");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addAdmin(newAdmin, "password123","239874387"));
//        assertEquals("Admin ID must be at least 5 digits.", thrown.getMessage());
//    }
//
//    @Test
//    public void testDuplicateAdminID() throws SQLException {
//        Admin newAdmin1 = new Admin("Eve Lee", 35, 12345, "eve@example.com");
//        userDAO.addAdmin(newAdmin1, "password123","239874387");
//
//        Admin newAdmin2 = new Admin("James Green", 40, 12345, "james@example.com");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addAdmin(newAdmin2, "password123","239874387"));
//        assertEquals("Admin ID already exists.", thrown.getMessage());
//    }
//
//    @Test
//    public void testEmptyFieldsAdmin() {
//        Admin newAdmin = new Admin("", 35, 12345, "");
//        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> userDAO.addAdmin(newAdmin, "password123","239874387"));
//        assertEquals("Name, email, and password cannot be empty.", thrown.getMessage());
//    }
//
//    // Tests for ID Validation (Student, Teacher, Admin)
//    @Test
//    public void testCheckExistingStudentID() throws SQLException {
//        Student newStudent = new Student("John Doe", 20, 12345, "john@example.com");
//        userDAO.addStudent(newStudent, "password123","239874387");
//        assertTrue(userDAO.isStudentIDExists(12345));
//    }
//
//    @Test
//    public void testCheckNonExistingStudentID() throws SQLException {
//        assertFalse(userDAO.isStudentIDExists(99999));
//    }
//
//    @Test
//    public void testCheckExistingTeacherID() throws SQLException {
//        Teacher newTeacher = new Teacher("Alice Smith", 30, 12345, "alice@example.com");
//        userDAO.addTeacher(newTeacher, "password123","239874387");
//        assertTrue(userDAO.isTeacherIDExists(12345));
//    }
//
//    @Test
//    public void testCheckNonExistingTeacherID() throws SQLException {
//        assertFalse(userDAO.isTeacherIDExists(99999));
//    }
//
//    @Test
//    public void testCheckExistingAdminID() throws SQLException {
//        Admin newAdmin = new Admin("Eve Lee", 35, 12345, "eve@example.com");
//        userDAO.addAdmin(newAdmin, "password123","239874387");
//        assertTrue(userDAO.isAdminIDExists(12345));
//    }
//
//    @Test
//    public void testCheckNonExistingAdminID() throws SQLException {
//        assertFalse(userDAO.isAdminIDExists(99999));
//    }
//
//        @Test
//        public void testConstructorWithTeachersAndStudents() throws SQLException {
//            // Create a valid student object
//            Classroom populatedClassroom = new Classroom(102, 35, 5, 1);
//            assertEquals(102, populatedClassroom.getClassRoomNumber());
//            assertEquals(35, populatedClassroom.getClassRoomCapacity());
//            assertEquals(5, populatedClassroom.getNumStudents());
//            assertEquals(1, populatedClassroom.getNumTeachers());
//        }
//
//        @Test
//        public void testAddStudent() {
//            classroom.addStudent(student);
//            List<Student> students = classroom.getStudents();
//            assertEquals(1, students.size());
//            assertEquals("John", students.getFirst().getName());
//        }
//
//        @Test
//        public void testDeleteStudent() {
//            classroom.addStudent(student);
//            classroom.deleteStudent(student);
//            List<Student> students = classroom.getStudents();
//            assertEquals(0, students.size());
//        }
//
//        @Test
//        public void testClassroomCapacity(){
//            assertEquals(30, classroom.getClassRoomCapacity());
//            // to mimic adding students up the max capacity
//            for (int i = 0; i < 30; i++) {
//                classroom.addStudent(new Student("Student " + i, 16, 1000 + i, "student" + i + "@example.com"));
//            }
//            assertEquals(30, classroom.getStudents().size());
//        }
//
//        @Test
//        public void testGetClassroomDetails(){
//            classroom.setTeacher(teacher);
//            classroom.addStudent(student);
//            assertEquals(101, classroom.getClassRoomNumber());
//            assertEquals(30, classroom.getClassRoomCapacity());
//            assertEquals(1, classroom.getStudents().size());
//            assertEquals("John", classroom.getStudents().getFirst().getName());
//            assertEquals("Steve", classroom.getTeacher().getName());
//        }
//
//        @Test
//        public void testAdminConstructorAndGetters(){
//            assertEquals("Alice", admin.getName());
//            assertEquals(40, admin.getAge());
//            assertEquals(1001, admin.getAdminID());
//            assertEquals("alice@gmail.com", admin.getEmail());
//        }
//
//        @Test
//        public void testAdminGetRole(){
//            assertEquals("Admin", admin.getRole());
//        }
//
//
//        @Test
//        public void testStudentConstructorAndGetters(){
//            assertEquals("John", student.getName());
//            assertEquals(16, student.getAge());
//            assertEquals(12345, student.getStudentID());
//            assertEquals("john@gmail.com", student.getEmail());
//        }
//
//        @Test
//        public void testStudentGetRole(){
//            assertEquals("Student", student.getRole());
//        }
//
//        @Test
//        public void testTeacherConstructorAndGetters(){
//            assertEquals("Steve", teacher.getName());
//            assertEquals(35, teacher.getAge());
//            assertEquals(14321, teacher.getTeacherID());
//            assertEquals("steve@gmail.com", teacher.getEmail());
//        }
//
//        @Test
//        public void testTeacherGetRole(){
//            assertEquals("Teacher", teacher.getRole());
//        }
//
//
//        @Test
//        public void testParentConstructorAndGetters(){
//            assertEquals("Sarah", parent.getName());
//            assertEquals("John", parent.getChildName());
//            assertEquals(12345, parent.getChildID());
//            assertEquals("sarah@gmail.com", parent.getEmail());
//        }
//
//        @Test
//        public void testParentGetRole(){
//            assertEquals("Parent", parent.getRole());
//        }
//    @AfterAll
//    public static void cleanup() throws SQLException {
//        connection.close();  // Close the connection after all tests are done
//    }
//
//    }
//
//
//
//
//
