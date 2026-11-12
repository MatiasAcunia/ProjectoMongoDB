package com.proyecto.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;

public class InscripcionesService {

    private final MongoCollection<Document> estudiantes;
    private final MongoCollection<Document> cursos;

    public InscripcionesService(MongoCollection<Document> estudiantes, MongoCollection<Document> cursos) {
        this.estudiantes = estudiantes;
        this.cursos = cursos;
    }

    public void consultarCursosEstudiante(String id) {
        if (!ObjectId.isValid(id)) {
            System.out.println("ID de estudiante inválido.");
            return;
        }

        Document usuario = estudiantes.find(Filters.eq("_id", new ObjectId(id))).first();
        if (usuario == null) {
            System.out.println("No se encontró el estudiante.");
            return;
        }

        List<ObjectId> cursosUsuario = usuario.getList("Cursos", ObjectId.class);
        if (cursosUsuario == null || cursosUsuario.isEmpty()) {
            System.out.println("El estudiante no tiene cursos registrados.");
            return;
        }

        for (ObjectId idCurso : cursosUsuario) {
            Document curso = cursos.find(Filters.eq("_id", idCurso)).first();
            if (curso != null) {
                System.out.println(curso.toJson());
            }
        }
    }

    public void buscarEstudiantesPorCiudad(String ciudad) {
        estudiantes.find(Filters.eq("Direccion.ciudad", ciudad))
                .forEach(estudiante -> System.out.println(estudiante.toJson()));
    }

    public void BuscarEstudiantesPorCoincidencia(String coincidencia) {
        FindIterable<Document> estudiantesIT = estudiantes.find(Filters.regex("Apellido", coincidencia, "i"));
        for (Document estudiante : estudiantesIT) {
            System.out.println(estudiante.toJson());
        }
    }
}
