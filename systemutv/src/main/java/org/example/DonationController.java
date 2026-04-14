package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

public class DonationController {

  @FXML
  private TextField amountField;

  private userInfo currentUser;
  private charityInfo selectedCharity;

  public void setData(userInfo user, charityInfo charity) {
    this.currentUser = user;
    this.selectedCharity = charity;
  }

  @FXML
  private void handleDonate(ActionEvent event) {
    try {
      double amount = Double.parseDouble(amountField.getText());

      Donation donation = new Donation(currentUser, selectedCharity, amount);
      donation.sendToDB();

      System.out.println("Donation successful!");

    } catch (Exception e) {
      System.out.println("Invalid amount.");
    }
  }

  @FXML
  private void goBack(ActionEvent event) {
    ((Stage)((Node)event.getSource()).getScene().getWindow()).close();
  }
}
