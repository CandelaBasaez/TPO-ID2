import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;

public class CargadoDatosPrueba {

    public static void InicializadoSistema() {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("BD2_Mongo");

        // Verificar si la colección de facturas ya existe
        if (!database.listCollectionNames().into(new ArrayList<>()).contains("Facturas")) {
            // Si no existe, crear la colección
            database.createCollection("Facturas");
        }

        if (!database.listCollectionNames().into(new ArrayList<>()).contains("Pagos")) {
            // Si no existe, crear la colección
            database.createCollection("Pagos");
        }

        if (!database.listCollectionNames().into(new ArrayList<>()).contains("CambiosCatalogo")) {
            // Si no existe, crear la colección
            database.createCollection("CambiosCatalogo");
        }

        // Cerrar la conexión con MongoDB
        mongoClient.close();
    }
}
