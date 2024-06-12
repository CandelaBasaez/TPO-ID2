package funciones;

import model.entity.Usuario;
import org.neo4j.driver.*;
import redis.clients.jedis.Jedis;

import java.util.*;

import static model.entity.Usuario.cargarDesdeRedis;

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
                Random random = new Random();
                int numCar=10000+ random.nextInt(90000);
                session.run("CREATE (c" + numCar + ":Carrito{userDNI:" + dni + "})");
            }
        }

        return usuario;
    }

    public static List<Usuario> mostrarUsuariosTOP() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Jedis jedis = new Jedis("localhost", 6379)) {
            List<String> dniUsuarios = jedis.zrangeByScore("categorias", Double.parseDouble("241"), 1000000000);

            for (String dniUsuario : dniUsuarios) {
                int dni = Integer.parseInt(dniUsuario);
                Usuario usuario = cargarDesdeRedis(dni);
                String us = usuario.toString();
                if (us != null) {
                    usuarios.add(usuario);
                }
            }
            for(Usuario usuario : usuarios){
                System.out.println(usuario);
            }
        }
        return usuarios;
    }

    public static List<Usuario> mostrarUsuariosMEDIUM() {
        List<Usuario> usuarios = new ArrayList<>();
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            List<String> dniUsuarios = jedis.zrangeByScore("categorias",120,240);

            for (String dniUsuario : dniUsuarios) {
                int dni = Integer.parseInt(dniUsuario);
                Usuario usuario = cargarDesdeRedis(dni);
                String us = usuario.toString();
                if (us != null) {
                    usuarios.add(usuario);
                }
            }
            for(Usuario usuario : usuarios){
                System.out.println(usuario);
            }
        }
        return usuarios;
    }
    public static List<Usuario> mostrarUsuariosLOW() {
        List<Usuario> usuarios = new ArrayList<>();

        try (Jedis jedis = new Jedis("localhost", 6379)) {
            List<String> dniUsuarios = jedis.zrangeByScore("categorias",0, Double.parseDouble("119"));

            for (String dniUsuario : dniUsuarios) {
                int dni = Integer.parseInt(dniUsuario);
                Usuario usuario = cargarDesdeRedis(dni);
                String us = usuario.toString();
                if (us != null) {
                    usuarios.add(usuario);
                }
            }
            for(Usuario usuario : usuarios){
                System.out.println(usuario);
            }
        }
        return usuarios;
    }

    public static void asignarCategoriaUsuario(int dni){
        System.out.print("Ingrese el tiempo que el usuario permanece conectado: ");
        Scanner time = new Scanner(System.in);
        int tiempo = time.nextInt();
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            jedis.zadd("categorias",tiempo, String.valueOf(dni));
        }
    }
}
