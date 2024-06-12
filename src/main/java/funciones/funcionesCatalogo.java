package funciones;

import model.entity.Usuario;
import model.entity.neo.Producto;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.fabric.stream.StatementResult;
import redis.clients.jedis.Jedis;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import scala.Predef;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

import static scala.Predef.StringFormat;


public class funcionesCatalogo {

    public static void mostrarCatalogo(){
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                Result result = session.run("MATCH (n:Producto) RETURN n");
                while (result.hasNext()) {
                    Record record = result.next();
                    System.out.println("Producto: "+record.get("n").get("nombre").asString());
                    System.out.println("Precio por unidad: "+record.get("n").get("precioUnitario").asInt());
                    System.out.println("--------------------------------");
                }
            }
        }
    }
    public static void modificarNomDesCatalogo(){
        System.out.println("Ingrese que producto desea modificar:");
        Scanner prod = new Scanner(System.in);
        String product = prod.nextLine();
        System.out.println("Ingrese que atributo del producto desea modificar:");
        Scanner part = new Scanner(System.in);
        String parte = part.nextLine();
        System.out.println("Ingrese el nuevo dato:");
        Scanner mod = new Scanner(System.in);
        String modif = mod.nextLine();
        String modi = modif.toString();
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                session.run("MATCH (n:Producto{nombre:"+product+"}) SET n."+parte+"="+modi);
            }
        }
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("CambiosCatalogo");
        Document document = new Document();
        document.put("descripcion","Se modifico"+parte+"de"+product);
        document.put("fecha", LocalDateTime.now());
        collection.insertOne(document);
        mongoClient.close();
    }
    public static void modificarPrecioCatalogo(){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("CambiosCatalogo");
        System.out.println("Ingrese que producto desea modificar:");
        Scanner prod = new Scanner(System.in);
        String product = prod.nextLine();
        System.out.println("Ingrese el nuevo precio:");
        Scanner price = new Scanner(System.in);
        int precio = price.nextInt();
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                session.run("MATCH (n:Producto{nombre:"+product+"}) SET n.precioUnitario="+precio);
            }
        }
        Document document = new Document();
        document.put("descripcion","Se modifico el precio de "+product);
        document.put("fecha", LocalDateTime.now());
        collection.insertOne(document);
        mongoClient.close();
    }

}
