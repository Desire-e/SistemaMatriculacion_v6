package org.iesalandalus.programacion.matriculacion.modelo.dominio;


//CAMBIOS V.3
public class GradoE extends Grado {
    private int numEdiciones;

    public GradoE(String nombre, int numAnios, int numEdiciones){
        super(nombre);
        setNumAnios(numAnios);
        setNumEdiciones(numEdiciones);
    }

    public int getNumEdiciones(){
        return numEdiciones;
    }

    public void setNumEdiciones(int numEdiciones){
        if (numEdiciones<=0){
            throw new IllegalArgumentException("El número de ediciones no puede ser menor ni igual a 0.");
        }
        this.numEdiciones = numEdiciones;
    }

    public void setNumAnios(int numAnios){
        if (numAnios != 1){
            throw new IllegalArgumentException("El Grado E solo puede tener 1 año.");
        }
        this.numAnios = numAnios;
    }


    // RETROALIMENTACIÓN:
    // El método toString debe llamar al toString del super y añadir su propios atributos.
    @Override
    public String toString() {
        return super.toString() +
                " - Años: " + numAnios +
                " - Número de ediciones: " + numEdiciones;
    }
}
