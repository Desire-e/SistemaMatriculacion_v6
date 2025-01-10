package org.iesalandalus.programacion.matriculacion.controlador;

import org.iesalandalus.programacion.matriculacion.MainApp;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Matriculas;
import org.iesalandalus.programacion.matriculacion.vista.Consola;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

import javax.naming.OperationNotSupportedException;

public class Controlador {
    private Modelo modelo;
    private Vista vista;


    /*Crea el constructor con parámetros que comprobará que no son nulos y los asignará a los atributos.
    Además, recuerda llamar al método setControlador de la vista con una instancia suya.*/
    public Controlador(Modelo modelo, Vista vista){
        if(modelo==null){
            throw new NullPointerException("ERROR: Modelo nulo.");
        }
        if(vista==null){
            throw new NullPointerException("ERROR: Vista nula.");
        }

        this.modelo=modelo;
        this.vista=vista;
        //INSTANCIAR A LA VISTA Y LLAMAR AL METODO SETCONTROLADOR
        this.vista.setControlador(this);

    }


    /*Crea los métodos comenzar y terminar, que llamarán a los correspondientes métodos en el modelo y en la vista.*/
    public void comenzar(){
        modelo.comenzar();
        vista.comenzar();
    }
    public void terminar(){
        modelo.terminar();
        vista.terminar();
    }


    /*Crea los demás métodos que realizarán operaciones de insertar, buscar, borrar y listar. Éstos simplemente
    harán una llamada al correspondiente método del modelo.*/
    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        modelo.insertar(alumno);
    }
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        modelo.insertar(asignatura);
    }
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        modelo.insertar(cicloFormativo);
    }
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        modelo.insertar(matricula);
    }


    public Alumno buscar(Alumno alumno) {
        modelo.buscar(alumno);
        return alumno;
    }
    public Asignatura buscar(Asignatura asignatura) {
        modelo.buscar(asignatura);
        return asignatura;
    }
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        modelo.buscar(cicloFormativo);
        return cicloFormativo;
    }
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException {
        modelo.buscar(matricula);
        return matricula;
    }


    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        modelo.borrar(alumno);
    }
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        modelo.borrar(asignatura);
    }
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        modelo.borrar(cicloFormativo);
    }
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        modelo.borrar(matricula);
    }


    public Alumno[] getAlumnos() {
        return modelo.getAlumnos();
    }
    public Asignatura[] getAsignaturas() {
        return modelo.getAsignaturas();
    }
    public CicloFormativo[] getCiclosFormativos() {
        return modelo.getCiclosFormativos();
    }
    public Matricula[] getMatriculas() throws OperationNotSupportedException {
        return modelo.getMatriculas();
    }


    public Matricula[] getMatriculas(Alumno alumno) throws OperationNotSupportedException{
        Matriculas matriculas = null;
        return matriculas.get(alumno);
    }
    public Matricula[] getMatriculas(CicloFormativo cicloFormativo) {
        Matriculas matriculas = null;
        return matriculas.get(cicloFormativo);
    }
    public Matricula[] getMatriculas(String cursoAcademico) {
        Matriculas matriculas = null;
        return matriculas.get(cursoAcademico);
    }

}
