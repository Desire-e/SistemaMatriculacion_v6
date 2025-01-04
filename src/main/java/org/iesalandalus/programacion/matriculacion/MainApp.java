package org.iesalandalus.programacion.matriculacion;


import org.iesalandalus.programacion.matriculacion.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.negocio.Matriculas;
import org.iesalandalus.programacion.matriculacion.vista.Consola;
import org.iesalandalus.programacion.matriculacion.vista.Opcion;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;

import java.time.LocalDate;

import static org.iesalandalus.programacion.matriculacion.vista.Consola.elegirOpcion;
import static org.iesalandalus.programacion.matriculacion.vista.Consola.mostrarMenu;

public class MainApp {
    public static final int CAPACIDAD=3;

    private static Alumnos alumnos = new Alumnos(CAPACIDAD);
    private static Matriculas matriculas = new Matriculas(CAPACIDAD);
    private static Asignaturas asignaturas = new Asignaturas(CAPACIDAD);
    private static CiclosFormativos ciclosFormativos = new CiclosFormativos(CAPACIDAD);


    public static void main(String[] args) {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR);

        System.out.println("Hasta luego!!!!");
    }

    private static void ejecutarOpcion(Opcion opcion){
        switch (opcion) {
            case INSERTAR_ALUMNO:
                insertarAlumno();
                break;
            case BUSCAR_ALUMNO:
                buscarAlumno();
                break;
            case BORRAR_ALUMNO:
                borrarAlumno();
                break;
            case MOSTRAR_ALUMNOS:
                mostrarAlumnos();
                break;
            case INSERTAR_CICLO_FORMATIVO:
                insertarCicloFormativo();
                break;
            case BUSCAR_CICLO_FORMATIVO:
                buscarCicloFormativo();
                break;
            case BORRAR_CICLO_FORMATIVO:
                borrarCicloFormativo();
                break;
            case MOSTRAR_CICLOS_FORMATIVOS:
                mostrarCiclosFormativos();
                break;
            case INSERTAR_ASIGNATURA:
                insertarAsignatura();
                break;
            case BUSCAR_ASIGNATURA:
                buscarAsignatura();
                break;
            case BORRAR_ASIGNATURA:
                borrarAsignatura();
                break;
            case MOSTRAR_ASIGNATURAS:
                mostrarAsignaturas();
                break;
            case INSERTAR_MATRICULA:
                insertarMatricula();
                break;
            case BUSCAR_MATRICULA:
                buscarMatricula();
                break;
            case ANULAR_MATRICULA:
                anularMatricula();
                break;
            case MOSTRAR_MATRICULAS:
                mostrarMatriculas();
                break;
            case MOSTRAR_MATRICULAS_ALUMNO:
                mostrarMatriculasPorAlumno();
                break;
            case MOSTRAR_MATRICULAS_CICLO_FORMATIVO:
                mostrarMatriculasPorCicloFormativo();
                break;
            case MOSTRAR_MATRICULAS_CURSO_ACADEMICO:
                mostrarMatriculasPorCursoAcademico();
                break;
            case SALIR:
                System.out.println("Saliendo del sistema...");
                break;
            default:
                System.out.println("Opción no válida.");
                break;
        }
    }

    // Crea el método insertarAlumno que nos pedirá los datos de un alumno, haciendo uso de la clase Consola, y lo
    // insertará en la colección correspondiente si es posible o informará del problema en caso contrario.
    private static void insertarAlumno(){
        try {
            Alumno nuevoAlumno = Consola.leerAlumno();
            alumnos.insertar(nuevoAlumno);
        } catch (Exception e) {
            System.out.println("Error al insertar alumno. " + e.getMessage());
        }
    }


    private static void buscarAlumno(){
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            Alumno busqueda = alumnos.buscar(alumno);
            if (busqueda != null) {
                System.out.println("Alumno encontrado: " + busqueda);
            } else {
                System.out.println("Alumno no encontrado.");
            }
        } catch (Exception e){
            System.out.println("Error al buscar alumno. " + e.getMessage());

        }
    }


    private static void borrarAlumno(){
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            alumnos.borrar(alumno);
        } catch (Exception e) {
            System.out.println("Error al borrar alumno. " + e.getMessage());
        }
    }


    private static void mostrarAlumnos(){
        System.out.println("Listado de alumnos:");

        // Verificar si no hay alumnos registradas
        boolean hayAlumnos = false;

        for (Alumno alumno : alumnos.get()) {
            if (alumno != null) {
                System.out.println(alumno);
                hayAlumnos = true;
            }
        }

        // Si no hay, informa:
        if (!hayAlumnos){
            System.out.println("No hay alumnos registrados.");
        }
    }


    private static void insertarAsignatura(){
        try {
            // Leer los datos de la asignatura
            Asignatura nuevaAsignatura = Consola.leerAsignatura(ciclosFormativos);
            // Insertar la asignatura en la colección de asignaturas
            asignaturas.insertar(nuevaAsignatura);
        } catch (Exception e) {
            System.out.println("Error al insertar asignatura. " + e.getMessage());
        }
    }


    private static void buscarAsignatura(){
        try {
            // Introducen por código la asignatura
            Asignatura asignatura = Consola.getAsignaturaPorCodigo();
            // Buscar la asignatura en la colección
            Asignatura busqueda = asignaturas.buscar(asignatura);
            // Método buscar() devuelve null si no la encontró
            if (busqueda != null) {
                System.out.println("Asignatura encontrada: " + busqueda);
            } else {
                System.out.println("Asignatura no encontrada.");
            }
        } catch (Exception e){
            System.out.println("Error al buscar asignatura. " + e.getMessage());
        }
    }


    private static void borrarAsignatura(){
        try {
            // Introducen por código la asignatura
            Asignatura asignatura = Consola.getAsignaturaPorCodigo();
            // Borra la asignatura o lanza excepciones
            asignaturas.borrar(asignatura);
        } catch (Exception e) {
            System.out.println("Error al borrar asignatura. " + e.getMessage());
        }
    }


    private static void mostrarAsignaturas(){
        // Recorrer colección del objeto asignaturas e imprimir contenido de su array si no es nulo
        System.out.println("Listado de asignaturas:");

        boolean hayAsignaturas = false;

        for (Asignatura asignatura : asignaturas.get()) {
            if (asignatura != null) {
                System.out.println(asignatura);
                hayAsignaturas = true;
            }
        }

        if (!hayAsignaturas){
            System.out.println("No hay asignaturas registradas.");
        }
    }


    private static void insertarCicloFormativo(){
        try {
            CicloFormativo nuevoCiclo = Consola.leerCicloFormativo();
            ciclosFormativos.insertar(nuevoCiclo);
        } catch (Exception e) {
            System.out.println("Error al insertar ciclo formativo. " + e.getMessage());
        }
    }


    private static void buscarCicloFormativo(){
        try {
            CicloFormativo ciclo = Consola.getCicloFormativoPorCodigo();
            CicloFormativo busqueda = ciclosFormativos.buscar(ciclo);

            if (busqueda != null) {
                System.out.println("Ciclo formativo encontrado: " + busqueda);
            } else {
                System.out.println("Ciclo formativo no encontrada.");
            }
        } catch (Exception e){
            System.out.println("Error al buscar ciclo formativo. " + e.getMessage());
        }
    }


    private static void borrarCicloFormativo(){
        try {
            CicloFormativo ciclo = Consola.getCicloFormativoPorCodigo();
            ciclosFormativos.borrar(ciclo);
        } catch (Exception e) {
            System.out.println("Error al borrar ciclo formativo. " + e.getMessage());
        }
    }


    private static void mostrarCiclosFormativos(){
        Consola.mostrarCiclosFormativos(ciclosFormativos);
    }


    private static void insertarMatricula(){
        try {
            Matricula nuevaMatricula = Consola.leerMatricula(alumnos, asignaturas);
            matriculas.insertar(nuevaMatricula);
        } catch (Exception e) {
            System.out.println("Error al insertar matrícula. " + e.getMessage());
        }
    }


    private static void buscarMatricula(){
        try {
            Matricula matricula = Consola.getMatriculaPorIdentificacion();
            Matricula busqueda = matriculas.buscar(matricula);

            if (busqueda != null) {
                System.out.println("Matrícula encontrada: " + busqueda);
            } else {
                System.out.println("Matrícula no encontrada.");
            }
        } catch (Exception e){
            System.out.println("Error al buscar matrícula. " + e.getMessage());
        }
    }


    private static void anularMatricula(){
        try {
            // Muestra las matrículas
            mostrarMatriculas();
            // Pide una matrícula por código y la busca
            Matricula matricula = Consola.getMatriculaPorIdentificacion();
            Matricula busqueda = matriculas.buscar(matricula);

            // Verificar si la matrícula ya está anulada
            if (busqueda.getFechaAnulacion() != null) {
                System.out.println("La matrícula ya está anulada. No se puede volver a anular.");
                return;
            }
            // Pide la fecha de anulación
            LocalDate fechaAnulacion = Consola.leerFecha("Fecha de anulación de la matrícula(DD/MM/AAAA): ");
            // Inserta fecha de anulación
            busqueda.setFechaAnulacion(fechaAnulacion);

        } catch (Exception e) {
            System.out.println("Error al anular la matrícula. " + e.getMessage());
        }
    }


    private static void mostrarMatriculas(){
        try {
            // Verificar si no hay matrículas registradas
            boolean hayMatriculas = false;

            System.out.println("Listado de matrículas:");
            for (Matricula matricula : matriculas.get()) {
                if (matricula != null) {
                    System.out.println(matricula);
                    hayMatriculas = true;
                }
            }

            // Si no hay, informa:
            if (!hayMatriculas) {
                System.out.println("No hay matrículas registradas.");
            }

        } catch (Exception e) {
            System.out.println("Error al mostrar matrículas. " + e.getMessage());
        }
    }


    private static void mostrarMatriculasPorAlumno(){
        try {
            // Muestra los alumnos disponibles
            mostrarAlumnos();

            // Solicitar al usuario el DNI del alumno
            Alumno alumno = Consola.getAlumnoPorDni();

            // Buscar el alumno por DNI
            Alumno alumnoExistente = alumnos.buscar(alumno);
            if (alumnoExistente == null) {
                System.out.println("No se ha encontrado ningún alumno con el DNI proporcionado.");
            }


            // Obtener y mostrar las matrículas del alumno registradas
            boolean hayMatriculas = false;
            System.out.println("Matrículas del alumno: ");
            for (Matricula matricula : matriculas.get()) {
                if (matricula != null && matricula.getAlumno().equals(alumnoExistente)) {
                    System.out.println(matricula.imprimir());
                    hayMatriculas = true;
                }
            }

            // Si no hay matrículas, informar al usuario
            if (!hayMatriculas) {
                System.out.println("El alumno no tiene matrículas registradas.");
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar las matrículas del alumno. " + e.getMessage());
        }
    }


    private static void mostrarMatriculasPorCicloFormativo(){
        try {
            // Muestra los ciclos formativos disponibles
            mostrarCiclosFormativos();

            // Pide el código del ciclo formativo
            CicloFormativo ciclo = Consola.getCicloFormativoPorCodigo();

            // Busca el ciclo formativo por código
            CicloFormativo busqueda = ciclosFormativos.buscar(ciclo);
            if (busqueda == null) {
                System.out.println("No se ha encontrado ningún ciclo formativo con el código proporcionado.");
            }

            // Obtener y mostrar las matrículas del ciclo formativo
            boolean hayMatriculas = false;
            System.out.println("Matrículas del ciclo formativo: ");
            for (Matricula matricula : matriculas.get()) {
                if (matricula != null) {
                    // Comprobar si alguna de las asignaturas de la matrícula pertenece al ciclo formativo
                    for (Asignatura asignatura : matricula.getColeccionAsignaturas()) {
                        if (asignatura != null && asignatura.getCicloFormativo().equals(busqueda)) {
                            System.out.println(matricula.imprimir());
                            hayMatriculas = true;
                            break;  // Salimos del bucle de asignaturas al encontrar una coincidente
                        }
                    }
                }
            }

            // Si no hay matrículas, informar al usuario
            if (!hayMatriculas) {
                System.out.println("No hay matrículas registradas para este ciclo formativo.");
            }

        } catch (Exception e) {
            System.out.println("Error al mostrar las matrículas del ciclo formativo. " + e.getMessage());
        }
    }


    private static void mostrarMatriculasPorCursoAcademico(){
        try {
            // Solicitar al usuario el curso académico
            System.out.println("Introduzca el curso académico (AA-AA): ");
            String cursoAca = Entrada.cadena();

            // Obtener y mostrar las matrículas con ese curso académico
            boolean hayMatriculas = false;
            System.out.println("Matrículas del curso académico: ");
            for (Matricula matricula : matriculas.get()) {
                if (matricula != null && matricula.getCursoAcademico().equals(cursoAca)) {
                    System.out.println(matricula.imprimir());
                    hayMatriculas = true;
                }
            }

            // Si no hay matrículas, informar
            if (!hayMatriculas) {
                System.out.println("El curso académico no tiene matrículas registradas, o el formato del curso académico es incorrecto (debe ser AA-AA).");
            }
        } catch (Exception e) {
            System.out.println("Error al mostrar las matrículas del curso académico. " + e.getMessage());
        }

    }


}
