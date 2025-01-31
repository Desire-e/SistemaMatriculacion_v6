package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CiclosFormativos {
    private List<CicloFormativo> coleccionCiclosFormativos;


    public CiclosFormativos() {
        this.coleccionCiclosFormativos = new ArrayList<>();
    }

    public List<CicloFormativo> get(){ return copiaProfundaCiclosFormativos(); }


    private List<CicloFormativo> copiaProfundaCiclosFormativos(){
        List<CicloFormativo> copiaProfunda = coleccionCiclosFormativos.stream()
                .filter(ciclo -> ciclo != null)
                .map(CicloFormativo::new)
                .collect(Collectors.toList());
        return copiaProfunda;
    }


    public void insertar (CicloFormativo cicloFormativo) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }

        if (coleccionCiclosFormativos.contains(cicloFormativo)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }

        coleccionCiclosFormativos.add(cicloFormativo);
    }


    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null){
            throw new NullPointerException("Ciclo nulo no puede buscarse.");
        }

        int indice;
        if (coleccionCiclosFormativos.contains(cicloFormativo)) {
            indice = coleccionCiclosFormativos.indexOf(cicloFormativo);
            cicloFormativo = coleccionCiclosFormativos.get(indice);
            return new CicloFormativo(cicloFormativo);
        }
        else return null;
    }


    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }

        if (!coleccionCiclosFormativos.contains(cicloFormativo)) {
            throw new OperationNotSupportedException("ERROR:No existe ningún ciclo formativo como el indicado.");
        }
        else coleccionCiclosFormativos.remove(cicloFormativo);
    }

}
