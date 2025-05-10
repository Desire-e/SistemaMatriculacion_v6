package org.iesalandalus.programacion.matriculacion;


import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.FactoriaFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.Modelo;
import org.iesalandalus.programacion.matriculacion.vista.FactoriaVista;
import org.iesalandalus.programacion.matriculacion.vista.Vista;

public class MainApp {
    public static void main(String[] args) {

        // Pasa al modelo una instancia de clase hija de FactoriaFuenteDatos,
        // en constructor de Modelo crea la fuente de datos.
        Modelo modelo = new Modelo (procesarArgumentosFuenteDatos(args));

        // La vista se crea en procesarArgumentosVista directamente, y se
        // almacena en la variable vista.
        // - Al crear VistaGrafica, su constructor ejecuta su comenzar() que
        //   llama al LanzadorVentanaPrincipal.comenzar(), encargado de ejecutar
        //   launch().
        // - Al crear VistaTexto, su constructor ejecuta su comenzar() que ejecuta
        //   el listado de opciones...
        Vista vista = procesarArgumentosVista(args);

        Controlador controlador = new Controlador(modelo, vista);

        controlador.comenzar();

    }

    /*
    Implementa el método procesarArgumentosFuenteDatos que creará un modelo cuya fuente de datos
    será la que se indique a través de los parámetros de la aplicación.
    Si el parámetro es -fdmemoria, se creará un modelo cuya fuente de datos será de tipo MEMORIA.
    En cambio, si el parámetro es -fdmysql, se creará un modelo cuya fuente de datos será de tipo MYSQL.
    */

    // String[] args es un array de String mediante el cual el Main obtiene los argumentos que el
    // usuario proporciona al ejecutar programa.

    // Si ejecutas java MainApp -fdmemoria
    // Entonces args.length == 1 y args[0].equals("-fdmemoria")
    private static FactoriaFuenteDatos procesarArgumentosFuenteDatos(String[] args){

        // El array que se pasa por parámetros al método se recorre, comprobando si contiene
        // algún objeto cadena que sea "-fdmemoria" o "-fdmysql".
        for (String arg : args) {
            // compara ignorando mayus/minus en el 1er arg pasado por el usuario
            if ("-fdmemoria".equalsIgnoreCase(arg)){
                return FactoriaFuenteDatos.MEMORIA;

            } else if ("-fdmysql".equalsIgnoreCase(arg)){
                return FactoriaFuenteDatos.MYSQL;
            }

            // Cuando haces un return, se termina la ejecución del método inmediatamente.
            // No se llega a la línea con la excepción.
        }

        // Solo si ninguno de los argumentos coincide con "fdmemoria" ni "fdmysql",
        // entonces se termina el bucle y se ejecuta la excepción:
        throw new IllegalArgumentException("ERROR: El argumento de fuente de datos " +
                                                "no es correcto, solo puede ser -fdmemoria o -fdmysql.");
    }


    private static Vista procesarArgumentosVista(String[] args){

        for (String arg : args) {
            if ("-vTexto".equalsIgnoreCase(arg)) {
                return FactoriaVista.TEXTO.crear();

            } else if ("-vGrafica".equalsIgnoreCase(arg)) {
                return FactoriaVista.GRAFICA.crear();
            }
        }

        throw new IllegalArgumentException("ERROR: El argumento de vista " +
                    "no es correcto, solo puede ser -vTexto o -vGrafica.");
    }
}
/* * * * *

2. Establecer argumentos para main() en IntelliJ
 Para agregar argumentos a la ejecución:
Ve al menú superior: Run > Edit Configurations...

En la ventana emergente:
Selecciona tu clase MainApp.
En el campo Program arguments, introduce los argumentos que deseas probar.

Para usar memoria y vista texto:
-fdmemoria -vTexto

Para usar MySQL y vista gráfica:
-fdmysql -vGrafica

* */