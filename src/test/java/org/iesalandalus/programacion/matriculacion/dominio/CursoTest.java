package org.iesalandalus.programacion.matriculacion.dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CursoTest {

    private static final String NOMBRE_NO_VALIDO = "El nombre del curso no es válido.";


    @Test
    void objectosValidosCurso() {
        assertEquals("PRIMERO", Curso.PRIMERO.name(), NOMBRE_NO_VALIDO);
        assertEquals("SEGUNDO", Curso.SEGUNDO.name(), NOMBRE_NO_VALIDO);

    }

}
