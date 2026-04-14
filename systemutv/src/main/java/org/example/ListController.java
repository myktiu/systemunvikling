package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListController {

  @FXML
  private ListView<CharityItem> charityListView;

  @FXML
  private TextField searchField;

  private final List<CharityItem> allCharities = new ArrayList<>();

  /** Class to hold charity info */
  public static class CharityItem {
    int id;
    String name;
    String url;

    public CharityItem(int id, String name, String url) {
      this.id = id;
      this.name = name;
      this.url = url;
    }

    @Override
    public String toString() {
      return name;
    }
  }

  @FXML
  public void initialize() {
    loadCharities();
    setupSearch();
    setupListView();
  }

  /** Load charities from DB */
  private void loadCharities() {
    String sql = "SELECT id, name, url FROM Charities ORDER BY name ASC";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String url = rs.getString("url");

        allCharities.add(new CharityItem(id, name, url));
      }

    } catch (SQLException e) {
      System.err.println("Error loading charities: " + e.getMessage());
    }

    charityListView.getItems().addAll(allCharities);
  }

  /** Search */
  private void setupSearch() {
    searchField.textProperty().addListener((obs, oldVal, newVal) -> {
      charityListView.getItems().clear();
      for (CharityItem item : allCharities) {
        if (item.name.toLowerCase().contains(newVal.toLowerCase())) {
          charityListView.getItems().add(item);
        }
      }
    });
  }

  /** ListView UI */
  private void setupListView() {
    charityListView.setCellFactory(lv -> new ListCell<>() {
      @Override
      protected void updateItem(CharityItem item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
          setGraphic(null);
        } else {

          // Name
          Label nameLabel = new Label(item.name);
          HBox.setHgrow(nameLabel, Priority.ALWAYS);
          nameLabel.setMaxWidth(Double.MAX_VALUE);

          // Link
          Hyperlink link = new Hyperlink("Website");
          link.setOnAction(e -> {
            try {
              Desktop.getDesktop().browse(new URI(item.url));
            } catch (Exception ex) {
              ex.printStackTrace();
            }
          });

          // Donate button
          Button donateBtn = new Button("Donate");
          donateBtn.setOnAction(e -> openDonationWindow(item));

          // Total donations
          double total = DonationDAO.getTotalByCharity(item.id);
          Label totalLabel = new Label("Total: " + total + " kr");

          // Layout
          HBox box = new HBox(10);
          box.getChildren().addAll(nameLabel, link, donateBtn, totalLabel);

          setGraphic(box);
        }
      }
    });
  }

  /** Open donation window */
  private void openDonationWindow(CharityItem item) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/donation-view.fxml"));
      Stage stage = new Stage();
      stage.setScene(new Scene(loader.load()));

      DonationController controller = loader.getController();

      // TEMP USER (replace with logged-in user later)
      userInfo user = new userInfo();
      user.setUserID(1);

      charityInfo charity = new charityInfo();
      charity.setCharityID(item.id);

      controller.setData(user, charity);

      stage.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /** Scene switcher */
  private void switchScene(ActionEvent event, String fxml) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/" + fxml));
    Scene scene = new Scene(loader.load(), 800, 600);

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void goBack(ActionEvent event) throws IOException {
    switchScene(event, "main-view.fxml");
  }
}