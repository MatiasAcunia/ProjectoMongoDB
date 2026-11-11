package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import com.mongodb.client.model.Projections;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

public class Conexion {

    // En Java se usa 'private static final' para constantes
    private static final String URI = System.getenv("MONGODB_URI") != null
            ? System.getenv("MONGODB_URI")
            : "mongodb://localhost:27017";

    private static MongoClient client = null;

    public static MongoClient getConexion() {

        if (client != null) {
            return client;
        }

        try {

            client = MongoClients.create(URI);

            System.out.println("Conexión exitosa a MongoDB");

            return client;

        } catch (Exception e) {
            client = null;
            System.err.println("Error al conectar a MongoDB: " + e.getMessage());
            return null;
        }
    }

    public static void cerrarConexion() {
        if (client != null) {
            client.close();
            client = null;
        }
    }

    public MongoDatabase getDatabase(String db){

        return client.getDatabase(db);
    }
}