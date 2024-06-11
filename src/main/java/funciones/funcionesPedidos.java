package funciones;

import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import scala.reflect.internal.pickling.UnPickler;

import java.util.Scanner;

public class funcionesPedidos {
    public static void agregarAlCarrito(int dni){
        try (Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "contraCande"))) {
            try (Session session = driver.session()) {
                Result result = session.run("MATCH (n:Producto) RETURN n");
                while (result.hasNext()) {
                    Record record = result.next();
                    System.out.println("--------------------------------------------------------");
                    System.out.println("Producto: "+record.get("n").get("nombre").asString());
                    System.out.println("Descripcion: "+record.get("n").get("descripcion").asString());
                    System.out.println("Precio por unidad: "+record.get("n").get("precioUnitario").asString());
                    System.out.println("--------------------------------------------------------");
                }
                System.out.print("Ingrese el nombre del producto que desea agregar: ");
                Scanner product = new Scanner(System.in);
                String producto = product.nextLine();
                System.out.print("Ingrese la cantidad del producto que desea agregar: ");
                Scanner cant = new Scanner(System.in);
                int cantidad = cant.nextInt();
                Result i = session.run("MATCH (c:Carrito{userDNI:"+dni+"})-[r]->(p:Producto{nombre:"+producto+"}) RETURN r");
                if (i!=null){
                    session.run("MATCH (c:Carrito{userDNI:"+dni+"}),(p:Producto{nombre:"+producto+"}) CREATE (c)-[:TIENE{cant:"+cantidad+"}]->(p)");
                }else{
                    System.out.print("El producto ya se encuentra en el carrito. Si desea modificar la cantidad ingrese 0, si no desea modificar la cantidad ingrese -1: ");
                    Scanner opc = new Scanner(System.in);
                    int opcion = opc.nextInt();
                    if (opcion == 0){
                        session.run("MATCH (c:Carrito{userDNI:"+dni+"})-[r]->(p:Producto{nombre:"+producto+"}) set r.cant="+cantidad);
                    }else if(opcion==-1){
                        session.run("MATCH (c:Carrito{userDNI:"+dni+"})-[r]->(p:Producto{nombre:"+producto+"}) return r");
                    }else{
                        System.out.println("Se ha ingresado un valor erroneo");
                    }
                }
            }
        }
    }
}
