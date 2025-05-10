package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.iesalandalus.programacion.matriculacion.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;

import java.net.URL;
import java.util.ResourceBundle;

public class ControladorVentanaPrincipal implements Initializable {

    // Método de interfaz Initializable
    // Inicializa la clase controladora
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // 1º Importar elemento de SB (view > skeleton)
    // * no necesita importar los H/VBox que hacen de contenedor
    // * no necesita importar los Button si no los necesitas para métodos concretos (mostrar valor según
    //   botón seleccionado, p.ej), solo importar sus métodos asociaods en onAction (SB)
    // @FXML private Button btnGestionAlumnos;
    @FXML private Button btnGestionAsignaturas;
    @FXML private Button btnGestionCiclos;
    @FXML private Button btnGestionMatriculas;
    @FXML private Button btnSalirVentanaPrincipal;


    // Desde una ventana, cargar/abrir otra ventana (la de gestión de Alumnos):
    @FXML void abrirVentanaGestionAlumnos(ActionEvent event) {
        try {
            // Carga ventana Alumnos
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource
                                    ("vistas/GestionAlumnos.fxml"));

            // Cargar jerarquia de árbol de sus componentes
            Parent raiz = fxmlLoader.load();

            // Crea escena (contenedor de la ventana)
            Scene escena = new Scene(raiz, 800, 500);

            // Crea escenario (ventana)
            Stage escenario = new Stage();
            // título de ventana
            escenario.setTitle("Gestion de alumnos");
            // ventana asociada a la escena
            escenario.setScene(escena);
            // ventana modal
            escenario.initModality(Modality.APPLICATION_MODAL);
            // ventana que no puede cambiar de tamaño
            escenario.setResizable(false);
            // muestra el diálogo y espera hasta que el usuario haga algo
            escenario.showAndWait();
            /*
            DIFERENCIA:
            escenario.initModality(Modality.APPLICATION_MODAL);
                Define el tipo de modalidad de la ventana antes de mostrarla.
                - APPLICATION_MODAL: La nueva ventana bloquea las demás ventanas hasta
                  que se cierre, y debe llamarse antes de show() o showAndWait().
                Hay otros tipos de Modality:
                - NONE: sin bloqueo.
                - WINDOW_MODAL: solo bloquea la ventana padre específica.

            escenario.showAndWait();
                Muestra la ventana y bloquea el hilo actual (normalmente el hilo principal de
                JavaFX) hasta que se cierre la ventana. Tras cerrarse la ventana, el código
                siguiente a showAndWait() se ejecuta.
                Requiere que ya se haya definido la modalidad (como APPLICATION_MODAL), o si no,
                actúa como una ventana normal.
            */

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML void abrirVentanaGestionAsignaturas(ActionEvent event) {
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

    @FXML void abrirVentanaGestionCiclos(ActionEvent event) {
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

    @FXML void abrirVentanaGestionMatriculas(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource
                    ("vistas/GestionMatriculas.fxml"));

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
    void salirVentanaPrincipal(ActionEvent event) {

        // si es true...
        if (Dialogos.mostrarDialogoConfirmacion("Sistema de matriculacion",
                "¿Realmente quieres salir de la aplicacion?")) {

            // Finaliza toda la aplicación Java inmediatamente.
            // El 0 significa "salida exitosa" (sin errores).
            // Se suele usar cuando quieres cerrar la app completamente, no solo una ventana.
            System.exit(0);
        }

        // si es false...
        // Indica que este evento (de tipo ActionEvent) ya fue procesado.
        // Previene que el evento siga propagándose a otros manejadores o comportamientos por defecto.
        else event.consume();
    }



}
