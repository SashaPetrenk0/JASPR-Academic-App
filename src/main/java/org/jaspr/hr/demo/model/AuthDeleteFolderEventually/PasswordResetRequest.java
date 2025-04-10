package org.jaspr.hr.demo.model.AuthDeleteFolderEventually;

import java.util.Date;

public class PasswordResetRequest {
    private String email;
    private String resetToken;
    private Date requestDate;

    // Constructor
    public PasswordResetRequest(String email, String resetToken, Date requestDate) {
        this.email = email;
        this.resetToken = resetToken;
        this.requestDate = requestDate;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    // toString() for easy representation
    @Override
    public String toString() {
        return "PasswordResetRequest{email='" + email + "', resetToken='" + resetToken + "', requestDate=" + requestDate + "}";
    }
}

