package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.*;

public class Alumno {

    /* Constantes y ER en static, pues todas las instancias comparten esa constante única, pertenece a la clase y no a
    la instancia */
    private static final String ER_TELEFONO = "[0-9]{9}";
    private static  final String ER_CORREO = "[a-zA-Z0-9._%+-]+@[a-z A-Z]{5,}.[a-zA-Z]{2,}";
    private static final String ER_DNI = "[0-9]{8}[A-Z a-z]";
    public static final String FORMATO_FECHA = "dd/MM/yyyy";
    private static final String ER_NIA = "[a-z]{4}[0-9]{3}";
    private static final int MIN_EDAD_ALUMNADO = 16;
    private String nombre;
    private String telefono;
    private String correo;
    private String dni;
    private LocalDate fechaNacimiento;
    private String nia;



    /*--------------------------------------------------------*/
    /* PATRONES
        - [xyz]: una sola letra que sea x/y/z.
        - [a-z]: solo acepta una letra (que puede ser desde a hasta z).
        - [A-Z a-z]: igual pero incluyendo mayus.
        - [0-9]: solo acepta 1 numero.
        - []+: uno o más caracteres.
        - []*: cero o mas caracteres.
        - []?: ninguno o un carácter
        - []{8}: admite 8 caracteres.
        - []{2,4}: admite 2, 3 o 4 caracteres.
        - []{5,}: admite 5 o más caracteres.
    */


    //Constructor con parámetros
    public Alumno (String nombre, String dni, String correo, String telefono, LocalDate fechaNacimiento){
        setNombre(nombre);
        setDni(dni);
        setCorreo(correo);
        setTelefono(telefono);
        setFechaNacimiento(fechaNacimiento);
        setNia();
    }

    //Constructor copia
    public Alumno (Alumno alumno){
        if (alumno == null) {
            throw new NullPointerException("ERROR: No es posible copiar un alumno nulo.");
        }

        setNombre(alumno.getNombre());
        setDni(alumno.getDni());
        setCorreo(alumno.getCorreo());
        setTelefono(alumno.getTelefono());
        setFechaNacimiento(alumno.getFechaNacimiento());
        setNia(alumno.getNia());
    }


    public String getNia(){ return nia; }
    //Establecer nia generado automáticamente en el siguiente setter, se encuentra en el constructor copia
    private void setNia (String nia){

        //Validar nulo
        if(nia == null){
            throw new NullPointerException("El NIA no puede estar vacío");
        }

        //Validar patrón
        Pattern patronNia = Pattern.compile(ER_NIA);
        Matcher comparaNiaPatron = patronNia.matcher(nia);
        this.nia=nia;
    }
    //Crear nia automático, se encuentra en el constructor
    private void setNia(){
        //Quito los espacios en un nombre, tomo sus 4 1º caracteres en minuscula. Así,
        //si un Alumno se llama Ian Pérez, su nia comenzará con 'ianp', y no con 'ian '.
        String letrasNombre = getNombre().replaceAll("\\s+", "");
        String letrasNia = letrasNombre.substring(0,4).toLowerCase();

        //3 últimos dígitos del dni.
        String numNia = getDni().substring(5,8);

        String niaAuto = letrasNia + numNia;
        this.nia=niaAuto;
    }

    public String getNombre(){ return nombre; }
    public void setNombre(String nombre){

        //Validar nulo
        if (nombre == null) {
            throw new NullPointerException("ERROR: El nombre de un alumno no puede ser nulo.");
        }

        //Validar vacío
        if (nombre.isBlank() || nombre.isEmpty()) {
            throw new IllegalArgumentException("ERROR: El nombre de un alumno no puede estar vacío.");
        }

        this.nombre=formateaNombre(nombre);                                          /* quizás hay que TRATAR*/
    }

    public String getDni() { return dni; }
    private void setDni(String dni) {
        //Validar nulo
        if (dni == null) {
            throw new NullPointerException("ERROR: El dni de un alumno no puede ser nulo.");
        }

        Pattern patronDni = Pattern.compile(ER_DNI);
        Matcher comparaDniPatron = patronDni.matcher(dni);
        //Validar vacío y patrón
        if (dni.isBlank() || dni.isEmpty() || !comparaDniPatron.matches()) {
            throw new IllegalArgumentException("ERROR: El dni del alumno no tiene un formato válido.");
        }

        //Validar letra dni y patrón
        if (!comprobarLetraDni(dni)) {
            throw new IllegalArgumentException("ERROR: La letra del dni del alumno no es correcta.");
        }

        this.dni = dni;
    }

    public String getTelefono() { return telefono; }
    public void setTelefono (String telefono) {

        //Validar nulo
        if (telefono == null) {
            throw new NullPointerException("ERROR: El teléfono de un alumno no puede ser nulo.");
        }

        //Validar vacío y patrón
        Pattern patronTlf = Pattern.compile(ER_TELEFONO);
        Matcher comparaTlfPatron = patronTlf.matcher(telefono);
        if (telefono.isBlank() || telefono.isEmpty() || !comparaTlfPatron.matches()) {
            throw new IllegalArgumentException("ERROR: El teléfono del alumno no tiene un formato válido.");
        }

        this.telefono = telefono;
    }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) {

        //Validar nulo
        if (correo == null) {
            throw new NullPointerException("ERROR: El correo de un alumno no puede ser nulo.");
        }

        //Validar vacío y patrón
        Pattern patronCorreo = Pattern.compile(ER_CORREO);
        Matcher comparaCorreoPatron = patronCorreo.matcher(correo);
        if (correo.isBlank() || correo.isEmpty() || !comparaCorreoPatron.matches()) {
            throw new IllegalArgumentException("ERROR: El correo del alumno no tiene un formato válido.");
        }

        this.correo = correo;
    }

    public LocalDate getFechaNacimiento(){ return fechaNacimiento; }
    private void setFechaNacimiento(LocalDate fechaNacimiento){

        //Validar nulo
        if (fechaNacimiento == null) {
            throw new NullPointerException("ERROR: La fecha de nacimiento de un alumno no puede ser nula.");
        }

        //Validar que fecha NO sea posterior a hoy
        if (fechaNacimiento.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("La fecha de nacimiento debe de ser anterior a la fecha actual");
        }

        //Validar que tengan 16 años mínimo: 'si fechaNacimiento es posterior a la fecha actual
        //restando 16 al año...'
        if (fechaNacimiento.isAfter(LocalDate.now().minusYears(MIN_EDAD_ALUMNADO))){
            throw new IllegalArgumentException("ERROR: La edad del alumno debe ser mayor o igual a 16 años.");

        }

        this.fechaNacimiento = fechaNacimiento;
    }

    private String getIniciales(){

        //Obtener nombre formateado
        String nombre = getNombre();
        StringBuilder iniciales = new StringBuilder();

        /* Almacenar en variable char que tomará valores de cada lugar del array. El
           array pasa de clase string a char para almacenarlo en la variable. */
        for (char letra : nombre.toCharArray()){
        /* Si letra es en mayusculas (estoy usando el nombre que ya está formateado), añadir letra
           al final del StringBuilder iniciales */
            if (Character.isUpperCase(letra)){
                iniciales.append(letra);
            }
        }
        return iniciales.toString().toUpperCase();
    }




    private String formateaNombre(String nombre){
        /* Elimina espacios de inicio y final con trim(). Elimina cualquier espacio (\s) que sea 2 o más
        seguidos (\\, +), reemplaza por 1 espacio */
        String nombreSinEspacios = nombre.trim().replaceAll("\\s+", " ");
        StringBuilder nombreFormateado = new StringBuilder();

        /* nombreParte va tomando cada valor del array (creado por los elementos de nombreSinEspacios que están
        separados por espacios */
        for (String nombreParte : nombreSinEspacios.split(" ")) {
            if (!nombreParte.isEmpty()) {
                /* .append(): agrega el carácter al final de un objeto StringBuilder.
                .substring(): devuelve los valores que hay entre los índices indicados por parámetro.
                .toUpperCase: clase Character método toUpperCase() pone caracter en mayus.

                Crear el nombreFormateado caracter a caracter: Se añade el 1er caracter de nombreParte en mayus,
                luego los demás carácteres hasta el final en minus. */
                //Primer caracter en mayusculas, demás minusculas, añade espacio entre cada nombreParte
                nombreFormateado.append(nombreParte.substring(0,1).toUpperCase())
                        //Demás en minusculas
                        .append(nombreParte.substring(1).toLowerCase())
                        //Añade espacio entre cada nombreParte
                        .append(" ");
            }
        }

        /* nombreFormateado pasa de ser StringBuilder a String, y se le elimina el espacio final, del for-each */
        return nombreFormateado.toString().trim();

    }


    /*
    Expresiones regulares:

    - Crear una cadena que contenga la expresión regular.
      private final String ER_DNI = "[0-9]{8}[A-Z a-z]";
    - Compilar dicha expresión regular en un patrón, mediante la clase Pattern.
      Pattern patronDni = Pattern.compile(ER_DNI);
    - Comprobar las coincidencias de dicho patrón en un texto dado, mediante la clase Matcher.
      Matcher comparaDniPatron = patronDni.matcher(dni);
    */
    private boolean comprobarLetraDni(String dni){
        //Expresión regular.
        Pattern patronDni = Pattern.compile(ER_DNI);
        Matcher comparaDniPatron = patronDni.matcher(dni);

        //Para verificar si el DNI es válido: dividir numero del dni entre 23. Comprobar tabla.

        //Separar letras y numero.
        int numDni = Integer.parseInt(dni.substring(0, 8));
        String letraDni = dni.substring(8);

        //Declarar, crear e inicializar un array.
        String[] tablaLetras = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S",
                                "Q", "V", "H", "L", "C", "K", "E" };

        //Calcula resto. El resto es la posición, en array tablaLetras, donde está la letra que debe estar en el dni
        int restoNumDni = numDni % 23;
        String letraDniCorrecta = tablaLetras[restoNumDni];

        //Comprobar que la letra del dni introducido coincide con la letraDniCorrecta establecida por la división y el array
        if (!letraDni.equals(letraDniCorrecta)){
            System.out.println("La letra del DNI no coincide.");
            return false;

        } else {
            return true;
        }

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alumno alumno = (Alumno) o;
        return Objects.equals(dni, alumno.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }


    public String imprimir(){
        return "INFORMACIÓN DEL ALUMNO:\n" +
                "nombre:" + getNombre() +
                "\ntelefono:" + getTelefono() +
                "\ncorreo:" + getCorreo() +
                "\ndni:" + getDni() +
                "\nfechaNacimiento:" + getFechaNacimiento().format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) +
                "\nnia:" + getNia();
    }


    @Override
    public String toString() {
        return String.format(
                "Número de Identificación del Alumnado (NIA)=" + getNia() + " " +
                        "nombre=" + getNombre() + " (" + getIniciales() + ")" + ", " +
                        "DNI=" + getDni() + ", " +
                        "correo=" + getCorreo() + ", " +
                        "teléfono=" + getTelefono() + ", " +
                        "fecha nacimiento=" + getFechaNacimiento().format(DateTimeFormatter.ofPattern(FORMATO_FECHA))
        );
    }
}
