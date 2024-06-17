package app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import model.entity.Usuario;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

//public class CargadoDatosPrueba {
//
//    public static void InicializadoSistema() {
//        CreadoColeccionesMongo();
//        CreadoDeUsuarios();
//        CargadoProductos();
//    }
//
//    private static void CargadoProductos() {
//        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
//            // Crea una nueva sesión
//            try (Session session = driver.session()) {
//                // Ejecuta la sentencia para crear los nodos
//                session.run("CREATE (cu:Producto{codigoProducto:1,nombre:'Cuaderno',descripcion:'Cuaderno tamaño A4 anillado',precioUnitario:1500}), " +
//                        "(la:Producto{codigoProducto:2,nombre:'Lapicera azul',descripcion:'Lapicera tinta azul',precioUnitario:300}), " +
//                        "(go:Producto{codigoProducto:3,nombre:'Marcador rojo',descripcion:'Marcador tinta roja para pizarra',precioUnitario:700}), " +
//                        "(re:Producto{codigoProducto:4,nombre:'Resaltador',descripcion:'Resaltador tinta amarilla',precioUnitario:500}), " +
//                        "(lp:Producto{codigoProducto:5,nombre:'Lapiz',descripcion:'Lapiz de garfito HB',precioUnitario:300})");
//            }
//        }
//    }
//
//    private static void CreadoDeUsuarios() {
//        int i = 1;
//
//        List<Usuario> usuariosTest = new ArrayList<>();
//
//        usuariosTest.add(new Usuario(44560825, "Candela", "González", "Monotributista", "Arroyo 48"));
//        usuariosTest.add(new Usuario(43065582, "Nicolas", "Perez", "Responsable Inscripto", "Salta 123"));
//        usuariosTest.add(new Usuario(42605825, "Juan", "García", "Consumidor Final", "Salta 123"));
//        usuariosTest.add(new Usuario(38290561, "María", "López", "Responsable Inscripto", "Av. San Martín 567"));
//        usuariosTest.add(new Usuario(47821590, "Santiago", "Martínez", "Monotributista", "Calle Belgrano 234"));
//        usuariosTest.add(new Usuario(31059874, "Lucía", "Rodríguez", "Consumidor Final", "Av. Rivadavia 789"));
//
//        for(Usuario user: usuariosTest){
//            try (Jedis jedis = new Jedis("localhost", 6379)) {
//                jedis.hset("usuario:" + user.getDni(), "dni", String.valueOf(user.getDni()));
//                jedis.hset("usuario:" + user.getDni(), "nombre", user.getNombre());
//                jedis.hset("usuario:" + user.getDni(), "apellido", user.getApellido());
//                jedis.hset("usuario:" + user.getDni(), "condIVA", user.getCondIVA());
//                jedis.hset("usuario:" + user.getDni(), "direccion", user.getDireccion());
//            }
//
//            try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
//                // Crea una nueva sesión
//                try (Session session = driver.session()) {
//                    // Ejecuta la sentencia para crear los nodos de tipo Carrito
//                    session.run("CREATE (c" + i + ":Carrito{userDNI:" + user.getDni() + "})");
//                }
//            }
//            i++;
//        }
//
//        try (Jedis jedis = new Jedis("localhost", 6379)) {
//            jedis.zadd("categorias", 250, "44560825");
//            jedis.zadd("categorias", 250, "43065582");
//            jedis.zadd("categorias", 230, "42605825");
//            jedis.zadd("categorias", 230, "38290561");
//            jedis.zadd("categorias", 110, "47821590");
//            jedis.zadd("categorias", 110, "31059874");
//        }
//    }
//
//    private static void CreadoColeccionesMongo() {
//        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
//        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
//
//        // Verificar si la colección de facturas ya existe
//        if (!database.listCollectionNames().into(new ArrayList<>()).contains("Facturas")) {
//            // Si no existe, crear la colección
//            database.createCollection("Facturas");
//        }
//
//        if (!database.listCollectionNames().into(new ArrayList<>()).contains("Pagos")) {
//            // Si no existe, crear la colección
//            database.createCollection("Pagos");
//        }
//
//        if (!database.listCollectionNames().into(new ArrayList<>()).contains("CambiosCatalogo")) {
//            // Si no existe, crear la colección
//            database.createCollection("CambiosCatalogo");
//        }
//
//        // Cerrar la conexión con MongoDB
//        mongoClient.close();
//    }
//}
