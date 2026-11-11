package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import com.mongodb.client.model.Projections;
import org.bson.conversions.Bson;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;


public void consultarCursosEstudiante(String id){
    //Encuentra el primer usuario coincidente
    Document usuario = estudiantes.find("_id", id).first();

    //obtengo la lista de cursos de usuario
    List<ObjectId> cursosUsuario = usuario.getList("Cursos", ObjectId.class);

    //recorro la lista de cursos de usuario
    for (ObjectId idCurso : cursosUsuario) {
        //recupero el curso evaluado
        Document curso = cursos.find(Filters.eq("_id", idCurso)).first();

        //imprimo el curso evaluado
        System.out.println(curso.toJson());
    }
}

public void buscarEstudiantesPorCiudad(String ciudad){
    Bson filtro = Filters.eq("Direccion.ciudad", ciudad);

    collection.find(filtro)
            .forEach(estudiante →
                    System.out.println(estudiante.toJson()));
}


public void BuscarEstudiantesPorCoincidencia(String coincidencia){
    Bson filtro = Filters.regex("Apellido", coincidencia, "i");

    FindIterable<Document> estudiantesIT = estudiantes.find(filtro);

    for (Document estudiante : estudiantesIT) {
        System.out.println(estudiante.toJson());
    }
}


