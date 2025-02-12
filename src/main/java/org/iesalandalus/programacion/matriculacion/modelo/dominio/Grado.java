package org.iesalandalus.programacion.matriculacion.modelo.dominio;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Grado {
/*
    GDCFGB("Grado D Ciclo Formativo de Grado Básico"),
    GDCFGM("Grado D Ciclo formativo de Grado Medio"),
    GDCFGS("Grado D Ciclo Formativo de Grado Superior");

    private String cadenaAMostrar;

    private Grado (String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir (){
        //  Método ordinal() da la posición del valor enumerado (empezando desde 0).
        return ordinal() + ".-" + cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "Grado{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }
*/

    //CAMBIOS V.3:
    protected String nombre;
    protected String iniciales;
    protected int numAnios;

    public Grado(String nombre){
        setNombre(nombre);
    }

    public String getNombre(){
        return nombre;
    }

    protected void setNombre(String nombre){
        if (nombre == null || nombre.isBlank() || nombre.isEmpty()){
            throw new IllegalArgumentException ("El nombre del grado no puede estar vacío.");
        }
        this.nombre=nombre;
        setIniciales(); // Actualiza las iniciales al modificar el nombre
    }

    // Implementa el método setIniciales que establecerá el atributo iniciales el cual se genera cogiendo el
    // primer carácter de cada palabra del nombre del Grado. Además, deberá estar todo en mayúsculas.
    // Ten en cuenta que cada vez que se modifique el nombre del grado, deberá actualizarse el atributo
    // iniciales al valor que le corresponda.
    private void setIniciales(){
        // Divide palabras separadas por espacio y crea una lista con cada una de ellas
        String [] palabras = nombre.split("\\s+");
        // Convierte en ArrayList
        List<String> palabrasLista = new ArrayList<>(Arrays.asList(palabras));


        StringBuilder iniciales = new StringBuilder();
        for (String palabra : palabrasLista) {
            // Toma la primera letra (palabra.charAt(0)), y se añade al objeto StringBuilder (.append)
            iniciales.append(palabra.charAt(0));
        }

        this.iniciales = iniciales.toString().toUpperCase(); // Convierte a mayúsculas y de StringBuilder a String.
    }

    @Override
    public String toString() {
        return "(" + iniciales + ") - " + nombre;
    }

    // Método abstracto para establecer el número de años (cada grado tiene su forma de establecer sus años)
    public abstract void setNumAnios(int numAnios);

}
