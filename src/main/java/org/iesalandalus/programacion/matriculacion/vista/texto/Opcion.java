package org.iesalandalus.programacion.matriculacion.vista.texto;

public enum Opcion {


    SALIR("Salir")
    //CAMBIOS V.3:
    {
        @Override
        // Aquí las instancias (salir, etc) heredan todas este método de este enum, con una implementación distinta
        // en cada una. Por ejemplo, esta instancia usa este método para llamar a  terminar(), o sea, su correspondiente
        // de la clase Vista.
        public void ejecutar() {
            vista.terminar();
        }
    },

    INSERTAR_ALUMNO("Insertar alumno"){
        @Override
        public void ejecutar(){
            vista.insertarAlumno();
        }
    },
    BUSCAR_ALUMNO("Buscar alumno"){
        @Override
        public void ejecutar(){
            vista.buscarAlumno();
        }
    },
    BORRAR_ALUMNO("Borrar alumno"){
        @Override
        public void ejecutar(){
            vista.borrarAlumno();
        }
    },
    MOSTRAR_ALUMNOS("Mostrar alumnos"){
        @Override
        public void ejecutar(){
            vista.mostrarAlumnos();
        }
    },

    INSERTAR_CICLO_FORMATIVO("Insertar ciclo formativo"){
        @Override
        public void ejecutar(){
            vista.insertarCicloFormativo();
        }
    },
    BUSCAR_CICLO_FORMATIVO("Buscar ciclo formativo"){
        @Override
        public void ejecutar(){
            vista.buscarCicloFormativo();
        }
    },
    BORRAR_CICLO_FORMATIVO("Borrar ciclo formativo"){
        @Override
        public void ejecutar(){
            vista.borrarCicloFormativo();
        }
    },
    MOSTRAR_CICLOS_FORMATIVOS("Mostrar ciclos formativos"){
        @Override
        public void ejecutar(){
            vista.mostrarCiclosFormativos();
        }
    },

    INSERTAR_ASIGNATURA("Insertar asignatura"){
        @Override
        public void ejecutar(){
            vista.insertarAsignatura();
        }
    },

    BUSCAR_ASIGNATURA("Buscar asignatura"){
        @Override
        public void ejecutar(){
            vista.buscarAsignatura();
        }
    },

    BORRAR_ASIGNATURA("Borrar asignatura"){
        @Override
        public void ejecutar(){
            vista.borrarAsignatura();
        }
    },

    MOSTRAR_ASIGNATURAS("Mostrar asignaturas"){
        @Override
        public void ejecutar(){
            vista.mostrarAsignaturas();
        }
    },

    INSERTAR_MATRICULA("Insertar matrícula"){
        @Override
        public void ejecutar(){
            vista.insertarMatricula();
        }
    },

    BUSCAR_MATRICULA("Buscar matrícula"){
        @Override
        public void ejecutar(){
            vista.buscarMatricula();
        }
    },

    ANULAR_MATRICULA("Anular matrícula"){
        @Override
        public void ejecutar(){
            vista.anularMatricula();
        }
    },

    MOSTRAR_MATRICULAS("Mostrar matrículas"){
        @Override
        public void ejecutar(){
            vista.mostrarMatriculas();
        }
    },


    MOSTRAR_MATRICULAS_ALUMNO("Mostrar matrículas alumno"){
        @Override
        public void ejecutar(){
            vista.mostrarMatriculasPorAlumno();
        }
    },
    MOSTRAR_MATRICULAS_CICLO_FORMATIVO("Mostrar matrículas ciclo formativo"){
        @Override
        public void ejecutar(){
            vista.mostrarMatriculasPorCicloFormativo();
        }
    },
    MOSTRAR_MATRICULAS_CURSO_ACADEMICO("Mostrar matrículas curso académico"){
        @Override
        public void ejecutar(){
            vista.mostrarMatriculasPorCursoAcademico();
        }
    }
    ;

    private final String cadenaAMostrar;

    private Opcion (String cadenaAMostrar) {
        this.cadenaAMostrar = cadenaAMostrar;
    }

    public String toString (){
        //  Método ordinal() da la posición del valor enumerado (empezando desde 0).
        return ordinal() + ".-" + cadenaAMostrar;
    }


    // CAMBIOS V.3:
    private static VistaTexto vista;

    public static void setVista(VistaTexto vista){
        if (vista == null) {
            throw new NullPointerException("La vista no puede ser nula.");
        }
        // Opcion.vista para llamar a la variable porque es static
        Opcion.vista = vista;
    }

    // Método abstracto: método que obligatoriamente deben tener las clases herederas, pero no especifica desde el padre
    // la implementación, pues varía en cada hija.

    /* el método ejecutar deberá ser implementado en cada instancia del enum Opcion, llamando al método que le corresponda
    de la clase Vista. Con esto se pretende que cada opción no solo contenga el mensaje que debe mostrarse por pantalla,
    sino también, el método de la clase Vista que debe ser ejecutado cuando el usuario de la aplicación elija dicha opción. */
    public abstract void ejecutar();

}
