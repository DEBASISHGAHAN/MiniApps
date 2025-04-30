package com.project.miniapps.NasaApodApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class NasaApodApp {
    public static void main(String[] args) {
        try {
            String apiKey = "3E0XKS1At0mZjGuAhv3Gg0h4eWzGLvUQScWwiMtf";
            String apiUrl = "https://api.nasa.gov/planetary/apod?api_key=" + apiKey;

            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON response
            JSONObject jsonObject = new JSONObject(response.toString());

            System.out.println("\n🌌 Astronomy Picture of the Day 🌌\n");
            System.out.println("📅 Date: " + jsonObject.getString("date"));
            System.out.println("🖼️ Title: " + jsonObject.getString("title"));
            System.out.println("📃 Explanation: " + jsonObject.getString("explanation"));
            System.out.println("🌐 Image URL: " + jsonObject.getString("url"));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

