package org.iesalandalus.programacion.matriculacion.vista;
import org.iesalandalus.programacion.matriculacion.vista.grafica.VistaGrafica;
import org.iesalandalus.programacion.matriculacion.vista.texto.VistaTexto;

public enum FactoriaVista {
    TEXTO{
        public Vista crear(){
            return new VistaTexto();
        }
    },
    GRAFICA{
        public Vista crear(){
            return new VistaGrafica();
        }
    };


    /* Método abstracto, deben implementarlo TEXTO y GRAFICA */
    public abstract Vista crear();
}
