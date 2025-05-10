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

public class ControladorGestionCiclosFormativos implements Initializable {

    /* MENU BAR */
    // ejecuta abrirVentanaGestionAlumnos()
    @FXML private MenuItem miGestionAlumnos;
    // en esta ventana no hace nada porque ya estamos en la propia ventana de Gestión de alumnos
    @FXML private MenuItem miGestionCiclos;
    // ejecuta abrirVentanaGestionAsignaturas()
    @FXML private MenuItem miGestionAsignaturas;
    // ejecuta abrirVentanaGestionMatriculas()
    @FXML private MenuItem miGestionMatriculas;
    // ejecuta salir()
    @FXML private MenuItem miSalir;


    /* BUTTON */
    // ejecuta abrirVentanaAniadirCiclo()
    @FXML private Button btnAniadirCiclo;


    /* BUSCADOR */
    // obtiene valores para busquedaNombre()
    @FXML private TextField tfBusquedaNombre;
    // ejecuta busquedaNombre()
    @FXML private Button btnBusquedaNombre;


    /* TOGGLE BUTTON */
    // ambos botones  ejecutan ordenNombres() para el tvCiclosFormativos
    @FXML private ToggleButton tbtnAscendente;
    @FXML private ToggleButton tbtnDescendente;
    // agrupar ToggleButton en un ToggleGroup (en initialize()), así solo uno estará activo a la vez
    ToggleGroup ordenGroup = new ToggleGroup();


    /* CONTEXT MENU */
    // ejecuta abrirVentanaMatriculasCiclo()
    @FXML private MenuItem miMatriculasCiclo;
    // ejecuta borrarCiclo()
    @FXML private MenuItem miBorrarCiclo;


    /* TABLEVIEW */
    // 1º Especifica tipo valor para cada fila de la tabla
    @FXML private TableView<CicloFormativo> tvCiclosFormativos;
    // 2º Especifica tipo valor para cada columna de la tabla
    @FXML private TableColumn<CicloFormativo, Integer> tcCodigoCiclo;
    @FXML private TableColumn<CicloFormativo, String> tcFamiliaCiclo;
    @FXML private TableColumn<CicloFormativo, String> tcGradoCiclo;
    @FXML private TableColumn<CicloFormativo, String> tcNombreCiclo;
    @FXML private TableColumn<CicloFormativo, Integer> tcHorasCiclo;
    // 3º Crear ObservableList, inicialmente vacío. Es la fuente de datos de la tabla
    private ObservableList<CicloFormativo> obsCiclos = FXCollections.observableArrayList();
    // 4º La colección que usa ObservableList para pasar datos a tabla, inicialmente vacía
    private List<CicloFormativo> coleccionCiclos = new ArrayList<>();

    private void cargaListadoCiclos(){
        // 5º Establecer qué atributo tendrá cada columna.
        this.tcCodigoCiclo.setCellValueFactory(new PropertyValueFactory<CicloFormativo, Integer>("codigo"));
        this.tcFamiliaCiclo.setCellValueFactory(new PropertyValueFactory<CicloFormativo, String>("familiaProfesional"));
            // fila: objeto que representa una fila de la tabla.
            // Es objeto tipo CellDataFeatures<S,T> (<CicloFormativo, String>). Es decir,
            // es un envoltorio que contiene info sobre una fila específica de la tabla.
            // Al objeto completo de la fila (CicloFormativo), se accede con getValue().
        this.tcGradoCiclo.setCellValueFactory(fila -> {
            // getValue(): devuelve el objeto CicloFormativo de la fila.
            // Obtiene el Grado del CicloFormativo.
            Grado gradoCiclo = fila.getValue().getGrado();

            // Si el grado no es null, pone concatenación de "Grado D / Grado E - nombre"
            // Si sí es null pone cadena vacía
            String gradoCadena = "";
            if (gradoCiclo != null){
                String gradoTipo;
                if (gradoCiclo instanceof GradoD){
                    gradoTipo = "Grado D";
                }
                else {
                    gradoTipo = "Grado E";
                }
                gradoCadena = gradoTipo + " - " + gradoCiclo.getNombre();
            }

            // La TableView espera un ObservableValue<T> (<String>) del setCellValueFactory.
            // Como estamos devolviendo un String, lo envolvemos en un ReadOnlyStringWrapper
            // (implementación de ObservableValue<String>).
            // Esto permite que el contenido sea observable (reacción a cambios).
            return new ReadOnlyStringWrapper(gradoCadena);
        });
        this.tcNombreCiclo.setCellValueFactory(new PropertyValueFactory<CicloFormativo, String>("nombre"));
        this.tcHorasCiclo.setCellValueFactory(new PropertyValueFactory<CicloFormativo, Integer>("horas"));


        // 6º Asigna la fuente de datos de la tabla (un ObservableList).
        tvCiclosFormativos.setItems(obsCiclos);

        // 7º Conecta a esta colección la colección de la clase CiclosFormativos,
        coleccionCiclos = Vista.getControlador().getCiclosFormativos();

        // 8º Reemplaza el contenido actual del ObservableList, por los de la coleccion asignada.
        obsCiclos.setAll(coleccionCiclos);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        /* TABLEVIEW */
        // 9º Precargar tabla en initialize
        cargaListadoCiclos();

        /* TOGGLE BUTTON */
        // Agrupación de los ToggleButton en el ToggleGroup
        tbtnAscendente.setToggleGroup(ordenGroup);
        tbtnDescendente.setToggleGroup(ordenGroup);
        // inicialmente con ninguno seleccionado
        ordenGroup.selectToggle(null);
    }


    /* BUTTON */
    @FXML
    void abrirVentanaAniadirCiclo(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource
                    ("vistas/AniadirCiclo.fxml"));
            Parent raiz = fxmlLoader.load();


            /* TABLEVIEW - Obtiene datos nuevos a insertar en tabla */
            // 1º Obtener controlador.
            // fxmlLoader.getController() devuelve una instancia del controlador asociada
            // al archivo FXML cargado
            ControladorAniadirCiclo controladorAniadirCiclo = fxmlLoader.getController();
            // 2º llamar a cargaDatos para que el ArrayList y ObservableList del
            // ControladorAniadirAlum, se inicialice con los datos que ya hay en la tabla
            controladorAniadirCiclo.cargaDatos(coleccionCiclos, obsCiclos);


            Scene escena = new Scene(raiz, 600, 700);

            Stage escenario = new Stage();
            escenario.setTitle("Añadir ciclo formativo");
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
    @FXML
    void busquedaNombre(ActionEvent event) {
        // Coleccion de ciclos a mostrar
        List<CicloFormativo> coleccionCiclosBusqueda = new ArrayList<>();


        // Si el TextField está vacío...
        if (tfBusquedaNombre.getText().isBlank() || tfBusquedaNombre.getText().isEmpty()) {
            // obtiene todos los CicloFormativo registrados ya en coleccionCiclos
            obsCiclos.setAll(coleccionCiclos);
        }


        // Si TextField no está vacío...
        // obtener cadena introducida en TextField (minuscula)
        String cadenaBusqueda = tfBusquedaNombre.getText().toLowerCase();

        // recorre todos los CicloFormativo registrados ya en coleccionCiclos
        for(CicloFormativo ciclo : coleccionCiclos) {
            // si el nombre de un ciclo (minuscula) contiene la cadena introducida en TextField
            if (ciclo.getNombre().toLowerCase().contains(cadenaBusqueda)) {
                // añade el ciclo a la colección a mostrar
                coleccionCiclosBusqueda.add(ciclo);
            }
        }

        // obtiene todos los CicloFormativo registrados en la resultante
        // coleccionCiclosBusqueda, y los pasa a la ObservableList
        obsCiclos.setAll(coleccionCiclosBusqueda);
    }

    /* TOGGLE BUTTON */
    @FXML
    void ordenNombres(ActionEvent event) {
        // Si el ToggleButton seleccionado es tbtnAscendente...
        if (tbtnAscendente.isSelected()) {
            // limpia orden anterior
            tvCiclosFormativos.getSortOrder().clear();

            // define tipo de orden a aplicar en la columna tcNombreCiclo
            tcNombreCiclo.setSortType(TableColumn.SortType.ASCENDING);

            // Añade la columna tcNombreCiclo a la lista de columnas que
            // se usan para ordenar en la tabla.
            // Esto es lo que realmente vincula la columna con el orden
            // que se aplicará al mostrar la tabla.
            // (dice a la tabla qué columna, con orden establecido antes,
            // usar para ordenar las filas)
            tvCiclosFormativos.getSortOrder().add(tcNombreCiclo);

            // Aplica el orden a la tabla. Sin esto no se actualiza la vista.
            tvCiclosFormativos.sort();
        }
        // Si el ToggleButton seleccionado es tbtnDescendente...
        else if (tbtnDescendente.isSelected()) {
            tvCiclosFormativos.getSortOrder().clear();
            tcNombreCiclo.setSortType(TableColumn.SortType.DESCENDING);
            tvCiclosFormativos.getSortOrder().add(tcNombreCiclo);
            tvCiclosFormativos.sort();
        }
        // Si no hay ToggleButton seleccionado...
        else {
            // orden por defecto
            tvCiclosFormativos.getSortOrder().clear();
        }
    }



    /* CONTEXT MENU */
    @FXML
    void abrirVentanaMatriculasCiclo(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource
                    ("vistas/MatriculasCiclo.fxml"));

            Parent raiz = fxmlLoader.load();


            // Obtener matriculas del ciclo seleccionado en tabla
            CicloFormativo cicloSeleccionado = tvCiclosFormativos.getSelectionModel().getSelectedItem();
            List<Matricula> matriculasCiclo = Vista.getControlador().getMatriculas(cicloSeleccionado);
            // Obtener controlador
            ControladorMatriculasCiclo controladorMatriculasCiclo = fxmlLoader.getController();
            // Pasarle el ciclo del que se quiere ver las matrículas
            controladorMatriculasCiclo.cargaMatriculasCiclo(matriculasCiclo, cicloSeleccionado);


            Scene escena = new Scene(raiz, 800, 400);

            Stage escenario = new Stage();
            escenario.setTitle("Matriculas del ciclo formativo");
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
    void borrarCiclo(ActionEvent event) {
        // Obtener ciclo actualmente seleccionado. Si no hay, devuelve null.
        CicloFormativo cicloSeleccionado = tvCiclosFormativos.getSelectionModel().getSelectedItem();

        // Si no hay ciclo seleccionado...
        /*if (cicloSeleccionado == null) {
            Dialogos.mostrarDialogoError("Borrar ciclo formativo", "Debe seleccionar un ciclo formativo para borrarlo.");
            return;
        }*/

        // Si sí hay ciclo seleccionado...
        // diálogo para confirmar
        boolean dialogoConfirma = Dialogos.mostrarDialogoConfirmacion("Borrar ciclo formativo", "¿Esta seguro de que quiere borrar el ciclo formativo?");

        // Si diálogo cancela operación...
        if (!dialogoConfirma) {
            // no hace nada, sale del método
            return;
        }

        // Si diálogo acepta...
        try {
            // borrar del sistema
            Vista.getControlador().borrar(cicloSeleccionado);

            // borrar de la tabla
            // FORMA 1: refresca la ObservableList (pasándole coleccion inicial pero sin ese ciclo,
            // para que siempre figuren los datos reales sin tener que cerrar y abrir de nuevo Gestion Ciclos Formativos)
            coleccionCiclos.remove(cicloSeleccionado);
            obsCiclos.setAll(coleccionCiclos);
            // FORMA 2: Al hacer buscar o poner orden asc/desc, desaparece. No se actualiza, debes cerrar y abrir ventana
            // obsAlumnos.remove(alumnoSeleccionado);

            // diálogo informativo
            Dialogos.mostrarDialogoInformacion("Borrar ciclo formativo", "Ciclo formativo borrado correctamente.");
        } catch (OperationNotSupportedException e) {
            Dialogos.mostrarDialogoError("Borrar ciclo formativo", "El ciclo formativo no pudo ser borrado:" + e.getMessage());
        }
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
