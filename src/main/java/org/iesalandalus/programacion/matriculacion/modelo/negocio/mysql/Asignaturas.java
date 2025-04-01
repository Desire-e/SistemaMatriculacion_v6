package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import com.mysql.cj.protocol.Resultset;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.cerrarConexion;
import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.establecerConexion;

public class Asignaturas implements IAsignaturas {
    private List<Asignatura> coleccionAsignaturas;
    private Connection conexion = null;


    public Asignaturas() {
        this.coleccionAsignaturas = new ArrayList<>();
        comenzar();
    }

    private static Asignaturas instancia;
    // DUDA: en el diagrama pone que debe ser privado
    public static Asignaturas getInstancia(){
        if (instancia == null){
            instancia = new Asignaturas();
        }
        return instancia;
    }


    @Override
    public void comenzar() {
        conexion = MySQL.establecerConexion();

    }
    @Override
    public void terminar() {
        if (conexion != null) {
            MySQL.cerrarConexion();  // Cerramos la conexión con la base de datos
            conexion = null;  // Restablece la referencia a la conexión
        }
    }



    /* El método getCurso que, dependiendo del parámetro de tipo String curso, deberá
    devolver un objeto de tipo Curso. */
    private Curso getCurso(String curso){
        Curso cursoAsig = null;

        switch (curso) {
            case "primero":
                cursoAsig = Curso.PRIMERO;
                break;
            case "segundo":
                cursoAsig = Curso.SEGUNDO;
                break;
        }

        return cursoAsig;
    }


    /* El método getEspecialidadProfesorado que, dependiendo del parámetro de tipo
    String especialidad, deberá devolver un objeto de tipo EspecialidadProfesorado. */
    private EspecialidadProfesorado getEspecialidadProfesorado (String especialidad){
        EspecialidadProfesorado especialidadAsig = null;

        switch (especialidad) {
            case "informatica":
                especialidadAsig = EspecialidadProfesorado.INFORMATICA;
                break;
            case "sistemas":
                especialidadAsig = EspecialidadProfesorado.SISTEMAS;
                break;
            case "fol":
                especialidadAsig = EspecialidadProfesorado.FOL;
                break;
        }

        return especialidadAsig;
    }


    @Override
    public int getTamano() {
        Statement sentencia = null;
        ResultSet resultados = null;

        int numAsignaturas = 0;
        try {
            sentencia = conexion.createStatement();

            String consulta = "SELECT COUNT(*) AS num_asignaturas FROM asignatura";
            resultados = sentencia.executeQuery(consulta);

            // Obtener nº de asignaturas
            if (resultados.next()) {
                numAsignaturas = resultados.getInt("num_asignaturas");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener número de asignaturas en la base de datos." + e.getMessage());
        }
        finally {
            try {
                if(resultados!=null) {
                    resultados.close();
                }

                if(sentencia!=null) {
                    sentencia.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
            }
        }

        return numAsignaturas;
    }


    @Override
    public List<Asignatura> get(){
        List<Asignatura> listadoAsignaturas = new ArrayList<>();

        Statement sentencia = null;
        ResultSet resultados = null;

        try {
            // 1º Crear sentencia, a partir de conexion:
            sentencia = conexion.createStatement();

            // 2º Crear ResultSet con una consulta, a partir de sentencia:
            String consulta = "SELECT * FROM asignatura ORDER BY nombre";
            resultados = sentencia.executeQuery(consulta);

            // 3º Obtener resultados a partir de ResultSet:
            System.out.println("Lista de asignaturas existentes:");
            while (resultados.next()) {
                // atributos para el obj Asignatura
                String codigoAsig = resultados.getString("codigo");
                String nombreAsig = resultados.getString("nombre");
                int horasAnualesAsig = resultados.getInt("horasAnuales");
                String curso = resultados.getString("curso");
                int horasDesdobleAsig = resultados.getInt("horasDesdoble");
                String especialidad = resultados.getString("especialidadProfesorado");
                int codigoCicloAsig = resultados.getInt("codigoCicloFormativo");


                // CONVERSIONES DE LOS VALORES ENUM, A OBJETOS
                Curso cursoAsig = getCurso(curso);
                EspecialidadProfesorado especialidadAsig = getEspecialidadProfesorado(especialidad);

                // Crear obj inventado CicloFormativo, menos el código, solo para buscarlo y obtener
                // el obj real almacenado en la BD:
                Grado gradoInventadoCiclo = new GradoE("grado inventado", 1, 1);
                CicloFormativo cicloInventado = new CicloFormativo(codigoCicloAsig, "familia", gradoInventadoCiclo, "ciclo inventado", 3);
                // DUDA: si getInstancia() no debe ser público, entonces debería llamar quizás a un método del controlador
                // para buscar este ciclo por código?
                CicloFormativo cicloAsig = CiclosFormativos.getInstancia().buscar(cicloInventado);


                Asignatura asignatura = new Asignatura(codigoAsig, nombreAsig, horasAnualesAsig, cursoAsig,
                        horasDesdobleAsig, especialidadAsig, cicloAsig);
                listadoAsignaturas.add(asignatura);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener asignaturas de la base de datos." + e.getMessage());
        }
        // 4º Cerrar recursos
        finally {
            try {
                if(resultados!=null) {
                    resultados.close();
                }
                if(sentencia!=null){
                    sentencia.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
            }
        }

        return listadoAsignaturas;
    }



    @Override
    public void insertar (Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null){
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }


        // 1. INSERTAR EN LA MEMORIA:
        if (coleccionAsignaturas.contains(asignatura)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }
        coleccionAsignaturas.add(asignatura);


        // 2. INSERTAR EN LA BD:
        // Verificar si la asignatura ya existe en la base de datos
        if (buscar(asignatura) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }

        String codigoAsig = asignatura.getCodigo();
        String nombreAsig = asignatura.getNombre();
        int horasAnualesAsig = asignatura.getHorasAnuales();
        Curso cursoAsig = asignatura.getCurso();
        int horasDesdobleAsig = asignatura.getHorasDesdoble();
        EspecialidadProfesorado especialidadProfesoradoAsig = asignatura.getEspecialidadProfesorado();
        int codCicloFormativoAsig = asignatura.getCicloFormativo().getCodigo();

        // Manejo de curso:
        String curso = null;
        switch (cursoAsig) {
            case Curso.PRIMERO:
                curso = "primero";
                break;
            case Curso.SEGUNDO:
                curso = "segundo";
                break;
        }
        // Manejo de especialidad:
        String especialidadProfesorado = null;
        switch (especialidadProfesoradoAsig) {
            case EspecialidadProfesorado.INFORMATICA:
                especialidadProfesorado = "informatica";
                break;
            case EspecialidadProfesorado.SISTEMAS:
                especialidadProfesorado = "sistemas";
                break;
            case EspecialidadProfesorado.FOL:
                especialidadProfesorado = "fol";
                break;
        }

        PreparedStatement psentencia = null;

        try {
            // Consulta para sentencia preparada
            String consulta = "INSERT INTO asignatura(codigo, nombre, horasAnuales, curso, horasDesdoble, " +
                    "especialidadProfesorado, codigoCicloFormativo) VALUES(?, ?, ?, ?, ?, ?, ?)";
            // Crear sentencia preparada (datos introducidos por usuario), a partir de conexion, con consulta
            psentencia = conexion.prepareStatement(consulta);

            // Valores para la consulta
            psentencia.setString(1, codigoAsig);
            psentencia.setString(2, nombreAsig);
            psentencia.setInt(3, horasAnualesAsig);
            psentencia.setString(4, curso);
            psentencia.setInt(5, horasDesdobleAsig);
            psentencia.setString(6, especialidadProfesorado);
            psentencia.setInt(7, codCicloFormativoAsig);


            // Se ejecuta la consulta de la sentencia preparada
            int filasInsertadas = psentencia.executeUpdate();
            if (filasInsertadas == 0){
                System.out.println("No se pudo insertar la asignatura en la base de datos.");
            }
            System.out.println("Inserción de asignatura con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al insertar asignatura en la base de datos." + e.getMessage());
        }

        finally {
            try {
                if(psentencia!=null){
                    psentencia.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
            }
        }

    }


    @Override
    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null){
            throw new NullPointerException("Asignatura nula no puede buscarse.");
        }

        String codigoBusqueda = asignatura.getCodigo();
        PreparedStatement psentencia = null;
        ResultSet asignaturaResult = null;
        Asignatura asignaturaEncontrada = null;

        try {

            // Consulta para sentencia preparada
            String consulta = "SELECT * FROM asignatura WHERE codigo = ?";
            // Crear sentencia preparada (datos introducidos por usuario), a partir de conexion, con consulta
            psentencia = conexion.prepareStatement(consulta);

            // Valores para la consulta
            psentencia.setString(1, codigoBusqueda);

            // Se ejecuta la consulta de la sentencia preparada
            asignaturaResult = psentencia.executeQuery();

            // Si hay resultados de la consulta, existe y crea obj Alumno para devolverlo al programa
            if (asignaturaResult.next()){
                String codigo = asignaturaResult.getString("codigo");
                String nombre =  asignaturaResult.getString("nombre");
                int horasAnuales =  asignaturaResult.getInt("horasAnuales");
                String curso = asignaturaResult.getString("curso");
                int horasDesdoble = asignaturaResult.getInt("horasDesdoble");
                String especialidadProfesorado = asignaturaResult.getString("especialidadProfesorado");
                int codigoCicloFormativo = asignaturaResult.getInt("codigoCicloFormativo");


                // CONVERSIONES DE LOS VALORES ENUM, A OBJETOS
                Curso cursoAsig = getCurso(curso);
                EspecialidadProfesorado especialidadAsig = getEspecialidadProfesorado(especialidadProfesorado);

                // Crear obj inventado CicloFormativo, menos el código, solo para buscarlo y obtener
                // el obj real almacenado en la BD:
                Grado gradoInventadoCiclo = new GradoE("grado inventado", 1, 1);
                CicloFormativo cicloInventado = new CicloFormativo(codigoCicloFormativo, "familia", gradoInventadoCiclo, "ciclo inventado", 3);
                // DUDA: si getInstancia() no debe ser público, entonces debería llamar quizás a un método del controlador
                // para buscar este ciclo por código?
                CicloFormativo cicloAsig = CiclosFormativos.getInstancia().buscar(cicloInventado);


                asignaturaEncontrada = new Asignatura(codigo, nombre,horasAnuales, cursoAsig, horasDesdoble,
                        especialidadAsig, cicloAsig);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar asignatura en la base de datos." + e.getMessage());
        }

        finally {
            try {
                if(psentencia!=null){
                    psentencia.close();
                }
                if(asignaturaResult!=null){
                    asignaturaResult.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
            }
        }

        return asignaturaEncontrada;

    }


    @Override
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }


        // Verificar si la asignatura existe en LA MEMORIA
        if (!coleccionAsignaturas.contains(asignatura)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
        }

        // Verificar si la asignatura existe en LA BD
        if (buscar(asignatura) == null) {
            throw new OperationNotSupportedException("ERROR: No existe una asignatura con ese código.");
        }

        // Verificar que la asignatura no está matriculada
        ResultSet resultadoVerif = null;
        PreparedStatement psVerif = null;
        try {
            String consultaVerifMatriculada = "SELECT * FROM asignaturasMatricula WHERE codigo = ? LIMIT 1";
            psVerif = conexion.prepareStatement(consultaVerifMatriculada);
            psVerif.setString(1, asignatura.getCodigo());

            resultadoVerif = psVerif.executeQuery();

            if (resultadoVerif.next()){
                throw new OperationNotSupportedException("La asignatura no puede ser borrada si se encuentra en una matrícula.");
            }

        } catch (SQLException e) {
            System.out.println("Error al verificar que la asignatura se encuentre en una matrícula");
        }




        // 1. BORRAR EN LA MEMORIA:
        coleccionAsignaturas.remove(asignatura);


        // 2. BORRAR EN LA BD:
        String codigo = asignatura.getCodigo();
        PreparedStatement psentencia = null;

        try {
            // Consulta para sentencia preparada
            String consulta = "DELETE FROM asignatura WHERE codigo = ?";
            // Crear sentencia preparada (datos introducidos por usuario), a partir de conexion, con consulta
            psentencia = conexion.prepareStatement(consulta);

            // Valores para la consulta
            psentencia.setString(1, codigo);

            // Se ejecuta la consulta de la sentencia preparada
            int filaBorrada = psentencia.executeUpdate();
            if (filaBorrada == 0){
                System.out.println("No se pudo borrar la asignatura en la base de datos.");
            }
            System.out.println("Borrado de asignatura con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al borrar asignatura en la base de datos." + e.getMessage());
        }

        finally {
            try {
                if(psentencia!=null){
                    psentencia.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
            }
        }
    }
}
