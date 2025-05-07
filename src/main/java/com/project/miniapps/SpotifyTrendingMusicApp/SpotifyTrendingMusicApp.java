package com.project.miniapps.SpotifyTrendingMusicApp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;

public class SpotifyTrendingMusicApp {
    public static void main(String[] args) {
        try {
            // Step 1: Get Access Token
            String clientId = "6fac0861073c4a4497714f25c3c4e70c";
            String clientSecret = "0707b36ee61d47d58bd052b8b19a5d4b";
            String accessToken = getAccessToken(clientId, clientSecret);

            // Step 2: Get Top Tracks
            String apiUrl = "https://api.spotify.com/v1/browse/new-releases?limit=5";
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parse JSON
            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray albums = jsonObject.getJSONObject("albums").getJSONArray("items");

            System.out.println("\n🎶 Trending Albums 🎶\n");

            for (int i = 0; i < albums.length(); i++) {
                JSONObject album = albums.getJSONObject(i);
                System.out.println("🎧 Album: " + album.getString("name"));
                System.out.println("👨‍🎤 Artist: " + album.getJSONArray("artists").getJSONObject(0).getString("name"));
                System.out.println("🌐 URL: " + album.getJSONObject("external_urls").getString("spotify"));
                System.out.println("--------------------------------------");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static String getAccessToken(String clientId, String clientSecret) throws Exception {
        String auth = clientId + ":" + clientSecret;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

        URL url = new URL("https://accounts.spotify.com/api/token");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Basic " + encodedAuth);
        conn.setDoOutput(true);

        String body = "grant_type=client_credentials";
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.writeBytes(body);
        out.flush();
        out.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject jsonObject = new JSONObject(response.toString());
        return jsonObject.getString("access_token");
    }
}
