package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Matriculas;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;

public class Vista {
    private static Controlador controlador;

    //CAMBIOS V.1:
    /*Crea el método setControlador que asignará el controlador pasado al atributo si éste no es nulo.*/
    public void setControlador(Controlador controlador){
        if (controlador == null){
            throw new NullPointerException("El controlador es nulo.");
        }
        this.controlador=controlador;
    }


    //CAMBIOS V.1:
    /*Crea el método comenzar que mostrará el menú, leerá una opción de consola y la ejecutará. Repetirá este proceso
    mientras la opción elegida no sea la correspondiente a salir. Utilizará los correspondientes métodos de la
    clase Consola.*/
    public void comenzar(){
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR);
        controlador.terminar();
    }


    //CAMBIOS V.1:
    /*Crea el método terminar que simplemente mostrará un mensaje de despedida por consola.*/
    public void terminar(){
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

    //CAMBIOS V.1:
    /*Modifica los métodos de insertar, buscar, borrar y listar. Recuerda que debes llamar a los correspondientes
    métodos del Controlador para realizar las operaciones.*/

    // Crea el método insertarAlumno que nos pedirá los datos de un alumno, haciendo uso de la clase Consola, y lo
    // insertará en la colección correspondiente si es posible o informará del problema en caso contrario.
    private static void insertarAlumno(){
        try {
            Alumno nuevoAlumno = Consola.leerAlumno();
            controlador.insertar(nuevoAlumno);
        } catch (Exception e) {
            System.out.println("Error al insertar alumno. " + e.getMessage());
        }
    }


    private static void buscarAlumno(){
        try {
            Alumno alumnoBuscado = Consola.getAlumnoPorDni();
            String dniAlumno = alumnoBuscado.getDni();
            boolean alumnoEncontrado = false;

            for (Alumno alumno : controlador.getAlumnos()){
                if (alumno != null && alumno.getDni().equals(dniAlumno)){
                    System.out.println("Alumno encontrado: " + alumno);
                    alumnoEncontrado = true;
                    break;
                }
            }

            if (!alumnoEncontrado){
                System.out.println("Alumno no encontrado.");
            }
        } catch (Exception e){
            System.out.println("Error al buscar alumno. " + e.getMessage());

        }
    }


    private static void borrarAlumno(){
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            controlador.borrar(alumno);
        } catch (Exception e) {
            System.out.println("Error al borrar alumno. " + e.getMessage());
        }
    }


    private static void mostrarAlumnos(){
        System.out.println("Listado de alumnos:");

        // Verificar si no hay alumnos registradas
        boolean hayAlumnos = false;

        for (Alumno alumno : controlador.getAlumnos()) {
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

    /*private*/ public static void insertarAsignatura(){
        try {
            // controlador.getCiclosFormativos() devuelve el array que contiene la clase CiclosFormativos.


            if (controlador.getCiclosFormativos() == null || controlador.getCiclosFormativos().length == 0) {
                System.out.println("No hay ciclos formativos registrados en el sistema.");
            }
            /* EN PROCESO
            //Muestra ciclos disponibles
            Consola.mostrarCiclosFormativos(controlador.getCiclosFormativos());
            //Pide código del ciclo a seleccionar
            int codigoCiclo = Consola.getCicloFormativoPorCodigo().getCodigo();
            for ()
              */


            // Mostrar los ciclos disponibles de la colección
            System.out.println("Ciclo formativo de la asignatura. Elija el número del ciclo a seleccionar:");
            for (int i = 0; i < controlador.getCiclosFormativos().length; i++) {
                if (controlador.getCiclosFormativos()[i] != null){
                    System.out.println((i) + ".- " + controlador.getCiclosFormativos()[i].imprimir());
                }
            }

            // Pedir ciclo formativo
            int seleccion = Entrada.entero();
            if (seleccion < 0 || seleccion >= controlador.getCiclosFormativos().length) {
                System.out.println("La opción seleccionada es inválida.");
            }
            CicloFormativo cicloSeleccionado = controlador.getCiclosFormativos()[seleccion];

            //Comprobar si existe en la colección.
            if (controlador.buscar(cicloSeleccionado) == null){
                System.out.println("El ciclo formativo no existe.");
            }


            // Crear la asignatura asociada al ciclo seleccionado
            Asignatura nuevaAsignatura = Consola.leerAsignatura(cicloSeleccionado);

            // Insertar la asignatura en la colección de asignaturas
            controlador.insertar(nuevaAsignatura);
        } catch (Exception e) {
            System.out.println("Error al insertar asignatura. " + e.getMessage());
        }
    }


    private static void buscarAsignatura(){
        try {
            Asignatura asignaturaBuscada = Consola.getAsignaturaPorCodigo();
            String codigoAsignatura = asignaturaBuscada.getCodigo();
            boolean asignaturaEncontrada = false;

            for (Asignatura asignatura : controlador.getAsignaturas()){
                if (asignatura != null && asignatura.getCodigo().equals(codigoAsignatura)){
                    System.out.println("Asignatura encontrada: " + asignatura);
                    asignaturaEncontrada = true;
                    break;
                }
            }

            if (!asignaturaEncontrada){
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
            controlador.borrar(asignatura);
        } catch (Exception e) {
            System.out.println("Error al borrar asignatura. " + e.getMessage());
        }
    }


    private static void mostrarAsignaturas(){
        // Recorrer colección del objeto asignaturas e imprimir contenido de su array si no es nulo
        System.out.println("Listado de asignaturas:");

        boolean hayAsignaturas = false;

        for (Asignatura asignatura : controlador.getAsignaturas()) {
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
            controlador.insertar(nuevoCiclo);
        } catch (Exception e) {
            System.out.println("Error al insertar ciclo formativo. " + e.getMessage());
        }
    }


    private static void buscarCicloFormativo(){
        try {
            CicloFormativo cicloBuscado = Consola.getCicloFormativoPorCodigo();
            int codigoCiclo = cicloBuscado.getCodigo();
            boolean cicloEncontrado = false;

            for (CicloFormativo ciclo : controlador.getCiclosFormativos()){
                if (ciclo != null && ciclo.getCodigo() == codigoCiclo){
                    System.out.println("Ciclo formativo encontrado: " + ciclo);
                    cicloEncontrado = true;
                    break;
                }
            }

            if (!cicloEncontrado){
                System.out.println("Ciclo formativo no encontrado.");
            }
        } catch (Exception e){
            System.out.println("Error al buscar ciclo formativo. " + e.getMessage());
        }
    }


    private static void borrarCicloFormativo(){
        try {
            CicloFormativo ciclo = Consola.getCicloFormativoPorCodigo();
            controlador.borrar(ciclo);
        } catch (Exception e) {
            System.out.println("Error al borrar ciclo formativo. " + e.getMessage());
        }
    }


    private static void mostrarCiclosFormativos(){
        Consola.mostrarCiclosFormativos(controlador.getCiclosFormativos());
    }


    /*Modifica el método insertarMatricula para que utilice los métodos leerAlumno, elegirAsignaturasMatricula y
    leerMatricula de la clase Consola.*/
    private static void insertarMatricula(){
        try {
            //Pedir alumno
            Alumno alumno = Consola.leerAlumno();
            //**Se registra el alumno en el sistema
            //**Quizás debo de especificar que busque al alumno y si existe, insertarlo (si da error al insertar un alumno ya registrado)
            controlador.insertar(alumno);
            //Pedir asignaturas
            Asignatura[] asignaturas = controlador.getAsignaturas();
            //Pasar array de las asignaturas que hay en el sistema, directamente con controlador.getAsignaturas()
            Matricula nuevaMatricula = Consola.leerMatricula(alumno,asignaturas);
            controlador.insertar(nuevaMatricula);
        } catch (Exception e) {
            System.out.println("Error al insertar matrícula. " + e.getMessage());
        }
    }


    private static void buscarMatricula(){
        try {
            Matricula matriculaBuscada = Consola.getMatriculaPorIdentificacion();
            int idMatricula = matriculaBuscada.getIdMatricula();
            boolean matriculaEncontrada = false;

            for (Matricula matricula : controlador.getMatriculas()){
                if (matricula != null && matricula.getIdMatricula() == idMatricula){
                    System.out.println("Matrícula encontrada: " + matricula);
                    matriculaEncontrada = true;
                    break;
                }
            }

            if (!matriculaEncontrada){
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
            Matricula busqueda = controlador.buscar(matricula);

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
            for (Matricula matricula : controlador.getMatriculas()) {
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
            Alumno alumnoExistente = controlador.buscar(alumno);
            if (alumnoExistente == null) {
                System.out.println("No se ha encontrado ningún alumno con el DNI proporcionado.");
            }


            // Obtener y mostrar las matrículas del alumno registradas
            boolean hayMatriculas = false;
            System.out.println("Matrículas del alumno: ");
            for (Matricula matricula : controlador.getMatriculas(alumnoExistente)) {
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
            CicloFormativo cicloFormativo = controlador.buscar(ciclo);
            if (cicloFormativo == null) {
                System.out.println("No se ha encontrado ningún ciclo formativo con el código proporcionado.");
            }

            // Obtener y mostrar las matrículas del ciclo formativo
            boolean hayMatriculas = false;
            System.out.println("Matrículas del ciclo formativo: ");
            for (Matricula matricula : controlador.getMatriculas(cicloFormativo)) {
                if (matricula != null) {
                    System.out.println(matricula.imprimir());
                    hayMatriculas = true;
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
            for (Matricula matricula : controlador.getMatriculas(cursoAca)) {
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
