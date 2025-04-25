package org.jaspr.hr.demo;

public abstract class User {
   protected String name;
   protected String email;
   protected String password;
   protected String role;

   public User(String name, String email, String password){
       this.name = name;
       this.email = email;
       this.password = password;
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
        return password;
    }
    public String getRoleValue()
    {
        return role;
    }
}
