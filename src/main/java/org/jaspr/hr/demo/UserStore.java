package org.jaspr.hr.demo;

import java.util.HashSet;
import java.util.Set;

public class UserStore {
    // 1. The static variable to hold the single instance of UserStore.
    private static UserStore instance;

    // 2. The Set to store registered users.
    private Set<String> registeredUsers = new HashSet<>();

    // 3. Private constructor to prevent instantiation from outside.
    private UserStore() { }

    // 4. Public static method to get the instance of UserStore.
    public static UserStore getInstance() {
        if (instance == null) {
            // If the instance doesn't exist, create it.
            instance = new UserStore();
        }
        return instance;
    }

    // 5. Register a user.
    public void registerUser(String username) {
        registeredUsers.add(username);
    }

    // 6. Check if a user is registered.
    public boolean isUserRegistered(String username) {
        return registeredUsers.contains(username);
    }

    // 7. Check if there are any registered users.
    public boolean hasAnyUsers() {
        return !registeredUsers.isEmpty();
    }
}

