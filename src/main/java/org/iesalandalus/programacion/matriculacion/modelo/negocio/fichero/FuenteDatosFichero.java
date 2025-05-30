package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.Matriculas;

public class FuenteDatosFichero implements IFuenteDatos {
    @Override
    public IAlumnos crearAlumnos() {
        return new Alumnos();
    }

    @Override
    public IAsignaturas crearAsignaturas() {
        return new Asignaturas();
    }

    @Override
    public ICiclosFormativos crearCiclosFormativos() {
        return new CiclosFormativos();
    }

    @Override
    public IMatriculas crearMatriculas() {
        return new Matriculas();
    }

}
