package app;

import funciones.funcionesUsuario;
import model.entity.Usuario;

public class App {

    public static void main(String[] args) {
        CargadoDatosPrueba.InicializadoSistema();

        System.out.println("Te damos la bienvenida a la Libreria");
        System.out.println("Seleccione la acción a realizar:");
        System.out.println("1. Seleccionar Usuario");
        System.out.println("2. Crear usuario");
        System.out.println("3. Ver usuarios por categorias");
        System.out.println("4. Crear usuario");
        System.out.println("5. Crear usuario");
        System.out.println("-1. Salir del sistema");

        int respuesta = 2;
        //! INGRESA OPCION

        int dni = 0;

        while (respuesta != -1){
            if (respuesta == 1){
                //! INGRESA DNI

                Usuario usuario = Usuario.cargarDesdeRedis(dni);

            } else if (respuesta == 2) {
                //! INGRESA DATOS DE USUARIO NUEVO
                int dni = null;
                String nombre = null;
                String apellido = null;
                String condIVA = null;
                String direccion = null;
                int tiempo = null;

                Usuario usuario = funcionesUsuario.crearUsuario(dni, nombre, apellido, condIVA, direccion);

            }else System.out.println("La entrada no es valida.");
        }

    }
}
