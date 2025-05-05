package com.project.miniapps.SimpleChatApplication;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final int PORT = 1234;
    private static Set<PrintWriter> clientWriters = new HashSet<>();

    public static void main(String[] args) throws IOException {
        System.out.println("com.project.miniapps.SimpleChatApplication.Server started. Waiting for clients...");
        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true){
            Socket clientSocket = serverSocket.accept();
            System.out.println("com.project.miniapps.SimpleChatApplication.Client connected: " + clientSocket);

            // Handle client in new thread
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }
    private static class ClientHandler implements Runnable{
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try {
                in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                out = new PrintWriter(socket.getOutputStream(), true);

                synchronized (clientWriters){
                    clientWriters.add(out);
                }

                String message;
                while ((message = in.readLine()) != null) {
                    System.out.println("Received: " + message);
                    // Broadcast to all clients
                    synchronized (clientWriters){
                        for (PrintWriter writer : clientWriters){
                            writer.println(message);
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + socket);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {}

                synchronized (clientWriters) {
                    clientWriters.remove(out);
                }
            }
        }
    }
}
