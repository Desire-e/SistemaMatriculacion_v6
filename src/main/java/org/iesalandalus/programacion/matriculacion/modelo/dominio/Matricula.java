package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Matricula {
    public static final int MAXIMO_MESES_ANTERIOR_ANULACION = 6;
    public static final int MAXIMO_DIAS_ANTERIOR_MATRICULA = 15;
    public static final int MAXIMO_NUMERO_HORAS_MATRICULA = 1000;
    public static final int MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA = 10;
    private static final String ER_CURSO_ACADEMICO = "[2]{1}[0-9]{1}-[2]{1}[0-9]{1}";
    public static final String FORMATO_FECHA = "dd/MM/yyyy";     //APLICAR EN METODOS IMPRIMIR Y TOSTRING
    private int idMatricula;
    private String cursoAcademico;
    private LocalDate fechaMatriculacion;
    private LocalDate fechaAnulacion;
    private Asignatura [] coleccionAsignaturas = new Asignatura[MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA];
    private Alumno alumno;


    public Matricula(int idMatricula, String cursoAcademico, LocalDate fechaMatriculacion, Alumno alumno,
                     Asignatura[] coleccionAsignaturas) throws OperationNotSupportedException{

        setIdMatricula(idMatricula);
        setCursoAcademico(cursoAcademico);
        setFechaMatriculacion(fechaMatriculacion);
        this.fechaAnulacion = null; // Fecha de anulación se establece como nula por defecto
        setAlumno(alumno);
        setColeccionAsignaturas(coleccionAsignaturas);

    }

    public Matricula(Matricula matricula) throws OperationNotSupportedException{

        //Validar nulo
        if (matricula == null){
            throw new NullPointerException("ERROR: No es posible copiar una matrícula nula.");
        }

        setIdMatricula(matricula.getIdMatricula());
        setCursoAcademico(matricula.getCursoAcademico());
        setFechaMatriculacion(matricula.getFechaMatriculacion());
        this.fechaAnulacion = matricula.getFechaAnulacion(); // Copiar fecha de anulación, puede ser nula
        setAlumno(matricula.getAlumno());
        setColeccionAsignaturas(matricula.getColeccionAsignaturas());
    }



    public int getIdMatricula() { return idMatricula; }
    public void setIdMatricula(int idMatricula) {

        //Validar nº positivo
        if (idMatricula <= 0) {
            throw new IllegalArgumentException("ERROR: El identificador de una matrícula no puede ser menor o igual a 0.");
        }

        this.idMatricula = idMatricula;
    }

    public String getCursoAcademico() { return cursoAcademico; }
    public void setCursoAcademico(String cursoAcademico) {

        //Valida nulo
        if (cursoAcademico == null){
            throw new NullPointerException("ERROR: El curso académico de una matrícula no puede ser nulo.");
        }

        //Valida vacío y patrón
        Pattern patronCursoAca = Pattern.compile(ER_CURSO_ACADEMICO);
        Matcher comparaCursoAcaPatron = patronCursoAca.matcher(cursoAcademico);
        if(cursoAcademico.isBlank() || cursoAcademico.isEmpty()){
            throw new IllegalArgumentException("ERROR: El curso académico de una matrícula no puede estar vacío.");
        }

        if (!comparaCursoAcaPatron.matches()){
            throw new IllegalArgumentException("ERROR: El formato del curso académico no es correcto.");
        }

        this.cursoAcademico = cursoAcademico;
    }

    public LocalDate getFechaMatriculacion() { return fechaMatriculacion; }
    public void setFechaMatriculacion(LocalDate fechaMatriculacion) {

        //Valida nulo
        if (fechaMatriculacion == null){
            throw new NullPointerException("ERROR: La fecha de matriculación de una mátricula no puede ser nula.");
        }

        //Validar que fecha NO sea posterior a hoy
        if (fechaMatriculacion.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("ERROR: La fecha de matriculación no puede ser posterior a hoy.");
        }

        //Debe ser fecha actual - 15 dias. Si es menor incluso, entonces excepcion
        if (fechaMatriculacion.isBefore(LocalDate.now().minusDays(MAXIMO_DIAS_ANTERIOR_MATRICULA))){
            throw new IllegalArgumentException("ERROR: La fecha de matriculación no puede ser anterior a " + MAXIMO_DIAS_ANTERIOR_MATRICULA + " días.");
        }

        this.fechaMatriculacion = fechaMatriculacion;
    }

    public LocalDate getFechaAnulacion() { return fechaAnulacion; }
    public void setFechaAnulacion(LocalDate fechaAnulacion) {

        //Valida nulo
        if (fechaAnulacion == null){
            throw new NullPointerException("La fecha de anulación no puede ser nulo");
        }

        //Validar que fecha NO sea posterior a hoy
        if (fechaAnulacion.isAfter(LocalDate.now())){
            throw new IllegalArgumentException("ERROR: La fecha de anulación de una matrícula no puede ser posterior a hoy.");
        }

        //Validar que no sea posterior a de 6 meses tras la fecha de Matriculacion
        if(fechaAnulacion.isAfter(getFechaMatriculacion().plusMonths(MAXIMO_MESES_ANTERIOR_ANULACION))){
            throw new IllegalArgumentException("ERROR: La fecha de anulación no puede ser anterior a la fecha de matriculación.");
        }

        //Validar que no sea antes de la fecha de matriculacion
        if(fechaAnulacion.isBefore(getFechaMatriculacion())){
            throw new IllegalArgumentException("ERROR: La fecha de anulación no puede ser anterior a la fecha de matriculación.");
        }

        this.fechaAnulacion = fechaAnulacion;
    }

    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno){

        //Validar nulo
        if (alumno == null){
            throw new NullPointerException("ERROR: El alumno de una matrícula no puede ser nulo.");
        }

        this.alumno = alumno;
    }

    public Asignatura[] getColeccionAsignaturas(){
        return coleccionAsignaturas;
    }
    public void setColeccionAsignaturas (Asignatura[] coleccionAsignaturas) throws OperationNotSupportedException {

        // Validar que no sea null
        if (coleccionAsignaturas == null) {
            throw new NullPointerException("ERROR: La lista de asignaturas de una matrícula no puede ser nula.");
        }
    /* Se crea con el máximo número de asignaturas, no se comprueba.
        // Validar tamaño mínimo y máximo
        if (coleccionAsignaturas.length < 1 || coleccionAsignaturas.length > MAXIMO_NUMERO_ASIGNATURAS_POR_MATRICULA) {
            throw new IllegalArgumentException("La colección debe tener 1 asignatura al menos y máximo 10.");
        }
    */
        //Validar que nº horas totales de todas las asignaturas de la colección no sea > 1000
        if (superaMaximoNumeroHorasMatricula(coleccionAsignaturas)){
            throw new OperationNotSupportedException("ERROR: No se puede realizar la matrícula ya que supera el máximo de horas permitidas (" + Matricula.MAXIMO_NUMERO_HORAS_MATRICULA + " horas).");
        }

        this.coleccionAsignaturas = coleccionAsignaturas;
    }



    private boolean superaMaximoNumeroHorasMatricula(Asignatura[] asignaturasMatricula ){
        int totalHoras = 0;

        /* Bucle sobre el array (en parámetros) de asignaturas en la matrícula, se asigna cada posición en cada bucle
           a la variable asignatura */
        for (Asignatura asignatura : asignaturasMatricula) {
            if (asignatura!=null){
                totalHoras += asignatura.getHorasAnuales();
            }
        }

        if (totalHoras > MAXIMO_NUMERO_HORAS_MATRICULA){
            return true;
        }
        return false;
    }



    //Para imprimir los nombres de las asignaturas que figuran en la colección de asignaturas
    private String asignaturasMatricula(){

        //Tomar array coleccionAsignaturas e imprimir nombres de esas asignaturas
        StringBuilder asignaturas = new StringBuilder();
        for (Asignatura asignatura : coleccionAsignaturas) {
            if (asignatura != null) {
                asignaturas.append(asignatura.getNombre()).append(", ");
            }
        }
        /* Si el StringBuilder no está vacío (hay asignaturas), elimina los últimos dos caracteres (coma y espacio
        de la última asignatura insertada)*/
        if (!asignaturas.isEmpty()) {
            asignaturas.setLength(asignaturas.length() - 2);
        }
        return asignaturas.toString();

    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matricula matricula = (Matricula) o;
        return idMatricula == matricula.idMatricula;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMatricula);
    }

    public String imprimir(){
        return "idMatricula=" + getIdMatricula() + ", " +
                "curso académico=" + getCursoAcademico() + ", " +
                "fecha matriculación=" + getFechaMatriculacion().format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) + ", " +
                "alumno=" + "{" + getAlumno().imprimir() + "}";
    }

    @Override
    public String toString() {

        if (fechaAnulacion == null){
            return "idMatricula=" + getIdMatricula() + ", " +
                    "curso académico=" + getCursoAcademico() + ", " +
                    "fecha matriculación=" + getFechaMatriculacion().format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) + ", " +
                    "alumno=" + getAlumno().imprimir() + ", " +
                    "Asignaturas=" + "{ " + asignaturasMatricula() + "}";

        } else return "idMatricula=" + getIdMatricula() + ", " +
                "curso académico=" + getCursoAcademico() + ", " +
                "fecha matriculación=" + getFechaMatriculacion().format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) + ", " +
                "fecha anulación=" + getFechaAnulacion().format(DateTimeFormatter.ofPattern(FORMATO_FECHA)) + ", " +
                "alumno=" + getAlumno().imprimir() + ", " +
                "Asignaturas=" + "{ " + asignaturasMatricula() + "}";
    }

}
