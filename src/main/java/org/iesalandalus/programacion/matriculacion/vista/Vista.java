package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.*;

public class Vista {
    private static Controlador controlador;

    //CAMBIO V.3:
    public Vista(){
        // Se asigna la vista a todas las opciones del enum
        Opcion.setVista(this);
    }

    public void setControlador(Controlador controlador){
        if (controlador == null){
            throw new NullPointerException("El controlador es nulo.");
        }
        this.controlador=controlador;
    }



    public void comenzar(){
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutarOpcion(opcion);
        } while (opcion != Opcion.SALIR);
        controlador.terminar();
    }



    public void terminar(){
        System.out.println("Hasta luego!!!!");
    }


    public void ejecutarOpcion(Opcion opcion){
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


    public static void insertarAlumno(){
        try {
            Alumno nuevoAlumno = Consola.leerAlumno();
            controlador.insertar(nuevoAlumno);
            System.out.println("Alumno insertado con éxito.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("Error al insertar alumno. " + e.getMessage());
        }
    }


    public static void buscarAlumno(){
        try {
            /* HACER QUE NO APAREZCA DATOS INVENTADOS DE getAlumnoPorDni() */
            String dniAlumno = Consola.getAlumnoPorDni().getDni();

            /* SIN STREAM (1)
            boolean alumnoEncontrado = false;
            for (Alumno alumno : controlador.getAlumnos()){
                if (alumno != null && alumno.getDni().equals(dniAlumno)){
                    System.out.println("Alumno encontrado: " + alumno);
                    alumnoEncontrado = true;
                    break;
                }
            }
            */

            //no se almacena en variable porque solo imprimirá al final, no devuelve un tipo de dato.
            controlador.getAlumnos().stream()
                    .filter(alumno -> alumno != null && alumno.getDni().equals(dniAlumno))
                    .findFirst()
                    .ifPresentOrElse(
                            alumno -> System.out.println("Alumno encontrado: " + alumno),
                            () -> System.out.println("Alumno no encontrado.")       //si no obtiene parametros
                    );

            /* SIN STREAM (2)
            if (!alumnoEncontrado){
                System.out.println("Alumno no encontrado.");
            }
            */

        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println("Error al buscar alumno. " + e.getMessage());

        }
    }


    public static void borrarAlumno(){
        try {
            Alumno alumno = Consola.getAlumnoPorDni();
            controlador.borrar(alumno);
            System.out.println("Alumno borrado con éxito.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("Error al borrar alumno. " + e.getMessage());
        }
    }


    public static void mostrarAlumnos(){
        List<Alumno> alumnos = controlador.getAlumnos();
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
        }

        /*  USANDO COMPARATOR CON BUCLE
        Collections.sort(alumnos, Comparator.comparing(Alumno::getNombre));
        System.out.println("Listado de alumnos:");

        for (Alumno alumno : alumnos) {
            System.out.println(alumno);
        }
        */

        /*USANDO STREAM*/
        System.out.println("Listado de alumnos:");

        alumnos.stream()
                .sorted(Comparator.comparing(Alumno::getNombre))    // Ordena los elementos del Stream en base
                                                                    // al comparador pasado como parámetro.
                .forEach(System.out::println);          // Imprime cada nombre del alumno
    }




    public static void insertarAsignatura(){
        try {

            /*Usar size() puede ser costoso. isEmpty() optimiza*/
            if (controlador.getCiclosFormativos() == null || controlador.getCiclosFormativos().isEmpty()) {
                System.out.println("No hay ciclos formativos registrados en el sistema.");
            }
            /*
            // Mostrar los ciclos disponibles de la colección
            System.out.println("Ciclo formativo de la asignatura. Elija el número del ciclo a seleccionar:");
            for (int i = 0; i < controlador.getCiclosFormativos().length; i++) {
                if (controlador.getCiclosFormativos()[i] != null){
                    System.out.println((i) + ".- " + controlador.getCiclosFormativos()[i].imprimir());
                }
            }
            */

            /*
            // Mostrar los ciclos disponibles de la colección
            System.out.println("Ciclo formativo de la asignatura. Elija el número del ciclo a seleccionar:");
            for (CicloFormativo ciclo : controlador.getCiclosFormativos()){
                if (ciclo != null){
                    int i= controlador.getCiclosFormativos().indexOf(ciclo);
                    System.out.println(i + ".- " + ciclo.imprimir());
                }
            }
            */

            // Mostrar los ciclos disponibles de la colección
            System.out.println("Ciclo formativo de la asignatura. Elija el número del ciclo a seleccionar:");
            controlador.getCiclosFormativos().stream()
                    .filter(ciclo -> ciclo != null)
                    .forEach(ciclo -> {
                        int i = controlador.getCiclosFormativos().indexOf(ciclo);
                        System.out.println(i + ".-" + ciclo.imprimir());
                        }
                    );


            // Pedir ciclo formativo
            int seleccion = Entrada.entero();
            if (seleccion < 0 || seleccion >= controlador.getCiclosFormativos().size()) {
                System.out.println("La opción seleccionada es inválida.");
            }
            CicloFormativo cicloSeleccionado = controlador.getCiclosFormativos().get(seleccion);

            //Comprobar si existe en la colección.
            if (controlador.buscar(cicloSeleccionado) == null){
                System.out.println("El ciclo formativo no existe.");
            }

            // Crear la asignatura asociada al ciclo seleccionado
            Asignatura nuevaAsignatura = Consola.leerAsignatura(cicloSeleccionado);

            // Insertar la asignatura en la colección de asignaturas
            controlador.insertar(nuevaAsignatura);
            System.out.println("Asignatura insertada con éxito.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("Error al insertar asignatura. " + e.getMessage());
        }
    }


    public static void buscarAsignatura(){
        try {
            String codigoAsignatura = Consola.getAsignaturaPorCodigo().getCodigo();

            controlador.getAsignaturas().stream()
                    .filter(asignatura -> asignatura != null && asignatura.getCodigo().equals(codigoAsignatura))
                    .findFirst()
                    .ifPresentOrElse(
                            asignatura -> System.out.println("Asignatura encontrada: " + asignatura),
                            () -> System.out.println("Asignatura no encontrada.")       //si no obtiene parametros
                    );

        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println("Error al buscar asignatura. " + e.getMessage());
        }
    }


    public static void borrarAsignatura(){
        try {
            // Introducen por código la asignatura
            Asignatura asignatura = Consola.getAsignaturaPorCodigo();
            // Borra la asignatura o lanza excepciones
            controlador.borrar(asignatura);
            System.out.println("Asigantura borrada con éxito.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("Error al borrar asignatura. " + e.getMessage());
        }
    }


    public static void mostrarAsignaturas(){
        List<Asignatura> asignaturas = controlador.getAsignaturas();
        if (asignaturas.isEmpty()) {
            System.out.println("No hay asignaturas registradas.");
        }

        System.out.println("Listado de asignaturas:");

        asignaturas.stream()
                .sorted(Comparator.comparing(Asignatura::getNombre))
                .forEach(System.out::println);
    }


    public static void insertarCicloFormativo(){
        try {
            CicloFormativo nuevoCiclo = Consola.leerCicloFormativo();
            controlador.insertar(nuevoCiclo);
            System.out.println("Ciclo formativo insertado con éxito.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("Error al insertar ciclo formativo. " + e.getMessage());
        }
    }


    public static void buscarCicloFormativo(){
        try {
            int codigoCiclo = Consola.getCicloFormativoPorCodigo().getCodigo();

            controlador.getCiclosFormativos().stream()
                    .filter(ciclo -> ciclo != null && ciclo.getCodigo() == codigoCiclo)
                    .findFirst()
                    .ifPresentOrElse(
                            ciclo -> System.out.println("Ciclo formativo encontrado: " + ciclo),
                            () -> System.out.println("Ciclo formativo no encontrado.")       //si no obtiene parametros
                    );

        } catch (NullPointerException | IllegalArgumentException e){
            System.out.println("Error al buscar ciclo formativo. " + e.getMessage());
        }
    }


    public static void borrarCicloFormativo(){
        try {
            CicloFormativo ciclo = Consola.getCicloFormativoPorCodigo();
            controlador.borrar(ciclo);
            System.out.println("Ciclo formativo borrado con éxito.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("Error al borrar ciclo formativo. " + e.getMessage());
        }
    }


    public static void mostrarCiclosFormativos(){
        List<CicloFormativo> ciclosFormativos = controlador.getCiclosFormativos();

        if (ciclosFormativos.isEmpty()) {
            System.out.println("No hay ciclos formativos registrados.");
        }

        System.out.println("Listado de ciclos formativos:");

        ciclosFormativos.stream()
                .sorted(Comparator.comparing(CicloFormativo::getNombre))
                .forEach(System.out::println);
    }




    /*Modifica el método insertarMatricula para que utilice los métodos leerAlumno, elegirAsignaturasMatricula y
    leerMatricula de la clase Consola.*/
    public static void insertarMatricula(){
        try {
            // LISTADO DE ALUMNOS
            mostrarAlumnos();

            // BUSCAR ALUMNO
            String dniAlumno = Consola.getAlumnoPorDni().getDni();

            // BUSCAR ALUMNO POR DNI
            Alumno alumnoMatricula = controlador.getAlumnos().stream()
                    // Aplica un filtro para seleccionar solo aquellos alumnos que cumplan condiciones
                    .filter(alumno -> alumno != null && alumno.getDni().equals(dniAlumno))
                    // alumnoMatricula almecena el primer alumno que cumpla con la condición del filtro.
                    .findFirst()
                    // Si no encuentra ninguno, almacena null.
                    .orElse(null);

            // Si el alumno no existe:
            if (alumnoMatricula == null) {
                System.out.println("Alumno inexistente.");
            }



            //Pedir asignaturas
            //Pasar array de las asignaturas que hay en el sistema a leerMatricula directamente con
            //controlador.getAsignaturas(), donde se elegirá la asignatura
            List<Asignatura> asignaturas = controlador.getAsignaturas();
            Matricula nuevaMatricula = Consola.leerMatricula(alumnoMatricula,asignaturas);

            controlador.insertar(nuevaMatricula);
            System.out.println("Matrícula insertada  con éxito.");
        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("Error al insertar matrícula. " + e.getMessage());
        }
    }


    public static void buscarMatricula(){
        try {
            int idMatricula = Consola.getMatriculaPorIdentificacion().getIdMatricula();

            controlador.getMatriculas().stream()
                    .filter(matricula -> matricula != null && matricula.getIdMatricula() == idMatricula)
                    .findFirst()
                    .ifPresentOrElse(
                            asignatura -> System.out.println("Matrícula encontrada: " + asignatura),
                            () -> System.out.println("Matrícula no encontrada.")       //si no obtiene parametros
                    );

        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e){
            System.out.println("Error al buscar matrícula. " + e.getMessage());
        }
    }


    public static void anularMatricula(){
        try {
            // Muestra las matrículas
            mostrarMatriculas();

            // Pide una matrícula por código y la busca
            //CAMBIOS V.1: Obtiene la matricula con datos inventados menos el id
            Matricula matFicticia = Consola.getMatriculaPorIdentificacion();
            Matricula matAnular= controlador.buscar(matFicticia);

            if(matAnular == null){
                System.out.println("No existe matrícula con el id proporcionado");
            }

            // Verificar si la matrícula ya está anulada
            if (matAnular.getFechaAnulacion() != null) {
                System.out.println("La matrícula ya está anulada. No se puede volver a anular.");
                return;
            }

            // Pide la fecha de anulación
            LocalDate fechaAnulacion = Consola.leerFecha("Fecha de anulación de la matrícula(DD/MM/AAAA): ");
            // Inserta fecha de anulación
            matAnular.setFechaAnulacion(fechaAnulacion);

        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("Error al anular la matrícula. " + e.getMessage());
        }
    }


    public static void mostrarMatriculas() {
        try {
            /*
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
            */

            List<Matricula> matriculas = controlador.getMatriculas();
            if (matriculas.isEmpty()) {
                System.out.println("No hay matrículas registradas.");
            }

            /* Collections.sort(): ordena listas. En este caso, ordena la lista matriculas.
               Comparator.comparing(): Crea un comparador basado en el valor
               retornado por el método de esa clase (ordenará según fecha, de antigua a reciente).

               ** Matricula::getFechaMatriculacion es una referencia a método que permite acceder directamente
                  al método getFechaMatriculacion de cada objeto Matricula.

               .reversed() invierte el orden natural. (fechas más reciente a antiguas)
               .thenComparing(): obtiene un criterio de desempate (m -> m.getAlumno().getNombre()),  que se
               aplica si dos o más matrículas tienen la misma fecha de matriculación, para pasar a comparar
               los nombres de los alumnos asociados utilizando m.getAlumno().getNombre().

               ** m -> m.getAlumno().getNombre()): m representa cada matrícula en la lista, getAlumno().getNombre()
                  accede al alumno asociado, y recupera el nombre.
                  'de esta matrícula, tras comparar por fecha (cuya fecha igual a otra), compara según nombre de alumno'
           */

            Collections.sort(matriculas, Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                    .thenComparing(m -> m.getAlumno().getNombre()));

            System.out.println("Listado de matrículas:");
            for (Matricula matricula : matriculas) {
                System.out.println(matricula);
            }


        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("Error al mostrar matrículas. " + e.getMessage());
        }
    }


    public static void mostrarMatriculasPorAlumno(){
        try {
            // Muestra los alumnos disponibles
            mostrarAlumnos();

            // Solicitar al usuario el DNI del alumno
            Alumno alumno = Consola.getAlumnoPorDni();

            // Buscar el alumno por DNI
            Alumno alumnoExistente = controlador.buscar(alumno);  // Si existe el alumno, devuelve copia de este, sino null
            if (alumnoExistente == null) {
                System.out.println("No se ha encontrado ningún alumno con el DNI proporcionado.");
            }

            /*
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
            */


            // Obtener las matrículas del alumno registradas
            List<Matricula> matriculasAlumno = controlador.getMatriculas(alumnoExistente);
            if (matriculasAlumno.isEmpty()){
                System.out.println("El alumno no tiene matrículas registradas.");
            }
            /*
            Collections.sort(matriculasAlumno, Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                    .thenComparing(m -> m.getAlumno().getNombre()));

            System.out.println("Matrículas del alumno: ");
            for (Matricula matricula : matriculasAlumno) {
                System.out.println(matricula);
            }
            */
            System.out.println("Matrículas del alumno: ");
            matriculasAlumno.stream().sorted(Comparator.comparing(Matricula::getFechaMatriculacion)
                            .reversed()
                            .thenComparing(m -> m.getAlumno().getNombre()))
                            .forEach(System.out::println);

        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("Error al mostrar las matrículas del alumno. " + e.getMessage());
        }
    }


    public static void mostrarMatriculasPorCicloFormativo(){
        try {
            mostrarCiclosFormativos();
            CicloFormativo ciclo = Consola.getCicloFormativoPorCodigo();
            CicloFormativo cicloFormativo = controlador.buscar(ciclo);
            if (cicloFormativo == null) {
                System.out.println("No se ha encontrado ningún ciclo formativo con el código proporcionado.");
            }


            List<Matricula> matriculasCiclo = controlador.getMatriculas(ciclo);
            if (matriculasCiclo.isEmpty()){
                System.out.println("No hay matrículas registradas para este ciclo formativo.");
            }
            /*
            Collections.sort(matriculasCiclo, Comparator.comparing(Matricula::getFechaMatriculacion).reversed()
                    .thenComparing(m -> m.getAlumno().getNombre()));


            System.out.println("Matrículas del ciclo formativo: ");
            for (Matricula matricula : matriculasCiclo) {
                System.out.println(matricula);
            }
            */

            System.out.println("Matrículas del ciclo formativo: ");
            matriculasCiclo.stream().sorted(Comparator.comparing(Matricula::getFechaMatriculacion)
                            .reversed()
                            .thenComparing(m -> m.getAlumno().getNombre()))
                            .forEach(System.out::println);

        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("Error al mostrar las matrículas del ciclo formativo. " + e.getMessage());
        }
    }


    public static void mostrarMatriculasPorCursoAcademico(){
        try {
            System.out.println("Introduzca el curso académico (AA-AA): ");
            String cursoAca = Entrada.cadena();


            List<Matricula> matriculasCurso = controlador.getMatriculas(cursoAca);
            if (matriculasCurso.isEmpty()){
                System.out.println("El curso académico no tiene matrículas registradas, o el formato del curso académico es incorrecto (debe ser AA-AA)..");
            }

            System.out.println("Matrículas del curso académico: ");
            matriculasCurso.stream().sorted(Comparator.comparing(Matricula::getFechaMatriculacion)
                            .reversed()
                            .thenComparing(m -> m.getAlumno().getNombre()))
                    .forEach(System.out::println);

        } catch (NullPointerException | IllegalArgumentException | OperationNotSupportedException e) {
            System.out.println("Error al mostrar las matrículas del curso académico. " + e.getMessage());
        }

    }

}
