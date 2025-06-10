package org.jaspr.hr.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class that creates a single instance of a Sqlite connection to our database, users.db
 */
public class SqliteConnection {
    private static Connection instance = null;

    /**
     * private constructor to ensure that this is the only place the connection can be initialised
     */
    private SqliteConnection() {
        String url = "jdbc:sqlite:users.db";
        try {
            instance = DriverManager.getConnection(url);
        } catch (SQLException sqlEx) {
            System.err.println(sqlEx);
        }
    }

    /**
     * Returns the single instance of the connection
     * @return the instance of the connection
     */
    public static Connection getInstance() {
        if (instance == null) {
            new SqliteConnection();
        }
        return instance;
    }
}