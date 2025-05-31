package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;

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
