package org.iesalandalus.programacion.matriculacion.vista.grafica;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.Alumnos;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

public class VistaGrafica extends Vista {
    // atributo llamado mediante el cual implementaremos el patrón Singleton.
    private VistaGrafica instancia;

    public VistaGrafica(){
        comenzar();
    }

    // Patrón Singleton: getter del atributo instancia, que asignará al atributo instancia una nueva
    // VistaGrafica si su valor es null.
    public VistaGrafica getInstancia(){
        if (instancia == null){
            instancia = new VistaGrafica();
        }
        return instancia;
    }

    // Llamará al LanzadorVentanaPrincipal para que se muestre la ventana principal de la aplicación.
    @Override
    public void comenzar(){
        // Método que lanza la app y llama a start()
        LanzadorVentanaPrincipal.comenzar();
    }

    // Llamará al controlador para que termine la aplicación.
    @Override
    public void terminar(){
        Controlador.terminar();
    }


















}
