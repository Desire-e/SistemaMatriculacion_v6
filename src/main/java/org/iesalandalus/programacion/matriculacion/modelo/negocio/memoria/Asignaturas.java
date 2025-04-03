package org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.CiclosFormativos;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.cerrarConexion;
import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.establecerConexion;

public class Asignaturas implements IAsignaturas {
    private List<Asignatura> coleccionAsignaturas;
    /*private Connection conexion = null;*/

    public Asignaturas() {
        this.coleccionAsignaturas = new ArrayList<>();
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
        return coleccionAsignaturas.size();
    }


    @Override
    public List<Asignatura> get(){
        return copiaProfundaAsignaturas();
    }


    private List<Asignatura> copiaProfundaAsignaturas(){
        List<Asignatura> copiaProfunda = coleccionAsignaturas.stream()
                .filter(asig -> asig != null)
                .map(Asignatura::new)
                .collect(Collectors.toList());
        return copiaProfunda;
    }


    @Override
    public void insertar (Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null){
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }


        // INSERTAR EN LA MEMORIA:
        if (coleccionAsignaturas.contains(asignatura)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }
        coleccionAsignaturas.add(asignatura);
    }


    @Override
    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null){
            throw new NullPointerException("Asignatura nula no puede buscarse.");
        }

        int indice;
        if (coleccionAsignaturas.contains(asignatura)) {
            indice = coleccionAsignaturas.indexOf(asignatura);
            asignatura = coleccionAsignaturas.get(indice);
            return new Asignatura(asignatura);
        }
        else return null;

    }


    @Override
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }

        // BORRAR EN LA MEMORIA:
        if (!coleccionAsignaturas.contains(asignatura)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
        }
        else coleccionAsignaturas.remove(asignatura);
    }
}
