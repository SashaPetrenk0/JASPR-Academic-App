import org.jaspr.hr.demo.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

    @Test
    public void testStudentGettersAndSetters() {
        Student student = new Student("John Doe", 20, 12345, "john@example.com");

        assertEquals("John Doe", student.getName());
        assertEquals(20, student.getAge());
        assertEquals(12345, student.getStudentID());
        assertEquals("john@example.com", student.getEmail());

        // Set new values
        student.setName("Jane Smith");
        student.setAge(22);
        student.setStudentID(54321);
        student.setEmail("jane@example.com");

        assertEquals("Jane Smith", student.getName());
        assertEquals(22, student.getAge());
        assertEquals(54321, student.getStudentID());
        assertEquals("jane@example.com", student.getEmail());
    }

    @Test
    public void testTeacherGettersAndSetters() {
        Teacher teacher = new Teacher("Alice Brown", 35, 67890, "alice@example.com");

        assertEquals("Alice Brown", teacher.getName());
        assertEquals(35, teacher.getAge());
        assertEquals(67890, teacher.getTeacherID());
        assertEquals("alice@example.com", teacher.getEmail());

        // Set new values
        teacher.setName("Bob Black");
        teacher.setAge(40);
        teacher.setTeacherID(98765);
        teacher.setEmail("bob@example.com");

        assertEquals("Bob Black", teacher.getName());
        assertEquals(40, teacher.getAge());
        assertEquals(98765, teacher.getTeacherID());
        assertEquals("bob@example.com", teacher.getEmail());
    }


    @Test
    public void testAdminGettersAndSetters() {
        Admin admin = new Admin("Emma White", 45, 11111, "emma@example.com");

        assertEquals("Emma White", admin.getName());
        assertEquals(45, admin.getAge());
        assertEquals(11111, admin.getAdminID());
        assertEquals("emma@example.com", admin.getEmail());

        // Set new values
        admin.setName("Liam Grey");
        admin.setAge(50);
        admin.setAdminID(22222);
        admin.setEmail("liam@example.com");

        assertEquals("Liam Grey", admin.getName());
        assertEquals(50, admin.getAge());
        assertEquals(22222, admin.getAdminID());
        assertEquals("liam@example.com", admin.getEmail());
    }
}
