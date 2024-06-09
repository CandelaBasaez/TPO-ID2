package funciones;

import model.entity.Usuario;

public class funcionesUsuario {


    public static Usuario crearUsuario(int dni, String nombre, String apellido, String condIVA, String direccion){
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setCondIVA(condIVA);
        usuario.setDireccion(direccion);

        usuario.guardarEnRedis();

        return usuario;
    }

}
