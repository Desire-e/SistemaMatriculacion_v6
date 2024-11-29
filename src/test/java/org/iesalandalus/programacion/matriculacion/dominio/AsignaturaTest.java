package org.iesalandalus.programacion.matriculacion.dominio;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class AsignaturaTest {

    private static final String ERROR_EXCEPCION = "Debería haber saltado la excepción.";
    private static final String ERROR_NO_EXCEPCION = "No debería haber saltado la excepción.";
    private static final int CODIGO_CF=1225;
    private static final String FAMILIA_PROFESIONAL_CF="Informática y Comunicaciones";
    private static final Grado GRADO_CF=Grado.GDCFGS;
    private static final String NOMBRE_CICLO_FORMATIVO="DAW";
    private static final int HORAS_CICLO_FORMATIVO=1000;
    private static final String CODIGO_ASIGNATURA="0100";
    private static final String CODIGO_ASIGNATURA_MAL="01";
    private static final String NOMBRE_ASIGNATURA="Programación";
    private static final int HORAS_ASIGNATURA=256;
    private static final Curso CURSO_ASIGNATURA=Curso.PRIMERO;
    private static final int HORAS_DESDOBLE_ASIGNATURA=4;
    private static final EspecialidadProfesorado ESPECIALIDAD_PROFESORADO_ASIGNATURA=EspecialidadProfesorado.INFORMATICA;

    private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto asignatura.";
    private static final String MENSAJE_EXCEPCION_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
    private static final String ERROR_CODIGO_NULO="ERROR: El código de una asignatura no puede ser nulo.";
    private static final String ERROR_CODIGO_INCORRECTO="ERROR: El código de una asignatura no puede estar vacío.";
    private static final String ERROR_CODIGO_FORMATO_INCORRECTO="ERROR: El código de la asignatura no tiene un formato válido.";
    private static final String ERROR_NOMBRE_NULO="ERROR: El nombre de una asignatura no puede ser nulo.";
    private static final String ERROR_NOMBRE_INCORRECTO="ERROR: El nombre de una asignatura no puede estar vacío.";
    private static final String ERROR_HORAS_INCORRECTAS="ERROR: El número de horas de una asignatura no puede ser menor o igual a 0 ni mayor a " + Asignatura.MAX_NUM_HORAS_ANUALES + ".";
    private static final String ERROR_CURSO_NULO="ERROR: El curso de una asignatura no puede ser nulo.";
    private static final String ERROR_HORAS_DESDOBLE_INCORRECTAS="ERROR: El número de horas de desdoble de una asignatura no puede ser menor a 0 ni mayor a "+ Asignatura.MAX_NUM_HORAS_DESDOBLES+ ".";
    private static final String ERROR_ESPECIALIDAD_NULA="ERROR: La especialidad del profesorado de una asignatura no puede ser nula.";
    private static final String ERROR_CICLO_FORMATIVO_ASIGNATURA_NULO="ERROR: El ciclo formativo de una asignatura no puede ser nulo.";

    private static final String ASIGNATURA_NULA = "Debería haber saltado una excepción indicando que no se puede copiar una asignatura nula.";
    private static final String ERROR_COPIAR_ASIGNATURA_NULA = "ERROR: No es posible copiar una asignatura nula.";
    private static final String TIPO_EXCEPCION_NO_CORRECTA = "El tipo de la excepción no es correcto.";


    CicloFormativo cf=new CicloFormativo(CODIGO_CF, FAMILIA_PROFESIONAL_CF, GRADO_CF, NOMBRE_CICLO_FORMATIVO, HORAS_CICLO_FORMATIVO);

    @Test
    public void constructorParametrosValidoCreaAsignaturaCorrectamenteTest() {
        Asignatura asignatura = null;
        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            assertEquals(CODIGO_ASIGNATURA, asignatura.getCodigo());
            assertEquals(NOMBRE_ASIGNATURA, asignatura.getNombre());
            assertEquals(HORAS_ASIGNATURA, asignatura.getHorasAnuales());
            assertEquals(CURSO_ASIGNATURA, asignatura.getCurso());
            assertEquals(HORAS_DESDOBLE_ASIGNATURA, asignatura.getHorasDesdoble());
            assertEquals(ESPECIALIDAD_PROFESORADO_ASIGNATURA, asignatura.getEspecialidadProfesorado());
            assertEquals(cf, asignatura.getCicloFormativo());

        } catch (IllegalArgumentException e) {
            fail(ERROR_NO_EXCEPCION);
        }
    }

    @Test
    public void constructorParametrosNoValidoTest() {
        Asignatura asignatura = null;
        try {
            asignatura = new Asignatura(null, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (NullPointerException e) {
            assertEquals(ERROR_CODIGO_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura("", NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CODIGO_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura("         ", NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CODIGO_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA_MAL, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CODIGO_FORMATO_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura("1234567", NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CODIGO_FORMATO_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA, null,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (NullPointerException e) {
            assertEquals(ERROR_NOMBRE_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA, "",HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NOMBRE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA, "            ",HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NOMBRE_INCORRECTO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,0,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_HORAS_INCORRECTAS, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,-2,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_HORAS_INCORRECTAS, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,301,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_HORAS_INCORRECTAS, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,null,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (NullPointerException e) {
            assertEquals(ERROR_CURSO_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,-2,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_HORAS_DESDOBLE_INCORRECTAS, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,7,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
            fail(ERROR_EXCEPCION);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_HORAS_DESDOBLE_INCORRECTAS, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,null,cf);
            fail(ERROR_EXCEPCION);
        } catch (NullPointerException e) {
            assertEquals(ERROR_ESPECIALIDAD_NULA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }

        try {
            asignatura = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,null);
            fail(ERROR_EXCEPCION);
        } catch (NullPointerException e) {
            assertEquals(ERROR_CICLO_FORMATIVO_ASIGNATURA_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }
    }

    @Test
    public void constructorCopiaValidoCopiaAsignaturaCorrectamente() {
        Asignatura asignatura = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);
        Asignatura asignatura1;
        try {
            asignatura1 = new Asignatura(asignatura);
            assertEquals(CODIGO_ASIGNATURA, asignatura1.getCodigo());
            assertEquals(NOMBRE_ASIGNATURA, asignatura1.getNombre());
            assertEquals(HORAS_ASIGNATURA, asignatura1.getHorasAnuales());
            assertEquals(CURSO_ASIGNATURA, asignatura1.getCurso());
            assertEquals(HORAS_DESDOBLE_ASIGNATURA, asignatura1.getHorasDesdoble());
            assertEquals(ESPECIALIDAD_PROFESORADO_ASIGNATURA, asignatura1.getEspecialidadProfesorado());
            assertEquals(cf, asignatura1.getCicloFormativo());

        } catch (IllegalArgumentException e) {
            fail(ERROR_NO_EXCEPCION);
        }
    }

    @Test
    public void constructorAsignaturaNulaLanzaExcepcion() {
        Asignatura asignatura = null;
        try {
            asignatura = new Asignatura(null);
            fail(ASIGNATURA_NULA);
        } catch (NullPointerException e) {
            assertEquals(ERROR_COPIAR_ASIGNATURA_NULA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(asignatura, OBJETO_DEBERIA_SER_NULO);
        }
        catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
    }

    @Test
    void toStringDevuelveLaCadenaEsperada() {
        Asignatura asignatura = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);

        String cadenaEsperada=String.format("Código=%s, nombre=%s, horas anuales=%d, curso=%s, horas desdoble=%d, ciclo formativo=%s, especialidad profesorado=%s", CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,
                CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA, cf.imprimir(),ESPECIALIDAD_PROFESORADO_ASIGNATURA);

        assertEquals(cadenaEsperada, asignatura.toString());

    }


    @Test
    void imprimirDevuelveLaCadenaEsperada() {


        Asignatura asignatura = new Asignatura(CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA,HORAS_ASIGNATURA,CURSO_ASIGNATURA,HORAS_DESDOBLE_ASIGNATURA,ESPECIALIDAD_PROFESORADO_ASIGNATURA,cf);

        String cadenaEsperada=String.format("Código asignatura=%s, nombre asignatura=%s, ciclo formativo=%s", CODIGO_ASIGNATURA, NOMBRE_ASIGNATURA, cf.imprimir());

        assertEquals(cadenaEsperada, asignatura.imprimir());

    }



}
