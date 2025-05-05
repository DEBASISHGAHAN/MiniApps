package com.project.miniapps.SimpleChatApplication;

import java.io.*;
import java.net.*;

public class Client {
    private static final String SERVER_IP = "127.0.0.1"; // localhost
    private static final int SERVER_PORT = 1234;

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(SERVER_IP, SERVER_PORT);
        System.out.println("Connected to the server!");

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        // Thread to listen messages from server
        new Thread(() -> {
            try {
                String serverMessage;
                while ((serverMessage = serverInput.readLine()) != null) {
                    System.out.println("com.project.miniapps.SimpleChatApplication.Server: " + serverMessage);
                }
            } catch (IOException e) {
                System.out.println("com.project.miniapps.SimpleChatApplication.Server connection closed.");
            }
        }).start();

        // Main thread for sending messages
        String userMessage;
        while ((userMessage = userInput.readLine()) != null) {
            out.println(userMessage);
        }

        socket.close();
    }
}
