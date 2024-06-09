package app;

import model.entity.Usuario;

public class App {

    public static void main(String[] args) {
        System.out.println("Bienvenido al sistema de x");

        System.out.println("Seleccione la accion a realizar");

        System.out.println("1.    Crear un usuario");

        String opcion = "1";

        if (opcion.equals("1")){
            Usuario usuario = new Usuario();


            usuario.guardarEnRedis();


        }



        Usuario usuario = new Usuario();

        usuario.setDni(44650285);
        usuario.setNombre("Candela");
        usuario.setApellido("Basaez");
        usuario.setCondIVA("Monotributista");
        usuario.setDireccion("Calle Falsa 123");

        usuario.guardarEnRedis();

    }
}
