package com.project.miniapps.NewsReaderAppGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class NewsReaderAppGUI {
    // JFrame for the GUI
    private static JFrame frame;
    private static JTextArea textArea;

    public static void main(String[] args) {
        frame = new JFrame("Latest News Reader");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Layout setup
        frame.setLayout(new BorderLayout());

        // Button to fetch the latest news
        JButton button = new JButton("Fetch Latest News");
        button.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(button, BorderLayout.NORTH);

        // Text area to display the news
        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 14));
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);

        // Add ActionListener to the button
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchNews();
            }
        });

        frame.setVisible(true);
    }

    // Method to fetch news from NewsAPI
    private static void fetchNews() {
        textArea.setText(""); // Clear previous news
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

            StringBuilder newsContent = new StringBuilder("📰 Top News Headlines 📰\n\n");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);
                newsContent.append("📝 Title: ").append(article.getString("title")).append("\n")
                        .append("📜 Description: ").append(article.optString("description", "No description")).append("\n")
                        .append("🌐 URL: ").append(article.getString("url")).append("\n")
                        .append("--------------------------------------------\n");
            }

            textArea.setText(newsContent.toString());

        } catch (Exception e) {
            textArea.setText("Error: " + e.getMessage());
        }
    }
}

