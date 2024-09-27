package com.blockchain;
import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1200)) {
            System.out.println("Starting server...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client was join: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
