package org.iesalandalus.programacion.matriculacion.modelo.negocio;

public interface IFuenteDatos {
    /*
    Esta clase será la encargada de implementar el patrón fábrica, devolviendo en cada caso el resultado de
    crear la colección a la que hace referencia su nombre:
    Método crearAlumnos devolverá una nueva colección de tipo Alumnos del paquete mysql.
    Método crearCiclosFormativos devolverá una nueva colección de tipo CiclosFormativos del paquete mysql.
    Método crearAsignaturas devolverá una nueva colección de tipo Asignaturas del paquete mysql.
    Método crearMatriculas devolverá una nueva colección de tipo Matriculas del paquete mysql.
    * */



    IAlumnos crearAlumnos();
    IAsignaturas crearAsignaturas();
    ICiclosFormativos crearCiclosFormativos();
    IMatriculas crearMatriculas();
}
