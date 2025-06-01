package org.iesalandalus.programacion.matriculacion.vista.grafica.controladores;


import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
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
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Asignatura;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Curso;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.EspecialidadProfesorado;
import org.iesalandalus.programacion.matriculacion.vista.Vista;
import org.iesalandalus.programacion.matriculacion.vista.grafica.recursos.LocalizadorRecursos;
import org.iesalandalus.programacion.matriculacion.vista.grafica.utilidades.Dialogos;

import javax.naming.OperationNotSupportedException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorGestionAsignaturas implements Initializable {

    /* MENU BAR */
    // cada uno ejecuta su abrirVentanaGestion---()
    @FXML private MenuItem miGestionAlumnos;
    @FXML private MenuItem miGestionCiclos;
    @FXML private MenuItem miGestionAsignaturas;     // en esta ventana no hace nada
    @FXML private MenuItem miGestionMatriculas;
    @FXML private MenuItem miSalir;
    @FXML private MenuItem miAcercaDe;


    // ejecuta abrirVentanaAniadirAsig()
    @FXML private Button btnAniadirAsignatura;

    /* BUSCADOR */
    // ejecuta busquedaNombre()
    @FXML private Button btnBusquedaNombre;
    @FXML private TextField tfBusquedaNombre;


    /* CONTEXT MENU */
    // ejecuta borrarAsignatura()
    @FXML private MenuItem miBorrarAsignatura;


    /* TOGGLE BUTTON - orden por nombre */
    // ejecutan busquedaNombre()
    @FXML private ToggleButton tbtnAscendente;
    @FXML private ToggleButton tbtnDescendente;
    ToggleGroup ordenGroup = new ToggleGroup();


    /* TABLEVIEW */
    // 1º Especifica tipo valor para cada fila de la tabla
    @FXML private TableView<Asignatura> tvAsignaturas;
    // 2º Especifica tipo valor para cada columna de la tabla
    @FXML private TableColumn<Asignatura, String> tcCodigoAsig;
    @FXML private TableColumn<Asignatura, String> tcNombreAsig;
    @FXML private TableColumn<Asignatura, String> tcCicloFormAsig;
    @FXML private TableColumn<Asignatura, String> tcCursoAsig;
    @FXML private TableColumn<Asignatura, String> tcEspecialidadAsig;
    @FXML private TableColumn<Asignatura, Integer> tcHorasAnualAsig;
    // 3º Crear ObservableList, inicialmente vacío. Es la fuente de datos de la tabla
    private ObservableList<Asignatura> obsAsignaturas = FXCollections.observableArrayList();
    // 4º La colección que usa ObservableList para pasar datos a tabla, inicialmente vacía
    private List<Asignatura> coleccionAsignaturas = new ArrayList<>();

    private void cargaListadoCiclos(){

        // 5º Establecer qué atributo tendrá cada columna.
        this.tcCodigoAsig.setCellValueFactory(new PropertyValueFactory<Asignatura, String>("codigo"));
        this.tcNombreAsig.setCellValueFactory(new PropertyValueFactory<Asignatura, String>("nombre"));
        this.tcCicloFormAsig.setCellValueFactory(fila ->{
            String nombreCicloAsignatura = fila.getValue().getCicloFormativo().getNombre();
            return new ReadOnlyStringWrapper(nombreCicloAsignatura);
        });
        this.tcCursoAsig.setCellValueFactory(fila ->{
            Curso curso = fila.getValue().getCurso();
            String cursoCadena;
            if (curso == Curso.PRIMERO){
                cursoCadena="Primero";
            } else {
                cursoCadena="Segundo";
            }
            return new ReadOnlyStringWrapper(cursoCadena);
        });
        this.tcEspecialidadAsig.setCellValueFactory(fila ->{
            EspecialidadProfesorado especialidad = fila.getValue().getEspecialidadProfesorado();
            String especialidadCadena;
            if (especialidad == EspecialidadProfesorado.FOL){
                especialidadCadena="FOL";
            } else if (especialidad == EspecialidadProfesorado.INFORMATICA) {
                especialidadCadena="Informática";
            } else especialidadCadena="Sistemas";
            return new ReadOnlyStringWrapper(especialidadCadena);
        });
        this.tcHorasAnualAsig.setCellValueFactory(new PropertyValueFactory<Asignatura, Integer>("horasAnuales"));

        // 6º Asigna la fuente de datos de la tabla (un ObservableList).
        tvAsignaturas.setItems(obsAsignaturas);

        // 7º Conecta a esta colección la colección de la clase Asignaturas,
        coleccionAsignaturas = Vista.getControlador().getAsignaturas();

        // 8º Reemplaza el contenido actual del ObservableList, por los de la coleccion asignada.
        obsAsignaturas.setAll(coleccionAsignaturas);
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
        // Inicialmente con ninguno seleccionado
        ordenGroup.selectToggle(null);

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
    /* Método que muestra un diálogo descriptivo de las funciones en la ventana actual */
    @FXML
    void acercaDe(ActionEvent event) {
        String informacion = "Esta ventana se encarga de la gestion de asignaturas. Aqui podras: \n" +
                "- Añadir una nueva asignatura \n" +
                "- Borrar asignaturas \n" +
                "- Buscar asignaturas por su nombre, y ordenarlos";
        Dialogos.mostrarDialogoInformacion("Gestion de asignaturas", informacion);
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


    /* BUTTON */
    @FXML
    void abrirVentanaAniadirAsig(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LocalizadorRecursos.class.getResource
                    ("vistas/AniadirAsig.fxml"));
            Parent raiz = fxmlLoader.load();

            // 1º Obtener controlador:
            // fxmlLoader.getController() devuelve una instancia del controlador asociada
            // al archivo FXML cargado
            ControladorAniadirAsig controladorAniadirAsig = fxmlLoader.getController();
            // 2º llamar a cargaDatos() para que el ArrayList y ObservableList del
            // ControladorAniadirAsig, se inicialice con los datos que ya hay en la tabla
            controladorAniadirAsig.cargaDatos(coleccionAsignaturas, obsAsignaturas);


            Scene escena = new Scene(raiz, 600, 500);

            Stage escenario = new Stage();
            escenario.setTitle("Añadir asignatura");
            escenario.setScene(escena);
            escenario.initModality(Modality.APPLICATION_MODAL);
            escenario.setResizable(false);
            escenario.showAndWait();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }


    /* CONTEXT MENU */
    @FXML
    void borrarAsignatura(ActionEvent event) {
        // 1. Obtener Asignatura actualmente seleccionada. Si no hay, devuelve null.
        Asignatura asignaturaSeleccionada = tvAsignaturas.getSelectionModel().getSelectedItem();

        // 2. Si no hay seleccionada...
        /*if (asignaturaSeleccionada == null) {
            Dialogos.mostrarDialogoError("Borrar asignatura", "Debe seleccionar una asignatura para borrarlo.");
            return;
        }*/

        // Si sí hay seleccionada...
        // diálogo para confirmar
        boolean dialogoConfirma = Dialogos.mostrarDialogoConfirmacion("Borrar asignatura", "¿Esta seguro de que quiere borrar la asignatura?");

        // 3. Si diálogo cancela operación...
        if (!dialogoConfirma) {
            // no hace nada, sale del método
            return;
        }

        // Si diálogo acepta...
        try {
            // borrar del sistema
            Vista.getControlador().borrar(asignaturaSeleccionada);

            // borrar de la tabla
            // FORMA 1: refresca la ObservableList (pasándole coleccion inicial pero sin esa asignatura,
            // para que siempre figuren los datos reales sin tener que cerrar y abrir de nuevo Gestion Asignaturas)
            coleccionAsignaturas.remove(asignaturaSeleccionada);
            obsAsignaturas.setAll(coleccionAsignaturas);
            // FORMA 2: Al hacer buscar o poner orden asc/desc, desaparece. No se actualiza, debes cerrar y abrir ventana
            // obsAlumnos.remove(alumnoSeleccionado);

            // diálogo informativo
            Dialogos.mostrarDialogoInformacion("Borrar asignatura", "Asignatura borrada correctamente.");
        } catch (OperationNotSupportedException e) {
            Dialogos.mostrarDialogoError("Borrar asignatura", "La asignatura no pudo ser borrada:" + e.getMessage());
        }
    }


    /* BUSCADOR */
    @FXML
    void busquedaNombre(ActionEvent event) {
        // Coleccion de asignaturas a mostrar
        List<Asignatura> coleccionAsignaturasBusqueda = new ArrayList<>();


        // Si el TextField está vacío...
        if (tfBusquedaNombre.getText().isBlank() || tfBusquedaNombre.getText().isEmpty()) {
            // fuente de datos de la tabla obtiene todas las Asignatura registradas
            obsAsignaturas.setAll(coleccionAsignaturas);
        }

        // Si TextField no está vacío...
        // obtener cadena introducida en TextField (minuscula)
        String cadenaBusqueda = tfBusquedaNombre.getText().toLowerCase();

        // recorre las Asignatura registradas ya
        for(Asignatura asig : coleccionAsignaturas) {
            // si el nombre de una asignatura (minuscula) contiene la cadena introducida en TextField
            if (asig.getNombre().toLowerCase().contains(cadenaBusqueda)) {
                // añade Asignatura a la colección de asignaturas a mostrar
                coleccionAsignaturasBusqueda.add(asig);
            }
        }

        // fuente de datos de la tabla obtiene todas las Asignatura añadidas a
        // la resultante coleccionCiclosBusqueda
        obsAsignaturas.setAll(coleccionAsignaturasBusqueda);
    }


    /* TOGGLE BUTTON */
    @FXML
    void ordenNombres(ActionEvent event) {
        // Si el ToggleButton seleccionado es tbtnAscendente...
        if (tbtnAscendente.isSelected()) {
            // limpia orden anterior
            tvAsignaturas.getSortOrder().clear();

            // define tipo de orden a aplicar en la columna tcNombreAsig
            tcNombreAsig.setSortType(TableColumn.SortType.ASCENDING);

            // Añade la columna tcNombreAsig a la lista de columnas que
            // se usan para ordenar en la tabla.
            // Esto es lo que realmente vincula la columna con el orden
            // que se aplicará al mostrar la tabla.
            // (dice a la tabla qué columna, con orden establecido antes,
            // usar para ordenar las filas)
            tvAsignaturas.getSortOrder().add(tcNombreAsig);

            // Aplica el orden a la tabla. Sin esto no se actualiza la vista.
            tvAsignaturas.sort();
        }
        // Si el ToggleButton seleccionado es tbtnDescendente...
        else if (tbtnDescendente.isSelected()) {
            tvAsignaturas.getSortOrder().clear();
            tcNombreAsig.setSortType(TableColumn.SortType.DESCENDING);
            tvAsignaturas.getSortOrder().add(tcNombreAsig);
            tvAsignaturas.sort();
        }
        // Si no hay ToggleButton seleccionado...
        else {
            // orden por defecto
            tvAsignaturas.getSortOrder().clear();
        }
    }



}
