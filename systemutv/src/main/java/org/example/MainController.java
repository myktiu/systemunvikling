package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {

  @FXML
  private ListView<String> charityListView;

  @FXML
  public void initialize() {
    loadCharities();
  }

  private void loadCharities() {
    String sql = "SELECT name FROM Charities ORDER BY name ASC";

    try (Connection conn = DatabaseManager.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

      while (rs.next()) {
        charityListView.getItems().add(rs.getString("name"));
      }

    } catch (SQLException e) {
      System.err.println("Error loading charities: " + e.getMessage());
    }
  }

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