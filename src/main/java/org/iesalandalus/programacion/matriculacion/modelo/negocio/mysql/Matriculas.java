package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.controlador.Controlador;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.cerrarConexion;
import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.establecerConexion;

public class Matriculas implements IMatriculas {
    //private List<Matricula> coleccionMatriculas;
    private Connection conexion = null;


    public Matriculas(){
        //this.coleccionMatriculas = new ArrayList<>();
        comenzar();
    }

    private static Matriculas instancia;
    static Matriculas getInstancia(){
        if (instancia == null){
            instancia = new Matriculas();
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


    @Override
    public int getTamano() {
        Statement sentencia = null;
        ResultSet resultados = null;

        int numMatriculas = 0;
        try {
            sentencia = conexion.createStatement();

            String consulta = "SELECT COUNT(*) AS num_matriculas FROM matricula";
            resultados = sentencia.executeQuery(consulta);

            // Obtener nº de matriculas
            if (resultados.next()) {
                numMatriculas = resultados.getInt("num_matriculas");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener número de matriculas en la base de datos." + e.getMessage());
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

        return numMatriculas;
    }



    /* El método getAsignaturasMatricula que a partir de un identificador de una matrícula,
    deberá devolver una lista de todas las asignaturas pertenecientes a dicha matrícula. */
    private List<Asignatura> getAsignaturasMatricula(int idMatricula) {
        List<Asignatura> listadoAsignaturasMatricula = new ArrayList<>();

        PreparedStatement psentencia = null;
        ResultSet resultados = null;

        try {
            String consulta = "SELECT a.* FROM asignatura a " +
                    "JOIN asignaturasMatricula am ON a.codigo = am.codigo " +
                    "WHERE am.idMatricula = ?";
            psentencia = conexion.prepareStatement(consulta);

            psentencia.setInt(1, idMatricula);

            resultados = psentencia.executeQuery();

            // Obtener resultados a partir de ResultSet:
            while (resultados.next()) {

                // atributos para el obj Asignatura
                String codigo = resultados.getString("codigo");
                String nombre = resultados.getString("nombre");
                int horasAnuales = resultados.getInt("horasAnuales");
                String curso = resultados.getString("curso");
                int horasDesdoble = resultados.getInt("horasDesdoble");
                String especialidadProfesorado = resultados.getString("especialidadProfesorado");
                int codigoCicloFormativo = resultados.getInt("codigoCicloFormativo");


                // CONVERSIONES DE LOS VALORES A OBJETOS
                // Manejo de curso, que es enum
                Curso cursoAsig = null;
                switch (curso) {
                    case "primero":
                        cursoAsig = Curso.PRIMERO;
                        break;
                    case "segundo":
                        cursoAsig = Curso.SEGUNDO;
                        break;
                }
                // Manejo de especialidadProfesorado, que es enum
                EspecialidadProfesorado especialidadAsig = null;
                switch (especialidadProfesorado) {
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
                // Manejo de cicloAsig
                Grado gradoInventadoCiclo = new GradoE("grado inventado", 1, 1);
                CicloFormativo cicloInventado = new CicloFormativo(codigoCicloFormativo, "familia", gradoInventadoCiclo, "ciclo inventado", 3);
                CicloFormativo cicloAsig = CiclosFormativos.getInstancia().buscar(cicloInventado);


                Asignatura asignatura = new Asignatura(codigo, nombre, horasAnuales, cursoAsig, horasDesdoble,
                        especialidadAsig, cicloAsig);

                listadoAsignaturasMatricula.add(asignatura);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener matrículas de la base de datos." + e.getMessage());
        }
        finally {
            try {
                if(resultados!=null) {
                    resultados.close();
                }
                if(psentencia!=null){
                    psentencia.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
            }
        }

        return listadoAsignaturasMatricula;
    }

/*
    private List<Asignatura> getAsignaturasMatricula(int idMatricula) {

        Matricula matriculaEncontrada = null;

        List<Asignatura> asignaturasMatricula = new ArrayList<>();;

        // quizas esto es innecesario
        // buscar si existe la matricula en memoria. Al insertar(), se inserta 1º en memoria, por lo que debería de
        // funcionar si llamo a este método en insertarAsignaturasMatricula(), el cual llamo en insertar()
        // durante la inserción en la BD posterior a la inserción en memoria.
        for (Matricula matricula : coleccionMatriculas){
            if (matricula != null && matricula.getIdMatricula() == idMatricula){
                matriculaEncontrada = matricula;
                break;
            }
        }

        // si no existe la matricula, da asignaturasMatricula vacía
        // si sí existe la matrícula (siempre contendrá coleccionAsignaturas con asignaturas), da asignaturasMatricula
        if (matriculaEncontrada != null) {
            for (Asignatura asignaturaMatr : matriculaEncontrada.getColeccionAsignaturas()) {
                if (asignaturaMatr != null) {
                    asignaturasMatricula.add(asignaturaMatr);
                }
            }
            // asignaturasMatricula = matriculaEncontrada.getColeccionAsignaturas()

            // return matriculaEncontrada.getColeccionAsignaturas()
        }

        return asignaturasMatricula;

    }
*/


    /* El método get deberá devolver una lista formada por todas las matrículas existentes en la base de datos
    ordenadas por fecha de matriculación en orden descendente (es decir, las matrículas más recientes primero)
    y en caso de que existan varias matrículas con la misma fecha de matriculación, deberá considerarse como
    segundo criterio de ordenación el nombre del alumno correspondiente a la matrícula. */
    @Override
    public List<Matricula> get() throws OperationNotSupportedException {
        List<Matricula> listadoMatriculas = new ArrayList<>();

        Statement sentencia = null;
        ResultSet resultados = null;

        try {
            // 1º Crear sentencia, a partir de conexion:
            sentencia = conexion.createStatement();

            // 2º Crear ResultSet con una consulta, a partir de sentencia:
            String consulta = "SELECT m.* " +
                                "FROM matricula m JOIN alumno a ON m.dni = a.dni " +
                                "ORDER BY m.fechaMatriculacion DESC, a.nombre ASC";
            resultados = sentencia.executeQuery(consulta);

            // 3º Obtener resultados a partir de ResultSet:
            System.out.println("Lista de matriculas existentes:");
            while (resultados.next()) {
                // atributos para el obj Matricula
                int idMatricula = resultados.getInt("idMatricula");
                String cursoAcademico = resultados.getString("cursoAcademico");
                LocalDate fechaMatriculacion = resultados.getDate("fechaMatriculacion").toLocalDate();
                Date fechaAnulacion = resultados.getDate("fechaAnulacion");
                // Manejar fechaAnulacion, que puede ser null
                LocalDate fechaAnulacionMatr = (fechaAnulacion != null) ? fechaAnulacion.toLocalDate() : null;
                String dni = resultados.getString("dni");

                // CONVERSIONES DE LOS VALORES A OBJETOS
                List<Asignatura> coleccionAsignaturas = getAsignaturasMatricula(idMatricula);

                LocalDate fechaNacInventada = LocalDate.of(2004, 3, 29);
                Alumno alumnoInventado = new Alumno("alumnoInventado", dni, "correoInventado@gmail.com", "989890989", fechaNacInventada);
                Alumno alumno = Alumnos.getInstancia().buscar(alumnoInventado);


                Matricula matricula = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion,
                                                    alumno, coleccionAsignaturas);
                // Insertar fechaAnulacion, si hay
                if(fechaAnulacion != null){
                    matricula.setFechaAnulacion(fechaAnulacionMatr);
                }

                listadoMatriculas.add(matricula);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener matrículas de la base de datos." + e.getMessage());
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

        return listadoMatriculas;
    }



    @Override
    public void insertar (Matricula matricula) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (matricula == null){
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }
        // Verificar si la asignatura ya existe en la base de datos
        if (buscar(matricula) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese código.");
        }


        int idMat = matricula.getIdMatricula();
        String cursoAcademicoMat = matricula.getCursoAcademico();
        LocalDate fechaMat = matricula.getFechaMatriculacion();
        String alumnoDniMat = matricula.getAlumno().getDni();
        LocalDate fechaAnulacionMat = matricula.getFechaAnulacion();
        List<Asignatura> asignaturasMat = matricula.getColeccionAsignaturas();

        PreparedStatement psentencia = null;

        try {
            // Consulta para sentencia preparada
            String consulta = "INSERT INTO matricula(idMatricula, cursoAcademico, fechaMatriculacion, fechaAnulacion, dni) " +
                                "VALUES(?, ?, ?, ?, ?)";
            // Crear sentencia preparada (datos introducidos por usuario), a partir de conexion, con consulta
            psentencia = conexion.prepareStatement(consulta);

            // Valores para la consulta
            psentencia.setInt(1, idMat);
            psentencia.setString(2, cursoAcademicoMat);
            psentencia.setDate(3, Date.valueOf(fechaMat));
            // Manejar fechaAnulacion, que puede ser null
            if (fechaAnulacionMat != null) {
                psentencia.setDate(4, Date.valueOf(fechaAnulacionMat));
            } else {
                psentencia.setNull(4, Types.DATE);
            }
            psentencia.setString(5, alumnoDniMat);

            // Se ejecuta la consulta de la sentencia preparada
            int filasInsertadas = psentencia.executeUpdate();
            if (filasInsertadas == 0){
                System.out.println("No se pudo insertar la matrícula en la base de datos.");
            }

            // Si se inserta la matrícula, hacer INSERT en la tabla asignaturasMatricula
            insertarAsignaturasMatricula(idMat, asignaturasMat);

        } catch (SQLException e) {
            System.out.println("Error al insertar matrícula en la base de datos." + e.getMessage());
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


    /* El método insertarAsignaturasMatricula que a partir de un identificador de matrícula y una lista de
    asignaturas pertenecientes a la matrícula, deberá realizar las inserciones correspondientes en la tabla
    asignaturasMatricula de la base de datos. */
    private void insertarAsignaturasMatricula(int idMatricula, List<Asignatura> coleccionAsignaturas){
        PreparedStatement psentencia = null;

        try {
            // itera sobre la coleccionAsignaturas, donde en cada iteracion se almacena el codigo de asignatura
            // en una variable y se hace insert con idMatricula y esa variable

            int filasInsertadas=0;
            for (Asignatura asignatura : coleccionAsignaturas){
                String codigoAsig = asignatura.getCodigo();

                String consulta = "INSERT INTO asignaturasMatricula(idMatricula, codigo) VALUES (?, ?)";
                psentencia = conexion.prepareStatement(consulta);

                psentencia.setInt(1, idMatricula);
                psentencia.setString(2, codigoAsig);


                filasInsertadas += psentencia.executeUpdate();
            }

            if (filasInsertadas == 0){
                System.out.println("No se pudo insertar la colección de asignaturas de la matrícula en la base de datos.");
            } //else System.out.println("Inserción de colección de asignaturas de la matrícula con éxito.");

        } catch(SQLException e) {
            System.out.println("Error al insertar colección de asignaturas de la matrícula en la base de datos." + e.getMessage());
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
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException{
        if (matricula == null){
            throw new NullPointerException("Matrícula nula no puede buscarse.");
        }


        int idBusqueda = matricula.getIdMatricula();
        PreparedStatement psentencia = null;
        ResultSet matriculaResult = null;
        Matricula matriculaEncontrada = null;

        try {

            // Consulta para sentencia preparada
            String consulta = "SELECT * FROM matricula WHERE idMatricula = ?";
            // Crear sentencia preparada (datos introducidos por usuario), a partir de conexion, con consulta
            psentencia = conexion.prepareStatement(consulta);

            // Valores para la consulta
            psentencia.setInt(1, idBusqueda);

            // Se ejecuta la consulta de la sentencia preparada
            matriculaResult = psentencia.executeQuery();

            // Si hay resultados de la consulta, existe y crea obj Matricula para devolverlo al programa
            if (matriculaResult.next()){
                int idMatricula = matriculaResult.getInt("idMatricula");
                String cursoAcademico = matriculaResult.getString("cursoAcademico");
                LocalDate fechaMatriculacion = matriculaResult.getDate("fechaMatriculacion").toLocalDate();;
                Date fechaAnulacion = matriculaResult.getDate("fechaAnulacion");
                // Manejar fechaAnulacion, que puede ser null
                LocalDate fechaAnulacionMatr = (fechaAnulacion != null) ? fechaAnulacion.toLocalDate() : null;
                String dni = matriculaResult.getString("dni");

                // Obtener la lista de asignaturas de esa matrícula
                List<Asignatura> coleccionAsignaturas = getAsignaturasMatricula(idMatricula);

                // Obtener el alumno con ese dni:
                // Crear obj inventado Alumno, menos el dni, solo para buscarlo y obtener
                // el obj real almacenado en la BD:
                LocalDate fechaNacInventada= LocalDate.of(2004, 11, 22);
                Alumno alumnoInventado = new Alumno("alumno inventado", dni, "correoinventado@gmail.com", "909890978", fechaNacInventada);
                Alumno alumno = Alumnos.getInstancia().buscar(alumnoInventado);


                matriculaEncontrada = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion, alumno,
                                                    coleccionAsignaturas);
                // Manejar fechaAnulacion, que puede ser null
                if(fechaAnulacionMatr != null){
                    matriculaEncontrada.setFechaAnulacion(fechaAnulacionMatr);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar asignatura en la base de datos." + e.getMessage());
        }

        finally {
            try {
                if(psentencia!=null){
                    psentencia.close();
                }
                if(matriculaResult!=null){
                    matriculaResult.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
            }
        }

        return matriculaEncontrada;
    }


    @Override
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null){
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }
        // Verificar si la matricula existe en la base de datos
        if (buscar(matricula) == null) {
            throw new OperationNotSupportedException("ERROR: No existe una matrícula con ese código.");
        }


        int idMat = matricula.getIdMatricula();
        PreparedStatement psentencia = null;

        try {
            // Consulta para sentencia preparada
            String consulta = "DELETE FROM matricula WHERE idMatricula = ?";
            // Crear sentencia preparada (datos introducidos por usuario), a partir de conexion, con consulta
            psentencia = conexion.prepareStatement(consulta);
            // Valores para la consulta
            psentencia.setInt(1, idMat);

            // Se ejecuta la consulta de la sentencia preparada
            int filaBorrada = psentencia.executeUpdate();
            if (filaBorrada == 0){
                System.out.println("No se pudo borrar la matrícula en la base de datos.");
            }
            System.out.println("Borrado de matrícula con éxito.");
            // Si se borró la fila de la tabla matricula, se borran las filas de la tabla asignaturasMatricula
            // que tienen su idMatricula como fk, pues es fk on delete cascade

        } catch (SQLException e) {
            System.out.println("Error al borrar matrícula en la base de datos." + e.getMessage());
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



    /* El método get de un alumno deberá devolver una lista de todas las matrículas del alumno pasado como parámetro. */
    @Override
    public List<Matricula> get(Alumno alumno) throws OperationNotSupportedException {
        List<Matricula> listadoMatriculasAlumno = new ArrayList<>();

        String dniAlumno = alumno.getDni();

        PreparedStatement psentencia = null;
        ResultSet resultados = null;

        try {
            String consulta = "SELECT * FROM matricula WHERE dni = ? ";
            psentencia = conexion.prepareStatement(consulta);

            psentencia.setString(1, dniAlumno);

            // Ejecuta la consulta y obtiene el ResultSet
            resultados = psentencia.executeQuery();

            while (resultados.next()) {
                // atributos para el obj Matricula
                int idMatricula = resultados.getInt("idMatricula");
                String cursoAcademico = resultados.getString("cursoAcademico");
                LocalDate fechaMatriculacion = resultados.getDate("fechaMatriculacion").toLocalDate();
                Date fechaAnulacion = resultados.getDate("fechaAnulacion");
                // Manejar fechaAnulacion, que puede ser null
                LocalDate fechaAnulacionMatr = (fechaAnulacion != null) ? fechaAnulacion.toLocalDate() : null;

                // CONVERSIONES DE LOS VALORES A OBJETOS
                List<Asignatura> coleccionAsignaturas = getAsignaturasMatricula(idMatricula);


                Matricula matricula = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion,
                        alumno, coleccionAsignaturas);
                // Insertar fechaAnulacion si hay
                if(fechaAnulacionMatr != null){
                    matricula.setFechaAnulacion(fechaAnulacionMatr);
                }
                listadoMatriculasAlumno.add(matricula);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener matrículas del alumno de la base de datos." + e.getMessage());
        }
        finally {
            try {
                if(resultados!=null) {
                    resultados.close();
                }
                if(psentencia!=null){
                    psentencia.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
            }
        }

        return listadoMatriculasAlumno;
    }


    @Override
    public List<Matricula> get(String cursoAcademico) throws OperationNotSupportedException {
        List<Matricula> listadoMatriculasCurso = new ArrayList<>();

        PreparedStatement psentencia = null;
        ResultSet resultados = null;

        try {
            String consulta = "SELECT * FROM matricula WHERE cursoAcademico = ? ";
            psentencia = conexion.prepareStatement(consulta);

            psentencia.setString(1, cursoAcademico);

            // Ejecuta la consulta y obtiene el ResultSet
            resultados = psentencia.executeQuery();

            while (resultados.next()) {
                // atributos para el obj Matricula
                int idMatricula = resultados.getInt("idMatricula");
                //String cursoAcademico = resultados.getString("cursoAcademico");
                LocalDate fechaMatriculacion = resultados.getDate("fechaMatriculacion").toLocalDate();
                Date fechaAnulacion = resultados.getDate("fechaAnulacion");
                // Manejar fechaAnulacion, que puede ser null
                LocalDate fechaAnulacionMatr = (fechaAnulacion != null) ? fechaAnulacion.toLocalDate() : null;
                String dni = resultados.getString("dni");

                // CONVERSIONES DE LOS VALORES A OBJETOS
                List<Asignatura> coleccionAsignaturas = getAsignaturasMatricula(idMatricula);
                LocalDate fechaNacInventada = LocalDate.of(2004, 3, 29);
                Alumno alumnoInventado = new Alumno("alumnoInventado", dni, "correoInventado@gmail.com", "989890989", fechaNacInventada);
                Alumno alumno = Alumnos.getInstancia().buscar(alumnoInventado);

                Matricula matricula = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion,
                        alumno, coleccionAsignaturas);
                // Manejar fechaAnulacion, que puede ser null
                if(fechaAnulacionMatr != null){
                    matricula.setFechaAnulacion(fechaAnulacionMatr);
                }
                listadoMatriculasCurso.add(matricula);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener matrículas del curso académico de la base de datos." + e.getMessage());
        }
        finally {
            try {
                if(resultados!=null) {
                    resultados.close();
                }
                if(psentencia!=null){
                    psentencia.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
            }
        }

        return listadoMatriculasCurso;
    }


    @Override
    public List<Matricula> get(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        List<Matricula> listadoMatriculasCiclo = new ArrayList<>();

        int codigoCiclo = cicloFormativo.getCodigo();

        PreparedStatement psentencia = null;
        ResultSet resultados = null;

        try {
            String consulta = "SELECT m.* FROM matricula m " +
                                "JOIN asignaturasMatricula am ON m.idMatricula = am.idMatricula " +
                                "JOIN asignatura a ON am.codigo = a.codigo " +
                                "WHERE a.codigoCicloFormativo = ? ";
            psentencia = conexion.prepareStatement(consulta);

            psentencia.setInt(1, codigoCiclo);

            // Ejecuta la consulta y obtiene el ResultSet
            resultados = psentencia.executeQuery();

            while (resultados.next()) {
                // atributos para el obj Matricula
                int idMatricula = resultados.getInt("idMatricula");
                String cursoAcademico = resultados.getString("cursoAcademico");
                LocalDate fechaMatriculacion = resultados.getDate("fechaMatriculacion").toLocalDate();
                Date fechaAnulacion = resultados.getDate("fechaAnulacion");
                // Manejar fechaAnulacion, que puede ser null
                LocalDate fechaAnulacionMatr = (fechaAnulacion != null) ? fechaAnulacion.toLocalDate() : null;
                String dni = resultados.getString("dni");

                // CONVERSIONES DE LOS VALORES A OBJETOS
                List<Asignatura> coleccionAsignaturas = getAsignaturasMatricula(idMatricula);
                LocalDate fechaNacInventada = LocalDate.of(2004, 3, 29);
                Alumno alumnoInventado = new Alumno("alumnoInventado", dni, "correoInventado@gmail.com", "989890989", fechaNacInventada);
                Alumno alumno = Alumnos.getInstancia().buscar(alumnoInventado);

                Matricula matricula = new Matricula(idMatricula, cursoAcademico, fechaMatriculacion,
                        alumno, coleccionAsignaturas);
                // Manejar fechaAnulacion, que puede ser null
                if(fechaAnulacionMatr != null){
                    matricula.setFechaAnulacion(fechaAnulacionMatr);
                }
                listadoMatriculasCiclo.add(matricula);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener matrículas del curso académico de la base de datos." + e.getMessage());
        }
        finally {
            try {
                if(resultados!=null) {
                    resultados.close();
                }
                if(psentencia!=null){
                    psentencia.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
            }
        }

        return listadoMatriculasCiclo;
    }
}
