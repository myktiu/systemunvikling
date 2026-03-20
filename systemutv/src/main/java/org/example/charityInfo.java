/**
 * Author: Erling Wendt
 * Version: MVP 0.2
 * 20/03/2026
 *
 * This class should allow for a showcase of charity info, so that a user can see
 * information about a charity and its donation history.
 */

package org.example;

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

    // TODO: trenger sendToDB() samme greia som userInfo
    // typ CharityRepository.save(charityInfo) eller noe lignende
    // fjern når ferdig
}