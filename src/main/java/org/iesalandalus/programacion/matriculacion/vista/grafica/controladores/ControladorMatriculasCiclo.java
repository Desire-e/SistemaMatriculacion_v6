package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControladorMatriculasCiclo {

    @FXML private TableView<Matricula> tvMatriculasCiclo;
    // nombre del ciclo
    @FXML private TableColumn<Matricula, String> tcCicloMatr;
    @FXML private TableColumn<Matricula, Integer> tcIdMatr;
    @FXML private TableColumn<Matricula, String> tcCursoMatr;
    @FXML private TableColumn<Matricula, LocalDate> tcFechaMatr;
    // listado de asignaturas (Nombre, Modalidad)
    @FXML private TableColumn<Matricula, String> tcAsignaturasMatr;
    private ObservableList<Matricula> obsMatriculas = FXCollections.observableArrayList();
    private List<Matricula> coleccionMatriculas = new ArrayList<>();


    public void cargaMatriculasCiclo(List<Matricula> matriculas, CicloFormativo cicloSeleccionado){
        // Obtener coleccion de matrículas del ciclo seleccionado en la tabla de GestionCiclosFormativos
        this.coleccionMatriculas=matriculas;

        // Establecer qué atributo tendrá cada columna.
        this.tcIdMatr.setCellValueFactory(new PropertyValueFactory<Matricula, Integer>("idMatricula"));
        this.tcCursoMatr.setCellValueFactory(new PropertyValueFactory<Matricula, String>("cursoAcademico"));
        this.tcFechaMatr.setCellValueFactory(new PropertyValueFactory<Matricula, LocalDate>("fechaMatriculacion"));

            // fila: objeto que representa una fila de la tabla. Es objeto
            // tipo CellDataFeatures<S,T> (<Matricula, String>). Es decir, es un
            // envoltorio que contiene info sobre una fila específica de la tabla.
            // Al objeto completo de la fila (Matricula), se accede con getValue().
        this.tcCicloMatr.setCellValueFactory(fila -> {
            // Este método obtiene directamente de GestionCiclosFormativos el
            // CicloFormativo de la matrícula.
            String nombreCiclo = cicloSeleccionado.getNombre();

            // La TableView espera un ObservableValue<T> (<String>) del
            // setCellValueFactory.
            // Como estamos devolviendo un String, lo envolvemos en un
            // ReadOnlyStringWrapper (implementación de ObservableValue<String>).
            // Esto permite que el contenido sea observable (reacción a cambios).
            return new ReadOnlyStringWrapper(nombreCiclo);
        });

        this.tcAsignaturasMatr.setCellValueFactory(fila -> {

            List<Asignatura> coleccionAsignaturas = fila.getValue().getColeccionAsignaturas();

            if (coleccionAsignaturas == null || coleccionAsignaturas.isEmpty()) {
                return new ReadOnlyStringWrapper("");
            }

            Modalidad modalidad;
            String cadenaAsignatura = "";

            // recorre las asignaturas de la Matricula
            for (Asignatura asig : coleccionAsignaturas){

                // obtén de una asignatura, su CicloFormativo, entonces el Grado del CicloFormativo
                Grado gradoCicloAsig = asig.getCicloFormativo().getGrado();

                // si el Grado es GradoD (tiene atributo modalidad)
                if (gradoCicloAsig instanceof GradoD){
                    // obtener modalidad del GradoD
                    modalidad = ((GradoD) gradoCicloAsig).getModalidad();
                    String cadenaModalidad="";
                    switch (modalidad) {
                        case Modalidad.PRESENCIAL:
                            cadenaModalidad="Presencial";
                            break;
                        case Modalidad.SEMIPRESENCIAL:
                            cadenaModalidad="Semipresencial";
                            break;
                    }

                    // concatena "nombre - modalidad" de la Asignatura actualmente recorrida,
                    // con la/s anterior/es almacenadas en la cadena
                    cadenaAsignatura = cadenaAsignatura + asig.getNombre() + " - " + cadenaModalidad + "\n";
                }
                // si el Grado es GradoE (NO tiene atributo modalidad)
                else
                    cadenaAsignatura = cadenaAsignatura + asig.getNombre() + "\n";
            }

            return new ReadOnlyStringWrapper(cadenaAsignatura);
        });

        // 6º asigna la fuente de datos de la tabla (un ObservableList).
        tvMatriculasCiclo.setItems(obsMatriculas);

        // 8º Reemplaza contenido actual del ObservableList, por el de la coleccion asignada.
        obsMatriculas.setAll(coleccionMatriculas);
    }







    }
