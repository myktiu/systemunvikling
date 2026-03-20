package org.example;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

public class DatabaseInitializer {

    public static void createTables() {
        String sqlUsers = "CREATE TABLE IF NOT EXISTS Users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "username TEXT UNIQUE NOT NULL,"
                + "password TEXT NOT NULL"
                + ");";

        String sqlCharities = "CREATE TABLE IF NOT EXISTS Charities ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT UNIQUE NOT NULL,"
                + "description TEXT,"
                + "url TEXT,"
                + "password TEXT," // Lagt til for chLogin
                + "is_verified INTEGER DEFAULT 1"
                + ");";

        String sqlDonations = "CREATE TABLE IF NOT EXISTS Donations ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "donor_id INTEGER,"
                + "charity_id INTEGER,"
                + "amount REAL,"
                + "date TEXT,"
                + "FOREIGN KEY(donor_id) REFERENCES Users(id),"
                + "FOREIGN KEY(charity_id) REFERENCES Charities(id)"
                + ");";

        try (Connection conn = org.example.DatabaseManager.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlUsers);
            stmt.execute(sqlCharities);
            stmt.execute(sqlDonations);
            System.out.println("Database-tabeller er klare.");

        } catch (SQLException e) {
            System.err.println("Feil ved opprettelse av tabeller: " + e.getMessage());
        }
    }
}