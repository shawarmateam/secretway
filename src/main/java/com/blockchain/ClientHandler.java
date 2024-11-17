package com.blockchain;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    public final static ArrayList<Socket> allSockets = new ArrayList<>();

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        allSockets.add(clientSocket);
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String message;

            while ((message = in.readLine()) != null) {
                Document obj = Document.parse(message);

                if (obj.containsKey("msg") && obj.containsKey("userId")) {
                    System.out.println("LOG: "+obj);
                    sendToServer(obj);
                }
            }
            allSockets.remove(clientSocket);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String findIp(String ip) {
        Pattern pattern = Pattern.compile("^(.*?)(?=:)");
        Matcher matcher = pattern.matcher(ip);

        return matcher.find() ? matcher.group() : null;
    }

    private static String findPort(String port) {
        Pattern pattern = Pattern.compile("(?<=:).*$");
        Matcher matcher = pattern.matcher(port);

        return matcher.find() ? matcher.group() : null;
    }

    private void sendToServer(Document msg) throws IOException {
        System.out.println("LOG: Start of sendToServer()");

        Document user = MongoClients.create("mongodb://localhost:27017").getDatabase("users_bd").getCollection("users")
                .find(eq("user_id", msg.getString("userId"))).first();

        assert user != null;
        System.out.println("LOG: User isn't null");

        String current_srv = user.getString("current_srv");

        MongoClients.create("mongodb://localhost:27017").getDatabase("servers_bd").getCollection("offacc_servers")
                .find(eq("server_ip", current_srv)).first();

        System.out.println("LOG: (sendToServer) sending message to server...");

        try (Socket offAccSocket = new Socket(findIp(current_srv), Integer.parseInt(Objects.requireNonNull(findPort(current_srv))))) {
            PrintWriter out = new PrintWriter(offAccSocket.getOutputStream(), true);
            msg.replace("client", false);
            System.out.println("LOG: (sendToServer) "+msg);
            out.println(msg.toJson());
        }
    }
}
