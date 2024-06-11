package funciones;

import com.mongodb.client.*;
import org.bson.Document;

public class funcionesFacturas {
    public static void mostrarFacturas(){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("Facturas");
        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
        mongoClient.close();
    }

    public static void crearFactura(){

    }

    public static void pagarFactura(){

    }
}
