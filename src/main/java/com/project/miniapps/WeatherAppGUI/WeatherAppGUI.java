package com.project.miniapps.WeatherAppGUI;

import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherAppGUI extends JFrame {
    private JTextField cityField;
    private JButton searchButton;
    private JLabel resultLabel;

    private final String API_KEY = "171c293ba6b8a3b44d1c556caa30e14e";

    public WeatherAppGUI(){
        setTitle("Weather App ☀️");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cityField = new JTextField(15);
        searchButton = new JButton("Get Weather");
        resultLabel = new JLabel("Enter a city and press the button.");

        JPanel panel = new JPanel();
        panel.add(new JLabel("City:"));
        panel.add(cityField);
        panel.add(searchButton);

        add(panel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.CENTER);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String city = cityField.getText().trim();
                if (!city.isEmpty()){
                    getWeather(city);
                } else {
                    resultLabel.setText("Please enter a city name!");
                }
            }
        });
    }

    private void getWeather(String city){
        try {
            // API URL
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null){
                content.append(inputLine);
            }

            in.close();
            conn.disconnect();

            // Parse JSON response
            JSONObject obj = new JSONObject(content.toString());
            JSONObject main = obj.getJSONObject("main");
            double temp = main.getDouble("temp");
            int humidity = main.getInt("humidity");
            String weatherDescription = obj.getJSONArray("weather").getJSONObject(0).getString("description");

            resultLabel.setText("<html>Temp: " + temp + "°C<br/>Humidity: " + humidity + "%<br/>Condition: "
                    + weatherDescription + "</html>");

        } catch (Exception ex){
            resultLabel.setText("Error fetching weather. Check city name.");
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
           e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WeatherAppGUI().setVisible(true);
            }
        });
    }
}
