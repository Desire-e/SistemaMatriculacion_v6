package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.vista.Vista;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;

public class ControladorAniadirAlum {
    @FXML private DatePicker dpNacimientoAlum;
    @FXML private TextField tfCorreoAlum;
    @FXML private TextField tfDniAlum;
    @FXML private TextField tfNombreAlum;
    @FXML private TextField tfTelefonoAlum;
    @FXML private Button btnAniadir;


    /* 1º Declara fuente de datos de la tabla */
    private List<Alumno> coleccionAlumnos = new ArrayList<>();
    private ObservableList<Alumno> obsAlumnos = FXCollections.observableArrayList();


    /* 2º Inicializa fuente de datos de la tabla (nos pasa los valores iniciales
          desde ControladorGestionAlumnos.abrirVentanaAniadirAlum()) */
    public void cargaDatos(List<Alumno> coleccionAlumnos, ObservableList<Alumno> obsAlumnos) {
        this.coleccionAlumnos = coleccionAlumnos;
        this.obsAlumnos = obsAlumnos;
    }


    /* 3º Añade nuevo Alumno y refresca fuente de datos (y por tanto la tabla en ControladorGestionAlumnos) */
    @FXML
    void aniadir(ActionEvent event) {

        Alumno alumnoNuevo;

        // Crear alumno
        // Capturar excepciones por datos introducidos inválidos (fecha, teléfono, ...)
        try {
            alumnoNuevo = new Alumno(tfNombreAlum.getText(),tfDniAlum.getText(),tfCorreoAlum.getText(), tfTelefonoAlum.getText(), dpNacimientoAlum.getValue());
        } catch(Exception e){
            Dialogos.mostrarDialogoError("Añadir alumno", "Datos introducidos erroneos: " + e.getMessage());
            return;
        }


        // Comprobar si alumnoNuevo creado existe
        if (obsAlumnos.contains(alumnoNuevo)) {
            Dialogos.mostrarDialogoError("Añadir alumno", "El alumno ya existe.");
            return;
        }

        // Añade alumno en la colección y recarga la fuente de datos de la tabla de GestionAlumnos
        // Capturar OperationNotSupportedEx. de insertar()
        try {
            // inserta en el sistema
            Vista.getControlador().insertar(alumnoNuevo);

            // inserta en la tabla
            // FORMA 1: Refresca la fuente de datos de la tabla (pasándole coleccion inicial + nuevo alumno,
            // para que siempre figuren los datos reales sin tener que cerrar y abrir de nuevo Gestion Alumnos)
            coleccionAlumnos.add(alumnoNuevo);
            obsAlumnos.setAll(coleccionAlumnos);
            // FORMA 2: Al hacer buscar o poner orden asc/desc, desaparece. No se actualiza, debes cerrar y abrir ventana
            // obsAlumnos.add(alumnoNuevo);

        } catch (OperationNotSupportedException e) {
            Dialogos.mostrarDialogoError("Añadir alumno", "El alumno no pudo ser insertado:" + e.getMessage());
            return;
        }


        // Informar de inserción exitosa
        Dialogos.mostrarDialogoInformacion("Añadir alumno", "Alumno insertado correctamente.");
        // cierra la ventana
        ((Stage) btnAniadir.getScene().getWindow()).close();
    }

}
