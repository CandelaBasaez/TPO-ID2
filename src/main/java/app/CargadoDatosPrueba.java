package app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import model.entity.Usuario;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

public class CargadoDatosPrueba {

    public static void InicializadoSistema() {
        CreadoColeccionesMongo();
        CreadoDeDatosPruebaRedis();

    }

    private static void CreadoDeDatosPruebaRedis() {
        List<Usuario> usuariosTest = new ArrayList<>();

        usuariosTest.add(new Usuario(44560825, "Candela", "González", "Monotributista", "Arroyo 48"));
        usuariosTest.add(new Usuario(43065582, "Nicolas", "Perez", "Responsable Inscripto", "Salta 123"));
        usuariosTest.add(new Usuario(42605825, "Juan", "García", "Consumidor Final", "Salta 123"));
        usuariosTest.add(new Usuario(38290561, "María", "López", "Responsable Inscripto", "Av. San Martín 567"));
        usuariosTest.add(new Usuario(47821590, "Santiago", "Martínez", "Monotributista", "Calle Belgrano 234"));
        usuariosTest.add(new Usuario(31059874, "Lucía", "Rodríguez", "Consumidor Final", "Av. Rivadavia 789"));

        for(Usuario user: usuariosTest){
            try (Jedis jedis = new Jedis("localhost", 6379)) {
                jedis.hset("usuario:" + user.getDni(), "dni", String.valueOf(user.getDni()));
                jedis.hset("usuario:" + user.getDni(), "nombre", user.getNombre());
                jedis.hset("usuario:" + user.getDni(), "apellido", user.getApellido());
                jedis.hset("usuario:" + user.getDni(), "condIVA", user.getCondIVA());
                jedis.hset("usuario:" + user.getDni(), "direccion", user.getDireccion());
            }
        }

        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.zadd("categorias", 250, "44560825");
            jedis.zadd("categorias", 250, "43065582");
            jedis.zadd("categorias", 230, "42605825");
            jedis.zadd("categorias", 230, "38290561");
            jedis.zadd("categorias", 110, "47821590");
            jedis.zadd("categorias", 110, "31059874");
        }

    }

    private static void CreadoColeccionesMongo() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");

        // Verificar si la colección de facturas ya existe
        if (!database.listCollectionNames().into(new ArrayList<>()).contains("Facturas")) {
            // Si no existe, crear la colección
            database.createCollection("Facturas");
        }

        if (!database.listCollectionNames().into(new ArrayList<>()).contains("Pagos")) {
            // Si no existe, crear la colección
            database.createCollection("Pagos");
        }

        if (!database.listCollectionNames().into(new ArrayList<>()).contains("CambiosCatalogo")) {
            // Si no existe, crear la colección
            database.createCollection("CambiosCatalogo");
        }

        // Cerrar la conexión con MongoDB
        mongoClient.close();
    }
}
