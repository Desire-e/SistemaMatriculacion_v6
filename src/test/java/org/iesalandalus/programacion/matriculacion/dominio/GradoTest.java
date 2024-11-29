package org.iesalandalus.programacion.matriculacion.dominio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GradoTest {

    private static final String NOMBRE_NO_VALIDO = "El grado del ciclo formativo no es válido.";


    @Test
    void objectosValidosGrado() {
        assertEquals("GDCFGB", Grado.GDCFGB.name(), NOMBRE_NO_VALIDO);
        assertEquals("GDCFGM", Grado.GDCFGM.name(), NOMBRE_NO_VALIDO);
        assertEquals("GDCFGS", Grado.GDCFGS.name(), NOMBRE_NO_VALIDO);
    }

}
