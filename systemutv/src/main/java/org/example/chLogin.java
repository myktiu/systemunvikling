/**
 * Version: MVP 0.3
 * Author: Erling Wendt
 * This class should create a login for charities.
 */
package org.example;
import org.example.charityInfo
public class chLogin {
    public org.example.charityInfo authenticate(String username, String password) {
        String hashed = org.example.passwordEncryption.hashPassword(password);

        // TODO: bytt ut placeholder names her Rohith
        // DBConnection db = DBConnection.getInstance();
        // if (!db.verifyCharity(charityName, hashed)) return null;
        // charityInfo charity = new charityInfo();
        // charity.setCharityID(db.getUserId(username));
        // charity.setCharityname(username);
        // charity.setDonationHistory(Donation.getDonationsByUser(user.getUserID()));
        // return charity;
        // TEMP mock - fjern når Rohith er klar
        charityInfo mockCharity = new charityInfo();
        mockCharity.setChPassword("1234"); // hashed automatically inside setPassword

        if (username.equals("test") && hashed.equals(mockCharity.getChPassword())) {
            mockCharity.setCharityID(1);
            mockCharity.setCharityName(username);
            mockCharity.setDonationHistory(Donation.getDonationsByUser(1));
            return mockCharity;
        }
        return null;
    }
}
