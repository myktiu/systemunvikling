/**
 * Author: Erling Wendt
 * Version: MVP 0.2
 * 20/03/2026
 *
 * This class should allow for a showcase of charity info, so that a user can see
 * information about a charity and its donation history.
 */

package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import org.example.Donation;
import org.example.passwordEncryption;
public class charityInfo {
    private int charityID;
    private String charityName;
    private String description;
    private List<org.example.Donation> donationHistory;
    private String chPassword;
    public int getCharityID() { return charityID; }
    public void setCharityID(int charityID) { this.charityID = charityID; }

    public String getCharityName() { return charityName; }
    public void setCharityName(String charityName) { this.charityName = charityName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getChPassword() {return chPassword;}
    public void setChPassword(String newPassword) {
        this.chPassword = passwordEncryption.hashPassword(newPassword);
    }

    public List<Donation> getDonationHistory() { return donationHistory; }
    public void setDonationHistory(List<org.example.Donation> donationHistory) {
        this.donationHistory = donationHistory;
    }

    // FULLFØRT TODO: Lagrer eller oppdaterer organisasjonen i databasen
    public void sendToDB() {
        String sql = "INSERT OR REPLACE INTO Charities (id, name, description, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = org.example.DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Hvis charityID er 0 (ny), vil SQLite tildele en ny ID pga AUTOINCREMENT
            if (this.charityID != 0) {
                pstmt.setInt(1, this.charityID);
            } else {
                pstmt.setNull(1, Types.INTEGER);
            }

            pstmt.setString(2, this.charityName);
            pstmt.setString(3, this.description);
            pstmt.setString(4, this.chPassword);

            pstmt.executeUpdate();
            System.out.println("Organisasjonsinfo lagret i databasen: " + this.charityName);

        } catch (SQLException e) {
            System.err.println("Feil ved lagring av organisasjon: " + e.getMessage());
        }
    }
}