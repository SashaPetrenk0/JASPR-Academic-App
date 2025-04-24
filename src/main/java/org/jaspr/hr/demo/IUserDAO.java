package org.jaspr.hr.demo;

import java.util.List;
/**
 * Interface for the Contact Data Access Object that handles
 * the CRUD operations for the Contact class with the database.
 */
public interface IUserDAO {
    /**
     * Adds a new student to the database.
     * @param student The user to add.
     */
    public void addStudent(Student student);
    /**
     * Adds a new teacher to the database.
     * @param teacher The user to add.
     */
    public void addTeacher(Teacher teacher);
    /**
     * Adds a new admin to the database.
     * @param admin The user to add.
     */
    public void addAdmin(Admin admin);
    /**
     * Adds a new parent to the database.
     * @param parent The user to add.
     */
    public void addParent(Parent parent);


    public String Authenticate(String email, String password);



    public Teacher getLoggedInTeacher (String email, String password);
    public Student getLoggedInStudent (String email, String password);

    /**
     * Updates an existing contact in the database.
     * @param studentID The contact to update.
     */
    public Student getStudent(int studentID);
    /**
     * Updates an existing contact in the database.
     * @param teacherID The contact to update.
     */
    public Teacher getTeacher(int teacherID);
    /**
     * Updates an existing contact in the database.
     * @param adminID The contact to update.
     */
    public Admin getAdmin(int adminID);

    /**
     * Deletes a contact from the database.
     * @param email Email that confirms identity.
     * @param oldPassword Password that confirms identity.
     * @param newPassword New Password to be changed to.
     */
    public void changePassword(String email, String oldPassword, String newPassword, String role);

    /**
     * Retrieves all contacts from the database.
     * @return A list of all contacts in the database.
     */
    public List<String> getAllStudentNames();

    public List<Student> getAllStudents();
}