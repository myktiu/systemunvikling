package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:hmh_database.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}