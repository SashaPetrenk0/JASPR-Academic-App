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
     * @param user The contact to update.
     */
    public void getUser(User user);
    /**
     * Deletes a contact from the database.
     * @param user The contact to delete.
     */
    public void changePassword(User user);

    /**
     * Retrieves all contacts from the database.
     * @return A list of all contacts in the database.
     */
    public List<Student> getAllStudents();
}