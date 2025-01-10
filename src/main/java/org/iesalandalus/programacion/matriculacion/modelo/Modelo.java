package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.Matriculas;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;

import javax.naming.OperationNotSupportedException;

/*Esta clase gestionará el modelo de datos de nuestra aplicación. Será la encargada de comunicarse
con las tres clases que hacen referencia a las colecciones de datos (alumnos, asignaturas, ciclos
formativos y matrículas).*/

public class Modelo {
    public int CAPACIDAD=1000;
    private Alumnos alumnos;
    private Matriculas matriculas;
    private Asignaturas asignaturas;
    private CiclosFormativos ciclosFormativos;


    /*método  que creará la instancia de las clases de negocio.*/
    public void comenzar(){
        alumnos = new Alumnos(CAPACIDAD);
        asignaturas = new Asignaturas(CAPACIDAD);
        ciclosFormativos = new CiclosFormativos(CAPACIDAD);
        matriculas = new Matriculas(CAPACIDAD);
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
        alumnos.buscar(alumno);
        return alumno;
    }
    public Asignatura buscar(Asignatura asignatura){
        asignaturas.buscar(asignatura);
        return asignatura;
    }
    public CicloFormativo buscar(CicloFormativo cicloFormativo){
        ciclosFormativos.buscar(cicloFormativo);
        return cicloFormativo;
    }
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException {
        matriculas.buscar(matricula);
        return matricula;
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
    public Alumno[] getAlumnos() { return alumnos.get(); }
    public Asignatura[] getAsignaturas(){
        return asignaturas.get();
    }
    public CicloFormativo[] getCiclosFormativos(){
        return ciclosFormativos.get();
    }
    public Matricula[] getMatriculas() throws OperationNotSupportedException {
        return matriculas.get();
    }
 }


