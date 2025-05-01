package com.project.miniapps.NewsReaderApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class NewsReaderApp {
    public static void main(String[] args) {
        try {
            String apiKey = "df23f7c634bd4ee79d4057abc28a576b";
            String urlString = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + apiKey;

            URL url = new URL(urlString);
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

            JSONObject jsonObject = new JSONObject(response.toString());
            JSONArray articles = jsonObject.getJSONArray("articles");

            System.out.println("\n📰 Top News Headlines 📰\n");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                System.out.println("📝 Title: " + article.getString("title"));
                System.out.println("📜 Description: " + article.optString("description", "No description"));
                System.out.println("🌐 URL: " + article.getString("url"));
                System.out.println("--------------------------------------------");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
