package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;

public class FuenteDatosMySQL implements IFuenteDatos {

    /*
    Crea la clase FuenteDatosMySQL  que deberá implementar la interfaz  IFuenteDatos,
    tal y como se indica en el diagrama.
    Esta clase será la encargada de implementar el patrón fábrica, devolviendo en cada caso el resultado
    de crear la colección a la que hace referencia su nombre:
        - Método crearAlumnos devolverá una nueva colección de tipo Alumnos del paquete mysql.
        - Método crearCiclosFormativos devolverá una nueva colección de tipo CiclosFormativos del paquete mysql.
        ...
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
