package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.cerrarConexion;
import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.establecerConexion;

public class Alumnos implements IAlumnos {

    /* TO10.
    Paquete memoria:
    - Implementa interfaz correspondiente (IAlumnos)
    - Implementar los métodos comenzar() y terminar() de su interfaz.
      Los cuerpos de ambos métodos en las cuatro clases deberán llamar respectivamente a los métodos
      establecerConexion() y cerrarConexion() de la clase MySQL.
    - Mover a paquete memoria
    */
    private List<Alumno> coleccionAlumnos;
    private Connection conexion = null;

    public Alumnos(){
        this.coleccionAlumnos = new ArrayList<>();
        /* TO10. DUDA: Se llama a comenzar()??
        comenzar();
        */
    }

    /* TO10. DUDA: qué sentido tienen comenzar() y terminar() en el paquete memoria??
    es solo porque dará error al implementar la IAlumnos?

    Los cuerpos de comenzar() y terminar() en las cuatro clases deberán llamar respectivamente
    a los métodos establecerConexion y cerrarConexion de la clase MySQL. */
    @Override
    public void comenzar() {
        conexion = establecerConexion();
    }
    @Override
    public void terminar() {
        if (conexion != null) {
            cerrarConexion();  // Cerramos la conexión con la base de datos
            conexion = null;  // Restablece la referencia a la conexión
        }
    }




    /* TO10.
    Paquete memoria: Manten los = métodos */
    @Override
    public int getTamano() {
        return coleccionAlumnos.size();
    }

    @Override
    public List<Alumno> get() {
         return copiaProfundaAlumnos();
    }


    // Copia profunda de lista colecciónAlumnos
/*
    private List<Alumno> copiaProfundaAlumnos(){
        // Crear una lista copia de coleccionAlumnos (lista original)
        List<Alumno> copiaProfunda = new ArrayList<>();

        for (Alumno alumno : coleccionAlumnos) {
            if (alumno != null) {
                // Usamos el constructor copia de Alumno
                // add() añade el elemento al conjunto. Si ya está, devuelve false y no lo añade.
                copiaProfunda.add(new Alumno(alumno));
            }
        }
        return  copiaProfunda;
    }
/*  Con stream: */
    private List<Alumno> copiaProfundaAlumnos(){
        List<Alumno> copiaProfunda = coleccionAlumnos.stream()
                .filter(alumno -> alumno != null)
                .map(Alumno::new)   // Crea una copia de cada objeto Alumno usando el constructor copia.
                                    // Usa el constructor copia de clase Alumno porque la lista de objetos obtenidos
                                    // con coleccionAlumnos.stream().filter() son tipo Alumno y no nulos
                .collect(Collectors.toList());  //Crea con esos objetos copiados una lista mutable
                                                // (puedes borrar/añadir después objetos)
        return copiaProfunda;
    }


    @Override
    // Insertar alumno no nulo al final de colecciónAlumnos, sin repetidos, si hay espacio.
    public void insertar (Alumno alumno) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (alumno == null){
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }

        // INSERTAR EN LA MEMORIA:
        if (coleccionAlumnos.contains(alumno)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }
        coleccionAlumnos.add(alumno);

    }


    @Override
    //Busca un alumno. Si existe devuelve el alumno, si no existe devuelve null
    public Alumno buscar(Alumno alumno) {
        if (alumno == null){
            throw new NullPointerException("Alumno nulo no puede buscarse.");
        }

        // Si existe el alumno, devuelve copia de este
        int indice;
        if (coleccionAlumnos.contains(alumno)) {
            // indexOf() devuelve el índice donde se encuentra el objeto indicado
            indice=coleccionAlumnos.indexOf(alumno);
            // get() devuelve el objeto exacto que se encuentra en el índice indicado
            // alumno ahora tiene exactamente el valor del objeto que se encuentra en la lista
            alumno = coleccionAlumnos.get(indice);
            return new Alumno(alumno);
        }
        // Si no existe, devuelve nulo
        else return null;
    }


    @Override
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null){
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }


        // BORRAR EN LA MEMORIA:
        //Comprobar que existe
        if (!coleccionAlumnos.contains(alumno)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
        }
        //Si existe, lo borra
        else coleccionAlumnos.remove(alumno);
    }







/*
    public int getCapacidad() { return capacidad; }
*/

/*
    // Busca y devuelve el índice de un alummo, devuelve -1 si no existe
    private int buscarIndice(Alumno alumno) {
        // Variable que se devuelve si no se encuentra
        int noExisteAlumno = -1;
        // Recorre array
        for (int i = 0; i < coleccionAlumnos.length; i++) {
            // Si en una posición, no nula, hay alumno = al alumno (el que buscamos)
            if (coleccionAlumnos[i] != null && coleccionAlumnos[i].equals(alumno)) {
                // SI ENCONTRÓ ALUMNO: Devuelve su indice
                return i;
            }
        }
        // Si al final no hay alumno = al alumno (el que buscamos)
        // NO ENCONTRÓ ALUMNO: devuelve -1
        return noExisteAlumno;
    }
*/

/*
    //APLICAR EN METODOS DONDE NO TIENEN COMO FIN MODIFICAR EL TAMAÑO ACTUAL DEL ARRAY
    private boolean tamanoSuperado(int indice){
        // Si supera tamaño actual del array, devuelve true
        if (indice >= getTamano()) {
            return true;
        } else return false;
    }
*/

/*
    private boolean capacidadSuperado(int indice){
        // Si supera capacidad máxima del array, devuelve true
        if (tamano >= capacidad || indice>=capacidad) {
            return true;
        } else
            return false;
    }
*/

/*
    private void desplazarUnaPosicionHaciaIzquierda(int indice){
        int i;
        // El índice debe de ir alumno por alumno (.length-1 porque si el array es coleccionAlumnos[5],
        // existen hasta alumno[4] como máximo)
        for ( i = indice; i < coleccionAlumnos.length - 1; i++){

            // La info del alumno en posición superior [i+1] pasa a esa posición [i]
            coleccionAlumnos[i]=coleccionAlumnos[i+1];
        }
        // La última posición alcanzada ahora no tiene alumno, pues se desplazó. Se asigna nulo
        coleccionAlumnos[i]=null;
    }
*/


}
