import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.*;
import java.util.*;


    //accedo a curso_nosql
    MongoDatabase database =
    mongoClient.getDatabase("curso_nosql");

    List<String> existentes = database.listCollectionNames().into(new ArrayList<>());

    if (!existentes.contains("estudiantes_java")) {
        database.createCollection("estudiantes_java");
        System.out.println("Colección estudiantes_java creada");
    }

    MongoCollection<Document> estudiantes = database.getCollection("estudiantes_java");




List<Document> estudiantesL = List.of(

        new Document("Nombre", "Lucas")
                .append("Apellido", "Marota")
                .append("Email", "lucas@gmail.com")
                .append("Edad", 22)
                .append("Estado", true)
                .append("Direccion", new Document("ciudad", "Punta Carretas")
                        .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso1.getObjectId("_id"),
                        curso2.getObjectId("_id"),
                        curso3.getObjectId("_id"))),

        new Document("Nombre", "Pablo")
                .append("Apellido", "Millon")
                .append("Email", "pablo@gmail.com")
                .append("Edad", 24)
                .append("Estado", false)
                .append("Direccion", new Document("ciudad", "Ciudad Vieja")
                        .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso2.getObjectId("_id"),
                        curso3.getObjectId("_id"),
                        curso4.getObjectId("_id"))),

        new Document("Nombre", "Francisco")
                .append("Apellido", "Gonzalez")
                .append("Email", "fran@gmail.com")
                .append("Edad", 25)
                .append("Estado", false)
                .append("Direccion", new Document("ciudad", "Sayago")
                        .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso1.getObjectId("_id"),
                        curso2.getObjectId("_id"),
                        curso5.getObjectId("_id"))),

        new Document("Nombre", "Jorge")
                .append("Apellido", "Galemire")
                .append("Email", "jorge@gmail.com")
                .append("Edad", 20)
                .append("Estado", true)
                .append("Direccion", new Document("ciudad", "Cordon")
                        .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso3.getObjectId("_id"),
                        curso4.getObjectId("_id"))),

        new Document("Nombre", "Eduardo")
                .append("Apellido", "Mateo")
                .append("Email", "eduardo@gmail.com")
                .append("Edad", 22)
                .append("Estado", true)
                .append("Direccion", new Document("ciudad", "Pocitos")
                        .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso1.getObjectId("_id"),
                        curso2.getObjectId("_id"))),

        new Document("Nombre", "Gonzalo")
                .append("Apellido", "Bergessio")
                .append("Email", "gonzalo@gmail.com")
                .append("Edad", 24)
                .append("Estado", false)
                .append("Direccion", new Document("ciudad", "Malvin")
                        .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso4.getObjectId("_id"),
                        curso5.getObjectId("_id"))),

        new Document("Nombre", "Jimena")
                .append("Apellido", "Gomez")
                .append("Email", "jimena@gmail.com")
                .append("Edad", 23)
                .append("Estado", true)
                .append("Direccion", new Document("ciudad", "Goes")
                        .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso1.getObjectId("_id"),
                        curso4.getObjectId("_id"))),


        new Document("Nombre", "Ana")
                .append("Apellido", "Perez")
                .append("Email", "ana@gmail.com")
                .append("Edad", 29)
                .append("Estado", true)
                .append("Direccion", new Document("ciudad", "Aguada")
                        .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso4.getObjectId("_id"),
                        curso5.getObjectId("_id"))),

);


    if(estudiantes.countDocuments() == 0){
        //inserto la lista estudiantesL en estudiantes y almaceno la variable insertResultado
        InsertManyResult insertResultado =
        estudiantes.insertMany(estudiantesL);

        System.out.println(
            "Cantidad insertada: "
                    + insertResultado.getInsertedIds().size()
        );
    }else{
        System.out.println("Los estudiantes ya estaban cargados");
    }
