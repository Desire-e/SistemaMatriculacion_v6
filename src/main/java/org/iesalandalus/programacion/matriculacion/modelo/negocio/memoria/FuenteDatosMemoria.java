package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Alumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Asignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Matriculas;

public class FuenteDatosMemoria implements IFuenteDatos {

    /*
    Esta clase será la encargada de implementar el patrón fábrica, devolviendo en cada caso el resultado de
    crear la colección a la que hace referencia su nombre:
        - crearAlumnos() devolverá una nueva colección de tipo Alumnos del paquete memoria.
        - Método crearCiclosFormativos devolverá una nueva colección de tipo CiclosFormativos del paquete memoria.
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
