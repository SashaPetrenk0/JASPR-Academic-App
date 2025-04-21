package org.jaspr.hr.demo;

import org.jaspr.hr.demo.model.Contact;

import java.util.List;
/**
 * Interface for the Contact Data Access Object that handles
 * the CRUD operations for the Contact class with the database.
 */
public interface IUserDAO {
    /**
     * Adds a new contact to the database.
     * @param user The user to add.
     */
    public void addUser(User user);
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
    public void changePassword(String email, String oldPassword, String newPassword);

    /**
     * Retrieves all contacts from the database.
     * @return A list of all contacts in the database.
     */
    public List<String> getAllStudentNames();

    public List<Student> getAllStudents();
}