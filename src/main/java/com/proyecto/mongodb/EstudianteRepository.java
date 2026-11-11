
List<Document> estudiantesL = List.of(

        new Document("Nombre", "Lucas")
                .append("Apellido", "Marota")
                .append("Email", "lucas@gmail.com")
                .append("Edad", 22)
                .append("Estado", true)
                .append("Direccion", new Document("ciudad", "Punta Carretas")
                                    .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso1.getObjectId("_id"))),

        new Document("Nombre", "Pablo")
                .append("Apellido", "Millon")
                .append("Email", "pablo@gmail.com")
                .append("Edad", 24)
                .append("Estado", false)
                .append("Direccion", new Document("ciudad", "Ciudad Vieja")
                                    .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso1.getObjectId("_id"))),

        new Document("Nombre", "Francisco")
                .append("Apellido", "Gonzalez")
                .append("Email", "fran@gmail.com")
                .append("Edad", 25)
                .append("Estado", false)
                .append("Direccion", new Document("ciudad", "Sayago")
                                    .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso1.getObjectId("_id"))),

        new Document("Nombre", "Jorge")
                .append("Apellido", "Galemire")
                .append("Email", "jorge@gmail.com")
                .append("Edad", 20)
                .append("Estado", true)
                .append("Direccion", new Document("ciudad", "Cordon")
                                    .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso1.getObjectId("_id"))),

        new Document("Nombre", "Eduardo")
                .append("Apellido", "Mateo")
                .append("Email", "eduardo@gmail.com")
                .append("Edad", 22)
                .append("Estado", true)
                .append("Direccion", new Document("ciudad", "Pocitos")
                                    .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso1.getObjectId("_id"))),

        new Document("Nombre", "Gonzalo")
                .append("Apellido", "Bergessio")
                .append("Email", "gonzalo@gmail.com")
                .append("Edad", 24)
                .append("Estado", false)
                .append("Direccion", new Document("ciudad", "Malvin")
                                    .append("Departamento", "Montevideo"))
                .append("FechaRegistro", fechaRegistro)
                .append("Cursos", List.of(curso1.getObjectId("_id"))),


);


            if (estudiantes.countDocuments() == 0) {
                //inserto la lista estudiantesL en estudiantes y almaceno la variable insertResultado
                InsertManyResult insertResultado =
                estudiantes.insertMany(estudiantesL);

                System.out.println(
                        "Cantidad insertada: "
                                + insertResultado.getInsertedIds().size()
                );
                        } else {
                        System.out.println("Los estudiantes ya estaban cargados");
            }
