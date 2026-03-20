package org.example;

import java.sql.*;

public class UserDAO {

    public static boolean registerUser(String username, String password) {
        String hashed = org.example.passwordEncryption.hashPassword(password);
        String sql = "INSERT INTO Users(username, password) VALUES(?, ?)";

        try (Connection conn = org.example.DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashed);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static int login(String username, String password) {
        String hashed = org.example.passwordEncryption.hashPassword(password);
        String sql = "SELECT id FROM Users WHERE username = ? AND password = ?";

        try (Connection conn = org.example.DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, hashed);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}