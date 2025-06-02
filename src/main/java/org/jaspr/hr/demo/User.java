package org.jaspr.hr.demo;

/**
 * An abstract class representing a user with a  name email, and role (student, teacher admin)
 */

public abstract class User {
   protected String name;
   protected String email;
   protected String role;

   public User(String name, String email){
       this.name = name;
       this.email = email;
       this.role = getRole();
   }

   public abstract String getRole();

   public String getName()
   {
       return name;
   }

    public String getEmail()
    {
        return email;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
