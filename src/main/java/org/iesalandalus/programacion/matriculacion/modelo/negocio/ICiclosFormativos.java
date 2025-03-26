package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;
import java.util.List;

public interface ICiclosFormativos {

    void comenzar();
    void terminar();

    List<CicloFormativo> get();
    int getTamano();
    void insertar(CicloFormativo cicloFormativo) throws OperationNotSupportedException;
    CicloFormativo buscar(CicloFormativo cicloFormativo);
    void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException;
}
