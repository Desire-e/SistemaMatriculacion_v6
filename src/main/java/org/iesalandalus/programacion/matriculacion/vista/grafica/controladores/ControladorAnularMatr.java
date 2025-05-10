package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.vista.Vista;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;
import org.iesalandalus.programacion.matriculacion.vista.texto.Consola;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControladorAnularMatr {

    @FXML private Button btnAnular;
    @FXML private DatePicker dpFechaAnula;

    Matricula matriculaAnular;
    List<Matricula> coleccionMatriculas = new ArrayList<>();
    ObservableList<Matricula> obsMatriculas = FXCollections.observableArrayList();

    public void cargaMatricula(Matricula matriculaAnular, ObservableList<Matricula> obsMatriculas, List<Matricula> coleccionMatriculas){
        this.matriculaAnular = matriculaAnular;
        this.coleccionMatriculas = coleccionMatriculas;
        this.obsMatriculas = obsMatriculas;

    }

    @FXML
    void anular(ActionEvent event) {

        if (dpFechaAnula.getValue()==null){
            Dialogos.mostrarDialogoError("Anular matricula", "La fecha no puede ser nula.");
            return;
        }

        try{

            // 1º Crear copia de Matricula original
            Matricula matriculaAnulada = new Matricula(matriculaAnular);
            // 2º Eliminar Matricula original
            Vista.getControlador().borrar(matriculaAnular);
            // 3º Dar fecha de anulación a la copia
            matriculaAnulada.setFechaAnulacion(dpFechaAnula.getValue());
            // 4º Insertar la copia ya anulada en coleccion/BD
            Vista.getControlador().insertar(matriculaAnulada);

            // tras actualizar sistema con matricula anulada, actualizo obs de la tabla en GestionMatriculas
            coleccionMatriculas.add(matriculaAnulada);
            coleccionMatriculas.remove(matriculaAnular);
            obsMatriculas.setAll(coleccionMatriculas);

        } catch (Exception e) {
            // tratar excepciones por fecha inválida:
            Dialogos.mostrarDialogoError("Anular matricula", "No se pudo anular la matricula: " + e.getMessage());
        }

        // Informar de anulación exitosa
        Dialogos.mostrarDialogoInformacion("Anular matricula", "Matricula anulada correctamente.");
        ((Stage)btnAnular.getScene().getWindow()).close();

    }

}
