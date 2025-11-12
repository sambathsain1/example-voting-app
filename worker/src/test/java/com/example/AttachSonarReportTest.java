package com.example;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.Test;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SonarQubeReportTest {

    private static final String SONAR_URL = "http://172.30.117.227:9000"; // your SonarQube base URL
    private static final String PROJECT_KEY = "voting-app";              // your SonarQube project key
    private static final String SONAR_TOKEN = System.getenv("SONAR_TOKEN"); // Jenkins will inject this token

    @Test
    void fetchAndAttachSonarQubeReport() throws Exception {
        if (SONAR_TOKEN == null || SONAR_TOKEN.isEmpty()) {
            Allure.step("SONAR_TOKEN not set. Skipping SonarQube check.");
            assertTrue(true);
            return;
        }

        String apiUrl = SONAR_URL + "/api/qualitygates/project_status?projectKey=" + PROJECT_KEY;

        Allure.step("Fetching SonarQube Quality Gate status from: " + apiUrl);

        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
        String basicAuth = "Basic " + java.util.Base64.getEncoder()
                .encodeToString((SONAR_TOKEN + ":").getBytes(StandardCharsets.UTF_8));
        connection.setRequestProperty("Authorization", basicAuth);
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        Allure.step("Response code: " + responseCode);
        assertEquals(200, responseCode, "SonarQube API did not return 200 OK");
        
        try (InputStream responseStream = connection.getInputStream()) {
            String response = new String(responseStream.readAllBytes(), StandardCharsets.UTF_8);
            Allure.addAttachment("SonarQube API Response", "application/json", response);

            JSONObject json = new JSONObject(response);
            JSONObject projectStatus = json.getJSONObject("projectStatus");
            String status = projectStatus.getString("status");

            Allure.step("Quality Gate Status: " + status);
            Allure.step("Details: " + projectStatus.toString(2));

            assertTrue(!"ERROR".equalsIgnoreCase(status),
                    "SonarQube Quality Gate failed with status: " + status);
        }
    }
}
