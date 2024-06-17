package funciones;

import com.mongodb.client.*;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import redis.clients.jedis.Jedis;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static com.mongodb.client.model.Filters.eq;
import static java.lang.reflect.Array.set;

public class funcionesFacturas {
    public static void mostrarFacturas(int dni){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("Facturas");
        Document filtro = new Document("dni", dni);
        FindIterable<Document> documents = collection.find(filtro);
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
        mongoClient.close();
    }

    public static void crearFactura(int dni){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("Facturas");
        String nombre="";
        String apellido="";
        String condIVA="";
        int monto = 0;
        try (Jedis jedis = new Jedis("redis://localhost:6379")) {
            if (jedis.exists("usuario:" + dni)) {
                nombre = jedis.hget("usuario:" + dni, "nombre");
                apellido = jedis.hget("usuario:" + dni, "apellido");
                condIVA = jedis.hget("usuario:" + dni, "condIVA");
            } else {
                System.out.println("El usuario con el DNI ingresado no existe");
            }
        }
        String fechaHora= LocalDateTime.now().toString();
        Random random = new Random();
        int numFac=100000+ random.nextInt(900000);
        Document document = new Document()
                .append("nroFactura", numFac)
                .append("nombre", nombre)
                .append("apellido", apellido)
                .append("dni", dni)
                .append("catIVA", condIVA)
                .append("productos", new ArrayList<>())
                .append("cantidades", new ArrayList<>())
                .append("fecha", fechaHora)
                .append("metodoPago","A definir")
                .append("montoIVA", 0)
                .append("condPago", "No paga");
        collection.insertOne(document);
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                Result produc = session.run("MATCH (c:Carrito{userDNI:" + dni + "})-[r:TIENE]->(p:Producto) return p");
                while (produc.hasNext()) {
                    Record record = produc.next();
                    int prodSet = record.get("p").get("codigoProducto").asInt();
                    int precioSet = record.get("p").get("precioUnitario").asInt();
                    Result cant = session.run("MATCH (c:Carrito{userDNI:" + dni + "})-[r:TIENE]->(p:Producto{codigoProducto:"+prodSet+"}) return r");
                    Record rec = cant.next();
                    int cantSet = rec.get("r").get("cant").asInt();
                    String nomProd = record.get("p").get("nombre").asString();

                    Document filtro = new Document("nroFactura", numFac);
                    MongoCursor<Document> cursor = collection.find(filtro).iterator();
                    if (cursor.hasNext()) {
                        Document doc = cursor.next();
                    }
                    Document actualizar = new Document("$push", new Document("productos", nomProd));
                    collection.updateOne(filtro, actualizar);
                    Document actuali = new Document("$push", new Document("cantidades", cantSet));
                    collection.updateOne(filtro, actuali);
                    monto = monto + (precioSet * cantSet);
                }
            }
        }
        double calculo = 0;
        if (condIVA.equals("Consumidor Final") || condIVA.equals("Monotributista")){
            calculo = monto*1.21;

        }else{
            calculo = monto*1.105;
        }
        Document filtro = new Document("nroFactura", numFac);
        Document actualizacion = new Document("$set", new Document("montoIVA", calculo));
        collection.updateOne(filtro, actualizacion);
        mongoClient.close();
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                session.run("MATCH (c:Carrito{userDNI:" + dni + "})-[r:TIENE]->(p:Producto) delete r");
            }
        }
    }

    public static void pagarFactura(int nroFac){
        System.out.println("Ingrese el metodo de pago: ");
        Scanner metodo = new Scanner(System.in);
        String metodoP = metodo.nextLine();
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("Facturas");

        Document filter = new Document("nroFactura", nroFac);
        Document update = new Document("$set", new Document("metodoPago", metodoP));
        collection.updateOne(filter, update);
        Document filtro = new Document("nroFactura", nroFac);
        Document actualizacion = new Document("$set", new Document("condPago", "Paga"));
        collection.updateOne(filtro, actualizacion);
        mongoClient.close();

    }
}
