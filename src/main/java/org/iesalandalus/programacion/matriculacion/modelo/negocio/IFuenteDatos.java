package org.iesalandalus.programacion.matriculacion.modelo.negocio;

public interface IFuenteDatos {

    IAlumnos crearAlumnos();
    IAsignaturas crearAsignaturas();
    ICiclosFormativos crearCiclosFormativos();
    IMatriculas crearMatriculas();
}
