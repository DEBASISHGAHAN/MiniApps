package com.project.miniapps.WorldExplorerApp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WorldExplorerApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter country name: ");
        String countryName = sc.nextLine();

        try {
            String apiUrl = "https://restcountries.com/v3.1/name/" + countryName;
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();

            // Parse JSON
            JSONArray jsonArray = new JSONArray(response.toString());
            JSONObject countryData = jsonArray.getJSONObject(0);

            String officialName = countryData.getJSONObject("name").getString("official");
            String capital = countryData.getJSONArray("capital").getString(0);
            long population = countryData.getLong("population");
            String region = countryData.getString("region");
            String currency = countryData.getJSONObject("currencies").keys().next();
            String flag = countryData.getString("flag");

            // Print country information
            System.out.println("\n🌍 Country: " + officialName);
            System.out.println("🏙️ Capital: " + capital);
            System.out.println("👥 Population: " + population);
            System.out.println("🌎 Region: " + region);
            System.out.println("💰 Currency: " + currency);
            System.out.println("🏴 Flag: " + flag);

        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }

        sc.close();
    }
}
