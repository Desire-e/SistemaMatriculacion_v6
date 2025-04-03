package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.cerrarConexion;
import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.establecerConexion;

public class CiclosFormativos implements ICiclosFormativos {
    //private List<CicloFormativo> coleccionCiclosFormativos;
    private Connection conexion = null;


    public CiclosFormativos() {
        //this.coleccionCiclosFormativos = new ArrayList<>();
        comenzar();
    }


    private static CiclosFormativos instancia;
    static CiclosFormativos getInstancia(){
        if (instancia == null){
            instancia = new CiclosFormativos();
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


    /* El método getGrado que, dependiendo del parámetro de tipo String tipoGrado, deberá devolver un
    objeto de tipo GradoD o GradoE. que serán creados a partir del resto de parámetros pasados al método. */
    public Grado getGrado(String tipoGrado, String nombreGrado, int numAniosGrado, String modalidad, int numEdiciones){
        // las variables ya se validan al pasarlas al constructor que crea el GradoD o GradoE
        // tipoGrado al provenir de un dato enum not null en la BD solo podrá valer gradod o gradoe


        // CONVERSIONES DE LOS VALORES OPCIONALES (NO SON NOT NULL) Y ENUM, A OBJETOS:
        Modalidad modalidadCicloGD = null;
        Grado gradoCiclo = null;

        if (tipoGrado.equals("gradod") && modalidad != null){
            // Manejo de modalidad, que puede ser null y es enum
            if (modalidad.equals("presencial")){
                modalidadCicloGD = Modalidad.PRESENCIAL;
            } else
                modalidadCicloGD = Modalidad.SEMIPRESENCIAL;
            // Asigna tipo de grado
            gradoCiclo = new GradoD(nombreGrado, numAniosGrado, modalidadCicloGD);

        } else if (tipoGrado.equals("gradoe") && numEdiciones > 0 ){
            // Manejo de numEdiciones, que puede ser null y es int unsigned:
            // El resultado.getInt(), del método del que obtendremos el valor que se asignará
            // a este param numEdiciones, da 0 cuando el valor es NULL en la BD. Por ello no
            // hay que comprobar si es null y pasarlo a 0, viene ya dado.

            // Asigna tipo de grado
            gradoCiclo = new GradoE(nombreGrado, numAniosGrado, numEdiciones);
        }

        return gradoCiclo;
    }


    @Override
    public int getTamano() {
        Statement sentencia = null;
        ResultSet resultados = null;

        int numCiclos = 0;
        try {
            sentencia = conexion.createStatement();

            String consulta = "SELECT COUNT(*) AS num_ciclos FROM cicloFormativo";
            resultados = sentencia.executeQuery(consulta);

            // Obtener nº de ciclos
            if (resultados.next()) {
                numCiclos = resultados.getInt("num_ciclos");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener número de ciclos formativos en la base de datos." + e.getMessage());
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

        return numCiclos;
    }


    @Override
    public List<CicloFormativo> get(){
        List<CicloFormativo> listadoCiclos = new ArrayList<>();

        Statement sentencia = null;
        ResultSet resultados = null;

        try {
            // 1º Crear sentencia, a partir de conexion:
            sentencia = conexion.createStatement();

            // 2º Crear ResultSet con una consulta, a partir de sentencia:
            String consulta = "SELECT * FROM cicloFormativo ORDER BY nombre";
            resultados = sentencia.executeQuery(consulta);

            // 3º Obtener resultados a partir de ResultSet:
            while (resultados.next()) {
                // atributos para el obj CicloFormativo
                int codigoCiclo = resultados.getInt("codigo");
                String familiaProfesionalCiclo = resultados.getString("familiaProfesional");
                String nombreCiclo = resultados.getString("nombre");
                int horasCiclo = resultados.getInt("horas");

                // atributos para el obj Grado, que es atributo de CicloFormativo
                String grado = resultados.getString("grado");
                String nombreGrado = resultados.getString("nombreGrado");
                int numAniosGrado = resultados.getInt("numAniosGrado");
                // atributos opcionales para el objeto grado
                String modalidad = resultados.getString("modalidad");
                int numEdiciones = resultados.getInt("numEdiciones");


                // CONVERSIONES DE LOS VALORES OPCIONALES (NO SON NOT NULL) Y ENUM, A OBJETOS
                Grado gradoCiclo = getGrado(grado, nombreGrado, numAniosGrado, modalidad, numEdiciones);

                CicloFormativo ciclo = new CicloFormativo(codigoCiclo, familiaProfesionalCiclo, gradoCiclo, nombreCiclo, horasCiclo);
                listadoCiclos.add(ciclo);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener ciclos formativos de la base de datos." + e.getMessage());
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

        return listadoCiclos;
    }


    @Override
    public void insertar (CicloFormativo cicloFormativo) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }
        // Verificar si el ciclo ya existe en la base de datos
        if (buscar(cicloFormativo) != null) {
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }


        // Obtener valores del cicloFormativo a insertar...
        int codigo = cicloFormativo.getCodigo();
        String familiaProfesional = cicloFormativo.getFamiliaProfesional();
        Grado gradoCiclo = cicloFormativo.getGrado();
        String nombre = cicloFormativo.getNombre();
        int horas = cicloFormativo.getHoras();
        String nombreGrado = gradoCiclo.getNombre();
        int numAniosGrado = gradoCiclo.getNumAnios();

        // CONVERSIONES DE LOS OBJETOS, A VALORES OPCIONALES (NO SON NOT NULL) Y ENUM --- inverso a getGradp()
        // Asignar a gradoCiclo valores del enum de la BD, según sea gradoCiclo instancia GradoD o E,
        // y la existencia o no de los atributos concretos de esos grados concretos (modalidad, numEdiciones)
        String grado = null;
        String modalidad = null;
        int  numEdiciones = 0;
        if (gradoCiclo instanceof GradoD){
            grado = "gradod";

            // atr opcional, puede ser null si no es gradod
            Modalidad modalidadCicloGD = ((GradoD) gradoCiclo).getModalidad();
            // asigna valor al atr modalidad
            if (modalidadCicloGD.equals(Modalidad.PRESENCIAL)){
                modalidad = "presencial";
            } else
                modalidad = "semipresencial";

        } else if (gradoCiclo instanceof GradoE){
            grado = "gradoe";

            // atr opcional, puede ser null si no es gradoe
            int numEdicionesCicloGE = ((GradoE) gradoCiclo).getNumEdiciones();
            // asigna valor al atr numEdiciones
            numEdiciones = numEdicionesCicloGE;
        }


        PreparedStatement psentencia = null;

        try {
            // Consulta para sentencia preparada
            String consulta = "INSERT INTO cicloFormativo(codigo, familiaProfesional," +
                    "grado, nombre, horas, nombreGrado, numAniosGrado, modalidad, numEdiciones) " +
                    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
            // Crear sentencia preparada (datos introducidos por usuario), a partir de conexion, con consulta
            psentencia = conexion.prepareStatement(consulta);

            // Valores para la consulta
            psentencia.setInt(1, codigo);
            psentencia.setString(2, familiaProfesional);
            psentencia.setString(3, grado);
            psentencia.setString(4, nombre );
            psentencia.setInt(5, horas);
            psentencia.setString(6, nombreGrado);
            psentencia.setInt(7, numAniosGrado);

            // Manejar modalidad que puede ser NULL
            if (modalidad != null){
                psentencia.setString(8, modalidad);
            } else psentencia.setNull(8, Types.VARCHAR); // Guarda NULL en la BD
            // Manejar numEdiciones que puede ser NULL
            if (numEdiciones > 0) {
                psentencia.setInt(9, numEdiciones);
            } else psentencia.setNull(9, Types.INTEGER); // Guarda NULL en la BD


            // Se ejecuta la consulta de la sentencia preparada
            int filasInsertadas = psentencia.executeUpdate();
            if (filasInsertadas == 0){
                System.out.println("No se pudo insertar el ciclo formativo en la base de datos.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar ciclo formativo en la base de datos." + e.getMessage());
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
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null){
            throw new NullPointerException("Ciclo nulo no puede buscarse.");
        }


        int cicloBusqueda = cicloFormativo.getCodigo();
        CicloFormativo cicloEncontrado = null;

        PreparedStatement psentencia = null;
        ResultSet cicloResult = null;

        try {

            // Consulta para sentencia preparada
            String consulta = "SELECT * FROM cicloFormativo WHERE codigo = ?";
            // Crear sentencia preparada (datos introducidos por usuario), a partir de conexion, con consulta
            psentencia = conexion.prepareStatement(consulta);

            // Valores para la consulta
            psentencia.setInt(1, cicloBusqueda);

            // Se ejecuta la consulta de la sentencia preparada
            cicloResult = psentencia.executeQuery();

            // Si hay resultados de la consulta, existe y crea obj CicloFormativo para devolverlo al programa
            if (cicloResult.next()){
                int codigo = cicloResult.getInt("codigo");
                String familiaProfesional = cicloResult.getString("familiaProfesional");
                String nombre = cicloResult.getString("nombre");
                int horas = cicloResult.getInt("horas");

                String grado = cicloResult.getString("grado");
                String nombreGrado = cicloResult.getString("nombreGrado");
                int numAniosGrado = cicloResult.getInt("numAniosGrado");
                // manejo de modalidad, que puede ser null y es enum
                String modalidad = cicloResult.getString("modalidad");
                int numEdiciones = cicloResult.getInt("numEdiciones");


                // CONVERSIONES DE LOS VALORES OPCIONALES (NO SON NOT NULL) Y ENUM, A OBJETOS
                Grado gradoCiclo = getGrado(grado, nombreGrado, numAniosGrado, modalidad, numEdiciones);


                cicloEncontrado = new CicloFormativo(codigo, familiaProfesional, gradoCiclo, nombre, horas);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar ciclo formativo en la base de datos." + e.getMessage());
        }

        finally {
            try {
                if(cicloResult!=null){
                    cicloResult.close();
                }

                if(psentencia!=null){
                    psentencia.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
            }
        }

        return cicloEncontrado;
    }


    @Override
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }
        // Verificar si el ciclo existe EN LA BD
        if (buscar(cicloFormativo) == null) {
            throw new OperationNotSupportedException("ERROR: No existe un ciclo con ese código.");
        }
        // Verificar si el ciclo está matriculado en una matrícula EN BD
        List<Matricula> matriculasVerif = Matriculas.getInstancia().get(cicloFormativo);
        if (!matriculasVerif.isEmpty()){
            throw new OperationNotSupportedException("El ciclo formativo no puede ser borrado si es de una asignatura que se encuentra en una matrícula.");
        }


        int codigo = cicloFormativo.getCodigo();
        PreparedStatement psentencia = null;

        try {
            // Consulta para sentencia preparada
            String consulta = "DELETE FROM cicloFormativo WHERE codigo = ?";
            // Crear sentencia preparada (datos introducidos por usuario), a partir de conexion, con consulta
            psentencia = conexion.prepareStatement(consulta);

            // Valores para la consulta
            psentencia.setInt(1, codigo);

            // Se ejecuta la consulta de la sentencia preparada
            int filaBorrada = psentencia.executeUpdate();
            if (filaBorrada == 0){
                System.out.println("No se pudo borrar el ciclo formativo en la base de datos.");
            }

        } catch (SQLException e) {
            System.out.println("Error al borrar ciclo formativo en la base de datos." + e.getMessage());
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
