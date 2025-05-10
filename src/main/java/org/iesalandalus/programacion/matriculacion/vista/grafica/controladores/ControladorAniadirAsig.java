package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import org.iesalandalus.programacion.matriculacion.vista.Vista;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;

public class ControladorAniadirAsig {

    // Atributos Asignatura:
    /* COMBO BOX */
    @FXML private ComboBox<CicloFormativo> cbCicloFormativo;
    List<CicloFormativo> coleccionCiclos = new ArrayList<>();
    ObservableList<CicloFormativo> obsCiclos = FXCollections.observableArrayList();
    /* TOGGLE BUTTON - curso */
    @FXML private ToggleButton tbtnCursoPrimero;
    @FXML private ToggleButton tbtnCursoSegundo;
    ToggleGroup cursoGroup = new ToggleGroup();
    /* TOGGLE BUTTON - especialidad */
    @FXML private ToggleButton tbtnEspecialidadFol;
    @FXML private ToggleButton tbtnEspecialidadInformatica;
    @FXML private ToggleButton tbtnEspecialidadSistemas;
    ToggleGroup especialidadGroup = new ToggleGroup();
    @FXML private TextField tfCodigoAsig;
    @FXML private TextField tfNombreAsig;
    @FXML private TextField tfHorasAnualAsig;
    @FXML private TextField tfHorasDesdobleAsig;


    // ejecuta aniadir()
    @FXML private Button btnAniadir;



    List<Asignatura> coleccionAsignaturas = new ArrayList<>();
    ObservableList<Asignatura> obsAsignaturas = FXCollections.observableArrayList();

    public void cargaDatos(List<Asignatura> coleccionAsignaturas, ObservableList<Asignatura> obsAsignaturas) {
        this.coleccionAsignaturas = coleccionAsignaturas;
        this.obsAsignaturas = obsAsignaturas;

        /* COMBO BOX */
        // ** comprobar si existen CicloFormativo registrados (si no hay no se pueden añadir Asignatura)
        cargaCiclosRegistrados();

        /* RADIO BUTTON */
        // Agrupación en ToggleGroup
        tbtnCursoPrimero.setToggleGroup(cursoGroup);
        tbtnCursoSegundo.setToggleGroup(cursoGroup);
        // inicialmente seleccionado primero
        cursoGroup.selectToggle(tbtnCursoPrimero);

        // Agrupación en ToggleGroup
        tbtnEspecialidadFol.setToggleGroup(especialidadGroup);
        tbtnEspecialidadSistemas.setToggleGroup(especialidadGroup);
        tbtnEspecialidadInformatica.setToggleGroup(especialidadGroup);
        // inicialmente seleccionado informática
        especialidadGroup.selectToggle(tbtnEspecialidadInformatica);

    }

    /* COMBO BOX */
    private void cargaCiclosRegistrados(){
        this.coleccionCiclos = Vista.getControlador().getCiclosFormativos();
        cbCicloFormativo.setItems(obsCiclos);
        obsCiclos.setAll(coleccionCiclos);

        // Valora si coleccionCiclos esta vacia, muestra diálogo y cierre la ventana AniadirAsig)
        if (coleccionCiclos.isEmpty()){
            Dialogos.mostrarDialogoError("Añadir asignatura", "Para añadir asignaturas deben haber ciclos formativos registrados.");
            // Cierra la ventana (Stage) en la que se encuentra el ComboBox cbCicloFormativo
            ((Stage) cbCicloFormativo.getScene().getWindow()).close();
        }


        // Para mostrar solo los nombres en el ComboBox y no el toString() completo del ciclo:

        // - setCellFactory(): define lo que se muestra en cada celda (elemento del combobox)
        // - celda -> new ListCell<>(): crea celda personalizada (sobre celda previa, el
        //   elemento del combobox)
        cbCicloFormativo.setCellFactory(celda -> new ListCell<>() {
            // - updateItem(): método de Cell > ListCell, llamado automáticamente cada que JavaFX
            //   actualiza una celda. Obtiene un obj y un boolean.
            //   Asigna internamente el nuevo obj (ciclo) y el boolean vacio (indica si celda
            //   está vacía) a la celda.
            //   Limpia el contenido anterior si está vacío (es como limpiarla antes de aplicar
            //   las actualizaciones sobre la celda)
            @Override
            protected void updateItem(CicloFormativo ciclo, boolean vacio) {
                // limpia celda
                super.updateItem(ciclo, vacio);
                // aplico mi propia actualización sobre la celda
                if (vacio || ciclo == null){
                    // si está vacía la celda o el ciclo es null, celda sin texto
                    setText(null);
                } else
                    // celda solo con nombre del ciclo
                    setText(ciclo.getNombre());
            }
        });

        // - setButtonCell(): define lo que se muestra en la 'celda' del valor seleccionado,
        //   cuando combobox no está desplegado.
        cbCicloFormativo.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(CicloFormativo ciclo, boolean vacio) {
                // limpia celda
                super.updateItem(ciclo, vacio);
                // aplico mi propia actualización sobre la celda
                if (vacio || ciclo == null){
                    setText(null);
                } else
                    setText(ciclo.getNombre());
            }
        });
    }


    @FXML
    void aniadir(ActionEvent event) {

        Asignatura asignaturaNueva;

        // 1. Creación de Asignatura
        //    Capturar excepciones por datos introducidos inválidos (código, nombre, ...)
        try {
            // creo objeto previo Curso
            Curso cursoAsig;
            if (cursoGroup.getSelectedToggle() == tbtnCursoSegundo){
                cursoAsig = Curso.SEGUNDO;
            } else cursoAsig = Curso.PRIMERO;

            // creo objeto previo EspecialidadProfesorado
            EspecialidadProfesorado especialidadAsig;
            if (especialidadGroup.getSelectedToggle() == tbtnEspecialidadFol){
                especialidadAsig = EspecialidadProfesorado.FOL;
            } else if (especialidadGroup.getSelectedToggle() == tbtnEspecialidadSistemas){
                especialidadAsig = EspecialidadProfesorado.SISTEMAS;
            } else especialidadAsig = EspecialidadProfesorado.INFORMATICA;

            asignaturaNueva = new Asignatura(
                    tfCodigoAsig.getText(),
                    tfNombreAsig.getText(),
                    Integer.parseInt(tfHorasAnualAsig.getText()),
                    cursoAsig,
                    Integer.parseInt(tfHorasDesdobleAsig.getText()),
                    especialidadAsig,
                    cbCicloFormativo.getSelectionModel().getSelectedItem());
        } catch(Exception e){
            Dialogos.mostrarDialogoError("Añadir asignatura", "Datos introducidos erroneos: " + e.getMessage());
            return;
        }

        // 2. Comprobar si asignaturaNueva existe
        if (obsAsignaturas.contains(asignaturaNueva)) {
            Dialogos.mostrarDialogoError("Añadir asignatura", "La asignatura ya existe.");
            return;
        }

        // 3. Añade Asignatura en la colección y recarga la fuente de datos de la tabla de GestionAsignaturas
        // Capturar OperationNotSupportedEx. de insertar()
        try {
            // inserta en el sistema
            Vista.getControlador().insertar(asignaturaNueva);

            // inserta en la tabla
            // Refresca la fuente de datos de la tabla (pasándole coleccion inicial + nueva asignatura,
            // para que siempre figuren los datos reales sin tener que cerrar y abrir de nuevo Gestion Asignaturas)
            coleccionAsignaturas.add(asignaturaNueva);
            obsAsignaturas.setAll(coleccionAsignaturas);

        } catch (OperationNotSupportedException e) {
            Dialogos.mostrarDialogoError("Añadir asignatura", "La asignatura no pudo ser insertada:" + e.getMessage());
            return;
        }

        // 4. Informar de inserción exitosa
        Dialogos.mostrarDialogoInformacion("Añadir asignatura", "Asignatura insertada correctamente.");
        // cierra la ventana
        ((Stage) btnAniadir.getScene().getWindow()).close();
    }



}
