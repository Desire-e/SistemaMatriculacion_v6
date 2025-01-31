package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Alumnos {
    private List<Alumno> coleccionAlumnos;



    public Alumnos(/*int capacidad*/){
        this.coleccionAlumnos = new ArrayList<>();
    }

    /*public int getCapacidad() { return capacidad; }*/
    /*public int getTamano() {
        // size() devuelve el tamaño de la lista
        return coleccionAlumnos.size();
    }
    */
    public List<Alumno> get() { return copiaProfundaAlumnos(); }



    // Copia profunda de lista colecciónAlumnos
    private List<Alumno> copiaProfundaAlumnos(){
        /*
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
         */

        List<Alumno> copiaProfunda = coleccionAlumnos.stream()
                .filter(alumno -> alumno != null)
                .map(Alumno::new)   // Crea una copia de cada objeto Alumno usando el constructor copia.
                                    // Usa el constructor copia de clase Alumno porque la lista de objetos obtenidos
                                    // con coleccionAlumnos.stream().filter() son tipo Alumno y no nulos
                .collect(Collectors.toList());  //Crea con esos objetos copiados una lista mutable
                                                // (puedes borrar/añadir después objetos)
        return copiaProfunda;
    }



    // Insertar alumno no nulo al final de colecciónAlumnos, sin repetidos, si hay espacio.
    public void insertar (Alumno alumno) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (alumno == null){
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }

        if (coleccionAlumnos.contains(alumno)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }

        coleccionAlumnos.add(alumno);
        /*No es necesario llamar a copiaProfundaAlumnos(), cuando se invoque copiará todos los alumnos que
        ya hay insertados*/
    }



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



    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null){
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }

        //Comprobar que existe
        if (!coleccionAlumnos.contains(alumno)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
        }
        //Si existe, lo borra
        else coleccionAlumnos.remove(alumno);
    }


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
