package org.jaspr.hr.demo;

/**
 * A singleton class that represents the currently logged-in user.
 */
public class UserSession {

    /**
     * The single instance of the UserSession.
     */
        private static UserSession instance;


        private User currentUser;
        private String role;


    /**
     * Private constructor to prevent instantiation from outside the class.
     */
        private UserSession() {
        }

    /**
     * Returns  instance from outside of class
     * @return single instance of user session. If none exists, one is created.
     */
        public static UserSession getInstance() {
            if (instance == null) {
                instance = new UserSession();
            }
            return instance;
        }

    /**
     * Retrieves all the current user as a User object
     * @return user object containing person who is logged-in.
     */
        public User getCurrentUser() {
            return currentUser;
        }

    /**
     * Populates the current user
     * @param user User object containing the name and email of the person logging in
     * @param role The role teacher,student,admin, of the user as a string.
     */
        public void setCurrentUser(User user, String role) {
            this.currentUser = user;
            this.role = role;
        }

    /**
     * Returns the role of the logged-in user
     * @return string role of the user 'teacher', 'student', 'admin'
     */
    public String getRole() {
        return role;
    }

    /**
     * Set user instance to null when user logs out
     */
        public void clearSession() {
            currentUser = null;
            role = null;
        }


}
