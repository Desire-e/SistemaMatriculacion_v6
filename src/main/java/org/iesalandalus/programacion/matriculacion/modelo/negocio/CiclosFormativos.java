package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;

public class CiclosFormativos {
    private int capacidad;
    private int tamano = 0;
    private CicloFormativo[] coleccionCiclosFormativos;


    public CiclosFormativos(int capacidad) {
        if (capacidad <= 0){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad=capacidad;
        this.coleccionCiclosFormativos = new CicloFormativo[capacidad];
    }


    public int getCapacidad() { return capacidad; }
    public int getTamano() { return tamano; }
    public CicloFormativo[] get(){ return copiaProfundaCiclosFormativos(); }


    private CicloFormativo [] copiaProfundaCiclosFormativos(){

        CicloFormativo[] copiaProfunda = new CicloFormativo[coleccionCiclosFormativos.length];

        for (int i = 0; i < coleccionCiclosFormativos.length; i++) {

            if (coleccionCiclosFormativos[i] != null) {
                copiaProfunda[i] = new CicloFormativo(coleccionCiclosFormativos[i]);
            }
        }
        return  copiaProfunda;
    }


    public void insertar (CicloFormativo cicloFormativo) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }

        int indice = buscarIndice(cicloFormativo);
        if (indice != -1){
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }

        if (capacidadSuperado(indice)) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más ciclos formativos.");
        }

        else {
            for (int i = 0; i < coleccionCiclosFormativos.length; i++) {
                if (coleccionCiclosFormativos[i] == null && !capacidadSuperado(i)) {

                    coleccionCiclosFormativos[i] = cicloFormativo;
                    copiaProfundaCiclosFormativos();
                    break;
                }
            }
            tamano++;
        }
    }


    private int buscarIndice(CicloFormativo cicloFormativo) {

        int noExisteCiclo = -1;

        for (int i = 0; i < coleccionCiclosFormativos.length; i++) {
            if (coleccionCiclosFormativos[i] != null && coleccionCiclosFormativos[i].equals(cicloFormativo)) {
                return i;
            }
        }

        return noExisteCiclo;
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


    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null){
            throw new NullPointerException("Ciclo nulo no puede buscarse.");
        }

        int indice = buscarIndice(cicloFormativo);

        if (indice != -1 && coleccionCiclosFormativos[indice] != null){

            for (CicloFormativo cicloCopia : copiaProfundaCiclosFormativos()){
                if (cicloCopia.equals(cicloFormativo)){
                    return cicloCopia;
                }
            }
        }

        return null;
    }


    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }

        int indice = buscarIndice(cicloFormativo);
        if(indice == -1){
            throw new OperationNotSupportedException("ERROR: No existe ningún ciclo formativo como el indicado.");

        } else{
            coleccionCiclosFormativos[indice] = null;
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;
        }
    }


    private void desplazarUnaPosicionHaciaIzquierda(int indice){

        int i;

        for ( i = indice; i < coleccionCiclosFormativos.length - 1; i++){
            coleccionCiclosFormativos[i]=coleccionCiclosFormativos[i+1];
        }

        coleccionCiclosFormativos[i]=null;
    }

}
