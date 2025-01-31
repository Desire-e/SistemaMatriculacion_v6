package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Asignaturas {
    private List<Asignatura> coleccionAsignaturas;

    public Asignaturas() {
        this.coleccionAsignaturas = new ArrayList<>();
    }

    public List<Asignatura> get(){ return copiaProfundaAsignaturas(); }


    private List<Asignatura> copiaProfundaAsignaturas(){
        List<Asignatura> copiaProfunda = coleccionAsignaturas.stream()
                .filter(asig -> asig != null)
                .map(Asignatura::new)
                .collect(Collectors.toList());
        return copiaProfunda;
    }


    public void insertar (Asignatura asignatura) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (asignatura == null){
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }

        if (coleccionAsignaturas.contains(asignatura)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }

        coleccionAsignaturas.add(asignatura);
    }



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


    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }

        if (!coleccionAsignaturas.contains(asignatura)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
        }
        else coleccionAsignaturas.remove(asignatura);
    }
}
