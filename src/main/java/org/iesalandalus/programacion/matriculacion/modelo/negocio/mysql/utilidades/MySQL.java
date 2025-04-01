package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    private static final String HOST = "dbsistemamatriculacion.cpa8y0y62j7t.us-east-1.rds.amazonaws.com"; // punto de enlace de la BD (copiar de AWS)
    private static final String ESQUEMA = "sistemamatriculacion"; // nombre de la BD (script.sql)
    private static final String USUARIO = "admin"; // usuario con el que te conectas a la BD (usuario que creaste en el script.sql)
    private static final String CONTRASENA = "sistemamatriculacion-2025"; // la password del usuario con el que te conectas a la BD
    private static Connection conexion = null;


    // evitar instanciación externa con constructor private
    private MySQL(){}


    // Crea el método establecerConexion que se encargará de realizar la conexión de la aplicación a
    // la base de datos alojada en la nube.
    public static Connection establecerConexion(){
        if (conexion == null){
            try{
                // Cargar driver de mysql
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Establece conexión con una BD. Puede lanzar SQLException
                conexion = DriverManager.getConnection("jdbc:mysql://"+HOST+":3306/"+ESQUEMA, USUARIO, CONTRASENA);

                System.out.println("Conexión establecida.");

            } catch(ClassNotFoundException e){
                System.out.println("Error cargando el Driver MySQL JDBC.");
                e.printStackTrace();
            } catch(SQLException e){
                System.out.println("Error realizando conexión. ");
                e.printStackTrace();
            }
        }

        return conexion;
    }


    //Crea el método cerrarConexion que se encargará de cerrar la conexión de la aplicación con la
    // base de datos alojada en la nube.
    public static void cerrarConexion(){

        if (conexion!=null) {    // si hay una conexión abierta (con valor DriverManager.getConnection())
            try {
                conexion.close();
                System.out.println("Conexión cerrada.");

                conexion = null;    // Evita que se intente cerrar dos veces la misma conexión
                                    // (al cerrarse, sigue teniendo el valor DriverManager.getConnection(),
                                    // pero de una conexión ya cerrada).

            } catch (SQLException e) {
                System.out.println("Error cerrando conexion.");
                e.printStackTrace();
            }
        } else System.out.println("No hay conexión abierta.");
    }

}
