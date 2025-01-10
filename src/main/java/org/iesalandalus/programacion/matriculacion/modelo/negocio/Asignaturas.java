package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;

import javax.naming.OperationNotSupportedException;

public class Asignaturas {

    private int capacidad;
    private int tamano = 0;
    private Asignatura[] coleccionAsignaturas;


    public Asignaturas(int capacidad) {
        if (capacidad <= 0){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad=capacidad;
        this.coleccionAsignaturas = new Asignatura[capacidad];
    }


    public int getCapacidad() { return capacidad; }
    public int getTamano() { return tamano; }
    public Asignatura[] get(){ return copiaProfundaAsignaturas(); }


    private Asignatura[] copiaProfundaAsignaturas(){

        Asignatura[] copiaProfunda = new Asignatura[coleccionAsignaturas.length];

        for (int i = 0; i < coleccionAsignaturas.length; i++) {

            if (coleccionAsignaturas[i] != null) {
                copiaProfunda[i] = new Asignatura(coleccionAsignaturas[i]);
            }
        }
        return  copiaProfunda;
    }


    public void insertar (Asignatura asignatura) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (asignatura == null){
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }

        int indice = buscarIndice(asignatura);
        if (indice != -1){
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }

        if (capacidadSuperado(indice)) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más asignaturas.");
        }

        else {
            for (int i = 0; i < coleccionAsignaturas.length; i++) {
                if (coleccionAsignaturas[i] == null && !capacidadSuperado(i)) {

                    coleccionAsignaturas[i] = asignatura;
                    copiaProfundaAsignaturas();
                    break;
                }
            }
            tamano++;
        }
    }


    private int buscarIndice(Asignatura asignatura) {

        int noExisteAsignatura = -1;

        for (int i = 0; i < coleccionAsignaturas.length; i++) {
            if (coleccionAsignaturas[i] != null && coleccionAsignaturas[i].equals(asignatura)) {
                return i;
            }
        }

        return noExisteAsignatura;
    }


    private boolean tamanoSuperado(int indice){
        /* Si supera tamaño actual del array, devuelve true */
        if (indice >= getTamano()) {
            return true;
        } else return false;
    }


    private boolean capacidadSuperado(int indice){
        if (tamano >= capacidad || indice>=capacidad) {
            return true;
        } else
            return false;

    }


    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null){
            throw new NullPointerException("Asignatura nula no puede buscarse.");
        }

        int indice = buscarIndice(asignatura);

        if (indice != -1 && coleccionAsignaturas[indice] != null){

            for (Asignatura asignaturaCopia : copiaProfundaAsignaturas()){
                if (asignaturaCopia.equals(asignatura)){
                    return asignaturaCopia;
                }
            }
        }

        return null;
    }


    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null){
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }

        int indice = buscarIndice(asignatura);
        if(indice == -1){
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");

        } else{
            coleccionAsignaturas[indice] = null;
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;
        }
    }


    private void desplazarUnaPosicionHaciaIzquierda(int indice){

        int i;

        for ( i = indice; i < coleccionAsignaturas.length - 1; i++){
            coleccionAsignaturas[i]=coleccionAsignaturas[i+1];
        }

        coleccionAsignaturas[i]=null;
    }


}
