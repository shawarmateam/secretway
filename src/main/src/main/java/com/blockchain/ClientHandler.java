package com.blockchain;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
                    System.out.println(obj.get("userId") + ">> " + obj.get("msg"));
                    sendToAll(obj);
                }
            }
            allSockets.remove(clientSocket);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static MongoCollection<Document> getUsrDatabase() {
        String uri = "mongodb://localhost:27017"; // any server

        MongoDatabase users_db;
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            users_db = mongoClient.getDatabase("users_bd");
            return users_db.getCollection("users");
        }
    }

    private static MongoCollection<Document> getOffAccServersDatabase() {
        String uri = "mongodb://localhost:27017"; // any server

        MongoDatabase servers_db;
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            servers_db = mongoClient.getDatabase("servers_bd");
            return servers_db.getCollection("offacc_servers");
        }
    }

    private static String findIp(String ip) {
        Pattern pattern = Pattern.compile("^(.*?)(?=:)");
        Matcher matcher = pattern.matcher(ip);

        return matcher.find() ? matcher.group(1) : null;
    }

    private static String findPort(String port) {
        Pattern pattern = Pattern.compile("(?<=:).*$");
        Matcher matcher = pattern.matcher(port);

        return matcher.find() ? matcher.group(1) : null;
    }

    private void sendToAll(Document msg) throws IOException {
//        for (Socket socket : allSockets) {
//            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//            out.println(user + ">> " + msg);
//        }

        MongoCollection<Document> usrDatabase = getUsrDatabase();
        Document user = usrDatabase.find(eq("usertag", msg.getString("user"))).first();

        // TODO: Переписать эту часть и убрать getOffAccServersDatabase()

        assert user != null;
        String current_srv = user.getString("current_srv");
        if (getOffAccServersDatabase().find(eq("server_ip", current_srv)).first() == null) {
            try (Socket offAccSocket = new Socket(findIp(current_srv), Integer.parseInt(Objects.requireNonNull(findPort(current_srv))))) {
                PrintWriter out = new PrintWriter(offAccSocket.getOutputStream(), true);
                out.println(msg);
            }
        }

    }
}
