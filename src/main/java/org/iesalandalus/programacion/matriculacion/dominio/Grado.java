package org.iesalandalus.programacion.matriculacion.dominio;

public enum Grado {
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
}
