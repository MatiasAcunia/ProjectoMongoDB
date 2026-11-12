package com.proyecto.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class Conexion {

    private static MongoClient client;

    public static MongoClient getConexion() {
        if (client != null) {
            return client;
        }

        String uri = System.getenv("MONGODB_URI");
        if (uri == null || uri.isBlank()) {
            uri = "mongodb://localhost:27017";
        }

        try {
            client = MongoClients.create(uri);
            client.getDatabase("admin").runCommand(new Document("ping", 1));
            return client;
        } catch (Exception e) {
            cerrarConexion();
            System.err.println("Error al conectar a MongoDB: " + e.getMessage());
            return null;
        }
    }

    public static MongoDatabase getDatabase(String nombre) {
        MongoClient conexion = getConexion();
        return conexion == null ? null : conexion.getDatabase(nombre);
    }

    public static void cerrarConexion() {
        if (client != null) {
            client.close();
            client = null;
        }
    }
}
