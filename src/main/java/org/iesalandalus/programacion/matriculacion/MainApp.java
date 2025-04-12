package org.iesalandalus.programacion.matriculacion;


import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.vista.texto.VistaTexto;

public class MainApp {
    public static void main(String[] args) {

        Modelo modelo = procesarArgumentosFuenteDatos(args);
        // MODIFICAR LUEGO!!!!
        VistaTexto vista = new VistaTexto();
        Controlador controlador = new Controlador(modelo, vista);

        controlador.comenzar();

    }

    /*
    Implementa el método procesarArgumentosFuenteDatos que creará un modelo cuya fuente de datos
    será la que se indique a través de los parámetros de la aplicación.
    Si el parámetro es -fdmemoria, se creará un modelo cuya fuente de datos será de tipo MEMORIA.
    En cambio, si el parámetro es -fdmysql, se creará un modelo cuya fuente de datos será de tipo MYSQL.
    */

    // String[] args es un array de String mediante el cual el Main obtiene los argumentos que el usuario proporciona
    // al ejecutar programa.

    // Si ejecutas java MainApp -fdmemoria
    // Entonces args.length == 1 y args[0].equals("-fdmemoria")
    private static Modelo procesarArgumentosFuenteDatos(String[] args){

        // compara ignorando mayus/minus en el 1er arg pasado por el usuario
        if ("-fdmemoria".equalsIgnoreCase(args[0])){
            return new Modelo(FactoriaFuenteDatos.MEMORIA);

        } else if ("-fdmysql".equalsIgnoreCase(args[0])){
            return new Modelo(FactoriaFuenteDatos.MYSQL);

        } else
            throw new IllegalArgumentException("ERROR: Modelo no pudo ser creado. El argumento de fuente de datos" +
                                                "no es correcto, solo puede ser -fdmemoria o -fdmysql.");
    }
}
