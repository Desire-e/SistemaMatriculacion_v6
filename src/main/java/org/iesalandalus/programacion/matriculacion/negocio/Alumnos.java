package org.iesalandalus.programacion.matriculacion.negocio;

import org.iesalandalus.programacion.matriculacion.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.dominio.Matricula;

import javax.naming.OperationNotSupportedException;

public class Alumnos {
    private int capacidad;  // cantidad maxima que puede contener
    private int tamano = 0;     // cantidad actual que contiene
    private Alumno[] coleccionAlumnos;



    public Alumnos(int capacidad){

        if (capacidad <= 0){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad=capacidad;

        /* Inicializada colección con la capacidad introducida por parámetros, de la que se hará más tarde una
           copia profunda */
        this.coleccionAlumnos = new Alumno[capacidad];

    }

    public int getCapacidad() { return capacidad; }
    public int getTamano() { return tamano; }
    public Alumno[] get() { return copiaProfundaAlumnos(); }



    // Copia profunda de Alumnos[] colecciónAlumnos
    private Alumno [] copiaProfundaAlumnos(){

        /* Crear un array copia de coleccionAlumnos (array original), del mismo tamaño */
        Alumno[] copiaProfunda = new Alumno[coleccionAlumnos.length];

        /* Recorrer el array coleccionAlumnos (original) */
        for (int i = 0; i < coleccionAlumnos.length; i++) {

            /* Verificar si la posición actual en array coleccionAlumnos (original) no es null */
            if (coleccionAlumnos[i] != null) {
                /* Crear una nueva posición en array copiaProfunda (copia): poner la posición actual que estamos
                   recorriendo del array coleccionAlumnos (original) y se le asigna como valor a esa posición un
                   objeto Alumno (usando el constructor copia, que tiene por parámetro Alumno alumno, y ese
                   parámetro será = al objeto Alumno alumno que ya hay en la coleccionAlumnos en la posición actual
                   que estamos recorriendo) */
                copiaProfunda[i] = new Alumno(coleccionAlumnos[i]);
            }
        }

        return  copiaProfunda;
    }



    // Insertar alumno no nulo al final de colecciónAlumnos, sin repetidos, si hay espacio.
    public void insertar (Alumno alumno) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (alumno == null){
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }

        /* Buscar si existe ya el alumno a insertar */
        int indice = buscarIndice(alumno);

        /* Si buscarIndice devuelve índice que NO es -1 (devuelve una posición real del array),
        es que el elemento YA EXISTE */
        if (indice != -1){
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }

        if (capacidadSuperado(indice)) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más alumnos.");
        }
        else {
            /* Insertar en la primera posición libre (la primera posción nula) */
            for (int i = 0; i < coleccionAlumnos.length; i++) {
                if (coleccionAlumnos[i] == null && !capacidadSuperado(i)) {

                    coleccionAlumnos[i] = alumno;
                    //Se crea una copia
                    copiaProfundaAlumnos();
                    //Para no crear copia del mismo alumno en todas las posiciones
                    break;
                }
            }
            tamano++;
        }
    }



    // Busca y devuelve el índice de un alummo, devuelve -1 si no existe
    public int buscarIndice(Alumno alumno) {

        /* Variable que se devuelve si no se encuentra */
        int noExisteAlumno = -1;

        /* Recorre array */
        for (int i = 0; i < coleccionAlumnos.length; i++) {
            /* Si en una posición, no nula, hay alumno = al alumno (el que buscamos) */
            if (coleccionAlumnos[i] != null && coleccionAlumnos[i].equals(alumno)) {
                /* SI ENCONTRÓ ALUMNO: Devuelve su indice */
                return i;
            }
        }

        /* Si al final no hay alumno = al alumno (el que buscamos) */
        /* NO ENCONTRÓ ALUMNO: devuelve -1  */
        return noExisteAlumno;
    }


    //APLICAR EN METODOS DONDE NO TIENEN COMO FIN MODIFICAR EL TAMAÑO ACTUAL DEL ARRAY
    private boolean tamanoSuperado(int indice){
        /* Si supera tamaño actual del array, devuelve true */
        if (indice >= getTamano()) {
            return true;
        } else return false;
    }



    private boolean capacidadSuperado(int indice){
        /* Si supera capacidad máxima del array, devuelve true */
        if (tamano >= capacidad || indice>=capacidad) {
            return true;
        } else
            return false;

    }



    //Busca un alumno. Si existe devuelve el alumno, si no existe devuelve null
    public Alumno buscar(Alumno alumno) {
        if (alumno == null){
            throw new NullPointerException("Alumno nulo no puede buscarse.");
        }

        /* Buscar si existe ya el alumno a buscar */
        int indice = buscarIndice(alumno);

        /* Si buscarIndice devuelve índice que NO es -1 (devuelve una posición real del array),
        es que el alumno SI EXISTE. Además si la posición no es nula */
        if (indice != -1 && coleccionAlumnos[indice] != null){
            for (Alumno alumnoCopia : copiaProfundaAlumnos()){
                if (alumnoCopia.equals(alumno)){
                    //Devuelve referencia copia
                    return alumnoCopia;

                }
            }

//            return coleccionAlumnos[indice];
        }

        /* Si indice = -1, NO EXISTE el alumno. Devuelve null */
        return null;
    }



    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null){
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }

        int indice = buscarIndice(alumno);
        if(indice == -1){
            throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
        } else{
            coleccionAlumnos[indice] = null;
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;

        }
    }



    private void desplazarUnaPosicionHaciaIzquierda(int indice){

        int i;
        /* El índice debe de ir alumno por alumno (.length-1 porque si el array es coleccionAlumnos[5],
        existen hasta alumno[4] como máximo) */
        for ( i = indice; i < coleccionAlumnos.length - 1; i++){

            /* La info del alumno en posición superior [i+1] pasa a esa posición [i] */
            coleccionAlumnos[i]=coleccionAlumnos[i+1];
        }

        /* La última posición alcanzada ahora no tiene alumno, pues se desplazó. Se asigna nulo */
        coleccionAlumnos[i]=null;
    }




}
