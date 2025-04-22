package org.jaspr.hr.demo;

public class UserSession {
        // Static instance for Singleton
        private static UserSession instance;

        // Example: store the currently logged-in teacher
        private Teacher currentTeacher;
        private Student currentStudent;
        private Admin currentAdmin;
        private Parent currentParent;

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
        public Teacher getCurrentTeacher() {
            return currentTeacher;
        }

        public void setCurrentTeacher(Teacher teacher) {
            this.currentTeacher = teacher;
        }

    // Getter and setter for the logged-in student
    public Student getCurrentStudent() {
        return currentStudent;
    }

    public void setCurrentStudent(Student student) {
        this.currentStudent = student;
    }

        // Clear the session when user logs out
        public void clearSession() {
            currentTeacher = null;
        }


}
