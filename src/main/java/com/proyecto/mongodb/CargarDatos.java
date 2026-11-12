package com.proyecto.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class CargarDatos {

    public static void main(String[] args) {
        MongoClient cliente = Conexion.getConexion();
        if (cliente == null) {
            System.err.println("No se pudo inicializar el cliente MongoDB.");
            System.exit(1);
            return;
        }

        String nombreBase = System.getenv("MONGODB_DATABASE");
        if (nombreBase == null || nombreBase.isBlank()) {
            nombreBase = "gestion_academica";
        }

        try {
            MongoDatabase base = cliente.getDatabase(nombreBase);
            MongoCollection<Document> cursos = base.getCollection("cursos");
            MongoCollection<Document> estudiantes = base.getCollection("estudiantes");

            new CursoRepository(cursos).defecto();
            new EstudianteRepository(estudiantes, cursos).cargarDatosPorDefecto();

            System.out.println("Base: " + nombreBase);
            System.out.println("Cursos: " + cursos.countDocuments());
            System.out.println("Estudiantes: " + estudiantes.countDocuments());
        } catch (Exception error) {
            System.err.println("Error cargando datos: " + error.getMessage());
            System.err.println("No se completó la carga inicial.");
        } finally {
            Conexion.cerrarConexion();
        }
    }
}
