package org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class Dialogos {

    public static boolean mostrarDialogoConfirmacion(String titulo, String contenido, Stage propietario) {
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        /* si quisiera aplicar estilos CSS:
        alerta.getDialogPane().getStylesheets().add(CSS);*/
        alerta.setTitle(titulo);
        alerta.setHeaderText(contenido);
        /* si quisiera poner texto inferior:
        alerta.setContentText("Los cambios no guardados se perderán"); */

        // Indicar la ventana propietaria/padre del dialog (si no lo tiene ya) y hacer el dialog modal
        if (propietario != null) {
            // Ventana modal (bloquea la ventana propietaria)
            alerta.initModality(Modality.APPLICATION_MODAL);
            // initOwner(stage) para indicar cuál es la ventana "propietaria" de esta ventana secundaria o dialog.
            // - Si usas Modality.WINDOW_MODAL, JavaFX necesita saber qué ventana debe bloquear. Para eso usas
            //   initOwner().
            // - Hace que la ventana secundaria se cierre automáticamente si se cierra la ventana principal.
            // - Hace que la ventana secundaria se mantenga al frente de su ventana padre.
            alerta.initOwner(propietario);
        }


        // showAndWait(): Muestra el diálogo y espera hasta que el usuario haga clic en algún botón (Aceptar,
        // Cancelar,...). Es bloqueante (el código se detiene hasta que el usuario responde). Devuelve un
        // Optional<ButtonType> con el botón que el usuario pulsó.
        // Optional<ButtonType>: Contenedor que puede contener o no un valor (en este caso, ButtonType).
        // Se usa para manejar situaciones donde el usuario puede cerrar el diálogo sin elegir ningún botón
        // (por ejemplo, con la X de la esquina), por eso es Optional, para evitar NullPointerException.
        Optional<ButtonType> respuesta = alerta.showAndWait();


        // isPresent(): Verifica si el usuario pulsó algún botón.
        // get(): Obtiene qué botón fue pulsado (OK, CANCEL, etc.).
        return (respuesta.isPresent() && respuesta.get() == ButtonType.OK);
        // devuelve true si pulsó un botón y ese botón fué OK
        // devuelve false si no pulsó un botón, o si lo pulsó y ese botón fue CANCEL
    }


    // Tiene sobrecarga del método para poder usarlo con o sin Stage propietario.
    public static boolean mostrarDialogoConfirmacion(String titulo, String contenido) {
        return mostrarDialogoConfirmacion(titulo, contenido, null);
    }

}
