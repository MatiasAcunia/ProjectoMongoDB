package com.proyecto.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.Date;
import java.util.List;

public class InscripcionesService {

    private final MongoCollection<Document> estudiantes;
    private final MongoCollection<Document> cursos;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    public InscripcionesService() {
        this(abrirBase());
    }

    private InscripcionesService(MongoDatabase db) {
        this(db.getCollection("estudiantes"), db.getCollection("cursos"));
    }

    private static MongoDatabase abrirBase() {
        String nombre = System.getenv("MONGODB_DATABASE");
        if (nombre == null || nombre.isBlank()) {
            nombre = "gestion_academica";
        }

        MongoDatabase db = Conexion.getDatabase(nombre);
        if (db == null) {
            throw new IllegalStateException("No se pudo conectar a MongoDB.");
        }
        return db;
    }

    public InscripcionesService(MongoCollection<Document> estudiantes, MongoCollection<Document> cursos) {
        this.estudiantes = estudiantes;
        this.cursos = cursos;
        this.estudianteRepository = new EstudianteRepository(estudiantes, cursos);
        this.cursoRepository = new CursoRepository(cursos);
    }

    public void cargarDatosPorDefecto() {
        cursoRepository.defecto();
        estudianteRepository.cargarDatosPorDefecto();
    }

    public void cerrarConexion() {
        Conexion.cerrarConexion();
    }

    public void inscribirEstudiante(String email, String codigo) {
        Document estudiante = estudianteRepository.buscarEstudiantePorEmail(email);
        if (estudiante == null) {
            System.out.println("No se encontró el estudiante.");
            return;
        }

        Document curso = cursoRepository.buscarCurso(codigo);
        if (curso == null) {
            System.out.println("No se encontró el curso.");
            return;
        }

        var resultado = estudiantes.updateOne(
                Filters.eq("_id", estudiante.get("_id")),
                Updates.addToSet("cursosInscriptos", curso.getString("Codigo"))
        );

        if (resultado.getModifiedCount() == 0) {
            System.out.println("El estudiante ya está inscrito en ese curso.");
        } else {
            System.out.println("Estudiante inscrito correctamente.");
        }
    }

    public void consultarCursosEstudiante(String email) {
        Document estudiante = estudianteRepository.buscarEstudiantePorEmail(email);
        if (estudiante == null) {
            System.out.println("No se encontró el estudiante.");
            return;
        }

        List<String> codigos = estudiante.getList("cursosInscriptos", String.class);
        if (codigos == null || codigos.isEmpty()) {
            System.out.println("El estudiante no tiene cursos registrados.");
            return;
        }

        for (String codigo : codigos) {
            Document curso = cursoRepository.buscarCurso(codigo);
            if (curso != null) {
                System.out.println(curso.toJson());
            }
        }
    }

    public void registrarEstudiante(String nombre, String apellido, String email, int edad,
                                   String ciudad, String departamento) {
        estudianteRepository.registrarEstudiante(nombre, apellido, email, edad,
                ciudad, departamento, new Date(), List.of());
    }

    public void listarEstudiantes() {
        estudianteRepository.listarEstudiantes();
    }

    public void buscarEstudiantePorEmail(String email) {
        Document estudiante = estudianteRepository.buscarEstudiantePorEmail(email);
        System.out.println(estudiante == null ? "No se encontró el estudiante." : estudiante.toJson());
    }

    public void modificarEdadEstudiante(String email, int edad) {
        estudianteRepository.modificarEdadEstudiante(email, edad);
    }

    public void eliminarEstudiante(String email, boolean confirmacion) {
        estudianteRepository.eliminarEstudiante(email, confirmacion);
    }

    public void registrarCurso(String codigo, String nombre, String descripcion,
                              int creditos, int cupo, Date fechaInicio) {
        cursoRepository.registrarCurso(codigo, nombre, descripcion, creditos, cupo, fechaInicio);
    }

    public void listarCursos() {
        cursoRepository.listarCursos();
    }

    public void buscarCurso(String codigo) {
        Document curso = cursoRepository.buscarCurso(codigo);
        System.out.println(curso == null ? "No se encontró el curso." : curso.toJson());
    }

    public void modificarCreditos(String codigo, int creditos) {
        cursoRepository.modificarCreditos(codigo, creditos);
    }

    public void buscarMayoresDe(int edad) {
        estudianteRepository.buscarMayoresDe(edad);
    }

    public void buscarEstudiantesPorCiudad(String ciudad) {
        estudianteRepository.buscarEstudiantesPorCiudad(ciudad);
    }

    public void buscarEstudiantesPorApellido(String apellido) {
        estudianteRepository.buscarEstudiantesPorApellido(apellido);
    }
}
