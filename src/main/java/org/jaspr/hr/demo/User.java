package org.jaspr.hr.demo;

/**
 * An abstract class representing a user with a  name email, and role (student, teacher admin)
 */

public abstract class User {
   protected String name;
   protected Integer age;
   protected String email;
   protected String role;

   public User(String name, int age, String email){
       this.name = name;
       this.age = age;
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

    public int getAge() { return  age;}
    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age){ this.age = age; }

    public void setEmail(String email) {
        this.email = email;
    }
}
