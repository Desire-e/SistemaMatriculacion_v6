package org.iesalandalus.programacion.matriculacion.dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EspecialidadProfesoradoTest {

    private static final String NOMBRE_NO_VALIDO = "La especialidad del profesorado no es válida.";


    @Test
    void objectosValidosEspecialidadProfesorado() {
        assertEquals("INFORMATICA", EspecialidadProfesorado.INFORMATICA.name(), NOMBRE_NO_VALIDO);
        assertEquals("SISTEMAS", EspecialidadProfesorado.SISTEMAS.name(), NOMBRE_NO_VALIDO);
        assertEquals("FOL", EspecialidadProfesorado.FOL.name(), NOMBRE_NO_VALIDO);
    }

}
