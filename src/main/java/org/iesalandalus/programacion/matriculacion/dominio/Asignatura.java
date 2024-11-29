package org.iesalandalus.programacion.matriculacion.dominio;

import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Asignatura {
    public static final int MAX_NUM_HORAS_ANUALES = 300;
    public static final int MAX_NUM_HORAS_DESDOBLES = 6;
    public static final String ER_CODIGO = "[0-9]{4}";
    private String codigo;
    private String nombre;
    private int horasAnuales;
    private Curso curso;
    private int horasDesdoble;
    private EspecialidadProfesorado especialidadProfesorado;
    private CicloFormativo cicloFormativo;



    //Constructor con parámetros
    public Asignatura (String codigo, String nombre, int horasAnuales, Curso curso, int horasDesdoble,
                       EspecialidadProfesorado especialidadProfesorado, CicloFormativo cicloFormativo) {
        setCodigo(codigo);
        setNombre(nombre);
        setHorasAnuales(horasAnuales);
        setCurso(curso);
        setHorasDesdoble(horasDesdoble);
        setEspecialidadProfesorado(especialidadProfesorado);
        setCicloFormativo(cicloFormativo);
    }

    //Constructor copia
    public Asignatura(Asignatura asignatura) {
        if (asignatura == null){
            throw new NullPointerException("ERROR: No es posible copiar una asignatura nula.");
        }

        setCodigo(asignatura.getCodigo());
        setNombre(asignatura.getNombre());
        setHorasAnuales(asignatura.getHorasAnuales());
        setCurso(asignatura.getCurso());
        setHorasDesdoble(asignatura.getHorasDesdoble());
        setEspecialidadProfesorado(asignatura.getEspecialidadProfesorado());
        setCicloFormativo(asignatura.getCicloFormativo());
    }


    public CicloFormativo getCicloFormativo(){
        return cicloFormativo;
    }

    public void setCicloFormativo(CicloFormativo cicloFormativo){

        //Validar nulo.
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: El ciclo formativo de una asignatura no puede ser nulo.");
        }

        this.cicloFormativo = cicloFormativo;
    }
    public String getCodigo() { return codigo; }
    private void setCodigo(String codigo){

        //Valida nulo
        if(codigo == null){
            throw new NullPointerException("ERROR: El código de una asignatura no puede ser nulo.");
        }

        //Valida vacío
        if(codigo.isEmpty() || codigo.isBlank()){
            throw new IllegalArgumentException("ERROR: El código de una asignatura no puede estar vacío.");
        }

        //Valida patrón
        Pattern patronCod = Pattern.compile(ER_CODIGO);
        Matcher comparaCodPatron = patronCod.matcher(codigo);
        if(!comparaCodPatron.matches()){
            throw new IllegalArgumentException("ERROR: El código de la asignatura no tiene un formato válido.");
        }

        this.codigo = codigo;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre){

        //Valida nulo
        if(nombre == null){
            throw new NullPointerException("ERROR: El nombre de una asignatura no puede ser nulo.");
        }

        //Valida vacío
        if(nombre.isEmpty() || nombre.isBlank()){
            throw new IllegalArgumentException("ERROR: El nombre de una asignatura no puede estar vacío.");
        }

        this.nombre = nombre;
    }

    public int getHorasAnuales(){ return horasAnuales; }
    public void setHorasAnuales(int horasAnuales){

        //Valida valores incorrectos
        if (horasAnuales > MAX_NUM_HORAS_ANUALES || horasAnuales <= 0) {
            throw new IllegalArgumentException("ERROR: El número de horas de una asignatura no puede ser menor o igual a 0 ni mayor a " + MAX_NUM_HORAS_ANUALES + ".");
        }

        this.horasAnuales=horasAnuales;
    }

    public Curso getCurso(){ return curso; }
    public void setCurso(Curso curso){

        //Valida nulo
        if(curso == null){
            throw new NullPointerException("ERROR: El curso de una asignatura no puede ser nulo.");
        }

        this.curso=curso;
    }

    public int getHorasDesdoble(){ return horasDesdoble; }
    public void setHorasDesdoble(int horasDesdoble){

        //Valida valores incorrectos
        if (horasDesdoble > MAX_NUM_HORAS_DESDOBLES || horasDesdoble <= 0) {
            throw new IllegalArgumentException("ERROR: El número de horas de desdoble de una asignatura no puede ser menor a 0 ni mayor a " + MAX_NUM_HORAS_DESDOBLES + ".");
        }

        this.horasDesdoble=horasDesdoble;
    }

    public EspecialidadProfesorado getEspecialidadProfesorado(){ return especialidadProfesorado; }
    public void setEspecialidadProfesorado(EspecialidadProfesorado especialidadProfesorado){

        //Valida nulo
        if(especialidadProfesorado == null){
            throw new NullPointerException("ERROR: La especialidad del profesorado de una asignatura no puede ser nula.");
        }

        this.especialidadProfesorado=especialidadProfesorado;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asignatura that = (Asignatura) o;
        return Objects.equals(codigo, that.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    public String imprimir(){
        return "Código asignatura="+ getCodigo() + ", " +
                "nombre asignatura=" + getNombre() + ", " +
                "ciclo formativo=" + cicloFormativo.imprimir();
    }

    @Override
    public String toString() {
        return "Código=" + codigo + ", " +
                "nombre=" + nombre + ", " +
                "horas anuales=" + horasAnuales + ", " +
                "curso=" +  curso + ", " +
                "horas desdoble=" + horasDesdoble + ", " +
                "ciclo formativo=" + cicloFormativo.imprimir() + ", " +
                "especialidad profesorado=" + especialidadProfesorado;
    }
}

