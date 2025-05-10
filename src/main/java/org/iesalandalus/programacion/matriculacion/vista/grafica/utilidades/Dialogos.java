package org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public class Dialogos {

    // Evita que se puedan instanciar objetos
    private Dialogos() {}

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


    public static void mostrarDialogoError(String titulo, String contenido, Stage propietario) {
        Alert alertaErr = new Alert(Alert.AlertType.ERROR);
        alertaErr.setTitle(titulo);
        alertaErr.setHeaderText(contenido);

        // si tiene ventana propietaria (padre)...
        if (propietario != null) {
            // hacer alertaErr ventana modal
            alertaErr.initModality(Modality.APPLICATION_MODAL);
            // indicar cuál es la ventana propietaria de alertaErr, para bloquearla
            alertaErr.initOwner(propietario);
        }

        // muestra el diálogo alertaErr, bloquea y espera hasta que el usuario haga clic en algún botón
        // ** en este caso, el diálogo no tiene botones, así que no se usa Optional<ButtonType>
        //    para evitar NullPointerEx., pues solo podrá pulsar 'x'.
        alertaErr.showAndWait();

        // ** opcional: si tiene ventana propietaria, cerrarla tras mostrar error.
        //    solo tiene sentido si el error requiere que se cierre la ventana original.
        if (propietario != null){
            propietario.close();
        }
    }

    public static void mostrarDialogoError(String titulo, String contenido) {
        Dialogos.mostrarDialogoError(titulo, contenido, null);
    }



    public static void mostrarDialogoInformacion(String titulo, String contenido, Stage propietario) {
        Alert alertaInfo = new Alert(Alert.AlertType.INFORMATION);
        alertaInfo.setTitle(titulo);
        alertaInfo.setHeaderText(contenido);

        if (propietario != null) {
            alertaInfo.initModality(Modality.APPLICATION_MODAL);
            alertaInfo.initOwner(propietario);
        }

        alertaInfo.showAndWait();
        if (propietario != null) {
            propietario.close();
        }
    }

    public static void mostrarDialogoInformacion(String titulo, String contenido) {
        Dialogos.mostrarDialogoInformacion(titulo, contenido, null);
    }

}
