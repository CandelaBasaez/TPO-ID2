package app;

import funciones.funcionesUsuario;
import model.entity.Usuario;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        CargadoDatosPrueba.InicializadoSistema();

        System.out.println("Te damos la bienvenida a la Libreria");
        System.out.println("Seleccione la acción a realizar:");
        System.out.println("1. Seleccionar Usuario");
        System.out.println("2. Crear usuario");
        System.out.println("3. Ver usuarios por categorias");
        System.out.println("4. Asignar usuario a una categoria");
        System.out.println("5. Ver Catalogo");
        System.out.println("6. Modificar Catalogo");
        System.out.println("7. Realizar pedido");
        System.out.println("8. Pagar factura");
        System.out.println("9. Ver Registro de Facturas");
        System.out.println("10. Ver Registro de Pagos");
        System.out.println("-1. Salir del sistema");

        Scanner opc = new Scanner(System.in);
        int opcion = opc.nextInt();

        while (opcion != -1){
            if (opcion == 1) {
                System.out.println("Ingrese el dni del usuario que desea buscar:");
                Scanner documento = new Scanner(System.in);
                int dni = documento.nextInt();
                Usuario usuario = Usuario.cargarDesdeRedis(dni);

            } else if (opcion == 2) {
                Scanner document = new Scanner(System.in);
                Scanner name = new Scanner(System.in);
                Scanner surname = new Scanner(System.in);
                Scanner address = new Scanner(System.in);
                Scanner condIva = new Scanner(System.in);
                int dni = document.nextInt();
                String nombre = name.nextLine();
                String apellido = surname.nextLine();
                String direccion = address.nextLine();
                String condIVA = condIva.nextLine();

                Usuario usuario = funcionesUsuario.crearUsuario(dni, nombre, apellido, condIVA, direccion);

            }else if (opcion == 3) {
                System.out.println("Ingrese el número correspondiente a la categoria que desea visualizar:");
                System.out.println("1. Todos los usuarios ordenados desde mayor a menor tiempo de conexion");
                System.out.println("2. Todos los usuarios de categoria TOP");
                System.out.println("3. Todos los usuarios de categoria MEDIUM");
                System.out.println("4. Todos los usuarios de categoria LOW");

                Scanner cat = new Scanner(System.in);
                int categoria = cat.nextInt();

                if (categoria == 1){

                }else if (categoria == 2){

                }else if (categoria == 3){

                }else if (categoria == 4){

                }else{
                    System.out.println("Se ha ingresado un dato erroneo");
                    //VER QUE HAGO ACA
                }

            }else if (opcion == 4) {
                System.out.println("Ingrese el DNI del usuario al que le quiere asignar una categroia:");
                Scanner doc = new Scanner(System.in);
                int documento = doc.nextInt();
                funcionesUsuario.asignarCategoriaUsuario(documento);
                
            }else if (opcion == 5) {

            }else if (opcion == 6) {

            }else if (opcion == 7) {

            }else if (opcion == 8) {

            }else if (opcion == 9) {

            }else if (opcion == 10) {

            }else{
                System.out.println("Se ingreso un dato erroneo");
                //VER QUE HAGO ACA
            }
        }

    }
}
