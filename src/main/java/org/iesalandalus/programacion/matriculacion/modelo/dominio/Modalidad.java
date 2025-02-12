package org.iesalandalus.programacion.matriculacion.modelo.dominio;


//CAMBIOS V.3
public enum Modalidad {
    PRESENCIAL("Presencial"), SEMIPRESENCIAL("Semipresencial");

    private final String cadenaAMostrar;

    private Modalidad (String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir(){
        //  Método ordinal() da la posición del valor enumerado (empezando desde 0).
        return ordinal() + ".-" + cadenaAMostrar;
    }

    public String toString() {
        return "Modalidad{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }
}
