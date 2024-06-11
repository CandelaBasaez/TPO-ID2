package app;

import funciones.funcionesCatalogo;
import funciones.funcionesUsuario;
import model.entity.Usuario;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        CargadoDatosPrueba.InicializadoSistema();

        System.out.println("Te damos la bienvenida a la Libreria");
        System.out.println("1. Seleccionar Usuario");//hecha
        System.out.println("2. Crear usuario");//hecha
        System.out.println("3. Ver usuarios por categorias");//hecha
        System.out.println("4. Asignar usuario a una categoria");//hecha
        System.out.println("5. Ver Catalogo");//hecha
        System.out.println("6. Modificar Catalogo");//hecha
        System.out.println("7. Realizar pedido");
        System.out.println("8. Pagar factura");
        System.out.println("9. Ver Registro de Facturas");
        System.out.println("10. Ver Registro de Pagos");
        System.out.println("-1. Salir del sistema");
        System.out.print("Seleccione la acción a realizar:");

        Scanner opc = new Scanner(System.in);
        int opcion = opc.nextInt();

        while (opcion != -1){
            if (opcion == 1) {
                System.out.print("Ingrese el dni del usuario que desea buscar:");
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
                int opCat=0;
                while(opCat!=-1) {
                    System.out.println("1. Todos los usuarios de categoria TOP");
                    System.out.println("2. Todos los usuarios de categoria MEDIUM");
                    System.out.println("3. Todos los usuarios de categoria LOW");
                    System.out.println("-1. Volver al menu principal");
                    System.out.print("Ingrese el número correspondiente a la categoria que desea visualizar:");

                    Scanner cat = new Scanner(System.in);
                    int categoria = cat.nextInt();
                    opCat=categoria;

                    if (categoria == 1) {
                        funcionesUsuario.mostrarUsuariosTOP();
                    } else if (categoria == 2) {
                        funcionesUsuario.mostrarUsuariosMEDIUM();
                    } else if (categoria == 3) {
                        funcionesUsuario.mostrarUsuariosLOW();
                    } else {
                        System.out.println("Se ha ingresado un dato erroneo");
                    }
                }

            }else if (opcion == 4) {
                System.out.print("Ingrese el DNI del usuario al que le quiere asignar una categoria:");
                Scanner doc = new Scanner(System.in);
                int documento = doc.nextInt();
                funcionesUsuario.asignarCategoriaUsuario(documento);

            }else if (opcion == 5) {
                funcionesCatalogo.mostrarCatalogo();

            }else if (opcion == 6) {
                int opModCat = 0;
                while(opModCat!=-1){
                    System.out.println("1. Modificar descripcion y/o nombre de un producto");
                    System.out.println("2. Modificar precio por unidad de un producto");
                    System.out.println("-1. Volver al menu principal");
                    System.out.print("Ingrese el número correspondiente a la accion que desea realizar:");

                    if (opModCat == 1){
                        funcionesCatalogo.modificarNomDesCatalogo();
                    } else if (opModCat == 2) {
                        funcionesCatalogo.modificarPrecioCatalogo();
                    }else{
                        System.out.println("Se ha ingresado un dato erroneo");
                    }
                }

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
