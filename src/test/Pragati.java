/* import org.jaspr.hr.demo.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.sql.*;
import java.util.List;

public class Pragati{
    private static Classroom classroom;
    private static Student student;
    private static Teacher teacher;
    private static Admin admin;
    private static Parent parent;




    @BeforeEach
    public void setup() {
        classroom = new Classroom(101, 30);
        student = new Student("John", 16, 12345, "john@gmail.com");
        teacher = new Teacher("Steve", 35, 14321, "steve@gmail.com");
        admin = new Admin("Alice", 40, 1001, "alice@gmail.com");
        parent = new Parent("Sarah", "John", 12345, "sarah@gmail.com");
    }

    @Test
    public void testConstructorWithTeachersAndStudents() throws SQLException {
        // Create a valid student object
        Classroom populatedClassroom = new Classroom(102, 35, 5, 1);
        assertEquals(102, populatedClassroom.getClassRoomNumber());
        assertEquals(35, populatedClassroom.getClassRoomCapacity());
        assertEquals(5, populatedClassroom.getNumStudents());
        assertEquals(1, populatedClassroom.getNumTeachers());
    }

    @Test
    public void testAddStudent() {
        classroom.addStudent(student);
        List<Student> students = classroom.getStudents();
        assertEquals(1, students.size());
        assertEquals("John", students.getFirst().getName());
    }

    @Test
    public void testDeleteStudent() {
        classroom.addStudent(student);
        classroom.deleteStudent(student);
        List<Student> students = classroom.getStudents();
        assertEquals(0, students.size());
    }

    @Test
    public void testClassroomCapacity(){
        assertEquals(30, classroom.getClassRoomCapacity());
        // to mimic adding students up the max capacity
        for (int i = 0; i < 30; i++) {
            classroom.addStudent(new Student("Student " + i, 16, 1000 + i, "student" + i + "@example.com"));
        }
        assertEquals(30, classroom.getStudents().size());
    }

    @Test
    public void testGetClassroomDetails(){
        classroom.setTeacher(teacher);
        classroom.addStudent(student);
        assertEquals(101, classroom.getClassRoomNumber());
        assertEquals(30, classroom.getClassRoomCapacity());
        assertEquals(1, classroom.getStudents().size());
        assertEquals("John", classroom.getStudents().getFirst().getName());
        assertEquals("Steve", classroom.getTeacher().getName());
    }

    @Test
    public void testAdminConstructorAndGetters(){
        assertEquals("Alice", admin.getName());
        assertEquals(40, admin.getAge());
        assertEquals(1001, admin.getAdminID());
        assertEquals("alice@gmail.com", admin.getEmail());
    }

    @Test
    public void testAdminGetRole(){
        assertEquals("Admin", admin.getRole());
    }


    @Test
    public void testStudentConstructorAndGetters(){
        assertEquals("John", student.getName());
        assertEquals(16, student.getAge());
        assertEquals(12345, student.getStudentID());
        assertEquals("john@gmail.com", student.getEmail());
    }

    @Test
    public void testStudentGetRole(){
        assertEquals("Student", student.getRole());
    }

    @Test
    public void testTeacherConstructorAndGetters(){
        assertEquals("Steve", teacher.getName());
        assertEquals(35, teacher.getAge());
        assertEquals(14321, teacher.getTeacherID());
        assertEquals("steve@gmail.com", teacher.getEmail());
    }

    @Test
    public void testTeacherGetRole(){
        assertEquals("Teacher", teacher.getRole());
    }


    @Test
    public void testParentConstructorAndGetters(){
        assertEquals("Sarah", parent.getName());
        assertEquals("John", parent.getChildName());
        assertEquals(12345, parent.getChildID());
        assertEquals("sarah@gmail.com", parent.getEmail());
    }

    @Test
    public void testParentGetRole(){
        assertEquals("Parent", parent.getRole());
    }

}


 */