package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.File;

public class DatabaseManager {
  private static final String DB_NAME = "hmh_database.db";
  private static final String URL = "jdbc:sqlite:" + DB_NAME;

  static {
    // Print absolute path for confirmation
    File dbFile = new File(DB_NAME);
    System.out.println("Database file path: " + dbFile.getAbsolutePath());
  }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}