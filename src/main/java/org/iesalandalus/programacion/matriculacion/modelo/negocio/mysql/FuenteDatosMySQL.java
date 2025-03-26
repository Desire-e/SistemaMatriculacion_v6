package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;

public class FuenteDatosMySQL implements IFuenteDatos {

    /*
    Esta clase será la encargada de implementar el patrón fábrica, devolviendo
    en cada caso el resultado de crear la colección a la que hace referencia su nombre:
    Método crearAlumnos devolverá una nueva colección de tipo Alumnos del paquete memoria.
    etc.
    */

    @Override
    public IAlumnos crearAlumnos(){
        return new Alumnos();
    }

    @Override
    public IAsignaturas crearAsignaturas(){
        return new Asignaturas();

    }

    @Override
    public ICiclosFormativos crearCiclosFormativos(){
        return new CiclosFormativos();

    }

    @Override
    public IMatriculas crearMatriculas(){
        return new Matriculas();

    }
}
