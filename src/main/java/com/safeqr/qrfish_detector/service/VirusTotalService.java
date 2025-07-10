package com.safeqr.qrfish_detector.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class VirusTotalService {

    private final String apiKey = "fc87984f9f9e76a3dfe9f3e6dc5c81ff44b5135396246687ff5bb136dbe6ea22";  // ← put your API key here

    public JSONObject getUrlVerdict(String urlToCheck) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // Step 1: Submit the URL
            String encodedUrl = URLEncoder.encode(urlToCheck, StandardCharsets.UTF_8);
            // For submitting, use plain URL in the form data (still URL-encoded)
            HttpRequest submitRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.virustotal.com/api/v3/urls"))
                    .header("x-apikey", apiKey)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString("url=" + URLEncoder.encode(urlToCheck, StandardCharsets.UTF_8)))
                    .build();


            HttpResponse<String> submitResponse = client.send(submitRequest, HttpResponse.BodyHandlers.ofString());

            JSONObject submitJson = new JSONObject(submitResponse.body());
            String analysisId = submitJson.getJSONObject("data").getString("id");

//            // Step 2: Poll analysis result
//            String analysisUrl = "https://www.virustotal.com/api/v3/analyses/" + analysisId;
//            JSONObject analysisJson = null;
//            int retries = 5;
//
//            while (retries-- > 0) {
//                HttpRequest analysisRequest = HttpRequest.newBuilder()
//                        .uri(URI.create(analysisUrl))
//                        .header("x-apikey", apiKey)
//                        .build();
//
//                HttpResponse<String> analysisResponse = client.send(analysisRequest, HttpResponse.BodyHandlers.ofString());
//                analysisJson = new JSONObject(analysisResponse.body());
//
//                String status = analysisJson.getJSONObject("data")
//                        .getJSONObject("attributes")
//                        .getString("status");
//
//                if ("completed".equalsIgnoreCase(status)) {
//                    return analysisJson.getJSONObject("data")
//                            .getJSONObject("attributes")
//                            .getJSONObject("stats");
//                }
//
//                // Wait 1 second before next try
//                Thread.sleep(1000);
//            }
//
//            System.err.println("Analysis not completed in time.");
//            return null;
            // Step 2: Use base64-encoded version of URL to get the scan result
            String base64Url = Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(urlToCheck.getBytes(StandardCharsets.UTF_8));

            HttpRequest analysisRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://www.virustotal.com/api/v3/urls/" + base64Url))
                    .header("x-apikey", apiKey)
                    .build();

            HttpResponse<String> analysisResponse = client.send(analysisRequest, HttpResponse.BodyHandlers.ofString());

            System.out.println("VirusTotal raw response: " + analysisResponse.body());

            JSONObject resultJson = new JSONObject(analysisResponse.body());

            return resultJson.getJSONObject("data")
                    .getJSONObject("attributes")
                    .getJSONObject("last_analysis_stats");

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
