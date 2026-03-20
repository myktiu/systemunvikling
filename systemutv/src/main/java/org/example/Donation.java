/**
 * Author: Erling Wendt
 * Version: MVP 0.2
 * 20/03/2026
 *
 * This class is supposed to create the ability for a user to donate to a charity.
 * And maybe also look at the sum of the donations a user has given.
 */

package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Donation {
    private int donorId;
    private int charityId;
    private String charityName;
    private double amount;
    private LocalDate date;

    public Donation(int donorId, int charityId, String charityName, double amount, LocalDate date) {
        this.donorId = donorId;
        this.charityId = charityId;
        this.charityName = charityName;
        this.amount = amount;
        this.date = date;
    }

    public int getDonorId() { return donorId; }
    public int getCharityId() { return charityId; }
    public String getCharityName() { return charityName; }
    public double getAmount() { return amount; }
    public LocalDate getDate() { return date; }

    public static List<Donation> getDonationsByUser(int userId) {
        // TODO Rohith: bytt ut med ekte DB kall
        // return DBConnection.getInstance().getDonationsByUser(userId);

        // TEMP dummy data
        List<Donation> list = new ArrayList<>();
        list.add(new Donation(userId, 1, "Røde Kors", 200.0, LocalDate.of(2026, 1, 15)));
        list.add(new Donation(userId, 2, "UNICEF", 500.0, LocalDate.of(2026, 2, 20)));
        list.add(new Donation(userId, 3, "Leger Uten Grenser", 350.0, LocalDate.of(2026, 3, 5)));
        return list;
    }

    public static List<Donation> getDonationsByCharity(int charityId) {
        // TODO Rohith: bytt ut med ekte DB kall
        // return DBConnection.getInstance().getDonationsByCharity(charityId);

        // TEMP dummy data - filtered by charityId
        List<Donation> all = getDonationsByUser(1);
        List<Donation> result = new ArrayList<>();
        for (Donation d : all) {
            if (d.getCharityId() == charityId) {
                result.add(d);
            }
        }
        return result;
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