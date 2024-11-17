package com.accsrv;
import com.mongodb.client.*;
import org.bson.Document;
import org.mindrot.jbcrypt.BCrypt;

import java.io.*;
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
        try (ServerSocket serverSocket = new ServerSocket(1201)) {
            System.out.println("Starting off. accounts server...");

            while (true) {
                System.out.println("LOG: Waiting for client...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("LOG: Client was join (" + clientSocket.getInetAddress()+")");

                Thread sv_thread = new Thread(() -> serverHandler(clientSocket));
                sv_thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void checkVer() {
        try {
            BufferedReader br0 = new BufferedReader(new FileReader("./VERSION"));
            String[] ver = br0.readLine().split(" ");

            System.out.println("LOG: (type) '"+ver[0]+"'");
            System.out.println("LOG: (version) '"+ver[1]+"'");

        } catch (IOException e) {
            e.printStackTrace();
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

    private static String findPort(String ip) {
        Pattern pattern = Pattern.compile("(?<=:).*$");
        Matcher matcher = pattern.matcher(ip);

        return matcher.find() ? matcher.group() : null;
    }

    public static void serverHandler(Socket clientSocket) {
        allSockets.add(clientSocket); // add server to list

        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true); //connect client socket
            String text;

            while ((text = in.readLine()) != null) { // infinity reading of text
                System.out.println(text);
                Document text_doc = Document.parse(text);

                // if client join to OffAcc Server
                if (text_doc.getBoolean("client")) {
                    // грамотно ли оформлен пакет
                    if (text_doc.containsKey("userId") && text_doc.containsKey("password") && text_doc.containsKey("msg") && text_doc.containsKey("sendUserId")) {
                        System.out.println("LOG: text_doc: "+text_doc);

                        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
                        MongoDatabase users_db = mongoClient.getDatabase("users_bd");
                        MongoCollection<Document> users = users_db.getCollection("users");

                        Document usr_doc = users.find(eq("user_id", text_doc.getString("userId"))).first();
                        assert usr_doc != null;

                        if (checkPassword(text_doc.getString("password"), usr_doc.getString("password"))) {
                            MongoDatabase msgs_servers_db = mongoClient.getDatabase("servers_bd");
                            MongoCollection<Document> msgs_servers = msgs_servers_db.getCollection("msgs_servers");

                            FindIterable<Document> servers = msgs_servers.find(eq("online", true)).sort(eq("layer", 1)).limit(LIMIT);

                            text_doc.remove("password");
                            for (Document server_doc : servers) {
                                sendMsg(server_doc, text_doc);
                            }
                        }
                    }
                    System.out.println("LOG: client disconnected");
                }

                // if msgS join to OffAcc server
                else {
                    if (text_doc.containsKey("userId") && text_doc.containsKey("msg")) {
                        Document user = MongoClients.create("mongodb://localhost:27017").getDatabase("users_bd").getCollection("users")
                                .find(eq("user_id", text_doc.getString("userId"))).first(); // user what sends msg

                        Document user_to_send = MongoClients.create("mongodb://localhost:27017").getDatabase("users_bd").getCollection("users")
                                .find(eq("user_id", text_doc.getString("sendUserId"))).first(); // user what gets msg

                        // TODO: сделать хранилище сообщений на пользовательских серверах msgS

                        System.out.println(user);
                        System.out.println(user_to_send);
                    }
                    System.out.println("LOG: msgS disconnected");
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

            System.out.println("LOG: '"+findPort(server_ip)+"'");
            try (Socket socket = new Socket(findIp(server_ip), Integer.parseInt(Objects.requireNonNull(findPort(server_ip))))) {
                PrintWriter msgs_out = new PrintWriter(socket.getOutputStream(), true);
                msgs_out.println(text_doc.toJson());
                System.out.println("LOG: Sended! ("+text_doc.toJson()+")");

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread_send.start();
    }
}
