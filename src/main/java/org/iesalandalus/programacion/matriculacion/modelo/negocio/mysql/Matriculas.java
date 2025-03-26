package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Matriculas implements IMatriculas {
    private List<Matricula> coleccionMatriculas;

    public Matriculas(){
        this.coleccionMatriculas = new ArrayList<>();
    }


    @Override
    public void comenzar() {
        //establecerConexion()
    }
    @Override
    public void terminar() {
        //cerrarConexion()
    }


    @Override
    public int getTamano() {
        // size() devuelve el tamaño de la lista
        return coleccionMatriculas.size();
    }


    @Override
    public List<Matricula> get() throws OperationNotSupportedException {
        return copiaProfundaMatriculas();
    }


    private List<Matricula> copiaProfundaMatriculas() throws OperationNotSupportedException {
        List<Matricula> copiaProfunda = new ArrayList<>();

        for (Matricula matricula : coleccionMatriculas) {
            if (matricula != null) {
                copiaProfunda.add(new Matricula(matricula));
            }
        }
        return  copiaProfunda;
    }


    @Override
    public void insertar (Matricula matricula) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (matricula == null){
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }

        if (coleccionMatriculas.contains(matricula)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese código.");
        }

        coleccionMatriculas.add(matricula);
    }



    @Override
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException{
        if (matricula == null){
            throw new NullPointerException("Matrícula nula no puede buscarse.");
        }

        int indice;
        if (coleccionMatriculas.contains(matricula)) {
            indice=coleccionMatriculas.indexOf(matricula);
            matricula = coleccionMatriculas.get(indice);
            return new Matricula(matricula);
        }
        else return null;
    }


    @Override
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null){
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }

        if (!coleccionMatriculas.contains(matricula)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
        }
        else coleccionMatriculas.remove(matricula);
    }



    // El método get que está sobrecargado y devolverá una colección de las matrículas realizadas por el
    // alumno pasado por parámetro o unca colección de las matrículas realizadas para el curso académico
    // indicado como parámetro o una colección de las matrículas realizadas para el ciclo formativo
    // indicado como parámetro.


    @Override
    public List<Matricula> get(Alumno alumno){
        /*int contador = 0;
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
        */

        /*int i = 0;
        //Para asignar las matrículas con coincidencias al nuevo array:
        for (Matricula matriculaAlumno : coleccionMatriculas){
            if (matriculaAlumno.getAlumno().equals(alumno)){
                coleccionMatriculasAlumn[i] = matriculaAlumno;
                i++;
            }
        }
        return coleccionMatriculasAlumn;
        */

        /* USANDO BUCLE
        // Crear lista
        List<Matricula> matriculasAlumno = new ArrayList<>();

        // Recorrer colección, si en la matrícula su alumno es el indicado,  se añade la matrícula a la
        // lista de matrículas del Alumno
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula.getAlumno().equals(alumno)) {
                matriculasAlumno.add(matricula);
            }
        }
        return matriculasAlumno;
        */

        /* USANDO STREAM
           Diferencia .toList() y collect(Collectors.toList()):

           > Usa .toList() si no necesitas modificar la lista después.
           > Usa .collect(Collectors.toList()) si necesitas una lista mutable (puede añadir/borrar datos).
           > Si necesitas una lista específica como LinkedList, usa .collect(Collectors.toCollection(LinkedList::new)).
        */

        // obtiene alumno de clase Modelo getMatriculas()
        List<Matricula> matriculasAlumno = coleccionMatriculas.stream()
                .filter(matricula -> matricula.getAlumno().equals(alumno))
                .collect(Collectors.toList());
        return matriculasAlumno;
    }


    @Override
    public List<Matricula> get(String cursoAcademico){
        /*
        List<Matricula> matriculasCurso = new ArrayList<>();

        for (Matricula matricula : coleccionMatriculas) {
            if (matricula.getCursoAcademico().equals(cursoAcademico)) {
                matriculasCurso.add(matricula);
            }
        }
        return matriculasCurso;
        */

        List<Matricula> matriculasCurso = coleccionMatriculas.stream()
                .filter(matricula -> matricula.getCursoAcademico().equals(cursoAcademico))
                .collect(Collectors.toList());
        return matriculasCurso;
    }


    @Override
    public List<Matricula> get(CicloFormativo cicloFormativo) {
        /*
        List<Matricula> matriculasCiclo = new ArrayList<>();

        for (Matricula matricula : coleccionMatriculas) {
            for (Asignatura asignatura : matricula.getColeccionAsignaturas()){
                if (asignatura.getCicloFormativo().equals(cicloFormativo)) {
                    matriculasCiclo.add(matricula);
                    // break para evitar procesar más asignaturas una vez que se encuentra una
                    // coincidencia dentro de la matrícula
                    break;
                }
            }
        }
        return matriculasCiclo;
        */

        List<Matricula> matriculasCiclo = coleccionMatriculas.stream()              // listado de matrículas.
                .filter(matricula -> matricula.getColeccionAsignaturas().stream()   // listado de asignaturas de cada matricula.
                        .anyMatch(asignatura -> asignatura.getCicloFormativo().equals(cicloFormativo)))
                        // anyMatch da true si alguna asignatura pertenece al ciclo formativo
                .collect(Collectors.toList());
        return matriculasCiclo;
    }

}
