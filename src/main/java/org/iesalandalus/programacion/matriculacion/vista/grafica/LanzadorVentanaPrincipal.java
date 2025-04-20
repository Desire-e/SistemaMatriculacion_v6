package org.iesalandalus.programacion.matriculacion.vista.grafica;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.iesalandalus.programacion.matriculacion.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;

public class LanzadorVentanaPrincipal extends Application {

    /* El método comenzar deberá lanzar la aplicación para que se muestre la ventana principal de la misma. */
    public static void comenzar(){
        // Método que lanza la app y llama a start()
        // Tener en cuenta: launch() solo puede llamarse una vez en toda la vida de la JVM. Si lo llamas
        // varias veces (p.ej., en tests o relanzamientos), lanzará error.
        launch();
    }



    /* El método confirmarSalida deberá mostrar un diálogo de confirmación en el que se pregunte al
    usuario si realmente desea salir de la aplicación. En caso afirmativo, la aplicación deberá terminar
    correctamente, cerrando la escena e indicando al controlador de la aplicación que finalice.
    En caso negativo, la aplicación deberá seguir mostrando la ventana principal. */
    private void confirmarSalida(Stage escenarioPrincipal, WindowEvent e){

        // si mostrarDialogoConfirmacion() es true...
        if (Dialogos.mostrarDialogoConfirmacion("Sistema de matriculación",
                "¿Realmente quieres salir de la aplicación?", escenarioPrincipal)) {
            // cierra escenario (ventana)
            escenarioPrincipal.close();
        }
        // si es false...
        // consume() se usa en eventos para cancelar o bloquear la propagación del evento a otros manejadores
        else e.consume();

    }



    // método heredado de Application, será lo que aparece en la ventana principal.
    /* El método start deberá mostrar una escena que contenga a la ventana principal.
    Dicha escena tendrá un tamaño fijo de ventana y además, cuando se cierre, deberá
    llamar al método confirmarSalida. */
    @Override
    public void start(Stage stage) throws Exception {
        try {
            // COPYPASTE DE PROYECTO: HolaDAW


            // Cargar fichero fxml del directorio vistas/VentanaX.fxml
            // LocalizadorRecursos es una interfaz sin implementación, solo sirve para dar el path absoluto,
            // sin tener que escribirlo explícitamente (por eso directorio vistas está dentro de carpeta con
            // nombre = al nombre de la estructura de paquetes)
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class
                    .getResource("vistas/VentanaPrincipal.fxml"));
                                            // CREAR FXML CON ESE NOMBRE

            // Cargar jerarquia de árbol:
            //  - 'load()' carga un archivo FXML, parsea el archivo FXML y crea la jerarquía de árbol / nodos
            //    (elementos visuales) definidos en él (como botones, paneles, etiquetas, etc.).
            //  - 'Parent' es la clase base para todos los nodos que pueden tener hijos en JavaFX (como VBox,
            //     AnchorPane, BorderPane, etc.).
            Parent raiz = fxmlLoader.load();

            /*
            1. creamos los controles, botones, hbox,... (en ControladorX)
            2. crear escena y decirle cual es nodo raíz
                2.1. Scene escena = new Scene(raíz, v, v1)
            3. decirle a escenario cual es la escena:
                3.1. decir a escenario el titulo del escenario: stage.setTitle("");
                3.2. decir a escenario cual es la escena: stage.setScene(escena);
                3.3. decir a escenario que ya se puede mostrar: stage.show();
            */

            // new Scene(): Crear una escena, que es el contenedor principal de lo que se muestra en una ventana.
            // La raíz es el nodo raíz de la interfaz, con archivo FXML cargado con FXMLLoader.
            // Puedes especificar ancho (v) y alto (v1)
            Scene escena = new Scene(raiz, 900, 700);

            // Lo que pone en la barra de título de la ventana
            stage.setTitle("Sistema de matriculación");

            // Al escenario asigna escena
            stage.setScene(escena);


            // Enganchar el cierre de ventana con confirmarSalida()
            // setOnCloseRequest(): define qué hacer cuando intenta cerrar ventana. Se le pasa un event handler(e),
            // que recibe un WindowEvent (recibido de confirmarSalida), y tú decides qué hacer con ese evento.

            // * ¿Por qué se llama antes de stage.show()?
            //    setOnCloseRequest(...) define comportamiento, stage.show() muestra la ventana. Si no defines el
            //    comportamiento antes de mostrarla, se podría cerrar y no habría ningún manejador que lo evite.
            stage.setOnCloseRequest( e -> confirmarSalida(stage, e));

            // Muestra el esecenario, la ventana
            stage.show();



        } catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
