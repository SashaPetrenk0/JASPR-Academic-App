package org.jaspr.hr.demo;

public abstract class User {
   protected String name;
   protected String email;
   protected String passwordHash;
   protected String role;
   protected String salt;

   public User(String name, String email, String passwordHash, String salt){
       this.name = name;
       this.email = email;
       this.passwordHash = passwordHash;
       this.salt = salt;
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
    public String getPassword()
    {
        return passwordHash;
    }
    public String getRoleValue()
    {
        return role;
    }
}
