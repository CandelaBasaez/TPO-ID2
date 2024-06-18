package funciones;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import scala.reflect.internal.pickling.UnPickler;

import java.util.Map;
import java.util.Scanner;

import static scala.Predef.StringFormat;

public class funcionesPedidos {
    public static void agregarAlCarrito(int dni) {
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                Result result = session.run("MATCH (n:Producto) RETURN n");
                while (result.hasNext()) {
                    Record record = result.next();
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Codigo del Producto: " + record.get("n").get("codigoProducto"));
                    System.out.println("Producto: " + record.get("n").get("nombre").asString());
                    System.out.println("Descripcion: " + record.get("n").get("descripcion").asString());
                    System.out.println("Precio por unidad: " + record.get("n").get("precioUnitario").asInt());
                    System.out.println("--------------------------------------------------------");
                }
                System.out.print("Ingrese el codigo del producto que desea agregar: ");
                Scanner product = new Scanner(System.in);
                int producto = product.nextInt();
                System.out.print("Ingrese la cantidad del producto que desea agregar: ");
                Scanner cant = new Scanner(System.in);
                int cantidad = cant.nextInt();
                Result i = session.run("MATCH (c:Carrito{userDNI:"+dni+"})-[r]->(p:Producto{codigoProducto:"+producto+"}) RETURN r");
                if (i != null) {
                    session.run("MATCH (c:Carrito{userDNI:" + dni + "}),(p:Producto{codigoProducto:"+producto+"}) CREATE (c)-[:TIENE{cant:" + cantidad + "}]->(p)");
                } else {
                    System.out.print("El producto ya se encuentra en el carrito. Si desea modificar la cantidad ingrese 0, si no desea modificar la cantidad ingrese -1: ");
                    Scanner opc = new Scanner(System.in);
                    int opcion = opc.nextInt();
                    if (opcion == 0) {
                        session.run("MATCH (c:Carrito{userDNI:" + dni + "})-[r]->(p:Producto{codigoProducto:"+producto+"}) set r.cant=" + cantidad);
                    } else if (opcion == -1) {
                        session.run("MATCH (c:Carrito{userDNI:" + dni + "})-[r]->(p:Producto{codigoProducto:"+producto+"}) return r");
                    } else {
                        System.out.println("Se ha ingresado un valor erroneo");
                    }
                }
            }
        }
    }

    public static void eliminarDelCarrito(int dni) {
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                Result result = session.run("MATCH (c:Carrito{userDNI:" + dni + "})-[:TIENE]->(p:Producto) return p");
                while (result.hasNext()) {
                    Record record = result.next();
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Codigo del producto: " + record.get("p").get("codigoProducto").asInt());
                    System.out.println("Producto: " + record.get("p").get("nombre").asString());
                    System.out.println("Precio por unidad: " + record.get("p").get("precioUnitario").asInt());
                    System.out.println("--------------------------------------------------------");
                }
                System.out.println("Ingrese el codigo del producto que desea eliminar: ");
                Scanner delete = new Scanner(System.in);
                int eliminado = delete.nextInt();
                session.run("MATCH (c:Carrito{userDNI:" + dni + "})-[r:TIENE]->(p:Producto{codigoProducto:" + eliminado + "}) DELETE r");
            }
        }
    }

    public static void modificarCant(int dni) {
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                Result result = session.run("MATCH (c:Carrito{userDNI:" + dni + "})-[r:TIENE]->(p:Producto) return p");
                while (result.hasNext()) {
                    Record record = result.next();
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Codigo del producto: " + record.get("p").get("codigoProducto").asInt());
                    System.out.println("Producto: " + record.get("p").get("nombre").asString());
                    System.out.println("Precio por unidad: " + record.get("p").get("precioUnitario").asInt());
                    System.out.println("--------------------------------------------------------");
                }
                System.out.println("Ingrese el codigo del producto cuya cantidad desea cambiar: ");
                Scanner mod = new Scanner(System.in);
                int modif = mod.nextInt();
                System.out.println("Ingrese la nueva cantidad: ");
                Scanner can = new Scanner(System.in);
                int canti = can.nextInt();

                if (canti == 0) {
                    session.run("MATCH (c:Carrito{userDNI:" + dni + "})-[r:TIENE]->(p:Producto{codigoProducto:" + modif + "}) DELETE r");
                } else if (canti > 0) {
                    session.run("MATCH (c:Carrito{userDNI:" + dni + "})-[r:TIENE]->(p:Producto{codigoProducto:" + modif + "}) set r.cant=" + canti);
                } else {
                    System.out.println("Se ha ingresado una cantidad que no es valida");
                }
            }
        }
    }

    public static void mostrarCarrito(int dni){
        System.out.println("");
        System.out.println("--------------------------------");
        System.out.println("CARRITO");
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                Result result = session.run("MATCH (c:Carrito{userDNI:"+ dni +"})-[r:TIENE]->(p:Producto) return p");
                while (result.hasNext()) {
                    Record record = result.next();
                    int codProd = record.get("p").get("codigoProducto").asInt();
                    int precProd = record.get("p").get("precioUnitario").asInt();
                    Result resultado = session.run("MATCH (c:Carrito{userDNI:"+ dni +"})-[r:TIENE]->(p:Producto{codigoProducto:"+codProd+"}) return r");
                    Record dato = resultado.next();
                    int cantProd = dato.get("r").get("cant").asInt();
                    System.out.println("--------------------------------");
                    System.out.println("Codigo del Producto: "+record.get("p").get("codigoProducto").asInt());
                    System.out.println("Producto: "+record.get("p").get("nombre").asString());
                    System.out.println("Descripcion: "+record.get("p").get("descripcion").asString());
                    System.out.println("Precio por unidad: "+precProd);
                    System.out.println("Cantidad: "+cantProd);
                    int suma = precProd*cantProd;
                    System.out.println("Monto total: "+suma);
                    System.out.println("--------------------------------");
                }
            }
        }
    }
}
