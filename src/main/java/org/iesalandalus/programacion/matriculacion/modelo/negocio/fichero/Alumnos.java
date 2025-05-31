package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.Alumno;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAlumnos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Alumnos implements IAlumnos {
    /* TO08.
    Paquete fichero:
    - Copia la clase Alumnos existente en el paquete memoria al paquete fichero,
      modificándolo para que al comenzar lea el fichero XML de alumnos, lo almacene
      en un una lista y al terminar lo vuelva a almacenar en dicho fichero.
    - El fichero debe estar situado en la carpeta datos de la raíz del proyecto, se
      debe llamar alumnos.xml y su estructura será la siguiente:
    */

    private List<Alumno> coleccionAlumnos;
    private static final String RUTA_FICHERO = "datos/alumnos.xml";

    public Alumnos() {
        this.coleccionAlumnos = new ArrayList<>();
        comenzar();
    }



    /* Método que leerá el fichero XML de Alumnos, y almacenará los nodos hijos Alumno
    en coleccionAlumnos. Llamado desde método comenzar() */
    private void leerXML(String rutaXml) {
        // 1º Pasar el XML a Document (DOM):
        Document doc = UtilidadesXML.xmlToDom(rutaXml);
        // si doc es null, es porque no se obtuvo rutaXml hacia un fichero
        if (doc == null) {
            System.out.println("No se ha podido leer el fichero ");
            return;
        }


        // 2º Recorrer Document (DOM) para pasar sus nodos a objetos:
        // Obtiene el elemento raíz del DOM (Alumnos)
        Element raizDOM = doc.getDocumentElement();
        // Obtiene la lista de nodos (hijos de nodo raíz) del DOM
        NodeList listaNodos = raizDOM.getElementsByTagName("Alumno");

        // Si hay nodos hijo...
        if (listaNodos.getLength() > 0) {

            // Recorre lista de nodos hijo
            for (int i = 0; i < listaNodos.getLength(); i++) {
                // Obtiene cada nodo hijo
                Node nodo = listaNodos.item(i);

                // Comprueba que el nodo hijo es un nodo del elemento (nodo padre),
                // y no un atributo del elemento (nodo padre).
                // ELEMENT_NODE / ELEMENT_ATTRIBUTE para comprobar qué es
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    // pasa el nodo hijo de tipo Node a Element
                    Element alumnoDOM = (Element) nodo;
                    // pasa el nodo hijo de tipo Element a Alumno
                    Alumno alumno = elementToAlumno(alumnoDOM);

                    // almacena el nodo hijo tipo Alumno en List<>
                    if (alumno != null) coleccionAlumnos.add(alumno);
                }
            }
        }
    }



    /* Método que pasa los nodos hijo Alumno de tipo Element a Alumno */
    private Alumno elementToAlumno(Element alumnoDOM) {

        // 1º Obtener atributos y nodos hijo del Element Alumno

        // Atributos:
        // - getAttribute("Dni"): da String del valor del atributo
        String dniAlum = alumnoDOM.getAttribute("Dni");

        // Nodos hijo:
        // - "(Element)": parsea cada tipo Node (nodos hijo del Element Alumno) a Element
        // - .item(0): 1ª etiqueta <Nombre> dentro de <Alumno>
        Element eNombre = (Element) alumnoDOM.getElementsByTagName("Nombre").item(0);
        Element eTelefono = (Element) alumnoDOM.getElementsByTagName("Telefono").item(0);
        Element eCorreo = (Element) alumnoDOM.getElementsByTagName("Correo").item(0);
        Element eFechaNacimiento = (Element) alumnoDOM.getElementsByTagName("FechaNacimiento").item(0);
        // * Se comprueba que los Element obtenidos del XML no contienen nulos:
        if (eNombre == null || eTelefono == null || eCorreo == null || eFechaNacimiento == null ) {
            return null;
        }


        // 2º Pasar los nodos hijo de tipo Element a su tipo correspondiente:
        String nombreAlum = eNombre.getTextContent();
        String correoAlum = eCorreo.getTextContent();
        String telefonoAlum = eTelefono.getTextContent();
        // * Pasar fecha teniendo en cuenta el formato que hay en la fecha escrita en XML
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(Alumno.FORMATO_FECHA);
        LocalDate fechaNacimientoAlum = LocalDate.parse(eFechaNacimiento.getTextContent(), formatoFecha);

        // 3º Dar valores a obj Alumno
        return new Alumno(nombreAlum, dniAlum, correoAlum, telefonoAlum, fechaNacimientoAlum);
    }



    /* Método que almacena en el fichero XML la coleccionAlumnos (al usar FileOutputStream
    el UtilidadesXML.domToXml(), si existe el fichero XML lo sobreescribe).
    Llamado desde método terminar() */
    private void escribirXML() {

        // 1º Crea Document DOM vacío, con el nombre del elemento raíz.
        Document DOMAlumnos = UtilidadesXML.crearDomVacio("Alumnos");

        // Almacena el elemento raíz del DOM (Alumnos).
        Element raizDOM = DOMAlumnos.getDocumentElement();


        // 2º Convierte cada obj Alumno en un Element alumno, y se añade al Document del
        // arbol DOM
        if (!coleccionAlumnos.isEmpty()) {
            for (Alumno alum : coleccionAlumnos) {
                Element alumnoDOM = alumnoToElement(DOMAlumnos, alum);
                raizDOM.appendChild(alumnoDOM);
            }
        }


        // * Crea carpeta datos y alumnos.xml SOLO si no existe
        File fichero = new File(RUTA_FICHERO);
        fichero.getParentFile().mkdirs();

        // 3º Convierte Document DOM en un fichero XML
        UtilidadesXML.domToXml(DOMAlumnos, RUTA_FICHERO);

    }



    /* Método que pasa un obj tipo Alumno a Element del DOM indicado */
    private Element alumnoToElement(Document DOMAlumnos, Alumno alumno) {

        // 1º Crea Element Alumno, del alumno pasado, dentro del DOM pasado.
        Element alumnoDOM = DOMAlumnos.createElement("Alumno");


        // 2º Establece atributos y nodos hijos al Element Alumno.
        String dniAlum = alumno.getDni();
        String nombreAlum = alumno.getNombre();
        String telefonoAlum = alumno.getTelefono();
        String correoAlum = alumno.getCorreo();
        LocalDate fechaNacimientoAlum = alumno.getFechaNacimiento();
        // * Dar formato a la fecha en el XML
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern(Alumno.FORMATO_FECHA);
        String fFechaNacimientoAlum = fechaNacimientoAlum.format(formatoFecha);

        // Atributos de Element Alumno:
        alumnoDOM.setAttribute("Dni", dniAlum);

        // Nodos hijos de Element Alumno:
        // ...crea elemento en el DOM
        Element eNombre = DOMAlumnos.createElement("Nombre");
        // ...establece texto contenido entre las etiquetas del elemento creado
        eNombre.setTextContent(nombreAlum);
        // ...establece el elemento como hijo de Element Alumno
        alumnoDOM.appendChild(eNombre);

        // ...y así sucesivamente...
        Element eTelefono = DOMAlumnos.createElement("Telefono");
        eTelefono.setTextContent(telefonoAlum);
        alumnoDOM.appendChild(eTelefono);
        Element eCorreo = DOMAlumnos.createElement("Correo");
        eCorreo.setTextContent(correoAlum);
        alumnoDOM.appendChild(eCorreo);
        Element eFechaNacimiento = DOMAlumnos.createElement("FechaNacimiento");
        eFechaNacimiento.setTextContent(fFechaNacimientoAlum);
        alumnoDOM.appendChild(eFechaNacimiento);

        return alumnoDOM;
    }




    /* Implementa el patrón singlenton a través del atributo instancia y del método getInstancia
    que si el atributo instancia es nulo devolverá una instancia de la clase Alumnos,
    y si no es nulo devolverá el valor del atributo instancia. */
    private static Alumnos instancia;
    // getInstancia es package, para que solo accedan las clases de mismo paquete
    static Alumnos getInstancia(){
        if (instancia == null){
            instancia = new Alumnos();
        }
        return instancia;
    }




    /* TO10.
    - Implementa interfaz correspondiente (IAlumnos)
    - Implementar los métodos comenzar() y terminar() de su interfaz.
      Los cuerpos de ambos métodos en las cuatro clases deberán llamar respectivamente a los métodos
      establecerConexion() y cerrarConexion() de la clase MySQL.
    - Mover a paquete memoria
    */

    @Override
    public void comenzar() {
        leerXML(RUTA_FICHERO);
    }

    @Override
    public void terminar() {
        escribirXML();
    }



    @Override
    public int getTamano() {
        return coleccionAlumnos.size();
    }

    @Override
    public List<Alumno> get() {
        return copiaProfundaAlumnos();
    }


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
/*  Con stream: */
    private List<Alumno> copiaProfundaAlumnos() {
        List<Alumno> copiaProfunda = coleccionAlumnos.stream()
                .filter(alumno -> alumno != null)
                .map(Alumno::new)   // Crea una copia de cada objeto Alumno usando el constructor copia.
                // Usa el constructor copia de clase Alumno porque la lista de objetos obtenidos
                // con coleccionAlumnos.stream().filter() son tipo Alumno y no nulos
                .collect(Collectors.toList());  //Crea con esos objetos copiados una lista mutable
        // (puedes borrar/añadir después objetos)
        return copiaProfunda;
    }


    @Override
    // Insertar alumno no nulo al final de colecciónAlumnos, sin repetidos, si hay espacio.
    public void insertar(Alumno alumno) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede insertar un alumno nulo.");
        }

        // INSERTAR EN LA MEMORIA:
        if (coleccionAlumnos.contains(alumno)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un alumno con ese dni.");
        }
        coleccionAlumnos.add(alumno);

    }


    @Override
    //Busca un alumno. Si existe devuelve el alumno, si no existe devuelve null
    public Alumno buscar(Alumno alumno) {
        if (alumno == null) {
            throw new NullPointerException("Alumno nulo no puede buscarse.");
        }

        // Si existe el alumno, devuelve copia de este
        int indice;
        if (coleccionAlumnos.contains(alumno)) {
            // indexOf() devuelve el índice donde se encuentra el objeto indicado
            indice = coleccionAlumnos.indexOf(alumno);
            // get() devuelve el objeto exacto que se encuentra en el índice indicado
            // alumno ahora tiene exactamente el valor del objeto que se encuentra en la lista
            alumno = coleccionAlumnos.get(indice);
            return new Alumno(alumno);
        }
        // Si no existe, devuelve nulo
        else return null;
    }


    @Override
    public void borrar(Alumno alumno) throws OperationNotSupportedException {
        if (alumno == null) {
            throw new NullPointerException("ERROR: No se puede borrar un alumno nulo.");
        }


        // BORRAR EN LA MEMORIA:
        //Comprobar que existe
        if (!coleccionAlumnos.contains(alumno)) {
            throw new OperationNotSupportedException("ERROR: No existe ningún alumno como el indicado.");
        }
        //Si existe, lo borra
        else coleccionAlumnos.remove(alumno);
    }


}
