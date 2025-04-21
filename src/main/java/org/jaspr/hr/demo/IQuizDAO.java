package org.jaspr.hr.demo;

import java.util.List;
/**
 * Interface for the Quiz Data Access Object that handles
 * the CRUD operations for the Quiz class with the database.
 */
public interface IQuizDAO {
    /**
     * Adds a new quiz to the database.
     * @param quiz The quiz to add.
     */
    public void addQuiz(Quiz quiz);


    /**
     * Deletes a contact from the database.
     * @param email Email that confirms identity.
     * @param oldPassword Password that confirms identity.
     * @param newPassword New Password to be changed to.
     */
    public void changePassword(String email, String oldPassword, String newPassword, String role);

    /**
     * Retrieves all quizzes created by a teacher from the database.
     * @return A list of all quizzes created by a specific teacher.
     * @param teacher teacher who owns the quizzes.
     */
    public List<String> getAllQuizzes(Teacher teacher);

    /**
     * Retrieves all quizzes created by a specific student from the database.
     * @return A list of all quizzes created by a specific student.
     * @param student student who owns the quizzes.
     */
    public List<String> getAllQuizzes(Student student);

    /**
     * Retrieves all quizzes assigned by a teacher to a student from the database.
     * @return A list of all quizzes assigned to the student by the teacher in the database.
     * @param teacher teacher who created and assigned the quiz.
     * @param student who is assigned to the quiz.
     */

    public List<String> getAllQuizzes(Teacher teacher, Student student );


}