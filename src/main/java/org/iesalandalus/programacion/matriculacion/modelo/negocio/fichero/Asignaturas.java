package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IAsignaturas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.CiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Asignaturas implements IAsignaturas {
    private List<Asignatura> coleccionAsignaturas;
    private static final String RUTA_FICHERO = "datos/asignaturas.xml";

    public Asignaturas() {
        this.coleccionAsignaturas = new ArrayList<>();
        comenzar();
    }


    /* Método que pasa los nodos hijo Asignatura de tipo Element a Asignatura */
    private Asignatura elementToAsignatura(Element asignaturaDOM) {

        // 1º Obtener atributos y nodos hijo del Element Asignatura

        // Atributos:
        // - getAttribute("Codigo"): da String del valor del atributo
        String codAsignatura = asignaturaDOM.getAttribute("Codigo");

        // Nodos hijo:
        // - "(Element)": parsea cada tipo Node (nodos hijo del Element Asignatura) a Element
        // - .item(0): 1ª etiqueta <Nombre> dentro de <Asignatura>
        Element eNombre = (Element) asignaturaDOM.getElementsByTagName("Nombre").item(0);
        Element eCurso = (Element) asignaturaDOM.getElementsByTagName("Curso").item(0);
        Element eEspecialidadProf = (Element) asignaturaDOM.getElementsByTagName("EspecialidadProfesorado").item(0);
        Element eCicloForm = (Element) asignaturaDOM.getElementsByTagName("CicloFormativo").item(0);
        Element eHoras = (Element) asignaturaDOM.getElementsByTagName("Horas").item(0);
        Element eHorasAnual = (Element) eHoras.getElementsByTagName("Anuales").item(0);
        Element eHorasDesd = (Element) eHoras.getElementsByTagName("Desdoble").item(0);
        // Se comprueba que los Element obtenidos del XML no contienen nulos:
        if (eNombre == null || eCurso == null || eEspecialidadProf == null ||
                eCicloForm == null || eHorasAnual == null || eHorasDesd == null) {
            return null;
        }


        // 2º Pasar los nodos hijo de tipo Element a su tipo correspondiente:
        String nombreAsignatura = eNombre.getTextContent();
        int horasAnualAsignatura = Integer.parseInt(eHorasAnual.getTextContent());
        Curso cursoAsignatura = null;
        if (eCurso.getTextContent().equalsIgnoreCase("Primero")) {
            cursoAsignatura = Curso.PRIMERO;
        } else if (eCurso.getTextContent().equalsIgnoreCase("Segundo")) {
            cursoAsignatura = Curso.SEGUNDO;
        }
        int horasDesdAsignatura = Integer.parseInt(eHorasDesd.getTextContent());
        EspecialidadProfesorado especialidadProfAsignatura = null;
        if (eEspecialidadProf.getTextContent().equalsIgnoreCase("Informatica")) {
            especialidadProfAsignatura = EspecialidadProfesorado.INFORMATICA;
        } else if (eEspecialidadProf.getTextContent().equalsIgnoreCase("FOL")) {
            especialidadProfAsignatura = EspecialidadProfesorado.FOL;
        } else if (eEspecialidadProf.getTextContent().equalsIgnoreCase("Sistemas")) {
            especialidadProfAsignatura = EspecialidadProfesorado.SISTEMAS;
        }
        CicloFormativo cicloAsignatura = null;
        // Crear obj inventado CicloFormativo, menos el código, solo para buscarlo y obtener
        // el obj real almacenado en la coleccionCiclosFormativos:
        Grado gradoFalsoCiclo = new GradoE("grado falso", 1, 1);
        CicloFormativo cicloFalso = new CicloFormativo(Integer.parseInt(eCicloForm.getTextContent()),
                "familia", gradoFalsoCiclo, "ciclo falso", 3);
        cicloAsignatura = CiclosFormativos.getInstancia().buscar(cicloFalso);
        if (cicloAsignatura==null) {
            return null;
        }

        // 3º Dar valores a obj Asignatura
        return new Asignatura(codAsignatura, nombreAsignatura, horasAnualAsignatura,
                cursoAsignatura, horasDesdAsignatura, especialidadProfAsignatura, cicloAsignatura);
    }



    /* Método que leerá el fichero XML de Asignaturas, y almacenará los nodos hijos Asignatura
    en coleccionAsignaturas. Llamado desde método comenzar() */
    private void leerXML(String rutaXml) {
        // 1º Pasar el XML a Document (DOM):
        Document doc = UtilidadesXML.xmlToDom(rutaXml);
        // si doc es null, es porque no se obtuvo rutaXml hacia un fichero
        if (doc == null) {
            System.out.println("No se ha podido leer el fichero ");
            return;
        }


        // 2º Recorrer Document (DOM) para pasar sus nodos a objetos:
        // Obtiene el elemento raíz del DOM (Asignaturas)
        Element raizDOM = doc.getDocumentElement();
        // Obtiene la lista de nodos (hijos de nodo raíz) del DOM
        NodeList listaNodos = raizDOM.getElementsByTagName("Asignatura");

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
                    Element asignaturaDOM = (Element) nodo;
                    // pasa el nodo hijo de tipo Element a Asignatura
                    Asignatura asignatura = elementToAsignatura(asignaturaDOM);

                    // almacena el nodo hijo tipo Asignatura en List<>
                    if (asignatura != null) coleccionAsignaturas.add(asignatura);
                }
            }
        }
    }



    /* Método que pasa un obj tipo Asignatura a Element del DOM indicado */
    private Element asignaturaToElement(Document DOMAsignaturas, Asignatura asignatura) {

        // 1º Crea Element Asignatura, de la asignatura pasada, dentro del DOM pasado.
        Element asignaturaDOM = DOMAsignaturas.createElement("Asignatura");


        // 2º Establece atributos y nodos hijos al Element Asignatura.
        String codAsign = asignatura.getCodigo();
        String nombreAsign = asignatura.getNombre();
        int horasAnualAsign = asignatura.getHorasAnuales();
        Curso cursoAsign = asignatura.getCurso();
        int horasDesdAsign = asignatura.getHorasDesdoble();
        EspecialidadProfesorado especialidadProfAsign = asignatura.getEspecialidadProfesorado();
        CicloFormativo cicloAsign = asignatura.getCicloFormativo();

        // Atributos de cada Element Asignatura:
        asignaturaDOM.setAttribute("Codigo", codAsign);

        // Nodos hijos de Element Asignatura:
        // ...crea elemento en el DOM
        Element eNombre = DOMAsignaturas.createElement("Nombre");
        // ...establece texto contenido entre las etiquetas del elemento creado
        eNombre.setTextContent(nombreAsign);
        // ...establece el elemento como hijo de Element Asignatura
        asignaturaDOM.appendChild(eNombre);

        // ...y así sucesivamente...
        String cursoAsignCad = null;
        if (cursoAsign == Curso.PRIMERO) {
            cursoAsignCad = "Primero";
        } else if (cursoAsign == Curso.SEGUNDO){
            cursoAsignCad = "Segundo";
        }
        Element eCurso = DOMAsignaturas.createElement("Curso");
        eCurso.setTextContent(cursoAsignCad);
        asignaturaDOM.appendChild(eCurso);

        String especialidadProfAsignCad = null;
        if (especialidadProfAsign == EspecialidadProfesorado.FOL) {
            especialidadProfAsignCad = "FOL";
        } else if (especialidadProfAsign == EspecialidadProfesorado.SISTEMAS){
            especialidadProfAsignCad = "Sistemas";
        } else if (especialidadProfAsign == EspecialidadProfesorado.INFORMATICA){
            especialidadProfAsignCad = "Informatica";
        }
        Element eEspecialidadProf = DOMAsignaturas.createElement("EspecialidadProfesorado");
        eEspecialidadProf.setTextContent(especialidadProfAsignCad);
        asignaturaDOM.appendChild(eEspecialidadProf);

        Element eCiclo = DOMAsignaturas.createElement("CicloFormativo");
        eCiclo.setTextContent(String.valueOf(cicloAsign.getCodigo()));
        asignaturaDOM.appendChild(eCiclo);

        Element eHoras = DOMAsignaturas.createElement("Horas");
        // nodos hijo de Element Horas
        Element eHorasAnual = DOMAsignaturas.createElement("Anuales");
        eHorasAnual.setTextContent(String.valueOf(horasAnualAsign));
        eHoras.appendChild(eHorasAnual);
        Element eHorasDesd = DOMAsignaturas.createElement("Desdoble");
        eHorasDesd.setTextContent(String.valueOf(horasDesdAsign));
        eHoras.appendChild(eHorasDesd);


        return asignaturaDOM;
    }



    /* Método que almacena en el fichero XML la coleccionAsignaturas (al usar FileOutputStream
    el UtilidadesXML.domToXml(), si existe el fichero XML lo sobreescribe).
    Llamado desde método terminar() */
    private void escribirXML() {

        // 1º Crea Document DOM vacío, con el nombre del elemento raíz.
        Document DOMAsignaturas = UtilidadesXML.crearDomVacio("Asignaturas");

        // Almacena el elemento raíz del DOM (Asignaturas).
        Element raizDOM = DOMAsignaturas.getDocumentElement();


        // 2º Convierte cada obj Asignatura en un Element Asignatura, y se añade al Document del
        // arbol DOM
        if (!coleccionAsignaturas.isEmpty()) {
            for (Asignatura asign : coleccionAsignaturas) {
                Element asignaturaDOM = asignaturaToElement(DOMAsignaturas, asign);
                raizDOM.appendChild(asignaturaDOM);
            }
        }


        // * Crea carpeta datos y asignaturas.xml SOLO si no existe
        File fichero = new File(RUTA_FICHERO);
        fichero.getParentFile().mkdirs();

        // 3º Convierte Document DOM en un fichero XML
        UtilidadesXML.domToXml(DOMAsignaturas, RUTA_FICHERO);

    }



    // Implementa el patrón singlenton
    private static Asignaturas instancia;
    // getInstancia es package, para que solo accedan las clases de mismo paquete
    static Asignaturas getInstancia(){
        if (instancia == null){
            instancia = new Asignaturas();
        }
        return instancia;
    }


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
        return coleccionAsignaturas.size();
    }


    @Override
    public List<Asignatura> get(){
        return copiaProfundaAsignaturas();
    }


    private List<Asignatura> copiaProfundaAsignaturas(){
        List<Asignatura> copiaProfunda = coleccionAsignaturas.stream()
                .filter(asig -> asig != null)
                .map(Asignatura::new)
                .collect(Collectors.toList());
        return copiaProfunda;
    }


    @Override
    public void insertar (Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null){
            throw new NullPointerException("ERROR: No se puede insertar una asignatura nula.");
        }


        // INSERTAR EN LA MEMORIA:
        if (coleccionAsignaturas.contains(asignatura)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una asignatura con ese código.");
        }
        coleccionAsignaturas.add(asignatura);
    }


    @Override
    public Asignatura buscar(Asignatura asignatura) {
        if (asignatura == null){
            throw new NullPointerException("Asignatura nula no puede buscarse.");
        }

        int indice;
        if (coleccionAsignaturas.contains(asignatura)) {
            indice = coleccionAsignaturas.indexOf(asignatura);
            asignatura = coleccionAsignaturas.get(indice);
            return new Asignatura(asignatura);
        }
        else return null;

    }


    @Override
    public void borrar(Asignatura asignatura) throws OperationNotSupportedException {
        if (asignatura == null) {
            throw new NullPointerException("ERROR: No se puede borrar una asignatura nula.");
        }

        // BORRAR EN LA MEMORIA:
        if (!coleccionAsignaturas.contains(asignatura)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna asignatura como la indicada.");
        }
        else coleccionAsignaturas.remove(asignatura);
    }
}
