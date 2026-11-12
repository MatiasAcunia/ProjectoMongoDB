package com.proyecto.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.bson.conversions.Bson;

import java.util.Date;

//accedo a curso_nosql
class CursoRepository {

    private MongoCollection<Document> cursos;

    // Constructor para inicializar la colección (ejemplo)
    public CursoRepository(MongoCollection<Document> cursos) {
        this.cursos = cursos;
    }

    public void defecto() {
        //creo los documentos reservando memoria
        Document curso1 = new Document("_id", new ObjectId())
                .append("Codigo", "CUR01")
                .append("Nombre", "Programación Java")
                .append("Descripcion", "Programacion orientada a objetos y clases")
                .append("CantCreditos", 11)
                .append("CuposMax", 30)
                .append("Estado", true)
                .append("FechaInicio", new Date());

        Document curso2 = new Document("_id", new ObjectId())
                .append("Codigo", "CUR02")
                .append("Nombre", "EDA")
                .append("Descripcion", "Estructura de datos")
                .append("CantCreditos", 15)
                .append("CuposMax", 35)
                .append("Estado", true)
                .append("FechaInicio", new Date());

        Document curso3 = new Document("_id", new ObjectId())
                .append("Codigo", "CUR03")
                .append("Nombre", "Base de Datos 1")
                .append("Descripcion", "Diseño de e implementacion de Base de datos relacionales")
                .append("CantCreditos", 10)
                .append("CuposMax", 29)
                .append("Estado", true)
                .append("FechaInicio", new Date());

        Document curso4 = new Document("_id", new ObjectId())
                .append("Codigo", "CUR04")
                .append("Nombre", "Base de Datos 2")
                .append("Descripcion", "Manejo de cursores y filtros")
                .append("CantCreditos", 12)
                .append("CuposMax", 15)
                .append("Estado", false)
                .append("FechaInicio", new Date());

        Document curso5 = new Document("_id", new ObjectId())
                .append("Codigo", "CUR05")
                .append("Nombre", "RPL")
                .append("Descripcion", "Relaciones personales y laborales")
                .append("CantCreditos", 5)
                .append("CuposMax", 25)
                .append("Estado", false)
                .append("FechaInicio", new Date());

        if (cursos.countDocuments() == 0) {
            //inserto los cursos almacenandolos en una variable
            InsertOneResult resultado1 = cursos.insertOne(curso1);
            InsertOneResult resultado2 = cursos.insertOne(curso2);
            InsertOneResult resultado3 = cursos.insertOne(curso3);
            InsertOneResult resultado4 = cursos.insertOne(curso4);
            InsertOneResult resultado5 = cursos.insertOne(curso5);

            //imprime los _id generados
            System.out.println(resultado1.getInsertedId());
            System.out.println(resultado2.getInsertedId());
            System.out.println(resultado3.getInsertedId());
            System.out.println(resultado4.getInsertedId());
            System.out.println(resultado5.getInsertedId());
        } else {
            System.out.println("Los cursos ya estaban cargados");
        }
    }

    public void registrarCurso(String codigo, String nombre, String descrpicion, int cantcreditos, int cuposmax, Date fechainicio){
        Document curso1 = new Document("_id", new ObjectId())
                .append("Codigo", codigo)
                .append("Nombre", nombre)
                .append("Descripcion", descrpicion)
                .append("CantCreditos", cantcreditos)
                .append("CuposMax", cuposmax)
                .append("Estado", true)
                .append("FechaInicio", fechainicio);

        InsertOneResult resultado1 = cursos.insertOne(curso1);
        System.out.println(resultado1.getInsertedId());
    }

    public void listarCursos(){
        //recorro con cursor documento por documento
        try (MongoCursor<Document> cursor = cursos.find().iterator()) {
            while (cursor.hasNext()) {
                Document curso = cursor.next();
                System.out.println(curso.toJson());
            }
        }
    }

    public Document buscarCurso(String id) {
        //construye el filtro
        Bson filtro = Filters.eq("_id", new ObjectId(id));
        //retorna el documento
        return cursos.find(filtro).first();
    }

    public void modificarCreditos(String id, int creditos){
        cursos.updateOne(
                Filters.eq("_id", new ObjectId(id)),
                Updates.set("CantCreditos", creditos));
    }
}