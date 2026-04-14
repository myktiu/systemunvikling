package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

  @FXML
  private ListView<String> charityListView;

  @FXML
  private ListView<String> topListView;

  @FXML
  private ListView<String> randomListView;

  @FXML
  public void initialize() {
    loadCharities();
    loadTop10();
    loadRandom10();
  }

  // Sidebar preview list

  private void loadCharities() {
    String sql = "SELECT name FROM Charities ORDER BY name ASC";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

      charityListView.getItems().clear();

      while (rs.next()) {
        charityListView.getItems().add(rs.getString("name"));
      }

    } catch (SQLException e) {
      System.err.println("Error loading charities: " + e.getMessage());
    }
  }

  // TOP 10
  private void loadTop10() {
    String sql =
            "SELECT c.name, COALESCE(SUM(d.amount), 0) as total " +
                    "FROM Charities c " +
                    "LEFT JOIN Donations d ON c.id = d.charity_id " +
                    "GROUP BY c.id " +
                    "ORDER BY total DESC " +
                    "LIMIT 10";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

      topListView.getItems().clear();

      int rank = 1;

      while (rs.next()) {
        String name = rs.getString("name");
        double total = rs.getDouble("total");

        topListView.getItems().add(
                rank + ". " + name + " - " + total + " kr"
        );

        rank++;
      }

    } catch (SQLException e) {
      System.err.println("Error loading top 10: " + e.getMessage());
    }
  }


// RANDOM 10

  private void loadRandom10() {
    String sql =
            "SELECT c.name, COALESCE(SUM(d.amount), 0) as total " +
                    "FROM Charities c " +
                    "LEFT JOIN Donations d ON c.id = d.charity_id " +
                    "GROUP BY c.id " +
                    "ORDER BY RANDOM() " +
                    "LIMIT 10";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

      randomListView.getItems().clear();

      while (rs.next()) {
        String name = rs.getString("name");
        double total = rs.getDouble("total");

        randomListView.getItems().add(
                name + " - " + total + " kr"
        );
      }

    } catch (SQLException e) {
      System.err.println("Error loading random 10: " + e.getMessage());
    }
  }

  // Scene switching

  private void switchScene(ActionEvent event, String fxml) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/" + fxml));
    Scene scene = new Scene(loader.load(), 800, 600);

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  @FXML
  private void goToLogin(ActionEvent event) throws IOException {
    switchScene(event, "login-view.fxml");
  }

  @FXML
  private void goToList(ActionEvent event) throws IOException {
    switchScene(event, "list-view.fxml");
  }
}