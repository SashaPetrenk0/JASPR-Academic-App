package org.jaspr.hr.demo;

import org.jaspr.hr.demo.users.User;

public class UserSession {
        // Static instance for Singleton
        private static UserSession instance;

        // Example: store the currently logged-in teacher
        private User currentUser;
        private String role;


        // Private constructor to prevent direct instantiation
        private UserSession() {
        }

        // Public method to get the single instance
        public static UserSession getInstance() {
            if (instance == null) {
                instance = new UserSession();
            }
            return instance;
        }

        // Getter and setter for the logged-in teacher
        public User getCurrentUser() {
            return currentUser;
        }

        public void setCurrentUser(User user, String role) {
            this.currentUser = user;
            this.role = role;
        }

    public String getRole() {
        return role;
    }

    // Clear the session when user logs out
        public void clearSession() {
            currentUser = null;
            role = null;
        }


}
