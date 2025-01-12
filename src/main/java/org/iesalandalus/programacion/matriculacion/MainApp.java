package org.iesalandalus.programacion.matriculacion;


import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.vista.Consola;
import org.iesalandalus.programacion.matriculacion.vista.Opcion;
import org.iesalandalus.programacion.matriculacion.vista.Vista;
import org.iesalandalus.programacion.utilidades.Entrada;

import javax.naming.OperationNotSupportedException;

import java.time.LocalDate;

import static org.iesalandalus.programacion.matriculacion.vista.Consola.elegirOpcion;
import static org.iesalandalus.programacion.matriculacion.vista.Consola.mostrarMenu;

public class MainApp {

    //CAMBIOS V.1: la implementación del main se pasa al método comenzar() de la clase Vista
    //Este método simplemente creará una vista, un modelo y un controlador, pasándoles las instancias antes
    //creadas. Luego simplemente invocará al método comenzar del controlador.
    public static void main(String[] args) {
        Modelo modelo = new Modelo();
        Vista vista = new Vista();
        Controlador controlador = new Controlador(modelo, vista);

        controlador.comenzar();

    }

    //CAMBIOS V.1: los métodos se pasan a la clase Vista

}
