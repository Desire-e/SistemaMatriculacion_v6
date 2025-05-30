package org.iesalandalus.programacion.matriculacion.modelo;

import org.iesalandalus.programacion.matriculacion.modelo.negocio.IFuenteDatos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.FuenteDatosFichero;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.FuenteDatosMemoria;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.mysql.FuenteDatosMySQL;

public enum FactoriaFuenteDatos {

    MEMORIA{
        public IFuenteDatos crear(){
            return new FuenteDatosMemoria();

        }
    },
    MYSQL{
        public IFuenteDatos crear(){
            return new FuenteDatosMySQL();

        }
    },
    FICHERO{
        public IFuenteDatos crear(){
            return new FuenteDatosFichero();

        }
    };

    /* Método abstracto, deben implementarlo MEMORIA y MYSQL */
    public abstract IFuenteDatos crear();


}
