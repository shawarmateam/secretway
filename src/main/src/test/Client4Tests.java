import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client4Tests {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 1200)) {
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
                    JSONObject objSocket = new JSONObject();
                    objSocket.put("msg", userInput);
                    objSocket.put("user", "Adisteyf");

                    out.println(objSocket.toJSONString());
                    System.out.println(in.readLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
