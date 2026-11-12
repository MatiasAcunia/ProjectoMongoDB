package com.proyecto.mongodb;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        InscripcionesService servicio = new InscripcionesService();

        try (Scanner sc = new Scanner(System.in)) {
            servicio.cargarDatosPorDefecto();

            String opcion;
            do {
                System.out.println("""
                        
                        ===== GESTIÓN ACADÉMICA =====
                        1. Registrar estudiante
                        2. Listar estudiantes
                        3. Buscar estudiante por correo
                        4. Modificar edad
                        5. Eliminar estudiante
                        6. Registrar curso
                        7. Listar cursos
                        8. Buscar curso por código
                        9. Modificar créditos
                        10. Inscribir estudiante en curso
                        11. Consultar cursos de estudiante
                        12. Buscar estudiantes mayores de una edad
                        13. Buscar estudiantes por ciudad
                        14. Buscar estudiantes por apellido
                        0. Salir
                        """);
                System.out.print("Opción: ");
                opcion = sc.nextLine().trim();

                try {
                    switch (opcion) {
                        case "1" -> {
                            System.out.print("Nombre: ");
                            String nombre = sc.nextLine();
                            System.out.print("Apellido: ");
                            String apellido = sc.nextLine();
                            System.out.print("Correo: ");
                            String email = sc.nextLine();
                            System.out.print("Edad: ");
                            int edad = Integer.parseInt(sc.nextLine());
                            System.out.print("Ciudad: ");
                            String ciudad = sc.nextLine();
                            System.out.print("Departamento: ");
                            String departamento = sc.nextLine();
                            servicio.registrarEstudiante(nombre, apellido, email, edad, ciudad, departamento);
                        }
                        case "2" -> servicio.listarEstudiantes();
                        case "3" -> {
                            System.out.print("Correo: ");
                            servicio.buscarEstudiantePorEmail(sc.nextLine());
                        }
                        case "4" -> {
                            System.out.print("Correo: ");
                            String email = sc.nextLine();
                            System.out.print("Nueva edad: ");
                            int edad = Integer.parseInt(sc.nextLine());
                            servicio.modificarEdadEstudiante(email, edad);
                        }
                        case "5" -> {
                            System.out.print("Correo: ");
                            String email = sc.nextLine();
                            System.out.print("¿Confirmar eliminación? (s/n): ");
                            boolean confirmar = sc.nextLine().trim().equalsIgnoreCase("s");
                            servicio.eliminarEstudiante(email, confirmar);
                        }
                        case "6" -> {
                            System.out.print("Código: ");
                            String codigo = sc.nextLine();
                            System.out.print("Nombre: ");
                            String nombre = sc.nextLine();
                            System.out.print("Descripción: ");
                            String descripcion = sc.nextLine();
                            System.out.print("Créditos: ");
                            int creditos = Integer.parseInt(sc.nextLine());
                            System.out.print("Cupo máximo: ");
                            int cupo = Integer.parseInt(sc.nextLine());
                            System.out.print("Fecha de inicio (AAAA-MM-DD): ");
                            Date fecha = Date.from(LocalDate.parse(sc.nextLine().trim())
                                    .atStartOfDay(ZoneOffset.UTC).toInstant());
                            servicio.registrarCurso(codigo, nombre, descripcion, creditos, cupo, fecha);
                        }
                        case "7" -> servicio.listarCursos();
                        case "8" -> {
                            System.out.print("Código del curso: ");
                            servicio.buscarCurso(sc.nextLine());
                        }
                        case "9" -> {
                            System.out.print("Código del curso: ");
                            String codigo = sc.nextLine();
                            System.out.print("Nuevos créditos: ");
                            int creditos = Integer.parseInt(sc.nextLine());
                            servicio.modificarCreditos(codigo, creditos);
                        }
                        case "10" -> {
                            System.out.print("Correo del estudiante: ");
                            String email = sc.nextLine();
                            System.out.print("Código del curso: ");
                            servicio.inscribirEstudiante(email, sc.nextLine());
                        }
                        case "11" -> {
                            System.out.print("Correo del estudiante: ");
                            servicio.consultarCursosEstudiante(sc.nextLine());
                        }
                        case "12" -> {
                            System.out.print("Edad mínima: ");
                            servicio.buscarMayoresDe(Integer.parseInt(sc.nextLine()));
                        }
                        case "13" -> {
                            System.out.print("Ciudad: ");
                            servicio.buscarEstudiantesPorCiudad(sc.nextLine());
                        }
                        case "14" -> {
                            System.out.print("Apellido: ");
                            servicio.buscarEstudiantesPorApellido(sc.nextLine());
                        }
                        case "0" -> System.out.println("Saliendo...");
                        default -> System.out.println("Opción inválida.");
                    }
                } catch (NumberFormatException | DateTimeParseException e) {
                    System.out.println("Número o fecha inválida.");
                }

            } while (!opcion.equals("0"));
        } finally {
            servicio.cerrarConexion();
        }
    }
}
