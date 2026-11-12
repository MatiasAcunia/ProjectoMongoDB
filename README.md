# Sistema de gestión académica

Proyecto Java para gestionar estudiantes, cursos y sus relaciones mediante MongoDB.

## Tecnologías

- Java 21
- Maven
- MongoDB
- MongoDB Java Sync Driver 5.13.0

## Configuración

La conexión utiliza la variable de entorno `MONGODB_URI`. Si no se define, se conecta a `mongodb://localhost:27017`.

La base de datos se selecciona con `MONGODB_DATABASE`. El valor predeterminado es `gestion_academica`.

## Compilación

```bash
mvn clean verify
```

## Ejecución

```bash
mvn exec:java -Dexec.mainClass=com.proyecto.mongodb.Main
```

La carga inicial prepara cinco cursos y diez estudiantes de ejemplo. Los datos incluyen estados activos e inactivos, direcciones embebidas, fechas BSON y referencias entre estudiantes y cursos.

Los estudiantes existentes se conservan y los registros de ejemplo se incorporan según su correo electrónico. Los cursos de ejemplo se agregan por código si todavía no existen.

## Estructura

El código fuente Java se encuentra en `src/main/java/com/proyecto/mongodb/`.

- `Conexion.java`: configuración del cliente MongoDB.
- `CursoRepository.java`: acceso a la colección de cursos.
- `EstudianteRepository.java`: acceso a la colección de estudiantes.
- `InscripcionesService.java`: operaciones de estudiantes, cursos e inscripciones.
- `Main.java`: menú de consola.
