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


import org.example.userInfo
public class login {

    public userInfo authenticate(String username, String password) {
        String hashed = passwordEncryption.hashPassword(password);

        // TODO: bytt ut placeholder names her Rohith
        // DBConnection db = DBConnection.getInstance();
        // if (!db.verifyUser(username, hashed)) return null;
        // UserInfo user = new UserInfo();
        // user.setUserID(db.getUserId(username));
        // user.setUsername(username);
        // user.setDonationHistory(Donation.getDonationsByUser(user.getUserID()));
        // return user;
        // TEMP mock - fjern når Rohith er klar
        userInfo mockUser = new userInfo();
        mockUser.setPassword("1234"); // hashed automatically inside setPassword

        if (username.equals("test") && hashed.equals(mockUser.getPassword())) {
            mockUser.setUserID(1);
            mockUser.setUsername(username);
            mockUser.setDonationHistory(Donation.getDonationsByUser(1));
            return mockUser;
        }
        return null;
    }
}