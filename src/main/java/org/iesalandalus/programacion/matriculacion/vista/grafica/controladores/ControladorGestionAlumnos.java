package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;
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
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.vista.Vista;
import org.iesalandalus.programacion.matriculacion.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

import javax.naming.OperationNotSupportedException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorGestionAlumnos implements Initializable {

    /* MENU BAR */
    // en esta ventana no hace nada porque ya estamos en la propia ventana de Gestión de alumnos
    @FXML private MenuItem miGestionAlumnos;
    // ejecuta abrirVentanaGestionCiclos()
    @FXML private MenuItem miGestionCiclos;
    // ejecuta abrirVentanaGestionAsignaturas()
    @FXML private MenuItem miGestionAsignaturas;
    // ejecuta abrirVentanaGestionMatriculas()
    @FXML private MenuItem miGestionMatriculas;
    // ejecuta salir()
    @FXML private MenuItem miSalir;
    // ejecuta acercaDe()
    @FXML private MenuItem miAcercaDe;

    /* BUTTON */
    // ejecuta abrirVentanaAniadirAlum()
    @FXML private Button btnAniadirAlumno;



    /* BUSCADOR */
    // ejecuta busquedaNombre()
    @FXML private Button btnBusquedaNombre;
    // obtiene valores para busquedaNombre()
    @FXML private TextField tfBusquedaNombre;



    /* TOGGLE BUTTON */
    // para dar a tableview orden ascendente / descendente
    // ambos botones  ejecutan ordenNombres()
    @FXML private ToggleButton tbtnAscendente;
    @FXML private ToggleButton tbtnDescendente;
    // declara ToggleGroup para agrupar los ToggleButton,
    // así solo uno estará activo a la vez
    // (asignados los ToggleButton al ToggleGroup en initialize())
    ToggleGroup ordenGroup = new ToggleGroup();



    /* CONTEXT MENU */
    // menú contextual al hacer click derecho sobre TABLA

    // ejecuta abrirVentanaMatriculasAlum()
    @FXML private MenuItem miMatriculasAlumno;
    // ejecuta borrarAlumno()
    @FXML private MenuItem miBorrarAlumno;



    /* TABLA - TABLEVIEW */
    // 1º Especifica que cada fila de la tabla contendrá un objeto Alumno
    @FXML private TableView<Alumno> tvAlumnos;
    // 2º Define una columna de la tabla que mostrará un valor de tipo String
    //    (en este caso, el nombre del Alumno), etc
    @FXML private TableColumn<Alumno, String> tcNombreAlum;
    @FXML private TableColumn<Alumno, String> tcDniAlum;
    @FXML private TableColumn<Alumno, String> tcCorreoAlum;
    @FXML private TableColumn<Alumno, String> tcTelefonoAlum;
    @FXML private TableColumn<Alumno, LocalDate> tcNacimientoAlum;
    // 3º Crear ObservableList (como ArrayList), inicialmente vacío.
    //    Es la fuente de datos de la tabla, contiene los Alumnos que iremos
    //    añadiendo para meterlos en la tabla
    private ObservableList<Alumno> obsAlumnos = FXCollections.observableArrayList();
    // 4º La colección que usa ObservableList para pasar datos a tabla, inicialmente vacía
    private List<Alumno> coleccionAlumnos = new ArrayList<>();


    /* TABLEVIEW */
    private void cargaListadoAlumnos(){
        // 5º Establecer qué atributo tendrá cada columna.
        //    Aplicar al nombre del tc, indicar el nombre del atributo en el
        //    constructor de la clase Alumno
        this.tcNombreAlum.setCellValueFactory(new PropertyValueFactory<Alumno, String>("nombre"));
        this.tcDniAlum.setCellValueFactory(new PropertyValueFactory<Alumno, String>("dni"));
        this.tcCorreoAlum.setCellValueFactory(new PropertyValueFactory<Alumno, String>("correo"));
        this.tcTelefonoAlum.setCellValueFactory(new PropertyValueFactory<Alumno, String>("telefono"));
        this.tcNacimientoAlum.setCellValueFactory(new PropertyValueFactory<Alumno, LocalDate>("fechaNacimiento"));

        // 6º setItems(...) método de TableView que asigna la fuente de datos de
        //    la tabla (un ObservableList).
        tvAlumnos.setItems(obsAlumnos);

        // 7º Conecta a esta colección la colección <Alumno> de la clase Alumnos,
        // llamando al controlador con getControlador() de la Vista
        coleccionAlumnos = Vista.getControlador().getAlumnos();

        // 8º setAll(...) Reemplaza el contenido actual del ObservableList, con los
        //    elementos de la coleccion asignada.
        //    Así, este ObservableList va a pasar los Alumnos registrados, al
        //    TableView (5º), para que los muestre.
        //    ** Si hay que actualizar datos en tabla por insercion/borrado, SOLO
        //    SE ACTUALIZA OBSERVABLELIST
        obsAlumnos.setAll(coleccionAlumnos);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* TABLEVIEW */
        // 9º Precargar tabla en initialize
        cargaListadoAlumnos();

        /* TOGGLE BUTTON */
        // Agrupación de ToggleButton en el ToggleGroup
        tbtnAscendente.setToggleGroup(ordenGroup);
        tbtnDescendente.setToggleGroup(ordenGroup);
        // inicialmente con ninguno seleccionado:
        ordenGroup.selectToggle(null);
    }




    /* BUTTON */
    /* Método que abre otra ventana para realizar registro de un Alumno */
    @FXML
    void abrirVentanaAniadirAlum(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource
                    ("vistas/AniadirAlum.fxml"));
            Parent raiz = fxmlLoader.load();


            /* TABLEVIEW - Obtiene datos nuevos a insertar en tabla */
            // 1º Obtener controlador.
            // fxmlLoader.getController() devuelve una instancia del controlador asociada
            // al archivo FXML cargado
            ControladorAniadirAlum controladorAniadirAlum = fxmlLoader.getController();
            // 2º llamar a cargaDatos para que el ArrayList y ObservableList del
            // ControladorAniadirAlum, se inicialice con los datos que ya hay en la tabla
            controladorAniadirAlum.cargaDatos(coleccionAlumnos, obsAlumnos);


            Scene escena = new Scene(raiz, 600, 400);

            Stage escenario = new Stage();
            escenario.setTitle("Añadir alumno");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setResizable(false);
            escenario.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }



    /* BUSCADOR */
    /* Método que busca por nombre en la tabla */
    @FXML
    void busquedaNombre(ActionEvent event) {
        // Coleccion de alumnos a mostrar
        List<Alumno> coleccionAlumnosBusqueda = new ArrayList<>();


        // Si el TextField está vacío...
        if (tfBusquedaNombre.getText().isBlank() || tfBusquedaNombre.getText().isEmpty()) {
            // obtiene todos los Alumno registrados ya en coleccionAlumnos, y los pasa a
            // la ObservableList (la fuente de datos de la TableView)
            obsAlumnos.setAll(coleccionAlumnos);
        }


        // Si TextField no está vacío...
        // obtener cadena introducida en TextField (minuscula)
        String cadenaBusqueda = tfBusquedaNombre.getText().toLowerCase();

        // recorre todos los Alumno registrados ya en coleccionAlumnos
        for(Alumno alum : coleccionAlumnos) {
            // si el nombre de un alumno (minuscula) contiene la cadena introducida en TextField
            if (alum.getNombre().toLowerCase().contains(cadenaBusqueda)) {
                // añade el alumno a la colección a mostrar
                coleccionAlumnosBusqueda.add(alum);
            }
        }

        // obtiene todos los Alumnos registrados en la resultante coleccionAlumnosBusqueda,
        // y los pasa a la ObservableList (la fuente de datos de la TableView)
        obsAlumnos.setAll(coleccionAlumnosBusqueda);
    }


    /* TOGGLE BUTTON */
    @FXML
    void ordenNombres(ActionEvent event) {

        // Si el ToggleButton seleccionado es = al tbtnAscendente...
        if (tbtnAscendente.isSelected()) {
            // limpia orden anterior
            tvAlumnos.getSortOrder().clear();

            // define tipo de orden a aplicar en la columna tcNombreAlum
            tcNombreAlum.setSortType(TableColumn.SortType.ASCENDING);

            // Añade la columna tcNombreAlum a la lista de columnas que
            // se usan para ordenar en la tabla.
            // Esto es lo que realmente vincula la columna con el orden
            // que se aplicará.
            // (dice a la tabla qué columna, con orden establecido antes,
            // usar para ordenar las filas)
            tvAlumnos.getSortOrder().add(tcNombreAlum);

            // Aplica el orden a la tabla. Sin esto no se actualiza la vista.
            tvAlumnos.sort();
        }
        // Si el ToggleButton seleccionado es = al tbtnDescendente...
        else if (tbtnDescendente.isSelected()) {
            tvAlumnos.getSortOrder().clear();
            tcNombreAlum.setSortType(TableColumn.SortType.DESCENDING);
            tvAlumnos.getSortOrder().add(tcNombreAlum);
            tvAlumnos.sort();
        }
        // Si no hay ToggleButton seleccionado...
        else {
            // orden por defecto
            tvAlumnos.getSortOrder().clear();
        }
    }
    /* TBTN --- FORMA 2 (usando getSelectedToggle())
    @FXML
    void ordenNombres(ActionEvent event) {

        // getSelectedToggle() devuelve el Toggle seleccionado
        // (castear para obtenerlo en ToggleButton)
        ToggleButton tbtnSeleccionado = (ToggleButton) ordenGroup.getSelectedToggle();

        // evitar NullPointerException si se hace doble click
        if (tbtnSeleccionado == null){
            return;
        }

        // si el ToggleButton seleccionado es = al tbtnAscendente
        if (tbtnSeleccionado.equals(tbtnAscendente)) {
            // limpia orden anterior
            tvAlumnos.getSortOrder().clear();
            // define tipo de orden a aplicar en la columna tcNombreAlum
            tcNombreAlum.setSortType(TableColumn.SortType.ASCENDING);
            // Añade la columna tcNombreAlum a la lista de columnas que
            // se usan para ordenar en la tabla.
            // Esto es lo que realmente vincula la columna con el orden
            // que se aplicará.
            tvAlumnos.getSortOrder().add(tcNombreAlum);
            // Aplica el orden a la tabla. Sin esto no se actualiza la vista.
            tvAlumnos.sort();
        }
        // si el ToggleButton seleccionado es = al tbtnDescendente
        else if (tbtnSeleccionado.equals(tbtnDescendente)) {
            tvAlumnos.getSortOrder().clear();
            tcNombreAlum.setSortType(TableColumn.SortType.DESCENDING);
            tvAlumnos.getSortOrder().add(tcNombreAlum);
            tvAlumnos.sort();
        }
        // si no hay ToggleButton seleccionado
        else {
            // orden por defecto
            tvAlumnos.getSortOrder().clear();
        }
    }*/



    /* CONTEXT MENU */
    @FXML
    void borrarAlumno(ActionEvent event) {

        // Obtener alumno seleccionado en tabla
        // getSelectionModel(): Devuelve el modelo de selección asociado a la tabla.
        // El modelo de selección gestiona qué filas están seleccionadas, si se
        // permite selección múltiple, etc.
        // getSelectedItem(): Devuelve el objeto actualmente seleccionado (es decir,
        // la fila seleccionada en la TableView). Si no hay seleccionado, devuelve null.
        Alumno alumnoSeleccionado = tvAlumnos.getSelectionModel().getSelectedItem();


        // Si no hay alumno seleccionado...
        /* if (alumnoSeleccionado == null) {
              Dialogos.mostrarDialogoError("Borrar alumno", "Debe seleccionar un alumno para borrarlo.");
              return;
          }
        */

        // Si sí hay alumno seleccionado...
        // diálogo para confirmar
        boolean dialogoConfirma = Dialogos.mostrarDialogoConfirmacion("Borrar alumno", "¿Esta seguro de que quiere borrar al alumno?");

        // Si diálogo cancela operación...
        if (!dialogoConfirma) {
            // no hace nada
            return;
        }

        // Si diálogo acepta...
        try {
            // borrar del sistema
            Vista.getControlador().borrar(alumnoSeleccionado);

            // borrar de la tabla
            // FORMA 1: refresca la fuente de datos de la tabla (pasándole coleccion inicial sin alumno,
            // para que siempre figuren los datos reales sin tener que cerrar y abrir de nuevo Gestion Alumnos)
            coleccionAlumnos.remove(alumnoSeleccionado);
            obsAlumnos.setAll(coleccionAlumnos);
            // FORMA 2: Al hacer buscar o poner orden asc/desc, desaparece. No se actualiza, debes cerrar y abrir ventana
            // obsAlumnos.remove(alumnoSeleccionado);

            // diálogo informativo
            Dialogos.mostrarDialogoInformacion("Borrar alumno", "Alumno borrado correctamente.");
        } catch (OperationNotSupportedException e) {
            Dialogos.mostrarDialogoError("Borrar alumno", "El alumno no pudo ser borrado:" + e.getMessage());
        }

    }

    @FXML
    void abrirVentanaMatriculasAlum(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource
                    ("vistas/MatriculasAlum.fxml"));

            Parent raiz = fxmlLoader.load();


            // Obtener matriculas del alumno seleccionado en tabla
            Alumno alumnoSeleccionado = tvAlumnos.getSelectionModel().getSelectedItem();
            List<Matricula> matriculasAlumno = Vista.getControlador().getMatriculas(alumnoSeleccionado);
            // Obtener controlador
            ControladorMatriculasAlum controladorMatriculasAlum = fxmlLoader.getController();
            // Pasarle el alumno del que se quiere ver las matrículas
            controladorMatriculasAlum.cargaMatriculasAlumno(matriculasAlumno);


            Scene escena = new Scene(raiz, 800, 400);

            Stage escenario = new Stage();
            escenario.setTitle("Matriculas del alumno");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setResizable(false);
            escenario.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }



    /* MENU BAR */
    /* Método que sale del programa completo (menubar) */
    @FXML
    void salir(ActionEvent event) {
        if (Dialogos.mostrarDialogoConfirmacion("Sistema de matriculacion",
                "¿Realmente quieres salir de la aplicacion?")) {
            System.exit(0);
        }
        else event.consume();
    }

    /* Método que muestra un diálogo descriptivo de las funciones en la ventana actual */
    @FXML
    void acercaDe(ActionEvent event) {
        String informacion = "Esta ventana se encarga de la gestion de alumnos. Aqui podras: \n" +
                                "- Añadir un nuevo alumno \n" +
                                "- Borrar alumnos \n" +
                                "- Acceder a las matriculas de cada alumno \n" +
                                "- Buscar alumnos por su nombre, y ordenarlos";
        Dialogos.mostrarDialogoInformacion("Gestion de alumnos", informacion);
    }

    /* Método que abre la ventana de gestión de ciclos formativos (menubar) */
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

    /* Método que abre la ventana de gestión de asignaturas (menubar) */
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

    /* Método que abre la ventana de gestión de matriculas (menubar) */
    @FXML
    void abrirVentanaGestionMatriculas(ActionEvent event) {
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

}
