import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class TestMongoDB {

    private static MongoCollection<Document> getUsrDatabase() { // its don't working
        String uri = "mongodb://localhost:27017"; // any server

        MongoDatabase users_db;
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            users_db = mongoClient.getDatabase("users_bd");
            return users_db.getCollection("users");
        } catch (Exception e) {
            System.out.println("ERROR: can't connect to MongoDB (getUsrDatabase): " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase users_db = mongoClient.getDatabase("users_bd"); // its working
        MongoCollection<Document> users = users_db.getCollection("users");

        Document text_doc = Document.parse("{msg:'s', sendUserId:'0', password:'hui_penis', client:true, userId:'0'}");
        Document usr_doc = users.find(eq("user_id", text_doc.getString("userId"))).first();
        System.out.println(usr_doc);
    }
}
