package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

import java.io.IOException;

public class LoginController<ActionEvent> {

  @FXML
  private TextField usernameField;

  @FXML
  private PasswordField passwordField;

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

  @FXML
  private void handleLogin(ActionEvent event) {
    String username = usernameField.getText();
    String password = passwordField.getText();

    // DIN OPPGAVE: Kall databasen
    int userId = org.example.UserDAO.login(username, password);

    if (userId != -1) {
      System.out.println("Suksess! Logget inn med ID: " + userId);
      // Her kan teamet legge til koden for å bytte scene til hovedmenyen
    } else {
      System.out.println("Feil brukernavn eller passord.");
    }
  }

  @FXML
  private void newUser(ActionEvent event) {
    System.out.println("New user clicked");
  }
}
