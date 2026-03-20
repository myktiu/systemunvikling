/**
 * Author: Erling Wendt
 * Version: MVP 0.3
 * 20/03/2026
 *
 * This class is supposed to create the ability for a user to donate to a charity.
 * And maybe also look at the sum of the donations a user has given.
 */

package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.example.userInfo
import org.example.charityInfo
public class Donation {
    private int donorId;
    private int charityId;
    private double amount;
    private LocalDate date;


    public Donation(userInfo donor, charityInfo charity, double amount) {
        this.donorId = donor.getUserID();
        this.charityId = charity.getCharityID();
        this.amount = amount;
        this.date = LocalDate.now();
    }

    public int getDonorId() { return donorId; }
    public int getCharityId() { return charityId; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }

    public static List<Donation> getDonationsByUser(int userId) {
        // TODO Rohith: bytt ut med ekte DB kall
        // return DBConnection.getInstance().getDonationsByUser(userId);

        // TEMP dummy data
        List<Donation> list = new ArrayList<>();

        return list;
    }

    public static List<Donation> getDonationsByCharity(int charityId) {
        // TODO Rohith: bytt ut med ekte DB kall
        // return DBConnection.getInstance().getDonationsByCharity(charityId);
        return new ArrayList<>();
    }

    public static double getTotalDonated(int userId) {
        return getDonationsByUser(userId)
                .stream()
                .mapToDouble(Donation::getAmount)
                .sum();
    }

    public void sendToDB() {
        // TODO Rohith: bytt ut med ekte DB kall
        // DBConnection.getInstance().saveDonation(this);
    }
}