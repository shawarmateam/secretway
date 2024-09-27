package com.accsrv;
import com.blockchain.ClientHandler;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.mindrot.jbcrypt.BCrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mongodb.client.model.Filters.eq;

public class OfficalAccountDB {
    public final static ArrayList<Socket> allSockets = new ArrayList<>();
    public final static int LIMIT = 5;

    public static void main(String[] args) {
        Thread sv_thread = new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(1201)) {
                System.out.println("Starting off. accounts server...");

                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Client was join: " + clientSocket.getInetAddress());
                    serverHandler(clientSocket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        sv_thread.start();
    }

    private static MongoCollection<Document> getUsrDatabase() {
        String uri = "mongodb://localhost:27017"; // any server

        MongoDatabase users_db;
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            users_db = mongoClient.getDatabase("users_bd");
            return users_db.getCollection("users");
        }
    }

    private static MongoCollection<Document> getMsgServersDatabase() {
        String uri = "mongodb://localhost:27017"; // any server

        MongoDatabase servers_db;
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            servers_db = mongoClient.getDatabase("servers_bd");
            return servers_db.getCollection("msgs_servers");
        }
    }

    private static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
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

    public static void serverHandler(Socket clientSocket) {
        allSockets.add(clientSocket); // add server to list

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); //connect client socket
            String text;

            while ((text = in.readLine()) != null) { // infinity reading of text
                Document text_doc = Document.parse(text);

                if (text_doc.containsKey("usertag") && text_doc.containsKey("password") && text_doc.containsKey("msg")) { // грамотно ли оформлен пакет
                    Document usr_doc = getUsrDatabase().find(eq("usertag", text_doc.getString("usertag"))).first();
                    assert usr_doc != null;

                    if (checkPassword(usr_doc.getString("password"), text_doc.getString("password"))) {
                        MongoCollection<Document> msg_servers_db = getMsgServersDatabase();
                        FindIterable<Document> servers = msg_servers_db.find(eq("online", true)).sort(eq("layer", 1)).limit(LIMIT);

                        for (Document server_doc : servers) {
                            sendMsg(server_doc, text_doc);
                        }
                    }
                }
            }
            allSockets.remove(clientSocket);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendMsg(Document server_doc, Document text_doc) {
        Thread thread_send = new Thread(() -> {
            String server_ip = server_doc.getString("server_ip");

            try (Socket socket = new Socket(findIp(server_ip), Integer.parseInt(Objects.requireNonNull(findPort(server_ip))))) {
                PrintWriter msgs_out = new PrintWriter(socket.getOutputStream(), true);
                msgs_out.println(text_doc.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread_send.start();
    }
}