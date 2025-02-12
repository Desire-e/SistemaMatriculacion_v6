package org.iesalandalus.programacion.matriculacion.modelo.dominio;


//CAMBIOS V.3
public enum TiposGrado {
    GRADOD("Grado D"), GRADOE("Grado E");
    private final String cadenaAMostrar;

    private TiposGrado (String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir(){
        //  Método ordinal() da la posición del valor enumerado (empezando desde 0).
        return ordinal() + ".-" + cadenaAMostrar;
    }

    public String toString() {
        return "Tipo de grado{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }

}
