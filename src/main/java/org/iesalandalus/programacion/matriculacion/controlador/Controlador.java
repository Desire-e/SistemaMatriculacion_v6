package org.iesalandalus.programacion.matriculacion.controlador;

import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

import javax.naming.OperationNotSupportedException;
import java.util.List;

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
        return modelo.buscar(alumno);
    }
    public Asignatura buscar(Asignatura asignatura) {
        return modelo.buscar(asignatura);
    }
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        return modelo.buscar(cicloFormativo);
    }
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException {
        return modelo.buscar(matricula);
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


    public List<Alumno> getAlumnos() {
        return modelo.getAlumnos();
    }
    public List<Asignatura> getAsignaturas() {
        return modelo.getAsignaturas();
    }
    public List<CicloFormativo> getCiclosFormativos() {
        return modelo.getCiclosFormativos();
    }
    public List<Matricula> getMatriculas() throws OperationNotSupportedException {
        return modelo.getMatriculas();
    }


    public List<Matricula> getMatriculas(Alumno alumno) throws OperationNotSupportedException{
        return modelo.getMatriculas(alumno);

        /* PASADO AL MODELO

        //Matriculas registradas en el sistema
        Matricula[] matriculas = modelo.getMatriculas();
        //Matriculas del alumno
        int tamanio = 1;
        Matricula[] matriculasAlumno = new Matricula[tamanio];
        int indice = 0;
        //Si en la matricula registrada, su alumno == al alumno obtenido por parámetro, se almacena en el array de
        //matriculasAlumno
        for (int i = 0; i < matriculas.length; i++) {
            if (matriculas[i] != null && matriculas[i].getAlumno().equals(alumno)) {
                matriculasAlumno[indice] = matriculas[i];
                indice++;
                tamanio++;
            }
        }
        return matriculasAlumno;*/
    }
    public List<Matricula> getMatriculas(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        return modelo.getMatriculas(cicloFormativo);

        /* PASADO AL MODELO

        //Matriculas registradas en el sistema
        Matricula[] matriculas = modelo.getMatriculas();
        //Matriculas del ciclo
        int tamanio = 1;
        Matricula[] matriculasCiclo = new Matricula[tamanio];
        int indice = 0;
        //La matricula tiene asignatura y la asignatura tiene ciclo
        boolean hayMatriculas = false;
        for (int i = 0; i < matriculas.length; i++) {
            if (matriculas[i] != null) {
                // Comprobar si alguna de las asignaturas de la matrícula pertenece al ciclo formativo
                for (Asignatura asignatura : matriculas[i].getColeccionAsignaturas()) {
                    if (asignatura != null && asignatura.getCicloFormativo().equals(cicloFormativo)) {
                        matriculasCiclo[indice] = matriculas[i];
                        indice++;
                        tamanio++;
                    }
                }
            }
        }
        return matriculasCiclo;*/
    }
    public List<Matricula> getMatriculas(String cursoAcademico) throws OperationNotSupportedException {
        return modelo.getMatriculas(cursoAcademico);

        /* PASADO AL MODELO

        Matricula[] matriculas = modelo.getMatriculas();
        int tamanio = 1;
        Matricula[] matriculasCursoAca = new Matricula[tamanio];
        int indice = 0;
        for (int i = 0; i < matriculas.length; i++) {
            if (matriculas[i] != null && matriculas[i].getCursoAcademico().equals(cursoAcademico)) {
                matriculasCursoAca[indice] = matriculas[i];
                indice++;
                tamanio++;
            }
        }
        return matriculasCursoAca;*/
    }

}
