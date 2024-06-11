package funciones;

import com.mongodb.client.*;
import org.bson.Document;

public class funcionesPagos {
    public static void mostrarPagos(){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("Pagos");
        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
        mongoClient.close();
    }

    public static void modificarPagoFactura(){

    }
}
