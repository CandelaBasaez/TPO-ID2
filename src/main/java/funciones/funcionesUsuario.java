package funciones;

import model.entity.Usuario;
import org.neo4j.driver.*;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Scanner;

public class funcionesUsuario {

    public static Usuario crearUsuario(int dni, String nombre, String apellido, String condIVA, String direccion) {
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCondIVA(condIVA);
        usuario.setDireccion(direccion);

        usuario.guardarEnRedis();

        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                Result i = session.run("MATCH (n:Carrito) RETURN COUNT(n)");
                session.run("CREATE (c" + i + 1 + ":Carrito{userDNI:" + dni + "})");
            }
        }

        return usuario;
    }

    public static void mostarUsuariosOrdenados() {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.zrevrange("categorias", 0, -1);
        }
    }
    public static void mostrarUsuariosTOP(){
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.zrangeByScore("categorias", Double.parseDouble("(240"), Double.parseDouble("inf"));
        }
    }
    public static void mostrarUsuariosMEDIUM(){
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.zrangeByScore("categorias",120,240);
        }
    }
    public static void mostrarUsuariosLOW(){
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.zrangeByScore("categorias",0, Double.parseDouble("(120"));
        }
    }
    public static void asignarCategoriaUsuario(int dni){
        Scanner time = new Scanner(System.in);
        int tiempo = time.nextInt();
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.zadd("categorias",tiempo, String.valueOf(dni));
        }
    }
}
