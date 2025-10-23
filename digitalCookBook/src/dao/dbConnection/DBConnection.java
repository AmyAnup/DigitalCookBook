package dao.dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Author: @AmyAnup
 * Date: 14/10/25
 *
 * DBConnection.java
 * Utility class to manage database connections for the Recipe Manager application.
 * Provides a static method to get a connection to the MySQL database.
 * 
 * Features:
 *  - Uses JDBC Driver for MySQL
 *  - Centralized connection management
 *  - Throws exceptions to handle errors at the calling level
 */

public class DBConnection {

    // Database URL with database name, SSL disabled, and UTC timezone
    private static final String URL = "jdbc:mysql://localhost:3306/recipemanager?useSSL=false&serverTimezone=UTC";
    private static final String USERNAME = "root";   // MySQL username
    private static final String PASSWORD = "";       // MySQL password

    // Private constructor to prevent instantiation
    public DBConnection() {}

    /**
     * Establishes and returns a connection to the MySQL database.
     * @return Connection object representing the database connection
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if JDBC Driver class is not found
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        // Load the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        // Establish and return the connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        System.out.println("✅ Connection established!");
        return conn;
    }
}

