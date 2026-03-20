package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    String name;
    String url;

    public CharityItem(String name, String url) {
      this.name = name;
      this.url = url;
    }

    @Override
    public String toString() {
      return name; // default text for ListView (won't be visible with custom cell)
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
    String sql = "SELECT name, url FROM Charities ORDER BY name ASC";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        String name = rs.getString("name");
        String url = rs.getString("url");
        allCharities.add(new CharityItem(name, url));
      }

    } catch (SQLException e) {
      System.err.println("Error loading charities: " + e.getMessage());
    }

    charityListView.getItems().addAll(allCharities);
  }

  /** Set up search functionality */
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

  /** Set up ListView to display name + hyperlink */
  private void setupListView() {
    charityListView.setCellFactory(lv -> new ListCell<>() {
      @Override
      protected void updateItem(CharityItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setGraphic(null);
        } else {
          Hyperlink link = new Hyperlink(item.url);
          link.setOnAction(e -> {
            try {
              Desktop.getDesktop().browse(new URI(item.url));
            } catch (Exception ex) {
              ex.printStackTrace();
            }
          });

          HBox box = new HBox(10);
          javafx.scene.control.Label nameLabel = new javafx.scene.control.Label(item.name);
          HBox.setHgrow(nameLabel, Priority.ALWAYS);
          nameLabel.setMaxWidth(Double.MAX_VALUE);

          box.getChildren().addAll(nameLabel, link);
          setGraphic(box);
        }
      }
    });
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