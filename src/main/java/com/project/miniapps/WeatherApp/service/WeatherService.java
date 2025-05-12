package com.project.miniapps.WeatherApp.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class WeatherService {
    private final String API_KEY = "171c293ba6b8a3b44d1c556caa30e14e";

    public Map<String, Object> fetchWeather(String city) {
        Map<String, Object> result = new HashMap<>();
        try {
            String urlStr = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                    "&appid=" + API_KEY + "&units=metric";
            URL url = new URL(urlStr);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder responseContent = new StringBuilder();
            String line;

            while ((line = in.readLine()) != null) {
                responseContent.append(line);
            }
            in.close();
            conn.disconnect();

            JSONObject obj = new JSONObject(responseContent.toString());
            JSONObject main = obj.getJSONObject("main");
            double temp = main.getDouble("temp");
            int humidity = main.getInt("humidity");
            String condition = obj.getJSONArray("weather").getJSONObject(0).getString("description");

            result.put("temperature", temp);
            result.put("humidity", humidity);
            result.put("condition", condition);
            result.put("city", obj.getString("name"));

        } catch (Exception e) {
            result.put("error", "City not found or API error.");
        }
        return result;
    }
}
