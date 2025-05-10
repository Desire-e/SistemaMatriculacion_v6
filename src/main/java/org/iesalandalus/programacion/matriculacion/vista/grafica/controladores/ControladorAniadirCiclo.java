package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.vista.Vista;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.util.ArrayList;
import java.util.List;

public class ControladorAniadirCiclo {

    // atributos de CicloFormativo
    @FXML private TextField tfCodigoCiclo;
    @FXML private TextField tfFamiliaProfCiclo;
    @FXML private TextField tfNombreCiclo;
    @FXML private TextField tfHorasCiclo;


    // atributos de Grado
    @FXML private TextField tfNombreGradoCiclo;
    @FXML private TextField tfAniosGradoCiclo;
    /* RADIO BUTTON  - tipo de grado */
    @FXML private RadioButton rbGradoD;
    @FXML private RadioButton rbGradoE;
    ToggleGroup tipoGradoGroup = new ToggleGroup();
    /* TOGGLE BUTTON - atributos de GradoD */
    @FXML private ToggleButton tbtnModalidadPresencialGradoD;
    @FXML private ToggleButton tbtnModalidadSemipresenGradoD;
    ToggleGroup modalidadGradoDGroup = new ToggleGroup();
    // atributos de GradoE
    @FXML private TextField tfNumEdicionesGradoE;


    /* BUTTON - ejecuta aniadir() */
    @FXML private Button btnAniadir;


    /* 1º Declara fuente de datos de la tabla a insertar el ciclo */
    private List<CicloFormativo> coleccionCiclos = new ArrayList<>();
    private ObservableList<CicloFormativo> obsCiclos = FXCollections.observableArrayList();

    /* 2º Inicializa fuente de datos de la tabla (nos pasa los valores iniciales
          desde ControladorGestionCiclosFormativos.abrirVentanaAniadirCiclo()) */
    public void cargaDatos(List<CicloFormativo> coleccionCiclos, ObservableList<CicloFormativo> obsCiclos) {
        this.coleccionCiclos = coleccionCiclos;
        this.obsCiclos = obsCiclos;


        /* RADIO BUTTON */
        // Agrupación en ToggleGroup
        rbGradoD.setToggleGroup(tipoGradoGroup);
        rbGradoE.setToggleGroup(tipoGradoGroup);
        // inicialmente seleccionado GradoE
        tipoGradoGroup.selectToggle(rbGradoE);
        /*
        - selectedToggleProperty() devuelve una propiedad observable que
          representa cuál RadioButton está seleccionado.
        - addListener(...) añade un listener que se ejecutará cada que
          cambie la selección del Toggle (en este caso RadioButtons).

        En la función lambda (...)->...
        - obs: el objeto observable (en este caso, la propiedad de selección).
        - oldToggle: el Toggle que estaba seleccionado antes del cambio.
        - newToggle: el nuevo Toggle seleccionado.
        - Llama a actualizarControlesGrado(): cada vez que el usuario cambia
          Toggle seleccionado, se actualizan los controles (activados/desactivados). */
        tipoGradoGroup.selectedToggleProperty().addListener((obs, oldToggle, newToggle) -> actualizarControlesGrado());
        // Se llama tras añadir el listener, para que los controles se configuren
        // con la opción por defecto (en este caso GradoE).
        actualizarControlesGrado();


        /* TOGGLE BUTTON */
        // Agrupación en el ToggleGroup
        tbtnModalidadPresencialGradoD.setToggleGroup(modalidadGradoDGroup);
        tbtnModalidadSemipresenGradoD.setToggleGroup(modalidadGradoDGroup);
        // inicialmente seleccionado Modalidad.PRESENCIAL
        modalidadGradoDGroup.selectToggle(tbtnModalidadPresencialGradoD);
    }

    private void actualizarControlesGrado(){
        if (tipoGradoGroup.getSelectedToggle() == rbGradoD){
            tfNumEdicionesGradoE.setDisable(true);
            tbtnModalidadPresencialGradoD.setDisable(false);
            tbtnModalidadSemipresenGradoD.setDisable(false);
        }
        if (tipoGradoGroup.getSelectedToggle() == rbGradoE){
            tfNumEdicionesGradoE.setDisable(false);
            tbtnModalidadPresencialGradoD.setDisable(true);
            tbtnModalidadSemipresenGradoD.setDisable(true);
        }
    }



    @FXML
    void aniadir(ActionEvent event) {

        CicloFormativo cicloNuevo;

        // Creación del ciclo:
        // * Capturar excepciones por datos introducidos inválidos (código, familiaProfesional, ...)
        try {
            // 1. creación objeto previo Grado D o E
            Grado gradoCicloNuevo;
            if (tipoGradoGroup.getSelectedToggle() == rbGradoD) {
                // objeto Modalidad
                Modalidad modalidadCicloNuevo;
                if(modalidadGradoDGroup.getSelectedToggle().equals(tbtnModalidadSemipresenGradoD)){
                    modalidadCicloNuevo = Modalidad.SEMIPRESENCIAL;
                } else modalidadCicloNuevo = Modalidad.PRESENCIAL;    // opcion por defecto, puesto en cargaDatos()
                // creación de GradoD
                gradoCicloNuevo = new GradoD(
                        tfNombreGradoCiclo.getText(),
                        Integer.parseInt(tfAniosGradoCiclo.getText()),
                        modalidadCicloNuevo);
            }
            else { // opcion por defecto, puesto en cargaDatos()
                // creación de GradoE
                gradoCicloNuevo = new GradoE(
                        tfNombreGradoCiclo.getText(),
                        Integer.parseInt(tfAniosGradoCiclo.getText()),
                        Integer.parseInt(tfNumEdicionesGradoE.getText()));
            }


            // 2. creación objeto CicloFormativo
            cicloNuevo = new CicloFormativo(
                    Integer.parseInt(tfCodigoCiclo.getText()),
                    tfFamiliaProfCiclo.getText(),
                    gradoCicloNuevo,
                    tfNombreCiclo.getText(),
                    Integer.parseInt(tfHorasCiclo.getText()));
        } catch(Exception e){
            Dialogos.mostrarDialogoError("Añadir ciclo formativo", "Datos introducidos erroneos: " +  e.getMessage());
            return;
        }


        // Comprobar si cicloNuevo creado existe
        if (obsCiclos.contains(cicloNuevo)) {
            Dialogos.mostrarDialogoError("Añadir ciclo formativo", "El ciclo formativo ya existe.");
            return;
        }

        // Añade ciclo en la colección y recarga la fuente de datos de la tabla de GestionCiclosFormativos
        // * Capturar OperationNotSupportedEx. de insertar()
        try {
            // 1. insertar en el sistema
            Vista.getControlador().insertar(cicloNuevo);

            // 2. insertar en la tabla
            // FORMA 1: Refresca la fuente de datos de la tabla (pasándole coleccion inicial + nuevo ciclo,
            // para que siempre figuren los datos reales sin tener que cerrar y abrir de nuevo Gestion Ciclos Formativos)
            coleccionCiclos.add(cicloNuevo);
            obsCiclos.setAll(coleccionCiclos);
            // FORMA 2: Al hacer buscar o poner orden asc/desc, desaparece. No se actualiza, debes cerrar y abrir ventana
            // obsAlumnos.add(alumnoNuevo);

        } catch (OperationNotSupportedException e) {
            Dialogos.mostrarDialogoError("Añadir ciclo formativo", "El ciclo formativo no pudo ser insertado:" + e.getMessage());
            return;
        }

        // Informar de inserción exitosa
        Dialogos.mostrarDialogoInformacion("Añadir ciclo formativo", "Ciclo formativo insertado correctamente.");
        // cierra la ventana
        ((Stage) btnAniadir.getScene().getWindow()).close();
    }

}
