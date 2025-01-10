package org.iesalandalus.programacion.matriculacion.modelo.negocio;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;

import javax.naming.OperationNotSupportedException;

public class Matriculas {
    private int capacidad;  // cantidad maxima que puede contener
    private int tamano = 0;     // cantidad actual que contiene
    private Matricula[] coleccionMatriculas;



    public Matriculas(int capacidad){
        if (capacidad <= 0){
            throw new IllegalArgumentException("ERROR: La capacidad debe ser mayor que cero.");
        }

        this.capacidad=capacidad;
        this.coleccionMatriculas = new Matricula[capacidad];

    }

    public int getCapacidad() { return capacidad; }
    public int getTamano() { return tamano; }
    public Matricula[] get() throws OperationNotSupportedException { return copiaProfundaMatriculas(); }


    private Matricula[] copiaProfundaMatriculas() throws OperationNotSupportedException {

        Matricula[] copiaProfunda = new Matricula[coleccionMatriculas.length];

        for (int i = 0; i < coleccionMatriculas.length; i++) {

            if (coleccionMatriculas[i] != null) {
                copiaProfunda[i] = new Matricula(coleccionMatriculas[i]);
            }
        }

        return  copiaProfunda;
    }


    public void insertar (Matricula matricula) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (matricula == null){
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }

        int indice = buscarIndice(matricula);

        if (indice != -1){
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese código.");
        }

        if (capacidadSuperado(indice)) {
            throw new OperationNotSupportedException("ERROR: No se aceptan más matrículas.");
        }
        else {
            for (int i = 0; i < coleccionMatriculas.length; i++) {
                if (coleccionMatriculas[i] == null && !capacidadSuperado(i)) {

                    coleccionMatriculas[i] = matricula;
                    copiaProfundaMatriculas();
                    break;
                }
            }
            tamano++;
        }
    }


    private int buscarIndice(Matricula matricula) {

        int noExisteMatricula = -1;

        for (int i = 0; i < coleccionMatriculas.length; i++) {
            if (coleccionMatriculas[i] != null && coleccionMatriculas[i].equals(matricula)) {
                return i;
            }
        }

        return noExisteMatricula;
    }


    private boolean tamanoSuperado(int indice){
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


    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException{
        if (matricula == null){
            throw new NullPointerException("Matrícula nula no puede buscarse.");
        }

        int indice = buscarIndice(matricula);

        if (indice != -1 && coleccionMatriculas[indice] != null){
            for (Matricula matriculaCopia : copiaProfundaMatriculas()){
                if (matriculaCopia.equals(matricula)){
                    return matriculaCopia;

                }
            }

        }
        return null;
    }


    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null){
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }

        int indice = buscarIndice(matricula);
        if(indice == -1){
            throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
        } else{
            coleccionMatriculas[indice] = null;
            desplazarUnaPosicionHaciaIzquierda(indice);
            tamano--;

        }
    }


    private void desplazarUnaPosicionHaciaIzquierda(int indice){

        int i;

        for ( i = indice; i < coleccionMatriculas.length - 1; i++){
            coleccionMatriculas[i]=coleccionMatriculas[i+1];
        }

        coleccionMatriculas[i]=null;
    }


    // El método get que está sobrecargado y devolverá una colección de las matrículas realizadas por el
    // alumno pasado por parámetro o unca colección de las matrículas realizadas para el curso académico
    // indicado como parámetro o una colección de las matrículas realizadas para el ciclo formativo
    // indicado como parámetro.

    public Matricula[] get(Alumno alumno){

        int contador = 0;

        //Para contar cuántas coincidencias del alumno hay en matrículas:
        //Recorre las matrículas
        for (Matricula matricula : coleccionMatriculas) {
            //Si en la matricula actual, su alumno = al alumno pasado...
            if (matricula.getAlumno().equals(alumno)) {
                contador++;
            }
        }

        //Crear array con el número de coincidencias del alumno en las matrículas:
        Matricula[] coleccionMatriculasAlumn = new Matricula[contador];

        int i = 0;
        //Para asignar las matrículas con coincidencias al nuevo array:
        for (Matricula matriculaAlumno : coleccionMatriculas){
            if (matriculaAlumno.getAlumno().equals(alumno)){
                coleccionMatriculasAlumn[i] = matriculaAlumno;
                i++;
            }
        }
        return coleccionMatriculasAlumn;
    }


    public Matricula[] get(String cursoAcademico){

        int contador = 0;

        for (Matricula matricula : coleccionMatriculas) {
            if (matricula.getCursoAcademico().equals(cursoAcademico)) {
                contador++;
            }
        }

        Matricula[] coleccionMatriculasCurso = new Matricula[contador];

        int i = 0;

        for (Matricula matriculaCurso : coleccionMatriculas) {

            if (matriculaCurso.getCursoAcademico().equals(cursoAcademico)){
                coleccionMatriculasCurso[i] = matriculaCurso;
                i++;
            }
        }
        return coleccionMatriculasCurso;
    }


    public Matricula[] get(CicloFormativo cicloFormativo) {
        int contador = 0;

        for (Matricula matricula : coleccionMatriculas) {
            for (Asignatura asignatura : matricula.getColeccionAsignaturas()) {

                if (asignatura.getCicloFormativo().equals(cicloFormativo)) {
                    contador++;
                    break;
                }
            }
        }

        Matricula[] coleccionMatriculasCiclo = new Matricula[contador];

        int i = 0;

        // Recorre las matrículas
        for (Matricula matricula : coleccionMatriculas) {
            //Dentro de una matrícula, recorre sus asignaturas
            for (Asignatura asignatura : matricula.getColeccionAsignaturas()) {

                // Si una asignatura específica de las recorridas tiene ciclo = al ciclo pasado...
                if (asignatura.getCicloFormativo().equals(cicloFormativo)) {
                    coleccionMatriculasCiclo[i] = matricula;
                    i++;

                    // break para evitar procesar más asignaturas una vez que se encuentra una
                    // coincidencia dentro de la matrícula
                    break;
                }
            }
        }
        return coleccionMatriculasCiclo;
    }

}
