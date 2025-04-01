package org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.CicloFormativo;
import org.iesalandalus.programacion.matriculacion.modelo.dominio.Matricula;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL;

import javax.naming.OperationNotSupportedException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.cerrarConexion;
import static org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.utilidades.MySQL.establecerConexion;

public class Alumnos implements IAlumnos {
    private List<Alumno> coleccionAlumnos;
    private Connection conexion = null;

    //DUDA: constructor no debe ser privado? (patrón sing.)
    public Alumnos(){
        this.coleccionAlumnos = new ArrayList<>();
        comenzar();
    }


    /* Implementa el patrón singlenton a través del atributo instancia y del método getInstancia
    que si el atributo instancia es nulo devolverá una instancia de la clase Alumnos,
    y si no es nulo devolverá el valor del atributo instancia. */
    private static Alumnos instancia;
    // DUDA: en el diagrama pone que debe ser privado
    public static Alumnos getInstancia(){
        if (instancia == null){
            instancia = new Alumnos();
        }
        return instancia;
    }

    /* Los cuerpos de comenzar() y terminar() en las cuatro clases deberán llamar respectivamente
    a los métodos establecerConexion y cerrarConexion de la clase MySQL. */
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



    /* El método getTamano deberá devolver el número de alumnos existentes en la base de datos. */
    @Override
    public int getTamano() {
        /*
        // size() devuelve el tamaño de la lista
        return coleccionAlumnos.size();
        */

        Statement sentencia = null;
        ResultSet resultados = null;

        int numAlumnos = 0;
        try {
            sentencia = conexion.createStatement();

            String consulta = "SELECT COUNT(*) AS num_alumnos FROM alumno";
            resultados = sentencia.executeQuery(consulta);

            // Obtener nº de alumnos
            if (resultados.next()) {
                numAlumnos = resultados.getInt("num_alumnos");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener número de alumnos en la base de datos." + e.getMessage());
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

        return numAlumnos;

    }

    /* El método get deberá devolver una lista formada por todos los alumnos existentes en la
    base de datos ordenados por dni. */
    @Override
    public List<Alumno> get() {
        // TO10: ya no es necesario, obtenemos datos de la BD
        /* return copiaprofundaAlumnos(); */


        List<Alumno> listadoAlumnos = new ArrayList<>();

        Statement sentencia = null;
        ResultSet resultados = null;

        try {
            // 1º Crear sentencia, a partir de conexion:
            sentencia = conexion.createStatement();

            // 2º Crear ResultSet con una consulta, a partir de sentencia:
            String consulta = "SELECT * FROM alumno ORDER BY dni";
            resultados = sentencia.executeQuery(consulta);

            // 3º Obtener resultados a partir de ResultSet:
            System.out.println("Lista de alumnos existentes:");
            while (resultados.next()) {
                String nombre= resultados.getString("nombre");
                String telefono = resultados.getString("telefono");
                String correo = resultados.getString("correo");
                String dni = resultados.getString("dni");
                // fechaNacimiento en la base de datos es de tipo DATE.
                // Al ser fechaNacimiento NOT NULL, no necesita un manejo adicional.
                LocalDate fechaNacimiento = resultados.getDate("fechaNacimiento").toLocalDate();

                Alumno alumno = new Alumno(nombre, dni, correo, telefono, fechaNacimiento);
                listadoAlumnos.add(alumno);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener alumnos de la base de datos." + e.getMessage());
        }
        // 4º Cerrar recursos
        // Si ocurre un error en sentencia = conexion.createStatement();
        // Entonces sentencia = null, y cuando finally intente hacer sentencia.close();
        // Se producirá un NullPointerException.
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

        return listadoAlumnos;
    }



    // TO10: ya no es necesario, obtenemos datos de la BD
    // Copia profunda de lista colecciónAlumnos
/*
    private List<Alumno> copiaProfundaAlumnos(){
        // Crear una lista copia de coleccionAlumnos (lista original)
        List<Alumno> copiaProfunda = new ArrayList<>();

        for (Alumno alumno : coleccionAlumnos) {
            if (alumno != null) {
                // Usamos el constructor copia de Alumno
                // add() añade el elemento al conjunto. Si ya está, devuelve false y no lo añade.
                copiaProfunda.add(new Alumno(alumno));
            }
        }
        return  copiaProfunda;
    }
/*  Con stream:
    private List<Alumno> copiaProfundaAlumnos(){
        List<Alumno> copiaProfunda = coleccionAlumnos.stream()
                .filter(alumno -> alumno != null)
                .map(Alumno::new)   // Crea una copia de cada objeto Alumno usando el constructor copia.
                                    // Usa el constructor copia de clase Alumno porque la lista de objetos obtenidos
                                    // con coleccionAlumnos.stream().filter() son tipo Alumno y no nulos
                .collect(Collectors.toList());  //Crea con esos objetos copiados una lista mutable
                                                // (puedes borrar/añadir después objetos)
        return copiaProfunda;
    }
*/



    /* El método insertar deberá insertar un nuevo alumno en la base de datos. */
    @Override
    // Insertar alumno no nulo al final de colecciónAlumnos, sin repetidos, si hay espacio.
    public void insertar (Alumno alumno) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (alumno == null){
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }


        // 1. INSERTAR EN LA MEMORIA:
        if (coleccionAlumnos.contains(alumno)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }
        coleccionAlumnos.add(alumno);


        // 2. INSERTAR EN LA BD:
        String nombre = alumno.getNombre();
        String telefono= alumno.getTelefono();
        String correo = alumno.getCorreo();
        String dni = alumno.getDni();
        LocalDate fechaNacimiento = alumno.getFechaNacimiento();

        PreparedStatement psentencia = null;

        try {
            // Verificar si el alumno ya existe en la base de datos
            if (buscar(alumno) != null) {
                throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
            }

            // Consulta para sentencia preparada
            String consulta = "INSERT INTO alumno(nombre, telefono, correo, dni, fechaNacimiento) VALUES(?, ?, ?, ?, ?)";
            // Crear sentencia preparada (datos introducidos por usuario), a partir de conexion, con consulta
            psentencia = conexion.prepareStatement(consulta);

            // Valores para la consulta
            psentencia.setString(1, nombre);
            psentencia.setString(2, telefono);
            psentencia.setString(3, correo);
            psentencia.setString(4, dni);
            psentencia.setDate(5, Date.valueOf(fechaNacimiento));

            // Se ejecuta la consulta de la sentencia preparada
            int filasInsertadas = psentencia.executeUpdate();
            if (filasInsertadas == 0){
                System.out.println("No se pudo insertar el alumno en la base de datos.");
            }
            System.out.println("Inserción de alumno con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al insertar alumno en la base de datos." + e.getMessage());
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



    /* El método buscar deberá devolver el resultado de encontrar en la base de datos al alumno pasado como parámetro. */
    @Override
    //Busca un alumno. Si existe devuelve el alumno, si no existe devuelve null
    public Alumno buscar(Alumno alumno) {
        if (alumno == null){
            throw new NullPointerException("Alumno nulo no puede buscarse.");
        }

        // TO10: ya no es necesario, obtenemos datos de la BD
        /*// Si existe el alumno, devuelve copia de este
        int indice;
        if (coleccionAlumnos.contains(alumno)) {
            // indexOf() devuelve el índice donde se encuentra el objeto indicado
            indice=coleccionAlumnos.indexOf(alumno);
            // get() devuelve el objeto exacto que se encuentra en el índice indicado
            // alumno ahora tiene exactamente el valor del objeto que se encuentra en la lista
            alumno = coleccionAlumnos.get(indice);
            return new Alumno(alumno);
        }
        // Si no existe, devuelve nulo
        else return null;*/


        String dniBuqueda = alumno.getDni();
        PreparedStatement psentencia = null;
        ResultSet alumnoResult = null;
        Alumno alumnoEncontrado = null;

        try {

            // Consulta para sentencia preparada
            String consulta = "SELECT * FROM alumno WHERE dni = ?";
            // Crear sentencia preparada (datos introducidos por usuario), a partir de conexion, con consulta
            psentencia = conexion.prepareStatement(consulta);

            // Valores para la consulta
            psentencia.setString(1, dniBuqueda);

            // Se ejecuta la consulta de la sentencia preparada
            alumnoResult = psentencia.executeQuery();

            // Si hay resultados de la consulta, existe y crea obj Alumno para devolverlo al programa
            if (alumnoResult.next()){
                String nombre = alumnoResult.getString("nombre");
                String telefono = alumnoResult.getString("telefono");
                String correo = alumnoResult.getString("correo");
                String dni = alumnoResult.getString("dni");
                LocalDate fechaNacimiento = alumnoResult.getDate("fechaNacimiento").toLocalDate();

                alumnoEncontrado = new Alumno(nombre,dni,correo,telefono,fechaNacimiento);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar alumno en la base de datos." + e.getMessage());
        }

        finally {
            try {
                if(psentencia!=null){
                    psentencia.close();
                }
                if(alumnoResult!=null){
                    alumnoResult.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar los recursos.");
            }
        }

        return alumnoEncontrado;

    }


    /* El método borrar deberá eliminar de la base de datos al alumno pasado como parámetro. */
    @Override
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null){
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }


        // Verificar que existe EN MEMORIA
        if (!coleccionAlumnos.contains(alumno)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
        }

        // Verificar si el alumno existe EN BD
        if (buscar(alumno) == null) {
            throw new OperationNotSupportedException("ERROR: No existe un alumno con ese dni.");
        }

        // Verificar si el alumno está matriculado en una matrícula EN BD
        List<Matricula> matriculasVerif = Matriculas.getInstancia().get(alumno);
        if (!matriculasVerif.isEmpty()){
            throw new OperationNotSupportedException("El alumno no puede ser borrado si se encuentra matriculado.");
        }


        // 1. BORRAR EN LA MEMORIA:
        coleccionAlumnos.remove(alumno);


        // 2. BORRAR EN LA BD:
        String dni = alumno.getDni();
        PreparedStatement psentencia = null;

        try {
            // Consulta para sentencia preparada
            String consulta = "DELETE FROM alumno WHERE dni = ?";
            // Crear sentencia preparada (datos introducidos por usuario), a partir de conexion, con consulta
            psentencia = conexion.prepareStatement(consulta);

            // Valores para la consulta
            psentencia.setString(1, dni);

            // Se ejecuta la consulta de la sentencia preparada
            int filaBorrada = psentencia.executeUpdate();
            if (filaBorrada == 0){
                System.out.println("No se pudo borrar al alumno en la base de datos.");
            }
            System.out.println("Borrado de alumno con éxito.");

        } catch (SQLException e) {
            System.out.println("Error al borrar alumno en la base de datos." + e.getMessage());
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






/*
    public int getCapacidad() { return capacidad; }
*/

/*
    // Busca y devuelve el índice de un alummo, devuelve -1 si no existe
    private int buscarIndice(Alumno alumno) {
        // Variable que se devuelve si no se encuentra
        int noExisteAlumno = -1;
        // Recorre array
        for (int i = 0; i < coleccionAlumnos.length; i++) {
            // Si en una posición, no nula, hay alumno = al alumno (el que buscamos)
            if (coleccionAlumnos[i] != null && coleccionAlumnos[i].equals(alumno)) {
                // SI ENCONTRÓ ALUMNO: Devuelve su indice
                return i;
            }
        }
        // Si al final no hay alumno = al alumno (el que buscamos)
        // NO ENCONTRÓ ALUMNO: devuelve -1
        return noExisteAlumno;
    }
*/

/*
    //APLICAR EN METODOS DONDE NO TIENEN COMO FIN MODIFICAR EL TAMAÑO ACTUAL DEL ARRAY
    private boolean tamanoSuperado(int indice){
        // Si supera tamaño actual del array, devuelve true
        if (indice >= getTamano()) {
            return true;
        } else return false;
    }
*/

/*
    private boolean capacidadSuperado(int indice){
        // Si supera capacidad máxima del array, devuelve true
        if (tamano >= capacidad || indice>=capacidad) {
            return true;
        } else
            return false;
    }
*/

/*
    private void desplazarUnaPosicionHaciaIzquierda(int indice){
        int i;
        // El índice debe de ir alumno por alumno (.length-1 porque si el array es coleccionAlumnos[5],
        // existen hasta alumno[4] como máximo)
        for ( i = indice; i < coleccionAlumnos.length - 1; i++){

            // La info del alumno en posición superior [i+1] pasa a esa posición [i]
            coleccionAlumnos[i]=coleccionAlumnos[i+1];
        }
        // La última posición alcanzada ahora no tiene alumno, pues se desplazó. Se asigna nulo
        coleccionAlumnos[i]=null;
    }
*/


}
