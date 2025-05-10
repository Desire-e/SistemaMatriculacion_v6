package org.iesalandalus.programacion.matriculacion.vista;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;

public abstract class Vista {
    // atributo que NO heredan las hijas, ni acceder a él directamente (pero sí con getControlador)
    private static Controlador controlador;


    // método que heredan las hijas, y podrían sobreescribir
    // el controlador en su constructor obtiene la vista y modelo (desde MainApp), y se pasa a si mismo
    // a la Vista mediante vista.setSontrolador(this)
    public void setControlador(Controlador controlador){
        if (controlador == null){
            throw new NullPointerException("El controlador es nulo.");
        }
        this.controlador=controlador;
    }


    // método que NO heredan las hijas, y no podrían sobreescribir, pero sí pueden llamarlo
    public static Controlador getControlador(){
        return controlador;
    }


    // método que heredan las hijas, y deben sobreescribir
    public abstract void comenzar();

    public abstract void terminar();

}
