package funciones;

import com.mongodb.client.*;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;

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

    public static void mostrarPagosPorUsuario(int dni){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("Pagos");
        Document filtro = new Document("DNIUsuario", dni);
        FindIterable<Document> documents = collection.find(filtro);
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
        mongoClient.close();
    }

    public static void registrarPagoFactura(int numFac,int dni){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("Pagos");
        String fechaHora = LocalDateTime.now().toString();
        Document document = new Document()
                .append("Accion", "Se pago la factura")
                .append("FacturaPaga", numFac)
                .append("DNIUsuario",dni)
                .append("fecha", fechaHora);
        collection.insertOne(document);
    }

}
