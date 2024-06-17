package app;

import funciones.*;
import model.entity.Usuario;
import redis.clients.jedis.Jedis;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        System.out.println("Ingrese su DNI para acceder al sitio: ");
        System.out.println("Si no tiene una cuenta ingrese 0 para crear un usuario:");
        Scanner iden = new Scanner(System.in);
        int dni = iden.nextInt();
        String permiso = funcionesUsuario.identificarUser(dni);
        if (permiso == "Administrador"){
            System.out.println("Te damos la bienvenida a la Libreria");
            System.out.println("1. Seleccionar Usuario");
            System.out.println("2. Ver usuarios por categorias");
            System.out.println("3. Asignar usuario a una categoria");
            System.out.println("4. Ver Catalogo");
            System.out.println("5. Modificar Catalogo");
            System.out.println("6. Ver Registro de Facturas");
            System.out.println("7. Ver Registro de Pagos");
            System.out.println("8. Ver Registro de Cambios en el Catalogo");
            System.out.println("-1. Salir del sistema");
            System.out.print("Seleccione la acción a realizar: ");

            Scanner opc = new Scanner(System.in);
            int opcion = opc.nextInt();

            while (opcion != -1) {
                if (opcion == 1) {
                    System.out.print("Ingrese el dni del usuario que desea buscar: ");
                    Scanner documento = new Scanner(System.in);
                    int dniUser = documento.nextInt();
                    Usuario usuario = Usuario.cargarDesdeRedis(dniUser);
                    System.out.println(usuario);

                } else if (opcion == 2) {
                    int opCat = 0;
                    while (opCat != -1) {
                        System.out.println("1. Todos los usuarios de categoria TOP");
                        System.out.println("2. Todos los usuarios de categoria MEDIUM");
                        System.out.println("3. Todos los usuarios de categoria LOW");
                        System.out.println("-1. Volver al menu principal");
                        System.out.print("Ingrese el número correspondiente a la categoria que desea visualizar: ");

                        Scanner cat = new Scanner(System.in);
                        int categoria = cat.nextInt();
                        opCat = categoria;

                        if (categoria == 1) {
                            funcionesUsuario.mostrarUsuariosTOP();
                            System.out.println("--------------------------------");
                        } else if (categoria == 2) {
                            funcionesUsuario.mostrarUsuariosMEDIUM();
                            System.out.println("--------------------------------");
                        } else if (categoria == 3) {
                            funcionesUsuario.mostrarUsuariosLOW();
                            System.out.println("--------------------------------");
                        } else {
                            System.out.println("Se ha ingresado un dato erroneo");
                        }
                    }

                } else if (opcion == 3) {
                    System.out.print("Ingrese el DNI del usuario al que le quiere asignar una categoria: ");
                    Scanner doc = new Scanner(System.in);
                    int documento = doc.nextInt();
                    funcionesUsuario.asignarCategoriaUsuario(documento);

                } else if (opcion == 4) {
                    funcionesCatalogo.mostrarCatalogo();

                } else if (opcion == 5) {
                    int opModCat = 0;
                    while (opModCat != -1) {
                        System.out.println("1. Modificar nombre de un producto");
                        System.out.println("2. Modificar descripcion de un producto");
                        System.out.println("3. Modificar precio por unidad de un producto");
                        System.out.println("4. Agregar producto");
                        System.out.println("5. Eliminar producto");
                        System.out.println("-1. Volver al menu principal");
                        System.out.print("Ingrese el número correspondiente a la accion que desea realizar: ");
                        Scanner opCt = new Scanner(System.in);
                        opModCat = opCt.nextInt();

                        if (opModCat == 1) {
                            funcionesCatalogo.modificarNombreCatalogo();
                        } else if (opModCat == 2) {
                            funcionesCatalogo.modificarDescCatalogo();
                        } else if (opModCat == 3) {
                            funcionesCatalogo.modificarPrecioCatalogo();
                        } else if (opModCat == 4) {
                            funcionesCatalogo.agregarProducto();
                        } else if (opModCat == 5) {
                            funcionesCatalogo.eliminarProducto();
                        } else {
                            System.out.println("Se ha ingresado un dato erroneo");
                        }
                    }
                }else if (opcion == 6) {
                    System.out.print("Ingrese el DNI para buscar facturas asociadas: ");
                    Scanner doc = new Scanner(System.in);
                    int documento = doc.nextInt();
                    funcionesFacturas.mostrarFacturas(documento);

                } else if (opcion == 7) {
                    funcionesPagos.mostrarPagos();

                } else if (opcion == 8) {
                    funcionesCatalogo.mostrarCambiosCatalogo();
                } else {
                    System.out.println("Se ingreso un dato erroneo");
                }
                    System.out.println("");
                    System.out.println("--------------------------------");
                    System.out.println("Te damos la bienvenida a la Libreria");
                    System.out.println("1. Seleccionar Usuario");
                    System.out.println("2. Ver usuarios por categorias");
                    System.out.println("3. Asignar usuario a una categoria");
                    System.out.println("4. Ver Catalogo");
                    System.out.println("5. Modificar Catalogo");
                    System.out.println("6. Ver Registro de Facturas");
                    System.out.println("7. Ver Registro de Pagos");
                    System.out.println("8. Ver Registro de Cambios en el Catalogo");
                    System.out.println("-1. Salir del sistema");
                    System.out.print("Seleccione la acción a realizar: ");

                    opcion = opc.nextInt();
                }
        }else if (permiso == "Cliente"){
            System.out.println("Te damos la bienvenida a la Libreria");
            System.out.println("1. Ver Catalogo");
            System.out.println("2. Modificar Carrito");
            System.out.println("3. Pagar factura");
            System.out.println("4. Ver Registro de Facturas");
            System.out.println("5. Ver Registro de Pagos");
            System.out.println("-1. Salir del sistema");
            System.out.print("Seleccione la acción a realizar: ");

            Scanner opc = new Scanner(System.in);
            int opcion = opc.nextInt();

            if (opcion == 1) {
                funcionesCatalogo.mostrarCatalogo();
            }else if (opcion == 2) {
                System.out.print("Ingrese su DNI para modificar su carrito: ");
                Scanner docu = new Scanner(System.in);
                int denei = docu.nextInt();
                System.out.println("1. Agregar producto");
                System.out.println("2. Modificar cantidad de un producto");
                System.out.println("3. Eliminar producto");
                System.out.println("0. Hacer pedido");
                System.out.println("-1. Volver al menu principal");
                System.out.print("Ingrese el número correspondiente a la accion que desea realizar: ");
                Scanner selec = new Scanner(System.in);
                int seleccion = selec.nextInt();
                while(seleccion!=-1) {
                    if (seleccion == 1) {
                        funcionesPedidos.agregarAlCarrito(denei);
                    } else if (seleccion == 2) {
                        funcionesPedidos.modificarCant(denei);
                    } else if (seleccion == 3) {
                        funcionesPedidos.eliminarDelCarrito(denei);
                    } else if(seleccion == 0){
                        funcionesFacturas.crearFactura(denei);
                    }else{
                        System.out.println("El valor ingresado no es valido");
                    }
                    funcionesPedidos.mostrarCarrito(denei);
                    System.out.print("Ingrese el número correspondiente a la accion que desea realizar: ");
                    seleccion = selec.nextInt();
                }

            }else if (opcion == 3) {
                System.out.print("Ingrese el DNI para buscar facturas asociadas: ");
                Scanner doc = new Scanner(System.in);
                int documento = doc.nextInt();
                funcionesFacturas.mostrarFacturas(documento);
                System.out.print("Ingrese el numero de la factura que desea pagar: ");
                Scanner nF= new Scanner(System.in);
                int numF = nF.nextInt();
                funcionesFacturas.pagarFactura(numF);
                funcionesPagos.registrarPagoFactura(numF,dni);

            }else if (opcion == 4) {
                funcionesFacturas.mostrarFacturas(dni);

            }else if (opcion == 5) {
                funcionesPagos.mostrarPagosPorUsuario(dni);

            }
        }else if (permiso == null){
            Scanner document = new Scanner(System.in);
            Scanner name = new Scanner(System.in);
            Scanner surname = new Scanner(System.in);
            Scanner address = new Scanner(System.in);
            Scanner condIva = new Scanner(System.in);
            System.out.println("Ingrese el DNI: ");
            int dniUser = document.nextInt();
            System.out.println("Ingrese el nombre: ");
            String nombre = name.nextLine();
            System.out.println("Ingrese el apellido: ");
            String apellido = surname.nextLine();
            System.out.println("Ingrese la direccion: ");
            String direccion = address.nextLine();
            System.out.println("Ingrese la condicion con respecto al IVA: ");
            String condIVA = condIva.nextLine();
            String permisos = "Cliente";

            Usuario usuario = funcionesUsuario.crearUsuario(dniUser, nombre, apellido, condIVA, direccion,permisos);
        }else{
            System.out.println("El usuario con ese DNI no existe");
        }

}}
