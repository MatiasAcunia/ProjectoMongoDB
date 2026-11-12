# Sistema de gestión académica

Proyecto final NoSQL de Tecnólogo en Informática. Aplicación Java 21, Maven y MongoDB con el driver síncrono 5.13.0. La interfaz de consola principal todavía está en desarrollo.

## Requisitos

- JDK 21
- Maven 3.9 o compatible
- Servidor MongoDB accesible desde la máquina

## Configuración y precarga

La conexión usa `MONGODB_URI` si está definida; de lo contrario intenta `mongodb://localhost:27017`. La carga inicial usa la base `gestion_academica`, o la indicada en `MONGODB_DATABASE`.

Compilar:

```bash
mvn clean verify
```

Cargar los datos iniciales:

```bash
mvn exec:java -Dexec.mainClass=com.proyecto.mongodb.CargarDatos
```

Se crean hasta 5 cursos y 10 estudiantes de ejemplo con inscripciones de prueba. La precarga de estudiantes agrega solo los correos que faltan, sin sobrescribir documentos existentes. Los cinco cursos se cargan únicamente si la colección está vacía; los existentes no se modifican. Las fechas de curso de la primera carga son distintas y se guardan como BSON Date.

## Organización

- `Conexion`: cliente MongoDB y configuración de conexión.
- `CursoRepository`: carga inicial y operaciones sobre cursos.
- `EstudianteRepository`: carga inicial y operaciones sobre estudiantes.
- `InscripcionesService`: consultas existentes relacionadas con estudiantes y cursos.
- `CargarDatos`: ejecutable autónomo de precarga.
- `Main`: pendiente de desarrollar el menú de consola.

## Modelo documental actual

`estudiantes`: Nombre, Apellido, Email, Edad, Estado, Direccion (ciudad y Departamento), FechaRegistro, Cursos (array de ObjectId).

`cursos`: Codigo, Nombre, Descripcion, CantCreditos, CuposMax, Estado, FechaInicio.

**Pendiente de adecuación a la consigna:** el array de inscripciones debe llamarse `cursosInscriptos` y contener códigos de curso, no ObjectId. También falta completar inscripciones, búsquedas por código, filtro de edad, validación de correos duplicados y menú.

## Entrega

Documentación provisional: `docs/sistema_gestion_academica_borrador.pdf`. Faltan capturas reales de MongoDB Compass, ejecución integral y defensa. Este repositorio todavía no representa un obligatorio completo.
