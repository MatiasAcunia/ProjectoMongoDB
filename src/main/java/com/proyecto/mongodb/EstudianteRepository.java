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
        // Obtenemos los cursos existentes en la BD para asociar sus _id reales
        List<Document> listaCursos = cursos.find().into(new ArrayList<>());
        if (listaCursos.isEmpty()) {
            System.out.println("No hay cursos registrados para asociar a los estudiantes.");
            return;
        }

        // Tomamos referencias de IDs de los cursos disponibles (si hay menos de 5, se reutilizan de forma segura)
        ObjectId id1 = listaCursos.get(0).getObjectId("_id");
        ObjectId id2 = listaCursos.size() > 1 ? listaCursos.get(1).getObjectId("_id") : id1;
        ObjectId id3 = listaCursos.size() > 2 ? listaCursos.get(2).getObjectId("_id") : id1;
        ObjectId id4 = listaCursos.size() > 3 ? listaCursos.get(3).getObjectId("_id") : id1;
        ObjectId id5 = listaCursos.size() > 4 ? listaCursos.get(4).getObjectId("_id") : id1;

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
                        .append("Cursos", List.of(id1, id2, id3)),

                new Document("Nombre", "Pablo")
                        .append("Apellido", "Millon")
                        .append("Email", "pablo@gmail.com")
                        .append("Edad", 24)
                        .append("Estado", false)
                        .append("Direccion", new Document("ciudad", "Ciudad Vieja")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("Cursos", List.of(id2, id3, id4)),

                new Document("Nombre", "Francisco")
                        .append("Apellido", "Gonzalez")
                        .append("Email", "fran@gmail.com")
                        .append("Edad", 25)
                        .append("Estado", false)
                        .append("Direccion", new Document("ciudad", "Sayago")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("Cursos", List.of(id1, id2, id5)),

                new Document("Nombre", "Jorge")
                        .append("Apellido", "Galemire")
                        .append("Email", "jorge@gmail.com")
                        .append("Edad", 20)
                        .append("Estado", true)
                        .append("Direccion", new Document("ciudad", "Cordon")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("Cursos", List.of(id3, id4)),

                new Document("Nombre", "Eduardo")
                        .append("Apellido", "Mateo")
                        .append("Email", "eduardo@gmail.com")
                        .append("Edad", 22)
                        .append("Estado", true)
                        .append("Direccion", new Document("ciudad", "Pocitos")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("Cursos", List.of(id1, id2)),

                new Document("Nombre", "Gonzalo")
                        .append("Apellido", "Bergessio")
                        .append("Email", "gonzalo@gmail.com")
                        .append("Edad", 24)
                        .append("Estado", false)
                        .append("Direccion", new Document("ciudad", "Malvin")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("Cursos", List.of(id4, id5)),

                new Document("Nombre", "Jimena")
                        .append("Apellido", "Gomez")
                        .append("Email", "jimena@gmail.com")
                        .append("Edad", 23)
                        .append("Estado", true)
                        .append("Direccion", new Document("ciudad", "Goes")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("Cursos", List.of(id1, id4)),

                new Document("Nombre", "Ana")
                        .append("Apellido", "Perez")
                        .append("Email", "ana@gmail.com")
                        .append("Edad", 29)
                        .append("Estado", true)
                        .append("Direccion", new Document("ciudad", "Aguada")
                                .append("Departamento", "Montevideo"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("Cursos", List.of(id4, id5)),

                new Document("Nombre", "Camila")
                        .append("Apellido", "Rodriguez")
                        .append("Email", "camila@gmail.com")
                        .append("Edad", 21)
                        .append("Estado", true)
                        .append("Direccion", new Document("ciudad", "Las Piedras")
                                .append("Departamento", "Canelones"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("Cursos", List.of(id2, id4)),

                new Document("Nombre", "Martin")
                        .append("Apellido", "Fernandez")
                        .append("Email", "martin@gmail.com")
                        .append("Edad", 27)
                        .append("Estado", false)
                        .append("Direccion", new Document("ciudad", "Maldonado")
                                .append("Departamento", "Maldonado"))
                        .append("FechaRegistro", fechaRegistro)
                        .append("Cursos", List.of(id1, id3))
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

    // 1. Registrar un estudiante
    public void registrarEstudiante(String nombre, String apellido, String email, int edad,
                                    String ciudad, String departamento, Date fechaRegistro,
                                    List<ObjectId> cursosIds) {
        Document estudiante = new Document("_id", new ObjectId())
                .append("Nombre", nombre)
                .append("Apellido", apellido)
                .append("Email", email)
                .append("Edad", edad)
                .append("Estado", true)
                .append("Direccion", new Document("ciudad", ciudad)
                        .append("Departamento", departamento))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", cursosIds != null ? cursosIds : List.of());

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
        if (resultado.getModifiedCount() > 0) {
            System.out.println("Edad actualizada exitosamente para el correo: " + email);
        } else {
            System.out.println("No se encontró el estudiante o la edad es la misma.");
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