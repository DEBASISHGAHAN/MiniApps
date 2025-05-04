package com.project.miniapps.PlantEncyclopediaApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlantEncyclopediaApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter plant name to search: ");
        String plantName = scanner.nextLine();

        try {
            String apiKey = "S3Tn1jBr3NGTwwCIbWxH2uDDtCpi9X0CYKAg2JzRm3g";
            String encodedPlantName = URLEncoder.encode(plantName, "UTF-8");
            String apiUrl = "https://trefle.io/api/v1/plants/search?token=" + apiKey + "&q=" + encodedPlantName;

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
            JSONArray dataArray = jsonObject.getJSONArray("data");

            if (dataArray.length() == 0) {
                System.out.println("No plant found with that name!");
                return;
            }

            // Show the first matching plant
            JSONObject plant = dataArray.getJSONObject(0);

            System.out.println("\n🌿 Common Name: " + plant.optString("common_name", "N/A"));
            System.out.println("🧪 Scientific Name: " + plant.optString("scientific_name", "N/A"));
            System.out.println("🌳 Family: " + plant.optString("family_common_name", "N/A"));
            System.out.println("🌱 Genus: " + plant.optJSONObject("genus").optString("name", "N/A"));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
