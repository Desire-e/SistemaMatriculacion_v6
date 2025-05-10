package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.iesalandalus.programacion.matriculacion.vista.Vista;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;

public class ControladorAniadirMatr {

    @FXML private DatePicker dpFechaMatriculacionMatr;
    @FXML private TextField tfCursoAcademicoMatr;
    @FXML private TextField tfIdMatr;
    /* LISTVIEW */
    @FXML private ListView<Asignatura> lvAsignaturasMatr;
    private List<Asignatura> coleccionAsignaturas = new ArrayList<>();
    private ObservableList<Asignatura> obsAsignaturas = FXCollections.observableArrayList();
    /* COMBOBOX */
    @FXML private ComboBox<Alumno> cbAlumnoMatr;
    private List<Alumno> coleccionAlumnos = new ArrayList<>();
    private ObservableList<Alumno> obsAlumnos = FXCollections.observableArrayList();


    @FXML private Button btnAniadir;



    // Carga los datos de las matrículas ya registradas
    private List<Matricula> coleccionMatriculas = new ArrayList<>();
    private ObservableList<Matricula> obsMatriculas = FXCollections.observableArrayList();
    public void cargaDatos(List<Matricula> coleccionMatriculas, ObservableList<Matricula> obsMatriculas){
        this.coleccionMatriculas = coleccionMatriculas;
        this.obsMatriculas = obsMatriculas;

        // Comprueba que existen en el sistema Asignatura y Alumno previamente
        // Carga Alumnos y Asignaturas registradas
        cargaAsignaturasRegistradas();
        cargaAlumnosRegistrados();
    }


    private void cargaAsignaturasRegistradas(){
        this.coleccionAsignaturas = Vista.getControlador().getAsignaturas();
        lvAsignaturasMatr.setItems(obsAsignaturas);
        obsAsignaturas.setAll(coleccionAsignaturas);
        // Establecer múltiple selección (una Matricula puede tener muchas Asignatura):
        lvAsignaturasMatr.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        // Valora si hay asignaturas registradas, muestra diálogo y cierre la ventana
        if (coleccionAsignaturas.isEmpty()){
            Dialogos.mostrarDialogoError("Añadir matricula", "Para añadir matriculas deben haber asignaturas registradas.");
            // Cierra la ventana (Stage) en la que se encuentra el ComboBox cbCicloFormativo
            ((Stage) lvAsignaturasMatr.getScene().getWindow()).close();
        }


        // Lo que mostrará el listview visualmente de cada objeto Asignatura:
        lvAsignaturasMatr.setCellFactory(celda ->new ListCell<>(){
            /*
            - updateItem(): Actualiza celda. Asigna internamente el nuevo obj
              (asignatura) y el boolean vacio (indica si celda está vacía) a la celda.
              Limpia el contenido anterior si está vacío (es como limpiarla antes de
              aplicar las actualizaciones sobre la celda) */
            @Override
            protected void updateItem(Asignatura asignatura, boolean vacio) {
                // limpia celda
                super.updateItem(asignatura, vacio);
                // aplico mi propia actualización sobre la celda
                if (vacio || asignatura == null){
                    // si está vacía la celda o asignatura es null, celda sin texto
                    setText(null);
                } else
                    // celda solo con nombre del ciclo
                    setText(asignatura.getNombre());
            }
        });
    }

    private void cargaAlumnosRegistrados(){
        this.coleccionAlumnos = Vista.getControlador().getAlumnos();
        cbAlumnoMatr.setItems(obsAlumnos);
        obsAlumnos.setAll(coleccionAlumnos);


        // Valora si hay alumnos registrados, muestra diálogo y cierre la ventana
        if (coleccionAlumnos.isEmpty()){
            Dialogos.mostrarDialogoError("Añadir matricula", "Para añadir matriculas deben haber alumnos registrados.");
            // Cierra la ventana (Stage) en la que se encuentra el ComboBox cbCicloFormativo
            ((Stage) cbAlumnoMatr.getScene().getWindow()).close();
        }


        // Lo que mostrará el combobox visualmente de cada objeto Alumno:
        cbAlumnoMatr.setCellFactory(celda -> new ListCell<>() {
            @Override
            protected void updateItem(Alumno alumno, boolean vacio) {
                // limpia celda
                super.updateItem(alumno, vacio);
                // aplico mi propia actualización sobre la celda
                if (vacio || alumno == null){
                    // si está vacía la celda o el alumno es null, celda sin texto
                    setText(null);
                } else
                    // celda solo con nombre del alumno
                    setText(alumno.getNombre());
            }
        });

        // Lo que se muestra en la 'celda' del valor seleccionado (cuando combobox no está desplegado):
        cbAlumnoMatr.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Alumno alumno, boolean vacio) {
                // limpia celda
                super.updateItem(alumno, vacio);
                // aplico mi propia actualización sobre la celda
                if (vacio || alumno == null){
                    setText(null);
                } else
                    setText(alumno.getNombre());
            }
        });
    }



    @FXML
    void aniadir(ActionEvent event) {

        Matricula matriculaNueva;

        // 1. Crear matrícula
        List<Asignatura> asignaturasSeleccionadas = new ArrayList<>(lvAsignaturasMatr.getSelectionModel().getSelectedItems());
        try {
            matriculaNueva = new Matricula(
                    Integer.parseInt(tfIdMatr.getText()),
                    tfCursoAcademicoMatr.getText(),
                    dpFechaMatriculacionMatr.getValue(),
                    cbAlumnoMatr.getSelectionModel().getSelectedItem(),
                    asignaturasSeleccionadas);
        } catch (Exception e) {
            Dialogos.mostrarDialogoError("Añadir matrícula", "Datos introducidos erroneos: " + e.getMessage());
            return;
        }

        // 2. Comprueba si ya existe
        if (obsMatriculas.contains(matriculaNueva)) {
            Dialogos.mostrarDialogoError("Añadir matrícula", "La matricula ya existe");
            return;
        }

        // 3. Añadir matricula a la coleccion (al sistema) y recarga la fuente de datos de la tabla
        try {
            // inserta en el sistema
            Vista.getControlador().insertar(matriculaNueva);

            // inserta en la tabla
            /*coleccionMatriculas.add(matriculaNueva);
            obsMatriculas.setAll(coleccionMatriculas);*/

            // tras actualizar sistema con matriculaNueva, actualizo obs de la tabla en GestionMatriculas
            // *** cuando añado, se añade un duplicado, por ello compruebo que no hayam duplicados:
            if (!coleccionMatriculas.contains(matriculaNueva)){
                coleccionMatriculas.add(matriculaNueva);
            }
            obsMatriculas.setAll(coleccionMatriculas);

        } catch (OperationNotSupportedException e) {
            Dialogos.mostrarDialogoError("Añadir matrícula", "La matrícula no pudo ser insertada:" + e.getMessage());
            return;
        }

        // 4. Informar de inserción exitosa
        Dialogos.mostrarDialogoInformacion("Añadir matrícula", "Matricula insertada correctamente");
        // cierra la ventana
        ((Stage) btnAniadir.getScene().getWindow()).close();
    }



}
