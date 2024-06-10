package funciones;

import model.entity.Usuario;
import org.neo4j.driver.*;

public class funcionesUsuario {
    
    public static Usuario crearUsuario(int dni, String nombre, String apellido, String condIVA, String direccion){
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCondIVA(condIVA);
        usuario.setDireccion(direccion);

        usuario.guardarEnRedis();

        usuario.agregarCategoria(tiempo,dni);

        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                Result i=session.run("MATCH (n:Carrito) RETURN COUNT(n)");
                session.run("CREATE (c" + i+1 + ":Carrito{userDNI:" + dni + "})");
            }
        }

        return usuario;
    }

}
