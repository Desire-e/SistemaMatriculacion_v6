package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.cerrarConexion;
import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.establecerConexion;

public class CiclosFormativos implements ICiclosFormativos {
    private List<CicloFormativo> coleccionCiclosFormativos;
    /*private Connection conexion = null;*/

    public CiclosFormativos() {
        this.coleccionCiclosFormativos = new ArrayList<>();
        comenzar();
    }

    @Override
    public void comenzar() {
        /*conexion = establecerConexion();*/
    }
    @Override
    public void terminar() {
        /*if (conexion != null) {
            cerrarConexion();  // Cerramos la conexión con la base de datos
            conexion = null;  // Restablece la referencia a la conexión
        }*/
    }




    @Override
    public int getTamano() {
        return coleccionCiclosFormativos.size();
    }


    @Override
    public List<CicloFormativo> get(){
        return copiaProfundaCiclosFormativos();
    }


    private List<CicloFormativo> copiaProfundaCiclosFormativos(){
        List<CicloFormativo> copiaProfunda = coleccionCiclosFormativos.stream()
                .filter(ciclo -> ciclo != null)
                .map(CicloFormativo::new)
                .collect(Collectors.toList());
        return copiaProfunda;
    }


    @Override
    public void insertar (CicloFormativo cicloFormativo) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }

        if (coleccionCiclosFormativos.contains(cicloFormativo)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }

        coleccionCiclosFormativos.add(cicloFormativo);
    }


    @Override
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


    @Override
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
