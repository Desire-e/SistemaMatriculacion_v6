package org.iesalandalus.programacion.matriculacion.modelo.dominio;

public enum EspecialidadProfesorado {
    INFORMATICA("Informática"), SISTEMAS("Sistemas"), FOL("FOL");
    private String cadenaAMostrar;

    private EspecialidadProfesorado (String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String imprimir (){
        //  Método ordinal() da la posición del valor enumerado (empezando desde 0).
        return ordinal() + ".-" + cadenaAMostrar;
    }

    @Override
    public String toString() {
        return "EspecialidadProfesorado{" +
                "cadenaAMostrar='" + cadenaAMostrar + '\'' +
                '}';
    }
}
