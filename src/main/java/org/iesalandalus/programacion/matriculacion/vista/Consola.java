package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.controlador.Controlador;



import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Consola {


    private Consola(){}

    public static void mostrarMenu(){

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("SISTEMA DE MATRICULACIÓN.");
        System.out.println("-----------------------------------------------------------------------------------------");
        // Imprime todos los objetos del array Opcion.values(), dando todos los objetos dentro de la clase enum Opcion
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion);
        }
    }



    /* SOBRE EL USO DE STREAM:
       En este caso no es recomendable usar Stream porque el método depende de una entrada del usuario y de una
       estructura de control iterativa (do-while).
       Los Streams están diseñados para operar sobre colecciones de datos de manera funcional, pero no para manejar
       bucles controlados por entrada de usuario.

       Aquí no estamos transformando ni filtrando una colección, sino esperando una entrada y validándola hasta que
       sea correcta.
    */
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
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println("Entrada inválida. Introduzca un número entre 0 y 19.");
            }
        } while (opcionSeleccionada == null);

        return opcionSeleccionada;
    }



    public static Alumno leerAlumno() {
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


    public static Alumno getAlumnoPorDni() {

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


    //CAMBIO V.3:
    public static TiposGrado leerTiposGrado(){
        TiposGrado tipoGradoSeleccionado = null;

        do {

            try {

                System.out.println("Elija un grado (0-1):");
                for (TiposGrado tipoGrado : TiposGrado.values()) {
                    System.out.println(tipoGrado.imprimir());
                }

                int opcion = Entrada.entero();
                tipoGradoSeleccionado = TiposGrado.values()[opcion];

                // Opcion.values() devuelve un array con los valores del enumerado, y cualquier número que
                // no corresponda a un índice de ese array genera esta excepción:
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Opción fuera de rango");
                // Otras entradas inválidas (cadenas):
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println("Entrada inválida. Introduzca un número entre 0 y 19.");
            }

        } while (tipoGradoSeleccionado == null);

        return tipoGradoSeleccionado;
    }


    //CAMBIO V.3:
    public static Modalidad leerModalidad(){
        Modalidad modalidadSeleccionada = null;

        do {

            try {

                System.out.println("Elija una modalidad (0-1)");
                for (Modalidad tipoGrado : Modalidad.values()) {
                    System.out.println(tipoGrado.imprimir());
                }

                int opcion = Entrada.entero();
                modalidadSeleccionada = Modalidad.values()[opcion];

                // Opcion.values() devuelve un array con los valores del enumerado, y cualquier número que
                // no corresponda a un índice de ese array genera esta excepción:
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Opción fuera de rango");
                // Otras entradas inválidas (cadenas):
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println("Entrada inválida. Introduzca un número entre 0 y 19.");
            }

        } while (modalidadSeleccionada == null);

        return modalidadSeleccionada;
    }


    //CAMBIO V.3:
    //Modifica el método leerGrado para que en función del tipo de grado del Ciclo Formativo elegido
    // por el usuario, cree el objeto correspondiente (Grado D o Grado E) con los atributos necesarios.
    public static Grado leerGrado(){
        /*
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
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println("Entrada inválida. Introduzca un número entre 0 y 2.");
            }
        } while (grado == null);

        return grado;
        */


        //CAMBIOS V.3:
        Grado grado = null;

        TiposGrado tipo = leerTiposGrado();
//        if (tipo == null) {
//            throw new NullPointerException("El tipo de grado no puede ser nulo");
//        }

        if (tipo == TiposGrado.GRADOD){
            System.out.println("Introduzca el nombre del grado:");
            String nombre = Entrada.cadena();
            System.out.println("Introduzca el número de años del grado (2 o 3):");
            int numAnios = Entrada.entero();
            Modalidad modalidad = leerModalidad();
            grado = new GradoD(nombre, numAnios, modalidad);
        }

        if (tipo == TiposGrado.GRADOE){
            System.out.println("Introduzca el nombre del grado:");
            String nombre = Entrada.cadena();
            System.out.println("Introduzca el número de años del grado (debe ser 1):");
            int numAnios = Entrada.entero();
            System.out.println("Introduzca el número de ediciones del grado (mayor a 0):");
            int numEdiciones = Entrada.entero();
            grado = new GradoE(nombre, numAnios, numEdiciones);

        }

        return grado;
    }



    public static CicloFormativo leerCicloFormativo() {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Introduzca los datos del ciclo formativo.");

        System.out.println("Código del ciclo:");
        int codigoCiclo = Entrada.entero();

        System.out.println("Familia profesional del ciclo:");
        String familiaProfCiclo = Entrada.cadena();

        System.out.println("Nombre del ciclo:");
        String nombreCiclo = Entrada.cadena();

        System.out.println("Horas del ciclo:");
        int horasCiclo = Entrada.entero();

        //CAMBIO V.3:
        // Llamar al método para seleccionar el tipo de grado e introducir los datos de este.
        System.out.println("Datos del grado:");
        Grado gradoCiclo = leerGrado();

        return new CicloFormativo(codigoCiclo, familiaProfCiclo, gradoCiclo, nombreCiclo, horasCiclo);
    }


    public static void mostrarCiclosFormativos(List<CicloFormativo> ciclosFormativos) {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Ciclos formativos existentes:");

        // Si no contiene nada:
        if (ciclosFormativos == null || ciclosFormativos.isEmpty()) {
            System.out.println("No hay ciclos formativos registrados.");
        }
        boolean hayCiclosValidos = false;
        for (CicloFormativo ciclo : ciclosFormativos) {
            if (ciclo != null) {
                hayCiclosValidos = true;
                System.out.println(ciclo.imprimir());
            }
        }

        if (!hayCiclosValidos) {
            System.out.println("No hay ciclos formativos válidos.");
        }

        System.out.println("-----------------------------------------------------------------------------------------");
    }

    public static CicloFormativo getCicloFormativoPorCodigo() {
        System.out.println("Introduzca el código del ciclo formativo: ");
        int codigoCiclo = Entrada.entero();
        //CAMBIO V.3:
        Grado grado = new GradoD("grado básico", 2, Modalidad.PRESENCIAL);

        return new CicloFormativo(codigoCiclo, "Informática", grado, "Base de Datos", 100);
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
            } catch (NullPointerException | IllegalArgumentException e) {
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
            } catch (NullPointerException | IllegalArgumentException e) {
                System.out.println("Entrada inválida. Introduzca un número entre 0 y 19.");
            }
        } while (especialidad == null);

        return especialidad;
    }


    public static Asignatura leerAsignatura(CicloFormativo cicloFormativo) {

        // Validar que la colección de ciclos formativos no sea nula ni esté vacía
        if (cicloFormativo == null) {
            System.out.println("El ciclo porporcionado es nulo.");
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

        return new Asignatura(codAsignatura, nombreAsignatura, horasAnualAsignatura, cursoAsignatura, horasDesdoAsignatura, especialidadAsignatura, cicloFormativo);
    }


    public static Asignatura getAsignaturaPorCodigo() {
        System.out.println("Introduzca el código de la asignatura.");
        String codigoAsignatura = Entrada.cadena();

        //CAMBIO V.3:
        Grado grado = new GradoD("grado básico", 2, Modalidad.PRESENCIAL);
        CicloFormativo ciclo = new CicloFormativo(5000, "Informática", grado, "DAM", 10);

        return new Asignatura(codigoAsignatura, "Programación", 200, Curso.PRIMERO, 3, EspecialidadProfesorado.INFORMATICA, ciclo);
    }


    private static void mostrarAsignaturas(List<Asignatura> asignaturas){
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Asignaturas existentes:");

        // Si no contiene nada:
        if (asignaturas.isEmpty()) {
            System.out.println("No hay asignaturas registradas.");
        }
        /*
        // Si sí contiene, recorrer y mostrar cada asignatura:
        boolean hayAsignaturasValidas = false;
        for (Asignatura asignatura : asignaturas) {
            if (asignatura != null) {
                hayAsignaturasValidas = true;
                System.out.println(asignatura.imprimir());
            }
        }
        */

        /* USANDO STREAM
           Diferencia forEach() y peek():

           > .peek() no es terminal (puedes llamar a más métodos tras este); se usa para efectos secundarios durante
           las operaciones intermedias de un Stream, como inspeccionar los elementos o realizar alguna acción
           (en este caso, imprimir).
           > .forEach() es terminal; se usa para realizar una operación sobre cada elemento.
        */
        // Filtramos las asignaturas nulas y las mostramos
        boolean hayAsignaturasValidas = asignaturas.stream()
                .filter(asignatura -> asignatura != null)
                .peek(asignatura -> System.out.println(asignatura.imprimir())) // Imprime las asignaturas válidas
                .count() > 0;              // Verifica si hay asignaturas válidas. Devuelve true o false.

        // Si no hay asignaturas válidas
        if (!hayAsignaturasValidas) {
            System.out.println("No hay asignaturas válidas.");
        }
        System.out.println("-----------------------------------------------------------------------------------------");
    }


    static boolean asignaturaYaMatriculada(List<Asignatura> asignaturasMatricula, Asignatura asignatura){
        if (asignaturasMatricula == null){
            throw new NullPointerException("La lista de asignaturas de la matrícula pasada no ha sido iniciada aún.");
        }
        if (asignatura == null) {
            throw new IllegalArgumentException("La asignatura a buscar en la lista no puede ser nula");
        }

        return asignaturasMatricula.contains(asignatura);//devuelve boolean
    }


    public static Matricula leerMatricula(Alumno alumno, List<Asignatura> asignaturas) throws OperationNotSupportedException{

        // Si parámetros están vacíos:
        if (alumno == null) {
            throw new NullPointerException("El alumno es nulo.");
        }
        if (asignaturas == null || asignaturas.isEmpty()) {
            throw new IllegalArgumentException("No hay asignaturas registradas en el sistema .");
        }

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("Introduzca los datos de la matrícula.");

        System.out.println("ID de la matrícula:");
        int idMatricula = Entrada.entero();

        System.out.println("Curso académico (AA-AA):");
        String cursoMatricula = Entrada.cadena();

        LocalDate fechaMatriculacion = leerFecha("Fecha de matriculación(DD/MM/AAAA):");

        ///////////////////////////////////////////////////////////////////////////////////

        System.out.println("Alumno seleccionado:");
        System.out.println(alumno);

        ///////////////////////////////////////////////////////////////////////////////////

        // ELEGIR ASIGNATURA DE LA LISTA DE ASIGNATURAS REGISTRADAS EN EL SISTEMA.
        List<Asignatura> asignaturasMatricula = elegirAsignaturasMatricula(asignaturas);

        ///////////////////////////////////////////////////////////////////////////////////

        return new Matricula(idMatricula, cursoMatricula, fechaMatriculacion, alumno, asignaturasMatricula);
    }



    public static List<Asignatura> elegirAsignaturasMatricula(List<Asignatura> asignaturas){
        //Nos dan por parámetro el array de asignaturas registradas en el sistema.
        if (asignaturas.isEmpty()){
            throw new IllegalArgumentException("No hay asignaturas registradas para poder seleccionar.");
        }

        List<Asignatura> asignaturasMatriculadas = new ArrayList<>();
        boolean agregarMasAsignaturas = false;
        do {
            // Mostrar todas las asignaturas de la coleccion pasada...
            mostrarAsignaturas(asignaturas);

            // ...y pedir una asignatura según código. Tomar solo el código para tomar la asignatura real de la lista
            String codigoAsignatura = getAsignaturaPorCodigo().getCodigo();

            /*
            Asignatura asignaturaSeleccionada = null;
            for (Asignatura asignatura : asignaturas){
                if (asignatura != null && asignatura.getCodigo().equals(codigoAsignatura)){
                    asignaturaSeleccionada = asignatura;
                    break;
                }
            }
            */

            Asignatura asignaturaSeleccionada = asignaturas.stream()
                    .filter(asignatura -> asignatura != null && asignatura.getCodigo().equals(codigoAsignatura))
                    .findFirst()            // Obtiene la primera coincidencia (se asimila al uso de break).
                                            // Devuelve obj Optional<Asignatura>
                    .orElse(null);    // Extrae el valor del optional si existe, si no devuelve null.
                                            // Esto convierte el Optional<Asignatura> en un Asignatura directamente
            /*
            Alternativas sin null para maenajar objetos Optional:
            > Usando ifPresent(): Evita el uso de null.
                Optional<Asignatura> optionalAsignatura = asignaturas.stream()
                            .filter(asignatura -> asignatura.getCodigo().equals(codigoAsignatura))
                            .findFirst();
                optionalAsignatura.ifPresent(asignatura -> {
                            System.out.println("Asignatura encontrada: " + asignatura);
                            });
            > Usando orElseThrow(): Si quieres lanzar una excepción en lugar de usar null.
                Asignatura asignaturaSeleccionada = asignaturas.stream()
                            .filter(asignatura -> asignatura.getCodigo().equals(codigoAsignatura))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Asignatura no encontrada."));
            > Usando orElseGet(): Si necesitas un valor por defecto
                Asignatura asignaturaSeleccionada = asignaturas.stream()
                            .filter(asignatura -> asignatura.getCodigo().equals(codigoAsignatura))
                            .findFirst()
                            .orElseGet(() -> new Asignatura("Código por defecto", "Nombre por defecto"));
            > Usando map(): Si solo necesitas extraer un dato de la asignatura y manejar la ausencia del valor
            con orElse() o orElseThrow().
                String nombreAsignatura = asignaturas.stream()
                            .filter(asignatura -> asignatura.getCodigo().equals(codigoAsignatura))
                            .findFirst()
                            .map(Asignatura::getNombre)
                            .orElse("Nombre desconocido");
            */


            // Comprobar si la lista obtenida contiene la asignaturaSeleccionada
            if (!asignaturas.contains(asignaturaSeleccionada) || asignaturaSeleccionada==null) {
                System.out.println("La asignatura seleccionada por código no está registrada en el sistema.");
                continue;
            }

            // Comprobar que no se añade una asignatura ya añadida antes
            if (asignaturaYaMatriculada(asignaturasMatriculadas, asignaturaSeleccionada)) {
                System.out.println("La asignatura ya está en la matrícula. No se puede repetir.");
                continue;
            }

            // Añadir la asignatura al array
            asignaturasMatriculadas.add(asignaturaSeleccionada);

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

        return asignaturasMatriculadas;
    }



    public static Matricula getMatriculaPorIdentificacion() throws OperationNotSupportedException {
        System.out.println("Introduzca el ID de la matrícula.");
        int idMatricula = Entrada.entero();

        //Creo matrícula con los restantes atributos inventados
        LocalDate fechaMat = LocalDate.now().minusDays(2);
        LocalDate fechaNac = LocalDate.of(2000, 10, 3);
        Alumno alumno = new Alumno("Emilio", "53143489B", "emilio@gmail.com", "600793568", fechaNac);
        List<Asignatura> coleccionAsignaturas = new ArrayList<>();

        return new Matricula(idMatricula, "24-25", fechaMat, alumno, coleccionAsignaturas);
    }

}







