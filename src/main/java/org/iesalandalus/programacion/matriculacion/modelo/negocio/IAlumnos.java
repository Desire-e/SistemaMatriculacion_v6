package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface IAlumnos {

    /* NOTA: Los métodos privados en interfaces deben tener implementación
       En Java, los métodos en una interfaz por defecto son públicos y abstractos.

       Los métodos privados en interfaces solo pueden ser usados dentro de la misma interfaz,
       Si quieres que otras clases implementen estos métodos, deben ser públicos y abstractos.
     */

    void comenzar();
    void terminar();


    List<Alumno> get();
    int getTamano();
    void insertar(Alumno alumno) throws OperationNotSupportedException;
    Alumno buscar(Alumno alumno);
    void borrar(Alumno alumno) throws OperationNotSupportedException;

}
