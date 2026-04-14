package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonationDAO {

    public static void saveDonation(int donorId, int charityId, double amount) {
        String sql = "INSERT INTO Donations(donor_id, charity_id, amount, date) VALUES(?, ?, ?, date('now'))";

        try (Connection conn = org.example.DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, donorId);
            pstmt.setInt(2, charityId);
            pstmt.setDouble(3, amount);
            pstmt.executeUpdate();
            System.out.println("Transaksjon fullført!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<org.example.Donation> getDonationObjectsByUser(int userId) {
        List<org.example.Donation> list = new ArrayList<>();
        String sql = "SELECT * FROM Donations WHERE donor_id = ?";
        try (Connection conn = org.example.DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Vi bruker en enkel "dummy" charityInfo her for å fylle Donation-objektet
                org.example.charityInfo tempCharity = new org.example.charityInfo();
                tempCharity.setCharityID(rs.getInt("charity_id"));

                org.example.userInfo tempUser = new org.example.userInfo();
                tempUser.setUserID(rs.getInt("donor_id"));

                list.add(new org.example.Donation(tempUser, tempCharity, rs.getDouble("amount")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
  public static double getTotalByCharity(int charityId) {
    String sql = "SELECT SUM(amount) as total FROM Donations WHERE charity_id = ?";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setInt(1, charityId);
      ResultSet rs = pstmt.executeQuery();

      if (rs.next()) {
        return rs.getDouble("total");
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return 0;
  }
}