package org.example;
/**
 * Author: Erling Wendt
 * Version: MVP 0.2
 * 19/03/2026
 *
 * This class should create a way for a user to log in, I plan to add a username, and a hashed password.
 * It should directly connect to a database, so that username and password can be checked to see if
 * they fit with our systems
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.userInfo;
import org.example.passwordEncryption;

public class login {

  public userInfo authenticate(String username, String password) {
    String hashed = passwordEncryption.hashPassword(password);
    String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, username);
      pstmt.setString(2, hashed);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          // User found, build userInfo object
          userInfo user = new userInfo();
          user.setUserID(rs.getInt("id"));
          user.setUsername(rs.getString("username"));

          // Load donation history for this user
          user.setDonationHistory(Donation.getDonationsByUser(user.getUserID()));

          System.out.println("Login successful for user: " + username);
          return user;
        }
      }

    } catch (SQLException e) {
      System.err.println("Database error during login: " + e.getMessage());
    }

    // Return null if username/password invalid
    return null;
  }
}