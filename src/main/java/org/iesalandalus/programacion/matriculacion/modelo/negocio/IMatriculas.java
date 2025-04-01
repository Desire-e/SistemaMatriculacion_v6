package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface IMatriculas {

    void comenzar();
    void terminar();

    List<Matricula> get() throws OperationNotSupportedException;
    int getTamano();
    void insertar(Matricula matricula) throws OperationNotSupportedException;
    Matricula buscar(Matricula matricula) throws OperationNotSupportedException;
    void borrar(Matricula matricula) throws OperationNotSupportedException;

    List<Matricula> get(Alumno alumno) throws OperationNotSupportedException;
    List<Matricula> get(String cursoAcademico) throws OperationNotSupportedException;
    List<Matricula> get(CicloFormativo cicloFormativo) throws OperationNotSupportedException;

}
