package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.dominio.*;
import org.iesalandalus.programacion.matriculacion.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.negocio.Asignaturas;

import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Consola {


    private Consola(){}

    public static void mostrarMenu(){

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("SISTEMA DE MATRICULACIÓN.");
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(Opcion.SALIR);
        System.out.println(Opcion.INSERTAR_ALUMNO);
        System.out.println(Opcion.BUSCAR_ALUMNO);
        System.out.println(Opcion.BORRAR_ALUMNO);
        System.out.println(Opcion.MOSTRAR_ALUMNOS);
        System.out.println(Opcion.INSERTAR_CICLO_FORMATIVO);
        System.out.println(Opcion.BUSCAR_CICLO_FORMATIVO);
        System.out.println(Opcion.BORRAR_CICLO_FORMATIVO);
        System.out.println(Opcion.MOSTRAR_CICLOS_FORMATIVOS);
        System.out.println(Opcion.INSERTAR_ASIGNATURA);
        System.out.println(Opcion.BUSCAR_ASIGNATURA);
        System.out.println(Opcion.BORRAR_ASIGNATURA);
        System.out.println(Opcion.MOSTRAR_ASIGNATURAS);
        System.out.println(Opcion.INSERTAR_MATRICULA);
        System.out.println(Opcion.BUSCAR_MATRICULA);
        System.out.println(Opcion.ANULAR_MATRICULA);
        System.out.println(Opcion.MOSTRAR_MATRICULAS);
        System.out.println(Opcion.MOSTRAR_MATRICULAS_ALUMNO);
        System.out.println(Opcion.MOSTRAR_MATRICULAS_CICLO_FORMATIVO);
        System.out.println(Opcion.MOSTRAR_MATRICULAS_CURSO_ACADEMICO);
    }


    public static Opcion elegirOpcion(){

        Opcion opcionSeleccionada = null;

        do {
            System.out.print("Elija una opción (0-19):");
            try {
                int opcion = Entrada.entero();
                opcionSeleccionada = Opcion.values()[opcion];

            // Opcion.values() devuelve un array con los valores del enumerado, y cualquier número que
            // no corresponda a un índice de ese array genera esta excepción:
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Opción fuera de rango");

            // Otras entradas inválidas (cadenas):
            } catch (Exception e) {
                System.out.println("Entrada inválida. Introduzca un número entre 0 y 19.");
            }
        } while (opcionSeleccionada == null);

        return opcionSeleccionada;
    }


    public static Alumno leerAlumno() throws IllegalArgumentException, NullPointerException {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Introduzca los datos del alumno.");

        System.out.println("Nombre:");
        String nombreAlumno = Entrada.cadena();

        System.out.println("DNI:");
        String dniAlumno = Entrada.cadena();

        System.out.println("Correo:");
        String correoAlumno = Entrada.cadena();

        System.out.println("Teléfono:");
        String tlfAlumno = Entrada.cadena();

        // Llama a leerFecha, se le pasa el mensaje a mostrar. leerFecha pedirá el dato, valida y devuelve la fecha
        // a la variable fechaNacimiento
        LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (DD/MM/AAAA):");

        return new Alumno(nombreAlumno, dniAlumno, correoAlumno, tlfAlumno, fechaNacimiento);

        /*SOBRE TRATAR Y PROPAGAR (THROWS): Si las excepciones (como IllegalArgumentException o NullPointerException)
          no se manejan en leerAlumno, deben ser capturadas en el método que llame a leerAlumno para evitar una
          terminación abrupta.*/
    }


    public static Alumno getAlumnoPorDni() throws NullPointerException, IllegalArgumentException {

        System.out.println("Introduzca el DNI del alumno.");
        String dni = Entrada.cadena();

        //Creo el alumno con los restantes atributos inventados
        LocalDate fecha = LocalDate.of(2000, 10, 3);

        return new Alumno("Juan", dni, "alberto@gmail.com", "660746030", fecha);
    }


    public static LocalDate leerFecha(String mensaje){

        LocalDate fechaValida = null;
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Repetir hasta que la fecha ingresada sea válida
        while (fechaValida == null) {

            // Imprime parámetro mensaje, que es el texto que se mostrará al usuario para pedirle una fecha.
            System.out.println(mensaje);
            String fechaEntrada = Entrada.cadena();

            try {
                // LocalDate.parse() pasa la fecha de String a LocalDate, y comprueba que sigue el formato.
                fechaValida = LocalDate.parse(fechaEntrada, formato);
                // Si la fecha es inválida o no cumple formato, capturar la excepción que se lanza desde el
                // método Localdate.parse()
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha inválido. Por favor, inténtalo de nuevo.");
            }
        }

        return fechaValida;
    }


    public static Grado leerGrado(){
        System.out.println("Grados existentes.");
        System.out.println(Grado.GDCFGB.imprimir());
        System.out.println(Grado.GDCFGM.imprimir());
        System.out.println(Grado.GDCFGS.imprimir());

        Grado grado = null;

        do {
            System.out.print("Elija un grado (0-2):");
            try {
                int opcion = Entrada.entero();
                grado = Grado.values()[opcion];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Opción fuera de rango");
            } catch (Exception e) {
                System.out.println("Entrada inválida. Introduzca un número entre 0 y 19.");
            }
        } while (grado == null);

        return grado;
    }


    public static CicloFormativo leerCicloFormativo() throws IllegalArgumentException, NullPointerException{
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Introduzca los datos del ciclo formativo.");

        System.out.println("Código:");
        int codigoCiclo = Entrada.entero();

        System.out.println("Familia profesional:");
        String familiaProfCiclo = Entrada.cadena();

        // Llamar al método para seleccionar entre los grados disponibles
        Grado gradoCiclo = leerGrado();

        System.out.println("Nombre:");
        String nombreCiclo = Entrada.cadena();

        System.out.println("Horas:");
        int horasCiclo = Entrada.entero();

        return new CicloFormativo(codigoCiclo, familiaProfCiclo, gradoCiclo, nombreCiclo, horasCiclo);
    }


    public static void mostrarCiclosFormativos(CiclosFormativos ciclosFormativos) {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Ciclos formativos existentes:");

        // Si está vacía
        if (ciclosFormativos == null) {
            System.out.println("La colección de ciclos formativos no se inició.");
        }

        // Obtener el array de ciclos formativos
        CicloFormativo[] arrayCiclos = ciclosFormativos.get();

        // Si no contiene nada:
        if (arrayCiclos == null || arrayCiclos.length == 0) {
            System.out.println("No hay ciclos formativos registrados.");
        }

        // Si sí contiene, recorrer y mostrar cada ciclo formativo:
        boolean hayCiclosValidos = false;
        for (CicloFormativo ciclo : arrayCiclos) {
            if (ciclo != null) {
                hayCiclosValidos = true;
                System.out.println(ciclo.imprimir());
            }
        }

        // Si sí contiene, pero no son válidos:
        if (!hayCiclosValidos) {
            System.out.println("No hay ciclos formativos válidos.");
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    /*
    SOBRE LAS EXCEPCIONES:
        No, la ejecución del método mostrarCiclosFormativos no dará una terminación abrupta debido a las excepciones
        lanzadas por la clase CiclosFormativos, siempre y cuando dichas excepciones no se produzcan durante la ejecución
        del método. Esto es porque el método mostrarCiclosFormativos no realiza operaciones que puedan directamente lanzar
        las excepciones de CiclosFormativos o CicloFormativo bajo las condiciones actuales.


        Análisis Detallado:
        > Excepciones de CiclosFormativos:
        Las excepciones como NullPointerException, IllegalArgumentException, y OperationNotSupportedException están
        asociadas principalmente con métodos como insertar, borrar, y el constructor de CiclosFormativos.
        El método get no lanza excepciones, ya que solo devuelve una copia profunda del array de ciclos formativos.

        > Uso de ciclosFormativos.get():
        En mostrarCiclosFormativos, se llama a ciclosFormativos.get(). Este método realiza una copia del array, iterando
        sobre los elementos, pero no lanza excepciones.

        > CicloFormativo:
        Las excepciones de CicloFormativo solo se lanzan en sus setters y constructor. En este caso, el método toString
        o imprimir no lanzan excepciones.

        > Control de Nulos y Arrays Vacíos:
        El método incluye verificaciones explícitas para evitar NullPointerException en los casos en que ciclosFormativos
        sea null, o cuando el array interno esté vacío o contenga elementos null.
    */


    public static CicloFormativo getCicloFormativoPorCodigo() throws IllegalArgumentException{
        ;
        System.out.println("Introduzca el código del ciclo formativo: ");
            int codigoCiclo = Entrada.entero();

        return new CicloFormativo(codigoCiclo, "Informática", Grado.GDCFGB, "Base de Datos", 100);
    }


    public static Curso leerCurso(){
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Cursos existentes.");
        System.out.println(Curso.PRIMERO.imprimir());
        System.out.println(Curso.SEGUNDO.imprimir());
        System.out.println("-----------------------------------------------------------------------------------------");

        Curso curso = null;

        do {
            System.out.print("Elija un curso (0-1):");
            try {
                int opcion = Entrada.entero();
                curso = Curso.values()[opcion];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Opción fuera de rango");
            } catch (Exception e) {
                System.out.println("Entrada inválida. Introduzca un número entre 0 y 19.");
            }
        } while (curso == null);

        return curso;
    }


    public static EspecialidadProfesorado leerEspecialidadProfesorado(){
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Especialidades existentes.");
        System.out.println(EspecialidadProfesorado.INFORMATICA.imprimir());
        System.out.println(EspecialidadProfesorado.SISTEMAS.imprimir());
        System.out.println(EspecialidadProfesorado.FOL.imprimir());
        System.out.println("-----------------------------------------------------------------------------------------");

        EspecialidadProfesorado especialidad = null;

        do {
            System.out.print("Elija una especialidad (0-2):");
            try {
                int opcion = Entrada.entero();
                especialidad = EspecialidadProfesorado.values()[opcion];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Opción fuera de rango");
            } catch (Exception e) {
                System.out.println("Entrada inválida. Introduzca un número entre 0 y 19.");
            }
        } while (especialidad == null);

        return especialidad;
    }


    public static Asignatura leerAsignatura(CiclosFormativos ciclosFormativos) throws NullPointerException, IllegalArgumentException {

        // Validar que la colección de ciclos formativos no sea nula ni esté vacía
        if (ciclosFormativos == null || ciclosFormativos.get().length == 0) {
            System.out.println("No hay ciclos formativos registrados. No puede crear una asignatura.");
            return null;
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Introduzca los datos de la asignatura.");

        System.out.println("Código:");
        String codAsignatura = Entrada.cadena();

        System.out.println("Nombre:");
        String nombreAsignatura = Entrada.cadena();

        System.out.println("Horas anuales:");
        int horasAnualAsignatura = Entrada.entero();

        System.out.println("Curso:");
        // Llamar al método para seleccionar entre los cursos disponibles
        Curso cursoAsignatura = leerCurso();

        System.out.println("Horas desdoble:");
        int horasDesdoAsignatura = Entrada.entero();

        System.out.println("Especialidad:");
        // Llamar al método para seleccionar entre los grados disponibles
        EspecialidadProfesorado especialidadAsignatura = leerEspecialidadProfesorado();

        System.out.println("Ciclo formativo:");
        // Muestra los ciclos formativos actuales existentes. Imprime cada ciclo.
        mostrarCiclosFormativos(ciclosFormativos);
        // Pide que busque un ciclo según su código. Devuelve el ciclo que se seleccionó.
        CicloFormativo cicloAsignatura = getCicloFormativoPorCodigo();

        return new Asignatura(codAsignatura, nombreAsignatura, horasAnualAsignatura, cursoAsignatura, horasDesdoAsignatura, especialidadAsignatura, cicloAsignatura);
    }


    public static Asignatura getAsignaturaPorCodigo() throws NullPointerException, IllegalArgumentException{
        System.out.println("Introduzca el código de la asignatura.");

        String codigoAsignatura = Entrada.cadena();

        CicloFormativo ciclo = new CicloFormativo(5000, "Informática", Grado.GDCFGB, "DAM", 10);

        return new Asignatura(codigoAsignatura, "Programación", 200, Curso.PRIMERO, 3, EspecialidadProfesorado.INFORMATICA, ciclo);
    }


    private static void mostrarAsignaturas(Asignaturas asignaturas){
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Asignaturas existentes:");
        // Si parámetro está vacío:
        if (asignaturas == null) {
            System.out.println("La colección de asignaturas no se inició.");
        }

        // Obtener array del parámetro
        Asignatura[] arrayAsignaturas = asignaturas.get();

        // Si no contiene nada:
        if (arrayAsignaturas == null || arrayAsignaturas.length == 0) {
            System.out.println("No hay asignaturas registradas.");
        }

        // Si sí contiene, recorrer y mostrar cada asignatura:
        boolean hayAsignaturasValidas = false;
        for (Asignatura asignatura : arrayAsignaturas) {
            if (asignatura != null) {
                hayAsignaturasValidas = true;
                System.out.println(asignatura.imprimir());
            }
        }

        // Si sí contiene, pero no son válidas:
        if (!hayAsignaturasValidas) {
            System.out.println("No hay asignaturas válidas.");
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }


    private static boolean asignaturaYaMatriculada(Asignatura[] asignaturasMatricula, Asignatura asignatura){

        if (asignaturasMatricula == null){
            throw new NullPointerException("La lista pasada no ha sido iniciada aún.");
        }
        if (asignatura == null) {
            throw new IllegalArgumentException("La asignatura a buscar en la lista no puede ser nula");
        }

        for (Asignatura asignat : asignaturasMatricula) {
            if (asignat != null && asignat.equals(asignatura)) {
                return true;
            }
        }

        return false;
    }


    public static Matricula leerMatricula(Alumnos alumnos, Asignaturas asignaturas) throws OperationNotSupportedException, NullPointerException, IllegalArgumentException{

        // Si parámetros están vacíos:
        if (alumnos == null) {
            throw new NullPointerException("La colección de alumnos no se inició.");
        }
        if (asignaturas == null) {
            throw new NullPointerException("La colección de asignaturas no se inició.");
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Introduzca los datos de la matrícula.");

        System.out.println("ID de la matrícula:");
        int idMatricula = Entrada.entero();

        System.out.println("Curso académico (AA-AA):");
        String cursoMatricula = Entrada.cadena();

        LocalDate fechaMatriculacion = leerFecha("Fecha de matriculación(DD/MM/AAAA):");

        ///////////////////////////////////////////////////////////////////////////////////
        // ELEGIR ALUMNO DE LA LISTA DEL OBJETO ALUMNOS
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Alumnos registrados:");
        Alumno alumnoSeleccionado;
        Alumno alumnoExistente;
        do{
            // Mostrar todos los alumnos no nulos de la colección Alumnos pasada...
            for (Alumno alumno : alumnos.get()) {
                if (alumno != null) {
                    System.out.println(alumno);
                }
            }

            // Si no contiene nada:
            if (alumnos.get() == null || alumnos.get().length == 0) {
                System.out.println("No hay asignaturas registradas.");
            }
            System.out.println("-----------------------------------------------------------------------------------------");

                //... Y pedir un alumno según DNI
            alumnoSeleccionado = getAlumnoPorDni();

            // Comprobar si el alumno seleccionado con getAlumnoPorDni() es nulo. Innecesario¿?
            if (alumnoSeleccionado == null) {
                System.out.println("Alumno no encontrado. Intente de nuevo.");
            }

            // Comprobar si el alumno seleccionado existe en la coleccion de Alumnos pasada
            alumnoExistente = alumnos.buscar(alumnoSeleccionado);

            // Si no existe en la colección de alumnos pasada:
            if (alumnoExistente == null){
                System.out.println("El alumno seleccionado por dni no está registrado.");
            }
        } while (alumnoExistente == null);

        ///////////////////////////////////////////////////////////////////////////////////
        // ELEGIR ASIGNATURA DE LA LISTA DEL OBJETO ASIGNATURAS
        int capacidadMax = 10;
        int contador = 0;
        boolean agregarMasAsignaturas = false;
        Asignatura[] asignaturasMatricula = new Asignatura[capacidadMax];

        do {
            // Mostrar todas las asignaturas no nulas de la coleccion Asignaturas pasada...
            mostrarAsignaturas(asignaturas);

            // ...y pedir una asignatura según código.
            Asignatura asignaturaSeleccionada = getAsignaturaPorCodigo();

            // Comprobar si la asignatura seleccionada existe en la coleccion de Asignaturas pasada
            Asignatura asignaturaExistente = asignaturas.buscar(asignaturaSeleccionada);

            // Si no existe en la colección de Asignaturas pasada:
            if (asignaturaExistente == null) {
                System.out.println("La asignatura seleccionada por código no está registrada.");
                continue;
                // continue hace que el resto del código en esa iteración se omita por completo.
                // La ejecución vuelve al inicio del bucle do-while, donde se repetirá el proceso para pedir
                // otra asignatura hasta que se seleccione una válida (cuando asignatura != null).
            }

            // Comprobar si la asignatura ya está en la matrícula
            boolean yaEnMatricula = asignaturaYaMatriculada(asignaturasMatricula, asignaturaExistente);

            // Si ya existe en la matrícula:
            if (yaEnMatricula) {
                System.out.println("La asignatura ya está en la matrícula. No se puede repetir.");
                continue;
            }

            // Comprobar si hay espacio en el array
            if (contador >= capacidadMax) {
                System.out.println("No se pueden añadir más asignaturas. Se ha alcanzado el límite de " + capacidadMax + " asignaturas.");
                break; // Salir del bucle ya que no se puede añadir más asignaturas
            }

            // Añadir la asignatura al array
            asignaturasMatricula[contador++] = asignaturaExistente;

            // Preguntar si se quieren añadir más asignaturas
            int respuesta;
            do{
                System.out.println("¿Desea añadir otra asignatura? (1-2):");
                System.out.println("1- Sí.");
                System.out.println("2- No.");
                respuesta = Entrada.entero();
            } while (respuesta < 1 || respuesta > 2);
            if (respuesta == 2){
                agregarMasAsignaturas = false;
            } else agregarMasAsignaturas = true;

        } while(agregarMasAsignaturas);

        ///////////////////////////////////////////////////////////////////////////////////

        return new Matricula(idMatricula, cursoMatricula, fechaMatriculacion, alumnoExistente, asignaturasMatricula);
    }


    public static Matricula getMatriculaPorIdentificacion() throws OperationNotSupportedException, IllegalArgumentException {
        System.out.println("Introduzca el ID de la matrícula.");
        int idMatricula = Entrada.entero();

        //Creo matrícula con los restantes atributos inventados
        LocalDate fechaMat = LocalDate.of(2024, 10, 3);
        LocalDate fechaNac = LocalDate.of(2000, 10, 3);
        Alumno alumno = new Alumno("Emilio", "53143489B", "emilio@gmail.com", "600793568", fechaNac);
        Asignatura[] coleccionAsignaturas = new Asignatura[10];

        return new Matricula(idMatricula, "24-25", fechaMat, alumno, coleccionAsignaturas);
    }


}


















