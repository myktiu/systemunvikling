package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class chLogin {

    public org.example.charityInfo authenticate(String username, String password) {
        // 1. Hasher passordet som skrives inn for å sammenligne med lagret hash i DB
        String hashed = org.example.passwordEncryption.hashPassword(password);

        // 2. SQL-spørring for å finne organisasjonen
        String sql = "SELECT * FROM Charities WHERE name = ? AND password = ?";

        try (Connection conn = org.example.DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, hashed);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    // 3. Oppretter et ekte charityInfo-objekt med data fra databasen
                    org.example.charityInfo charity = new org.example.charityInfo();
                    charity.setCharityID(rs.getInt("id"));
                    charity.setCharityName(rs.getString("name"));
                    charity.setDescription(rs.getString("description"));

                    // 4. Henter donasjonshistorikken for denne spesifikke organisasjonen
                    // Bruker metoden vi har i Donation-klassen
                    charity.setDonationHistory(org.example.Donation.getDonationsByCharity(charity.getCharityID()));

                    System.out.println("Organisasjonsinnlogging vellykket: " + username);
                    return charity;
                }
            }
        } catch (SQLException e) {
            System.err.println("Databasefeil ved organisasjonsinnlogging: " + e.getMessage());
        }

        // Returnerer null hvis brukernavn/passord ikke stemmer eller teknisk feil
        return null;
    }
}