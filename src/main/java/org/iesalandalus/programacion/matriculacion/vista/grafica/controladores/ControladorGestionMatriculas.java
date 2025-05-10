package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.vista.Vista;
import org.iesalandalus.programacion.matriculacion.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorGestionMatriculas implements Initializable {

    /* MENU BAR */
    @FXML private MenuItem miGestionAlumnos;
    @FXML private MenuItem miGestionAsignaturas;
    @FXML private MenuItem miGestionCiclos;
    // en esta ventana no hará nada
    @FXML private MenuItem miGestionMatriculas;
    @FXML private MenuItem miSalir;

    @FXML private Button btnAniadirMatricula;

    /* CONTEXT MENU */
    @FXML private MenuItem miAnularMatricula;

    /* TABLEVIEW */
    @FXML private TableView<Matricula> tvMatriculas;
    @FXML private TableColumn<Matricula, String> tcNombreAlumnoMatr;
    @FXML private TableColumn<Matricula, String> tcDniAlumnoMatr;
    @FXML private TableColumn<Matricula, String> tcCursoAcaMatr;
    @FXML private TableColumn<Matricula, String> tcAsignaturasMatr;
    @FXML private TableColumn<Matricula, LocalDate> tcFechaAnulaMatr;
    // Fuente de datos de tabla, inicialmente vacía
    private ObservableList<Matricula> obsMatriculas = FXCollections.observableArrayList();
    // La colección que usa ObservableList para pasar datos a tabla, inicialmente vacía
    private List<Matricula> coleccionMatriculas = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cargaListadoMatriculas();
    }

    private void cargaListadoMatriculas(){
        this.tcNombreAlumnoMatr.setCellValueFactory(fila -> {
            String nombreAlumnoMatr = fila.getValue().getAlumno().getNombre();
            return new ReadOnlyStringWrapper(nombreAlumnoMatr);
        });
        this.tcDniAlumnoMatr.setCellValueFactory(fila -> {
            String dniAlumnoMatr = fila.getValue().getAlumno().getDni();
            return new ReadOnlyStringWrapper(dniAlumnoMatr);
        });
        this.tcCursoAcaMatr.setCellValueFactory(new PropertyValueFactory<Matricula, String>("cursoAcademico"));
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

        // Si fechaAnulacion es null, la celda muestra "null" ; hacer que muestre ""
        // La celda normal de la fechaAnulacion:
        // setCellValueFactory(): indica qué valor debe usar (nivel interno)
        this.tcFechaAnulaMatr.setCellValueFactory(new PropertyValueFactory<Matricula, LocalDate>("fechaAnulacion"));
        // La celda personalizada de la fechaAnulacion, si es null:
        // setCellFactory(): indica cómo se debe mostrar (nivel visual)
        this.tcFechaAnulaMatr.setCellFactory(celda -> new TableCell<Matricula, LocalDate>() {
            // - updateItem(): método de Cell > TableCell, llamado automáticamente cada que JavaFX
            //   actualiza una celda. Obtiene un obj y un boolean.
            //   Asigna internamente el nuevo obj (LocalDate) y el boolean vacio (indica si celda
            //   está vacía) a la celda.
            //   Limpia el contenido anterior si está vacío (es como limpiarla antes de aplicar
            //   las actualizaciones sobre la celda)
            @Override
            protected void updateItem(LocalDate fechaAnula, boolean vacio) {
                super.updateItem(fechaAnula, vacio);
                if (vacio || fechaAnula == null) {
                    setText("");
                } else {
                    setText(fechaAnula.toString()); // O usar fechaAnula.format(...) si prefieres formato
                }
            }
        });



        tvMatriculas.setItems(obsMatriculas);
        try {
            /* Excepción de get() en Matriculas, al crear new Matricula() para almacenarla de la BD a la
            coleccion de objetos Matricula: Se lanza si alguna matrícula, sumando las horas de cada
            asignatura, supera el nº de horas > 1000.
            Trato aquí por si se inserta matrícula desde la BD, que no de error al ejecutar aplicación. */
            coleccionMatriculas = Vista.getControlador().getMatriculas();
        } catch (OperationNotSupportedException e) {
            Dialogos.mostrarDialogoInformacion("Gestión de matriculas",
                    "No se pudieron cargar algunas matrículas: " + e.getMessage());
        }
        obsMatriculas.setAll(coleccionMatriculas);
    }


    /* MENU BAR */
    @FXML
    void salir(ActionEvent event) {
        if (Dialogos.mostrarDialogoConfirmacion("Sistema de matriculacion",
                "¿Realmente quieres salir de la aplicacion?")) {
            System.exit(0);
        }
        else event.consume();
    }
    @FXML
    void abrirVentanaGestionAlumnos(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource
                    ("vistas/GestionAlumnos.fxml"));

            Parent raiz = fxmlLoader.load();

            Scene escena = new Scene(raiz, 800, 500);

            Stage escenario = new Stage();
            escenario.setTitle("Gestion de matriculas");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setResizable(false);
            escenario.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    void abrirVentanaGestionAsignaturas(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource
                    ("vistas/GestionAsignaturas.fxml"));

            Parent raiz = fxmlLoader.load();

            Scene escena = new Scene(raiz, 800, 500);

            Stage escenario = new Stage();
            escenario.setTitle("Gestion de asignaturas");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setResizable(false);
            escenario.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    void abrirVentanaGestionCiclos(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource
                    ("vistas/GestionCiclosFormativos.fxml"));

            Parent raiz = fxmlLoader.load();

            Scene escena = new Scene(raiz, 800, 500);

            Stage escenario = new Stage();
            escenario.setTitle("Gestion de ciclos formativos");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setResizable(false);
            escenario.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    /* BUTTON */
    @FXML
    void abrirVentanaAniadirMatr(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource
                    ("vistas/AniadirMatr.fxml"));
            Parent raiz = fxmlLoader.load();


            ControladorAniadirMatr controladorAniadirMatr = fxmlLoader.getController();
            controladorAniadirMatr.cargaDatos(coleccionMatriculas, obsMatriculas);


            Scene escena = new Scene(raiz, 600, 500);

            Stage escenario = new Stage();
            escenario.setTitle("Añadir matriculas");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setResizable(false);
            escenario.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    /* CONTEXT MENU */
    @FXML
    void anularMatricula(ActionEvent event) {
        // Matrícula seleccionada actualemente
        Matricula matriculaSeleccionada = tvMatriculas.getSelectionModel().getSelectedItem();

        // Comprobar si no hay seleccionada
        /*if (matriculaSeleccionada == null) {
            Dialogos.mostrarDialogoError("Anular matricula", "Debe seleccionar una matricula para anularla.");
            return;
        }*/

        // Comprobar si tiene una fechaAnulacion (ya anulada)
        if (matriculaSeleccionada.getFechaAnulacion() != null){
            Dialogos.mostrarDialogoError("Anular matrícula", "La matricula ya ha sido anulada");
            return;
        }

        // Anular: abre ventana AnularMatr.fxml

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class
                    .getResource("vistas/AnularMatr.fxml"));

            Parent raiz = fxmlLoader.load();

            ControladorAnularMatr controladorAnularMatr = fxmlLoader.getController();
            controladorAnularMatr.cargaMatricula(matriculaSeleccionada, obsMatriculas, coleccionMatriculas);

            Scene escena = new Scene (raiz, 500, 220);
            Stage escenario = new Stage();
            escenario.setTitle("Anular matrícula");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setResizable(false);
            escenario.showAndWait();
        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
