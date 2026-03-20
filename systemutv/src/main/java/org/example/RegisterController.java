package org.example;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import java.io.IOException;

public class RegisterController {

  @FXML private TextField emailField;
  @FXML private TextField usernameField;
  @FXML private PasswordField passwordField;
  @FXML private PasswordField confirmPasswordField;

  private void switchScene(ActionEvent event, String fxml) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/" + fxml));
    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

    Scene scene = stage.getScene();
    scene.setRoot(loader.load());
  }

  @FXML
  private void goBack(ActionEvent event) throws IOException {
    switchScene(event, "login-view.fxml");
  }

  @FXML
  private void handleRegister(ActionEvent event) throws IOException {
    String email = emailField.getText(); // not used yet
    String username = usernameField.getText();
    String password = passwordField.getText();
    String confirm = confirmPasswordField.getText();

    if (!password.equals(confirm)) {
      System.out.println("Passwords do not match!");
      return;
    }

    boolean success = UserDAO.registerUser(username, password);

    if (success) {
      System.out.println("User registered!");

      switchScene(event, "login-view.fxml");

    } else {
      System.out.println("Registration failed (username may exist)");
    }
  }
}
