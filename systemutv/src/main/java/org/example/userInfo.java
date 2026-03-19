/**
 * Author: Erling Wendt
 * Version: MVP 0.2
 * 19/03/2026
 * This class should allow for a showcase of user info, so that a user can see their own donation history,
 * and potentially change usernames or passwords
 */
package org.example;
import java.util.List;



public class userInfo {
    private int userID;
    private String username;
    private List<Donation> donationHistory;
    private String password;

    public int  getUserID() {return userID;}
    public void setUserID(int userID) {this.userID = userID;}

    public String getUsername() {return username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}
    public void setPassword(String newPassword) {
        this.password = passwordEncryption.hashPassword(newPassword);
    }

    public List<Donation> getDonationHistory() {return this.donationHistory;}
    public void setDonationHistory(List<Donation> donationHistory) {
        this.donationHistory = donationHistory;
    }
    //TODO: I need a way to upload a set detail about a user to the DB. Called something like sendToDB().
    //TODO: Honestly it should be its own class that can be called upon whenever we need.
}
