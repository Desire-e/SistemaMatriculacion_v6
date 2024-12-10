package org.iesalandalus.programacion.matriculacion.dominio;

import java.util.Objects;

public class CicloFormativo {
    public static final int MAXIMO_NUMERO_HORAS = 2000;
    private int codigo;
    private String familiaProfesional;
    private Grado grado;
    private String nombre;
    private int horas;



    //Constructor
    public CicloFormativo(int codigo, String familiaProfesional, Grado grado, String nombre, int horas) {
        setCodigo(codigo);
        setFamiliaProfesional(familiaProfesional);
        setGrado(grado);
        setNombre(nombre);
        setHoras(horas);
    }

    //Constructor copia
    public CicloFormativo(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: No es posible copiar un ciclo formativo nulo.");
        }

        setCodigo(cicloFormativo.getCodigo());
        setFamiliaProfesional(cicloFormativo.getFamiliaProfesional());
        setGrado(cicloFormativo.getGrado());
        setNombre(cicloFormativo.getNombre());
        setHoras(cicloFormativo.getHoras());

    }



    public int getCodigo() {
        return codigo;
    }
    private void setCodigo(int codigo) {

        //Validar que mide 4 dígitos
        String codigoString = Integer.toString(codigo);
        if (codigoString.length() != 4) {
            throw new IllegalArgumentException("El código debe contener 4 dígitos.");
        }


        this.codigo = codigo;
    }

    public String getFamiliaProfesional() {
        return familiaProfesional;
    }
    public void setFamiliaProfesional(String familiaProfesional) {

        //Validar nulo
        if(familiaProfesional == null){
            throw new NullPointerException("ERROR: La familia profesional de un ciclo formativo no puede ser nula.");
        }
        //Validar vacío
        if(familiaProfesional.isEmpty() || familiaProfesional.isBlank()){
            throw new IllegalArgumentException("ERROR: La familia profesional no puede estar vacía.");
        }

        this.familiaProfesional = familiaProfesional;
    }

    public Grado getGrado() {
        return grado;
    }
    public void setGrado(Grado grado) {

        //Validar nulo
        if(grado == null){
            throw new NullPointerException("ERROR: El grado de un ciclo formativo no puede ser nulo.");
        }

        this.grado = grado;
    }

    public String getNombre(){

        return nombre;
    }
    public void setNombre(String nombre){

        //Validar nulo
        if(nombre == null){
            throw new NullPointerException("ERROR: El nombre de un ciclo formativo no puede ser nulo.");
        }
        //Validar vacío
        if(nombre.isEmpty() || nombre.isBlank()){
            throw new IllegalArgumentException("ERROR: El nombre de un ciclo formativo no puede estar vacío.");
        }

        this.nombre=nombre;
    }

    public int getHoras(){
        return horas;
    }
    public void setHoras(int horas) {

        //Validar que no supere el nº máximo de horas
        if (horas > MAXIMO_NUMERO_HORAS || horas<=0 ){
            throw new IllegalArgumentException("ERROR: El número de horas de un ciclo formativo no puede ser menor o igual a 0 ni mayor a 2000.");
        }

        this.horas = horas;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CicloFormativo that = (CicloFormativo) o;
        return codigo == that.codigo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }

    public String imprimir() {
        return "Código ciclo formativo=" + codigo + ", " +
                "nombre ciclo formativo=" + nombre;
    }

    @Override
    public String toString() {
        return "Código ciclo formativo=" + codigo + ", " +
                "familia profesional=" + familiaProfesional + ", " +
                "grado=" + grado + ", " +
                "nombre ciclo formativo=" + nombre + ", " +
                "horas=" + horas;

    }
}
