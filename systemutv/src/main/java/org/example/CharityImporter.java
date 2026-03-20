package org.example;

import java.sql.*;

public class CharityImporter {

    public static void importInitialData() {
        String[] charities = {"Røde Kors", "Leger Uten Grenser", "Flyktninghjelpen", "SOS Barnebyer"};
        // Vi setter et standardpassord "1234" for alle organisasjoner slik at chLogin fungerer i demoen
        String defaultPass = org.example.passwordEncryption.hashPassword("1234");
        String sql = "INSERT OR IGNORE INTO Charities(name, description, password) VALUES(?, ?, ?)";

        try (Connection conn = org.example.DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (String name : charities) {
                pstmt.setString(1, name);
                pstmt.setString(2, "Offisiell organisasjon godkjent av Innsamlingskontrollen.");
                pstmt.setString(3, defaultPass);
                pstmt.executeUpdate();
            }
            System.out.println("Data fra Innsamlingskontrollen er lastet inn.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}