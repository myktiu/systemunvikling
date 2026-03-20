package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CharityImporter {

  private static final String API_URL = "https://app.innsamlingskontrollen.no/api/public/v1/all";

  public static void importFromAPI() {
    try {
      // 1. Fetch data from API
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(API_URL))
              .GET()
              .build();

      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      // 2. Parse JSON
      ObjectMapper mapper = new ObjectMapper();
      JsonNode root = mapper.readTree(response.body());

      // 3. Prepare DB insert
      String sql = "INSERT OR IGNORE INTO Charities(name, description, url, password) VALUES(?, ?, ?, ?)";
      String defaultPass = passwordEncryption.hashPassword("1234");

      try (Connection conn = DatabaseManager.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(sql)) {

        for (JsonNode charity : root) {
          String name = charity.path("name").asText();

          String description = "Status: " + charity.path("status").asText();
          String url = charity.path("url").asText();

          pstmt.setString(1, name);
          pstmt.setString(2, description);
          pstmt.setString(3, url);
          pstmt.setString(4, defaultPass);

          pstmt.executeUpdate();
        }
      }

      System.out.println("Charities imported from API successfully!");

    } catch (Exception e) {
      System.err.println("Error importing charities: " + e.getMessage());
      e.printStackTrace();
    }
  }
}