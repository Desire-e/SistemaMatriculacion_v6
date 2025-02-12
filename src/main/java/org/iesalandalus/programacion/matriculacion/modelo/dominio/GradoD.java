package org.iesalandalus.programacion.matriculacion.modelo.dominio;


//CAMBIOS V.3
public class GradoD extends Grado{
    private Modalidad modalidad;

    public GradoD(String nombre, int numAnios, Modalidad modalidad){
        //Constructor de la clase padre (Grado)
        super(nombre);
        //Método abstracto: heredado obligatoriamente
        setNumAnios(numAnios);
        setModalidad(modalidad);
    }

    public Modalidad getModalidad(){
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad){
        if (modalidad==null){
            throw new NullPointerException("La modalidad no puede ser nula.");
        }
        this.modalidad=modalidad;
    }


    public void setNumAnios(int numAnios){
        if (numAnios != 3 && numAnios != 2){
            throw new IllegalArgumentException("El Grado D solo puede tener 2 o 3 años.");
        }

        // Hereda la variable numAnios
        this.numAnios = numAnios;
    }

    @Override
    public String toString() {
        return "GradoD{" +
                "modalidad=" + modalidad +
                ", nombre='" + nombre + '\'' +
                ", iniciales='" + iniciales + '\'' +
                ", numAnios=" + numAnios +
                '}';
    }
}
