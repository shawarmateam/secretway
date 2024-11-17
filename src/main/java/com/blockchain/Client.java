package com.blockchain;
import org.bson.Document;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("Введите сообщение (введите '!exit' для выхода):");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try (Socket socket = new Socket("localhost", 1201)) {
                System.out.println("LOG: connected to server");
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // TODO: пофиксить ошибку изза которой один клиент пишет только 1но сооб.

                String userInput;
                userInput = scanner.nextLine();
                if (userInput.isEmpty()) {
                    continue;
                }
                if ("!exit".equalsIgnoreCase(userInput)) {
                    break;
                }
                switch (userInput) {
                    case "!help":
                        System.out.println("Commands:\n!exit - exit from messenger\n!help - show this text");
                        break;
                }

                if (userInput.charAt(0) != '!') {
                    Document objSocket = new Document();
                    objSocket.put("msg", userInput);
                    objSocket.put("userId", "0");
                    objSocket.put("password", "hui_penis");
                    objSocket.put("sendUserId", "0");
                    objSocket.put("client", true);

                    System.out.println("LOG: message send to server '" + socket.getInetAddress() + "'\n" + objSocket);
                    out.println(objSocket.toJson());
                    System.out.println("LOG: end of try()");
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("LOG: end of while() cycle");
        }
    }
}
