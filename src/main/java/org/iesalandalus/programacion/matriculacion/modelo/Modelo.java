package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Matriculas;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;

import javax.naming.OperationNotSupportedException;
import java.util.List;

/*Esta clase gestionará el modelo de datos de nuestra aplicación. Será la encargada de comunicarse
con las tres clases que hacen referencia a las colecciones de datos (alumnos, asignaturas, ciclos
formativos y matrículas).*/

public class Modelo {
    //public int CAPACIDAD=1000;
    private Alumnos alumnos;
    private Matriculas matriculas;
    private Asignaturas asignaturas;
    private CiclosFormativos ciclosFormativos;


    /*método  que creará la instancia de las clases de negocio.*/
    public void comenzar(){
        alumnos = new Alumnos(/*CAPACIDAD*/);
        asignaturas = new Asignaturas(/*CAPACIDAD*/);
        ciclosFormativos = new CiclosFormativos(/*CAPACIDAD*/);
        matriculas = new Matriculas(/*CAPACIDAD*/);
    }


    /*método que simplemente mostrará un mensaje informativo indicando que el modelo ha terminado.*/
    public void terminar(){
        System.out.println("Modelo terminado.");
    }


    /*Crea los diferentes métodos insertar (para Alumno, Asignatura, Ciclo Formativo y Matricula).*/
    public void insertar(Alumno alumno) throws OperationNotSupportedException {
        alumnos.insertar(alumno);
    }
    public void insertar(Asignatura asignatura) throws OperationNotSupportedException {
        asignaturas.insertar(asignatura);
    }
    public void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        ciclosFormativos.insertar(cicloFormativo);
    }
    public void insertar(Matricula matricula) throws OperationNotSupportedException {
        matriculas.insertar(matricula);
    }


    /*Crea los diferentes métodos buscar, cada uno de los cuales devolverá una nueva instancia del elemento
    encontrado si éste existe.*/
    public Alumno buscar(Alumno alumno){
        return alumnos.buscar(alumno);
    }
    public Asignatura buscar(Asignatura asignatura){
        return asignaturas.buscar(asignatura);
    }
    public CicloFormativo buscar(CicloFormativo cicloFormativo){
        return ciclosFormativos.buscar(cicloFormativo);
    }
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException {
        return matriculas.buscar(matricula);
    }


    /*Crea los diferentes métodos borrar (para Alumno, Asignatura, Ciclo Formativo y Matricula).*/
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        alumnos.borrar(alumno);
    }
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        asignaturas.borrar(asignatura);
    }
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        ciclosFormativos.borrar(cicloFormativo);
    }
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        matriculas.borrar(matricula);
    }


    /*Crea los diferentes métodos get, que deben devolver una lista de los diferentes elementos de la aplicación
    (Alumnos, Asignaturas, Ciclos Formativos y Matrículas).*/
    public List<Alumno> getAlumnos() { return alumnos.get(); }
    public List<Asignatura> getAsignaturas(){
        return asignaturas.get();
    }
    public List<CicloFormativo> getCiclosFormativos(){
        return ciclosFormativos.get();
    }
    public List<Matricula> getMatriculas() throws OperationNotSupportedException {
        return matriculas.get();
    }



    public List<Matricula> getMatriculas(Alumno alumno) {
        /*
        //Matriculas del alumno
        List<Matricula> matriculasAlumno = new ArrayList<>();

        //Si en la matricula registrada, su alumno == al alumno obtenido por parámetro, se almacena en el array de
        //matriculasAlumno
        for(Matricula matricula : matriculas){
            if (matricula != null && matricula.getAlumno().equals(alumno)){
                matriculasAlumno.add(matricula);
            }
        }
        */

        //obtiene alumno y se lo pasa a clase Matriculas get(), que devuelve una List<Matricula>
        return matriculas.get(alumno);
    }


    public List<Matricula> getMatriculas(CicloFormativo cicloFormativo) {
        /*
        //Matriculas del ciclo
        List<Matricula> matriculasCiclo = new ArrayList<>();

        //La matricula tiene asignatura y la asignatura tiene ciclo
        for (Matricula matricula : matriculas){
            if (matricula != null){
                for (Asignatura asignatura : matricula.getColeccionAsignaturas()){
                    if (asignatura != null && asignatura.getCicloFormativo().equals(cicloFormativo)){
                        matriculasCiclo.add(matricula);
                    }
                }
            }
        }
        */

        return matriculas.get(cicloFormativo);
    }


    public List<Matricula> getMatriculas(String cursoAcademico) {
        /*
        List<Matricula> matriculasCurso = new ArrayList<>();

        for(Matricula matricula : matriculas){
            if (matricula != null && matricula.getCursoAcademico().equals(cursoAcademico)){
                matriculasCurso.add(matricula);
            }
        }
        */

        return matriculas.get(cursoAcademico);
    }
 }


