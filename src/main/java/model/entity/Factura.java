package model.entity;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import model.dto.ProductoFacturaDTO;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Factura {

    private int nroFactura;
    private int dni;
    private String nombreCliente;
    private List<ProductoFacturaDTO> productos;
    private String metodoPago;
    private String tipoFactura;
    private int montoTotal;
    private int montoIVA;
    private String condFactura;

    public Factura(int nroFactura, int dni, String nombreCliente, List<ProductoFacturaDTO> productos, String metodoPago, String tipoFactura, int montoTotal, int montoIVA, String condFactura) {
        this.nroFactura = nroFactura;
        this.dni = dni;
        this.nombreCliente = nombreCliente;
        this.productos = productos;
        this.metodoPago = metodoPago;
        this.tipoFactura = tipoFactura;
        this.montoTotal = montoTotal;
        this.montoIVA = montoIVA;
        this.condFactura = condFactura;
    }

    public Factura() {
    }

    public void insertarFactura() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("Facturas");

        // Convertir la instancia de Factura a un documento BSON
        Document documento = new Document("nroFactura", this.nroFactura)
                .append("dni", this.dni)
                .append("nombreCliente", this.nombreCliente)
                .append("productos", this.productos)
                .append("metodoPago", this.metodoPago)
                .append("tipoFactura", this.tipoFactura)
                .append("montoTotal", this.montoTotal)
                .append("montoIVA", this.montoIVA)
                .append("condFactura", this.condFactura);

        // Insertar el documento en la colección de MongoDB
        collection.insertOne(documento);

        // Cerrar la conexión con MongoDB
        mongoClient.close();
    }

    public static Factura obtenerFacturaPorNumero(int nroFactura) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("Facturas");

        // Realizar la consulta a MongoDB para obtener la factura por su número
        Document documentoFactura = collection.find(Filters.eq("nroFactura", nroFactura)).first();

        // Verificar si se encontró la factura
        if (documentoFactura != null) {
            // Crear una instancia de Factura y llenarla con los datos del documento
            Factura factura = new Factura();
            factura.setNroFactura(documentoFactura.getInteger("nroFactura"));
            factura.setDni(documentoFactura.getInteger("dni"));
            factura.setNombreCliente(documentoFactura.getString("nombreCliente"));
            // Llenar los demás campos de la factura según corresponda

            // Cerrar la conexión con MongoDB
            mongoClient.close();

            return factura;
        } else {
            // Cerrar la conexión con MongoDB
            mongoClient.close();

            return null;
        }
    }

    public static List<Factura> obtenerFacturasPorDNIClienteYCondicion(int dni, String condFactura) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");
        MongoCollection<Document> collection = database.getCollection("Facturas");

        // Realizar la consulta a MongoDB para obtener las facturas por número de DNI y condición de factura
        FindIterable<Document> documentosFacturas = collection.find(
                Filters.and(
                        Filters.eq("dni", dni),
                        Filters.eq("condFactura", condFactura)
                )
        );

        // Lista para almacenar las facturas encontradas
        List<Factura> facturas = new ArrayList<>();

        // Iterar sobre los documentos de las facturas y crear instancias de Factura
        for (Document documentoFactura : documentosFacturas) {
            Factura factura = new Factura();
            factura.setNroFactura(documentoFactura.getInteger("nroFactura"));
            factura.setDni(documentoFactura.getInteger("dni"));
            factura.setNombreCliente(documentoFactura.getString("nombreCliente"));
            // Llenar los demás campos de la factura según corresponda

            facturas.add(factura);
        }

        // Cerrar la conexión con MongoDB
        mongoClient.close();

        return facturas;
    }


    public int getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(int nroFactura) {
        this.nroFactura = nroFactura;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<ProductoFacturaDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoFacturaDTO> productos) {
        this.productos = productos;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getTipoFactura() {
        return tipoFactura;
    }

    public void setTipoFactura(String tipoFactura) {
        this.tipoFactura = tipoFactura;
    }

    public int getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(int montoTotal) {
        this.montoTotal = montoTotal;
    }

    public int getMontoIVA() {
        return montoIVA;
    }

    public void setMontoIVA(int montoIVA) {
        this.montoIVA = montoIVA;
    }

    public String getCondFactura() {
        return condFactura;
    }

    public void setCondFactura(String condFactura) {
        this.condFactura = condFactura;
    }
}


