package org.jaspr.hr.demo.model.AuthDeleteFolderEventually;

import org.jaspr.hr.demo.model.usersDeleteFolderEventually.User;

public class Session {
    private String sessionId;
    private User user;

    // Constructor
    public Session(String sessionId, User user) {
        this.sessionId = sessionId;
        this.user = user;
    }

    // Getters and Setters
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // toString() for easy representation
    @Override
    public String toString() {
        return "Session{sessionId='" + sessionId + "', user=" + user + "}";
    }
}

