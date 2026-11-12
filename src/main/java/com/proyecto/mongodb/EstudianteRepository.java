package com.proyecto.mongodb;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class EstudianteRepository {

    private MongoCollection<Document> estudiantes;
    private MongoCollection<Document> cursos;


    public EstudianteRepository(MongoCollection<Document> estudiantes, MongoCollection<Document> cursos) {
        this.estudiantes = estudiantes;
        this.cursos = cursos;
    }


    public void cargarDatosPorDefecto() {
        migrarCursosInscriptos();

        for (String codigo : List.of("CUR01", "CUR02", "CUR03", "CUR04", "CUR05")) {
            if (cursos.find(Filters.eq("Codigo", codigo)).first() == null) {
                System.out.println("No hay cursos registrados para asociar a los estudiantes.");
                return;
            }
        }

        Date fechaRegistro = new Date();

        List<Document> estudiantesL = List.of(
                new Document("Nombre", "Lucas")
                        .append("Apellido", "Marota")
                        .append("Email", "lucas@gmail.com")
                        .append("Edad", 22)
                        .append("Estado", true)
                        .append("Direccion", new Document("ciudad", "Punta Carretas")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("cursosInscriptos", List.of("CUR01", "CUR02", "CUR03")),

                new Document("Nombre", "Pablo")
                        .append("Apellido", "Millon")
                        .append("Email", "pablo@gmail.com")
                        .append("Edad", 24)
                        .append("Estado", false)
                        .append("Direccion", new Document("ciudad", "Ciudad Vieja")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("cursosInscriptos", List.of("CUR02", "CUR03", "CUR04")),

                new Document("Nombre", "Francisco")
                        .append("Apellido", "Gonzalez")
                        .append("Email", "fran@gmail.com")
                        .append("Edad", 25)
                        .append("Estado", false)
                        .append("Direccion", new Document("ciudad", "Sayago")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("cursosInscriptos", List.of("CUR01", "CUR02", "CUR05")),

                new Document("Nombre", "Jorge")
                        .append("Apellido", "Galemire")
                        .append("Email", "jorge@gmail.com")
                        .append("Edad", 20)
                        .append("Estado", true)
                        .append("Direccion", new Document("ciudad", "Cordon")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("cursosInscriptos", List.of("CUR03", "CUR04")),

                new Document("Nombre", "Eduardo")
                        .append("Apellido", "Mateo")
                        .append("Email", "eduardo@gmail.com")
                        .append("Edad", 22)
                        .append("Estado", true)
                        .append("Direccion", new Document("ciudad", "Pocitos")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("cursosInscriptos", List.of("CUR01", "CUR02")),

                new Document("Nombre", "Gonzalo")
                        .append("Apellido", "Bergessio")
                        .append("Email", "gonzalo@gmail.com")
                        .append("Edad", 24)
                        .append("Estado", false)
                        .append("Direccion", new Document("ciudad", "Malvin")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("cursosInscriptos", List.of("CUR04", "CUR05")),

                new Document("Nombre", "Jimena")
                        .append("Apellido", "Gomez")
                        .append("Email", "jimena@gmail.com")
                        .append("Edad", 23)
                        .append("Estado", true)
                        .append("Direccion", new Document("ciudad", "Goes")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("cursosInscriptos", List.of("CUR01", "CUR04")),

                new Document("Nombre", "Ana")
                        .append("Apellido", "Perez")
                        .append("Email", "ana@gmail.com")
                        .append("Edad", 29)
                        .append("Estado", true)
                        .append("Direccion", new Document("ciudad", "Aguada")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("cursosInscriptos", List.of("CUR04", "CUR05")),

                new Document("Nombre", "Camila")
                        .append("Apellido", "Rodriguez")
                        .append("Email", "camila@gmail.com")
                        .append("Edad", 21)
                        .append("Estado", true)
                        .append("Direccion", new Document("ciudad", "Las Piedras")
                                .append("Departamento", "Canelones"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("cursosInscriptos", List.of("CUR02", "CUR04")),

                new Document("Nombre", "Martin")
                        .append("Apellido", "Fernandez")
                        .append("Email", "martin@gmail.com")
                        .append("Edad", 27)
                        .append("Estado", false)
                        .append("Direccion", new Document("ciudad", "Maldonado")
                                .append("Departamento", "Maldonado"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("cursosInscriptos", List.of("CUR01", "CUR03"))
        );

        int insertados = 0;
        for (Document estudiante : estudiantesL) {
            String email = estudiante.getString("Email");
            if (estudiantes.find(Filters.eq("Email", email)).first() == null) {
                estudiantes.insertOne(estudiante);
                insertados++;
            }
        }
        System.out.println("Cantidad de estudiantes insertados: " + insertados);
    }

    public void migrarCursosInscriptos() {
        for (Document estudiante : estudiantes.find(Filters.exists("Cursos"))) {
            List<?> referencias = estudiante.getList("Cursos", Object.class);
            if (referencias == null) {
                continue;
            }

            List<String> codigos = new ArrayList<>();
            if (estudiante.containsKey("cursosInscriptos")) {
                List<String> existentes = estudiante.getList("cursosInscriptos", String.class);
                if (existentes != null) {
                    codigos.addAll(existentes);
                }
            }

            boolean completo = true;
            for (Object referencia : referencias) {
                if (!(referencia instanceof ObjectId id)) {
                    completo = false;
                    break;
                }

                Document curso = cursos.find(Filters.eq("_id", id)).first();
                if (curso == null || curso.getString("Codigo") == null) {
                    completo = false;
                    break;
                }

                String codigo = curso.getString("Codigo");
                if (!codigos.contains(codigo)) {
                    codigos.add(codigo);
                }
            }

            if (completo) {
                estudiantes.updateOne(
                        Filters.eq("_id", estudiante.get("_id")),
                        Updates.combine(
                                Updates.set("cursosInscriptos", codigos),
                                Updates.unset("Cursos")
                        )
                );
            }
        }
    }

    // 1. Registrar un estudiante
    public void registrarEstudiante(String nombre, String apellido, String email, int edad,
                                    String ciudad, String departamento, Date fechaRegistro,
                                    List<String> cursosCodigos) {
        Document estudiante = new Document("_id", new ObjectId())
                .append("Nombre", nombre)
                .append("Apellido", apellido)
                .append("Email", email)
                .append("Edad", edad)
                .append("Estado", true)
                .append("Direccion", new Document("ciudad", ciudad)
                        .append("Departamento", departamento))
                .append("FechaRegistro", fechaRegistro)
                .append("cursosInscriptos", cursosCodigos != null ? cursosCodigos : List.of());

        InsertOneResult resultado = estudiantes.insertOne(estudiante);
        System.out.println("Estudiante registrado con ID: " + resultado.getInsertedId());
    }

    // 2. Listar todos los estudiantes
    public void listarEstudiantes() {
        try (MongoCursor<Document> cursor = estudiantes.find().iterator()) {
            while (cursor.hasNext()) {
                Document estudiante = cursor.next();
                System.out.println(estudiante.toJson());
            }
        }
    }

    // 3. Buscar un estudiante por correo electrónico
    public Document buscarEstudiantePorEmail(String email) {
        Bson filtro = Filters.eq("Email", email);
        return estudiantes.find(filtro).first();
    }

    // 4. Modificar la edad de un estudiante (buscando por correo electrónico)
    public void modificarEdadEstudiante(String email, int nuevaEdad) {
        Bson filtro = Filters.eq("Email", email);
        Bson actualizacion = Updates.set("Edad", nuevaEdad);

        var resultado = estudiantes.updateOne(filtro, actualizacion);
        if (resultado.getMatchedCount() == 0) {
            System.out.println("No se encontró el estudiante.");
        } else if (resultado.getModifiedCount() == 0) {
            System.out.println("El estudiante ya tiene esa edad.");
        } else {
            System.out.println("Edad actualizada correctamente.");
        }
    }

    // 5. Eliminar un estudiante por correo electrónico, previa confirmación
    public void eliminarEstudiante(String email, boolean confirmacion) {
        if (!confirmacion) {
            System.out.println("Operación cancelada: Se requiere confirmación para eliminar al estudiante.");
            return;
        }

        Bson filtro = Filters.eq("Email", email);
        DeleteResult resultado = estudiantes.deleteOne(filtro);

        if (resultado.getDeletedCount() > 0) {
            System.out.println("Estudiante con correo " + email + " eliminado correctamente.");
        } else {
            System.out.println("No se encontró ningún estudiante registrado con ese correo electrónico.");
        }
    }
}