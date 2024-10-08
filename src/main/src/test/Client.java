import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1201)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);
            String userInput;

            System.out.println("Введите сообщение (введите '!exit' для выхода):");
            while (true) {
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

                    System.out.println("LOG: message send to server '"+socket.getInetAddress()+"'\n"+objSocket);
                    out.println(objSocket.toJson());
                    System.out.println(in.readLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
