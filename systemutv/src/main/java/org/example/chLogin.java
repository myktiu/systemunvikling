/**
 * Version: MVP 0.3
 * Author: Erling Wendt
 * This class should create a login for charities.
 */
package org.example;
public class chLogin {
    public charityInfo authenticate(String username, String password) {
        String hashed = passwordEncryption.hashPassword(password);

        // TODO: bytt ut placeholder names her Rohith
        // DBConnection db = DBConnection.getInstance();
        // if (!db.verifyUser(username, hashed)) return null;
        // charityInfo user = new charityInfo();
        // user.setUserID(db.getUserId(username));
        // user.setUsername(username);
        // user.setDonationHistory(Donation.getDonationsByUser(user.getUserID()));
        // return user;
        // TEMP mock - fjern når Rohith er klar
        charityInfo mockCharity = new charityInfo();
        mockCharity.setPassword("1234"); // hashed automatically inside setPassword

        if (username.equals("test") && hashed.equals(mockCharity.getChPassword())) {
            mockCharity.setCharityID(1);
            mockCharity.setCharityname(username);
            mockCharity.setDonationHistory(Donation.getDonationsByUser(1));
            return mockCharity;
        }
        return null;
    }
}
