package org.iesalandalus.programacion.matriculacion.dominio;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class CicloFormativoTest {

    private static final String CADENA_NO_ESPERADA = "La cadena devuelta no es la esperada.";

    private static final String NOMBRE_NO_ESPERADO = "El nombre devuelto no es el mismo que el pasado al constructor.";
    private static final String CODIGO_NO_ESPERADO = "El código devuelto no es el mismo que el pasado al constructor.";
    private static final String FAMILIA_PROFESIONAL_NO_ESPERADA = "La familia profesional devuelta no es la misma que la pasada al constructor.";
    private static final String GRADO_NO_ESPERADO = "El grado devuelto no es el mismo que el pasado al constructor.";
    private static final String HORAS_NO_ESPERADA = "Las horas devueltas no son la misma que las pasadas al constructor.";

    private static final int CODIGO_CF=1225;
    private static final String FAMILIA_PROFESIONAL_CF="Informática y Comunicaciones";
    private static final Grado GRADO_CF=Grado.GDCFGS;
    private static final String NOMBRE_CICLO_FORMATIVO="DAW";
    private static final int HORAS_CICLO_FORMATIVO=1000;
    private static final int CODIGO_CF_MAL=100;
    private static final int CODIGO_CF_MAL_2=11225;
    


    private static final String TIPO_EXCEPCION_NO_CORRECTA = "El tipo de la excepción no es correcto.";
    private static final String CODIGO_INCORRECTO = "Debería haber saltado una excepción indicando que el código es incorrecto";
    private static final String ERROR_NOMBRE_NULO = "ERROR: El nombre de un ciclo formativo no puede ser nulo.";
    private static final String ERROR_NOMBRE_NO_VALIDO = "ERROR: El nombre de un ciclo formativo no puede estar vacío.";
    private static final String FAMILIA_PROFESIONAL_INCORRECTA = "Debería haber saltado una excepción indicando que la familia profesional es incorrecta.";
    private static final String ERROR_FAMILIA_PROFESIONAL_NULA = "ERROR: La familia profesional de un ciclo formativo no puede ser nula.";
    private static final String ERROR_FAMILIA_PROFESIONAL_NO_VALIDA = "ERROR: La familia profesional no puede estar vacía.";
    private static final String ERROR_GRADO_NULO = "ERROR: El grado de un ciclo formativo no puede ser nulo.";
    private static final String GRADO_INCORRECTO = "Debería haber saltado una excepción indicando que el grado es incorrecto";


    private static final String MENSAJE_EXCEPCION_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
    private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto ciclo formativo.";
    private static final String NOMBRE_INCORRECTO = "Debería haber saltado una excepción indicando que el nombre es incorrecto";

    private static final String HORAS_INCORRECTA = "Debería haber saltado una excepción indicando que las horas son incorrectas";
    private static final String ERROR_HORAS_INCORRECTA = "ERROR: El número de horas de un ciclo formativo no puede ser menor o igual a 0 ni mayor a " + CicloFormativo.MAXIMO_NUMERO_HORAS + ".";
    private static final String CICLO_FORMATIVO_NO_ESPERADO = "El ciclo formativo copiado debería ser el mismo que el pasado como parámetro.";
    private static final String CICLO_FORMATIVO_NULO = "Debería haber saltado una excepción indicando que no se puede copiar un ciclo formativo nulo.";
    private static final String ERROR_COPIAR_CICLO_FORMATIVO_NULO = "ERROR: No es posible copiar un ciclo formativo nulo.";

    @Test
    public void constructorCodigoValidoFamiliaProfesionalValidaGradoValidoNombreValidoHorasValidasCreaCicloFormativoCorrectamente() {
        CicloFormativo cf=new CicloFormativo(CODIGO_CF, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);

        assertEquals(CODIGO_CF, cf.getCodigo(), CODIGO_NO_ESPERADO);
        assertEquals(FAMILIA_PROFESIONAL_CF, cf.getFamiliaProfesional(), FAMILIA_PROFESIONAL_NO_ESPERADA);
        assertEquals(GRADO_CF, cf.getGrado(), GRADO_NO_ESPERADO);
        assertEquals(NOMBRE_CICLO_FORMATIVO, cf.getNombre(), NOMBRE_NO_ESPERADO);
        assertEquals(HORAS_CICLO_FORMATIVO, cf.getHoras(), HORAS_NO_ESPERADA);

    }

    @Test
    public void constructorCodigoNoValidoFamiliaProfesionalValidaGradoValidoNombreValidoHorasValidasLanzaExcepcion() {
        CicloFormativo cf = null;
        try {
            cf = new CicloFormativo(CODIGO_CF_MAL, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);
            fail(CODIGO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertNull(cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            cf = new CicloFormativo(CODIGO_CF_MAL_2, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);
            fail(CODIGO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertNull(cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

    }

    @Test
    public void constructorCodigoValidoFamiliaProfesionalNoValidaGradoValidoNombreValidoHorasValidasLanzaExcepcion() {
        CicloFormativo cf = null;

        try {
            cf = new CicloFormativo(CODIGO_CF, null, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);
            fail(FAMILIA_PROFESIONAL_INCORRECTA);
        } catch (NullPointerException e) {
            assertEquals(ERROR_FAMILIA_PROFESIONAL_NULA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            cf = new CicloFormativo(CODIGO_CF, "", GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);
            fail(FAMILIA_PROFESIONAL_INCORRECTA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_FAMILIA_PROFESIONAL_NO_VALIDA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            cf = new CicloFormativo(CODIGO_CF, "   ", GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);
            fail(FAMILIA_PROFESIONAL_INCORRECTA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_FAMILIA_PROFESIONAL_NO_VALIDA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

    }

    @Test
    public void constructorCodigoValidoFamiliaProfesionalValidaGradoNoValidoNombreValidoHorasValidasLanzaExcepcion() {
        CicloFormativo cf = null;

        try {
            cf = new CicloFormativo(CODIGO_CF, FAMILIA_PROFESIONAL_CF, null, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);
            fail(GRADO_INCORRECTO);
        } catch (NullPointerException e) {
            assertEquals(ERROR_GRADO_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

    }

    @Test
    public void constructorCodigoValidoFamiliaProfesionalValidaGradoValidoNombreNoValidoHorasValidasLanzaExcepcion() {
        CicloFormativo cf = null;

        try {
            cf = new CicloFormativo(CODIGO_CF, FAMILIA_PROFESIONAL_CF, GRADO_CF, null, HORAS_CICLO_FORMATIVO);
            fail(NOMBRE_INCORRECTO);
        } catch (NullPointerException e) {
            assertEquals(ERROR_NOMBRE_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            cf = new CicloFormativo(CODIGO_CF, FAMILIA_PROFESIONAL_CF, GRADO_CF,"", HORAS_CICLO_FORMATIVO);
            fail(NOMBRE_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NOMBRE_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            cf = new CicloFormativo(CODIGO_CF, FAMILIA_PROFESIONAL_CF, GRADO_CF, "   ", HORAS_CICLO_FORMATIVO);
            fail(NOMBRE_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NOMBRE_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

    }

    @Test
    public void constructorCodigoValidoFamiliaProfesionalValidaGradoValidoNombreValidoHorasNoValidasLanzaExcepcion() {
        CicloFormativo cf = null;

        try {
            cf = new CicloFormativo(CODIGO_CF, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, 0);
            fail(HORAS_INCORRECTA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_HORAS_INCORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            cf = new CicloFormativo(CODIGO_CF, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, 3000);
            fail(HORAS_INCORRECTA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_HORAS_INCORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            cf = new CicloFormativo(CODIGO_CF, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, -2);
            fail(HORAS_INCORRECTA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_HORAS_INCORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
    }

    @Test
    public void constructorCicloFormativoValidoCopiaCicloFormativoCorrectamente() {
        CicloFormativo cf1 = new CicloFormativo(CODIGO_CF, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);
        CicloFormativo cf2 = new CicloFormativo(cf1);
        assertEquals(cf1, cf2, CICLO_FORMATIVO_NO_ESPERADO);
        assertEquals(CODIGO_CF, cf2.getCodigo(), CODIGO_NO_ESPERADO);
        assertEquals(FAMILIA_PROFESIONAL_CF, cf2.getFamiliaProfesional(), FAMILIA_PROFESIONAL_NO_ESPERADA);
        assertEquals(GRADO_CF, cf2.getGrado(), GRADO_NO_ESPERADO);
        assertEquals(NOMBRE_CICLO_FORMATIVO, cf2.getNombre(), NOMBRE_NO_ESPERADO);
        assertEquals(HORAS_CICLO_FORMATIVO, cf2.getHoras(), HORAS_NO_ESPERADA);
    }

    @Test
    public void constructorCicloFormativoNuloLanzaExcepcion() {
        CicloFormativo cf = null;

        try {
            cf = new CicloFormativo(null);
            fail(CICLO_FORMATIVO_NULO);
        } catch (NullPointerException e) {
            assertEquals(ERROR_COPIAR_CICLO_FORMATIVO_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(cf, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
    }

    @Test
    public void toStringDevuelveLaCadenaEsperada() {
        CicloFormativo cf = new CicloFormativo(CODIGO_CF, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);

        assertEquals(String.format("Código ciclo formativo=%d, familia profesional=%s, grado=%s, nombre ciclo formativo=%s, horas=%s",CODIGO_CF, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO), cf.toString(), CADENA_NO_ESPERADA);

    }

    @Test
    public void imprimirDevuelveLaCadenaEsperada() {
        CicloFormativo cf = new CicloFormativo(CODIGO_CF, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);

        assertEquals(String.format("Código ciclo formativo=%d, nombre ciclo formativo=%s",CODIGO_CF, NOMBRE_CICLO_FORMATIVO), cf.imprimir(), CADENA_NO_ESPERADA);

    }

}
