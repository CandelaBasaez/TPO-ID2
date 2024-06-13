package funciones;

import com.mongodb.client.*;
import model.entity.Usuario;
import model.entity.neo.Producto;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.fabric.stream.StatementResult;
import redis.clients.jedis.Jedis;
import org.bson.Document;
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
                    System.out.println("--------------------------------");
                    System.out.println("Codigo del Producto: "+record.get("n").get("codigoProducto").asInt());
                    System.out.println("Producto: "+record.get("n").get("nombre").asString());
                    System.out.println("Descripcion: "+record.get("n").get("descripcion").asString());
                    System.out.println("Precio por unidad: "+record.get("n").get("precioUnitario").asInt());
                    System.out.println("--------------------------------");
                }
            }
        }
    }

    public static void agregarProducto(){
        System.out.println("Ingrese un identificador de dos letras para el producto:");
        Scanner id = new Scanner(System.in);
        String iden = id.nextLine();
        System.out.println("Ingrese el codigo del producto que desea agregar:");
        Scanner cod = new Scanner(System.in);
        int codigo = cod.nextInt();
        System.out.println("Ingrese el nombre del producto:");
        Scanner prod = new Scanner(System.in);
        String producto = prod.nextLine();
        System.out.println("Ingrese la descripcion:");
        Scanner desc = new Scanner(System.in);
        String descrip = desc.nextLine();
        System.out.println("Ingrese el precio por unidad del producto:");
        Scanner prec = new Scanner(System.in);
        int precio = prec.nextInt();
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                String query = ("CREATE(" + iden + ":Producto{codigoProducto:$codigo,nombre:$nombre,descripcion:$descripcion,precioUnitario:$precio})");
                session.run(query, Values.parameters("codigo", codigo, "nombre", producto, "descripcion", descrip, "precio", precio));
            }
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
            MongoCollection<Document> collection = database.getCollection("CambiosCatalogo");
            Document document = new Document();
            document.put("descripcion", "Se añadio un producto");
            document.put("fecha", LocalDateTime.now().toString());
            collection.insertOne(document);
            mongoClient.close();
        }
    }

    public static void eliminarProducto(){
        mostrarCatalogo();
        System.out.println("Ingrese el codigo del producto que desea eliminar:");
        Scanner cod = new Scanner(System.in);
        int codigo = cod.nextInt();
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                String query = ("MATCH(p:Producto{codigoProducto:$codigo}) DETACH DELETE p");
                session.run(query, Values.parameters("codigo", codigo));
            }
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
            MongoCollection<Document> collection = database.getCollection("CambiosCatalogo");
            Document document = new Document();
            document.put("descripcion", "Se elimino un producto");
            document.put("fecha", LocalDateTime.now().toString());
            collection.insertOne(document);
            mongoClient.close();
        }
    }


    public static void modificarNombreCatalogo(){
        String nomPr = "";
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                Result result = session.run("MATCH (n:Producto) RETURN n");
                while (result.hasNext()) {
                    Record record = result.next();
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Codigo del Producto: " + record.get("n").get("codigoProducto"));
                    System.out.println("Producto: " + record.get("n").get("nombre").asString());
                    System.out.println("Precio por unidad: " + record.get("n").get("precioUnitario").asInt());
                    System.out.println("--------------------------------------------------------");
                }
                System.out.println("Ingrese el codigo del producto que desea modificar:");
                Scanner prod = new Scanner(System.in);
                int product = prod.nextInt();
                System.out.println("Ingrese el nuevo nombre:");
                Scanner name = new Scanner(System.in);
                String nomb = name.nextLine().toString();
                String query=("MATCH (n:Producto{codigoProducto:" + product + "}) SET n.nombre=$nombre");
                session.run(query,Values.parameters("nombre",nomb));
                nomPr = nomb;
            }
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
            MongoCollection<Document> collection = database.getCollection("CambiosCatalogo");
            Document document = new Document();
            document.put("descripcion", "Se modifico el nombre de " + nomPr);
            document.put("fecha", LocalDateTime.now().toString());
            collection.insertOne(document);
            mongoClient.close();
        }
    }

    public static void modificarDescCatalogo(){
        String nomPr = "";
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                Result result = session.run("MATCH (n:Producto) RETURN n");
                while (result.hasNext()) {
                    Record record = result.next();
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Codigo del Producto: " + record.get("n").get("codigoProducto"));
                    System.out.println("Producto: " + record.get("n").get("nombre").asString());
                    System.out.println("Descripcion: " + record.get("n").get("descripcion").asString());
                    System.out.println("Precio por unidad: " + record.get("n").get("precioUnitario").asInt());
                    System.out.println("--------------------------------------------------------");
                }
                System.out.println("Ingrese el codigo del producto que desea modificar:");
                Scanner prod = new Scanner(System.in);
                int product = prod.nextInt();
                System.out.println("Ingrese la nueva descripcion:");
                Scanner des = new Scanner(System.in);
                String desc = des.nextLine().toString();
                String query=("MATCH (n:Producto{codigoProducto:" + product + "}) SET n.descripcion=$descripcion");
                session.run(query,Values.parameters("descripcion",desc));
                nomPr = des.nextLine();
            }
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
            MongoCollection<Document> collection = database.getCollection("CambiosCatalogo");
            Document document = new Document();
            document.put("descripcion", "Se modifico el nombre de " + nomPr);
            document.put("fecha", LocalDateTime.now().toString());
            collection.insertOne(document);
            mongoClient.close();
        }
    }

    public static void modificarPrecioCatalogo(){
        String nomPr = "";
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                Result result = session.run("MATCH (n:Producto) RETURN n");
                while (result.hasNext()) {
                    Record record = result.next();
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Codigo del Producto: " + record.get("n").get("codigoProducto"));
                    System.out.println("Producto: " + record.get("n").get("nombre").asString());
                    System.out.println("Precio por unidad: " + record.get("n").get("precioUnitario").asInt());
                    System.out.println("--------------------------------------------------------");
                }
                System.out.println("Ingrese el codigo del producto que desea modificar:");
                Scanner prod = new Scanner(System.in);
                int product = prod.nextInt();
                System.out.println("Ingrese el nuevo precio:");
                Scanner price = new Scanner(System.in);
                int precio = price.nextInt();
                session.run("MATCH (n:Producto{codigoProducto:" + product + "}) SET n.precioUnitario=" + precio);
                Result res = session.run("MATCH (n:Producto{codigoProducto:" + product + "}) return n");
                Record rec = res.next();
                nomPr = rec.get("n").get("nombre").asString();
            }
            MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
            MongoCollection<Document> collection = database.getCollection("CambiosCatalogo");
            Document document = new Document();
            document.put("descripcion", "Se modifico el precio de " + nomPr);
            document.put("fecha", LocalDateTime.now().toString());
            collection.insertOne(document);
            mongoClient.close();
        }
    }
    public static void mostrarCambiosCatalogo(){
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("CambiosCatalogo");
        FindIterable<Document> documents = collection.find();
        for (Document document : documents) {
            System.out.println(document.toJson());
        }
        mongoClient.close();
    }

}
