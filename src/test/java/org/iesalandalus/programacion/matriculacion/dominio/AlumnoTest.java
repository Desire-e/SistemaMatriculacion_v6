package org.iesalandalus.programacion.matriculacion.dominio;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class AlumnoTest {

    private static final String CADENA_NO_ESPERADA = "La cadena devuelta no es la esperada.";

    private static final String NOMBRE_NO_ESPERADO = "El nombre devuelto no es el mismo que el pasado al constructor.";
    private static final String DNI_NO_ESPERADO = "El DNI devuelto no es el mismo que el pasado al constructor.";
    private static final String TELEFONO_NO_ESPERADO = "El teléfono devuelto no es el mismo que el pasado al constructor.";
    private static final String CORREO_NO_ESPERADO = "El correo devuelto no es el mismo que el pasado al constructor.";
    private static final String FECHA_NACIMIENTO_NO_ESPERADA = "La fecha de nacimiento devuelta no es la misma que la pasada al constructor.";
    private static final String NIA_NO_ESPERADO = "El cálculo del Número de Identificación del Alumno no es el correcto.";

    private static final String NOMBRE_JRJR = "José Ramón Jiménez Reyes";
    private static final String DNI_JRJR = "11223344B";
    private static final String TELEFONO_JRJR = "950112233";
    private static final String CORREO_JRJR = "joseramon.jimenez@iesalandalus.org";
    private static final String NOMBRE_MAL_ARDR = "  ANDRÉS   RuBiO   dEl             río";
    private static final String NOMBRE_ARDR = "Andrés Rubio Del Río";
    private static final String DNI_ARDR = "22334455Y";
    private static final String TELEFONO_ARDR = "666223344";
    private static final String CORREO_ARDR = "andres.rubio@iesalandalus.org";

    private static final LocalDate FECHA_NACIMIENTO_JRJR=LocalDate.of(2002, 9, 15);
    private static final LocalDate FECHA_NACIMIENTO_ARDR=LocalDate.of(1992, 7, 3);
    private static final String NIA_JRJR=NOMBRE_JRJR.toLowerCase().substring(0,4)+DNI_JRJR.substring(5,8);;
    private static final String NIA_ARDR=NOMBRE_ARDR.toLowerCase().substring(0,4)+DNI_ARDR.substring(5,8);;
    private static final String FORMATO_FECHA="dd/MM/yyyy";
    private static final String TIPO_EXCEPCION_NO_CORRECTA = "El tipo de la excepción no es correcto.";
    private static final String NOMBRE_INCORRECTO = "Debería haber saltado una excepción indicando que el nombre es incorrecto";
    private static final String ERROR_NOMBRE_NULO = "ERROR: El nombre de un alumno no puede ser nulo.";
    private static final String ERROR_NOMBRE_NO_VALIDO = "ERROR: El nombre de un alumno no puede estar vacío.";
    private static final String DNI_INCORRECTO = "Debería haber saltado una excepción indicando que el DNI es incorrecto";
    private static final String ERROR_DNI_NULO = "ERROR: El dni de un alumno no puede ser nulo.";
    private static final String ERROR_DNI_NO_VALIDO = "ERROR: El dni del alumno no tiene un formato válido.";
    private static final String ERROR_CORREO_NULO = "ERROR: El correo de un alumno no puede ser nulo.";
    private static final String CORREO_INCORRECTO = "Debería haber saltado una excepción indicando que el correo es incorrecto";
    private static final String ERROR_CORREO_NO_VALIDO = "ERROR: El correo del alumno no tiene un formato válido.";
    private static final String ERROR_TELEFONO_NO_VALIDO = "ERROR: El teléfono del alumno no tiene un formato válido.";

    private static final String MENSAJE_EXCEPCION_NO_CORRECTO = "El mensaje devuelto por la excepción no es correcto.";
    private static final String OBJETO_DEBERIA_SER_NULO = "No se debería haber creado el objeto alumno.";
    private static final String ERROR_LETRA_DNI_NO_VALIDA = "ERROR: La letra del dni del alumno no es correcta.";
    private static final String TELEFONO_INCORRECTO = "Debería haber saltado una excepción indicando que el teléfono es incorrecto";

    private static final String ERROR_TELEFONO_NULO = "ERROR: El teléfono de un alumno no puede ser nulo.";
    private static final String FECHA_NACIMIENTO_INCORRECTA = "Debería haber saltado una excepción indicando que la fecha de nacimiento es incorrecta";
    private static final String ERROR_FECHA_NULA = "ERROR: La fecha de nacimiento de un alumno no puede ser nula.";
    private static final String ERROR_FECHA_NACIMIENTO_INCORRECTA = "ERROR: La edad del alumno debe ser mayor o igual a 16 años.";
    private static final String ALUMNO_NO_ESPERADO = "El alumno copiado debería ser el mismo que el pasado como parámetro.";
    private static final String ALUMNO_NULO = "Debería haber saltado una excepción indicando que no se puede copiar un alumno nulo.";
    private static final String ERROR_COPIAR_ALUMNO_NULO = "ERROR: No es posible copiar un alumno nulo.";

    @Test
    public void constructorNombreValidoDniValidoCorreoValidoTelefonoValidoFechaNacimientoValidaNiaValidoCreaAlumnoCorrectamente() {
        Alumno alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
        assertEquals(NOMBRE_JRJR, alumno.getNombre(), NOMBRE_NO_ESPERADO);
        assertEquals(DNI_JRJR, alumno.getDni(), DNI_NO_ESPERADO);
        assertEquals(CORREO_JRJR, alumno.getCorreo(), CORREO_NO_ESPERADO);
        assertEquals(TELEFONO_JRJR, alumno.getTelefono(), TELEFONO_NO_ESPERADO);
        assertEquals(NIA_JRJR, alumno.getNia(), NIA_NO_ESPERADO);


        alumno = new Alumno(NOMBRE_MAL_ARDR, DNI_ARDR, CORREO_ARDR, TELEFONO_ARDR, FECHA_NACIMIENTO_ARDR);
        assertEquals(NOMBRE_ARDR, alumno.getNombre(), NOMBRE_NO_ESPERADO);
        assertEquals(DNI_ARDR, alumno.getDni(), DNI_NO_ESPERADO);
        assertEquals(CORREO_ARDR, alumno.getCorreo(), CORREO_NO_ESPERADO);
        assertEquals(TELEFONO_ARDR, alumno.getTelefono(), TELEFONO_NO_ESPERADO);
        assertEquals(FECHA_NACIMIENTO_ARDR, alumno.getFechaNacimiento(), FECHA_NACIMIENTO_NO_ESPERADA);
        assertEquals(NIA_ARDR, alumno.getNia(), NIA_NO_ESPERADO);

    }

    @Test
    public void constructorNombreNoValidoDniValidoCorreoValidoTelefonoValidoFechaNacimientoValidaLanzaExcepcion() {
        Alumno alumno = null;
        try {
            alumno = new Alumno(null, DNI_JRJR, CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(NOMBRE_INCORRECTO);
        } catch (NullPointerException e) {
            assertEquals(ERROR_NOMBRE_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
        try {
            alumno = new Alumno("", DNI_JRJR, CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(NOMBRE_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NOMBRE_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
        try {
            alumno = new Alumno("  ", DNI_JRJR, CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(NOMBRE_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_NOMBRE_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
    }

    @Test
    public void constructorNombreValidoDniNoValidoCorreoValidoTelefonoValidoFechaNacimientoValidaLanzaExcepcion() {
        Alumno alumno = null;

        try {
            alumno = new Alumno(NOMBRE_JRJR, null, CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(DNI_INCORRECTO);
        } catch (NullPointerException e) {
            assertEquals(ERROR_DNI_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, "", CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(DNI_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_DNI_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, "   ", CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(DNI_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_DNI_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, "11223344", CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(DNI_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_DNI_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, "112233445A", CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(DNI_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_DNI_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, "11223344AA", CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(DNI_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_DNI_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, "11223344A", CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(DNI_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_LETRA_DNI_NO_VALIDA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(alumno,OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
    }

    @Test
    public void constructorNombreValidoDniValidoCorreoNoValidoTelefonoValidoFechaNacimientoValidaLanzaExcepcion() {
        Alumno alumno = null;

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, null, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(CORREO_INCORRECTO);
        } catch (NullPointerException e) {
            assertEquals(ERROR_CORREO_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, "", TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(CORREO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CORREO_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, "   ", TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(CORREO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CORREO_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, "11223344", TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(CORREO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CORREO_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, "arr@.com", TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(CORREO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CORREO_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, "arr.es.com", TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(CORREO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CORREO_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, "arr@gmail", TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);
            fail(CORREO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_CORREO_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(alumno,OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
    }

    @Test
    public void constructorNombreValidoDniValidoCorreoValidoTelefonoNoValidoFechaNacimientoValidaLanzaExcepcion() {
        Alumno alumno = null;

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, null, FECHA_NACIMIENTO_JRJR);
            fail(TELEFONO_INCORRECTO);
        } catch (NullPointerException e) {
            assertEquals(ERROR_TELEFONO_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, "", FECHA_NACIMIENTO_JRJR);
            fail(TELEFONO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_TELEFONO_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, "   ", FECHA_NACIMIENTO_JRJR);
            fail(TELEFONO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_TELEFONO_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, "1", FECHA_NACIMIENTO_JRJR);
            fail(TELEFONO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_TELEFONO_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, "arr@.com", FECHA_NACIMIENTO_JRJR);
            fail(TELEFONO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_TELEFONO_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, "9991122334", FECHA_NACIMIENTO_JRJR);
            fail(TELEFONO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_TELEFONO_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, "99911223", FECHA_NACIMIENTO_JRJR);
            fail(TELEFONO_INCORRECTO);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_TELEFONO_NO_VALIDO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(alumno,OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
    }

    @Test
    public void constructorNombreValidoDniValidoCorreoValidoTelefonoValidoFechaNacimientoNoValidaLanzaExcepcion() {
        Alumno alumno = null;

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, TELEFONO_JRJR, null);
            fail(FECHA_NACIMIENTO_INCORRECTA);
        } catch (NullPointerException e) {
            assertEquals(ERROR_FECHA_NULA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }

        try {
            alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, TELEFONO_JRJR, LocalDate.of(2020,10,12));
            fail(FECHA_NACIMIENTO_INCORRECTA);
        } catch (IllegalArgumentException e) {
            assertEquals(ERROR_FECHA_NACIMIENTO_INCORRECTA, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertNull(alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }


    }

    @Test
    public void constructorAlumnoValidoCopiaAlumnoCorrectamente() {
        Alumno alumno1 = new Alumno(NOMBRE_ARDR, DNI_ARDR, CORREO_ARDR, TELEFONO_ARDR, FECHA_NACIMIENTO_ARDR);
        Alumno alumno2 = new Alumno(alumno1);
        assertEquals(alumno1, alumno2, ALUMNO_NO_ESPERADO);
        assertEquals(NOMBRE_ARDR, alumno2.getNombre(), NOMBRE_NO_ESPERADO);
        assertEquals(DNI_ARDR, alumno2.getDni(), DNI_NO_ESPERADO);
        assertEquals(CORREO_ARDR, alumno2.getCorreo(), CORREO_NO_ESPERADO);
        assertEquals(TELEFONO_ARDR, alumno2.getTelefono(), TELEFONO_NO_ESPERADO);
        assertEquals(FECHA_NACIMIENTO_ARDR, alumno2.getFechaNacimiento(), FECHA_NACIMIENTO_NO_ESPERADA);
        assertEquals(NIA_ARDR,alumno2.getNia(),NIA_NO_ESPERADO);
    }

    @Test
    public void constructorAlumnoNuloLanzaExcepcion() {
        Alumno alumno = null;

        try {
            alumno = new Alumno(null);
            fail(ALUMNO_NULO);
        } catch (NullPointerException e) {
            assertEquals(ERROR_COPIAR_ALUMNO_NULO, e.getMessage(), MENSAJE_EXCEPCION_NO_CORRECTO);
            assertEquals(null, alumno, OBJETO_DEBERIA_SER_NULO);
        } catch (Exception e) {
            fail(TIPO_EXCEPCION_NO_CORRECTA);
        }
    }

    @Test
    public void toStringDevuelveLaCadenaEsperada() {
        Alumno alumno = new Alumno(NOMBRE_JRJR, DNI_JRJR, CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR);

        assertEquals(String.format("Número de Identificación del Alumnado (NIA)=%s " + "nombre=%s (%s), DNI=%s, correo=%s, teléfono=%s, fecha nacimiento=%s",NIA_JRJR, NOMBRE_JRJR, "JRJR", DNI_JRJR, CORREO_JRJR, TELEFONO_JRJR, FECHA_NACIMIENTO_JRJR.format(DateTimeFormatter.ofPattern(FORMATO_FECHA))), alumno.toString(), CADENA_NO_ESPERADA);

        alumno = new Alumno(NOMBRE_MAL_ARDR, DNI_ARDR, CORREO_ARDR, TELEFONO_ARDR, FECHA_NACIMIENTO_ARDR);
        assertEquals(String.format("Número de Identificación del Alumnado (NIA)=%s " + "nombre=%s (%s), DNI=%s, correo=%s, teléfono=%s, fecha nacimiento=%s", NIA_ARDR, NOMBRE_ARDR, "ARDR", DNI_ARDR, CORREO_ARDR, TELEFONO_ARDR, FECHA_NACIMIENTO_ARDR.format(DateTimeFormatter.ofPattern(FORMATO_FECHA))), alumno.toString(), CADENA_NO_ESPERADA);
    }


}
