package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

  @Override
  public void start(Stage stage) throws Exception {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/main-view.fxml"));
    Scene scene = new Scene(loader.load(), 800, 600);

    stage.setTitle("JavaFX Sample App");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    org.example.DatabaseInitializer.createTables(); // Lager tabellene
    org.example.CharityImporter.importFromAPI();; // Fyller inn organisasjonene
    launch();
  }
}