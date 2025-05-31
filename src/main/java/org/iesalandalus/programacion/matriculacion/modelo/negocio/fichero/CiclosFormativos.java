package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.ICiclosFormativos;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.naming.OperationNotSupportedException;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CiclosFormativos implements ICiclosFormativos {
    private List<CicloFormativo> coleccionCiclosFormativos;
    private static final String RUTA_FICHERO = "datos/ciclos.xml";

    public CiclosFormativos() {
        this.coleccionCiclosFormativos = new ArrayList<>();
        comenzar();
    }



    /* Método que pasa los nodos hijo CicloFormativo de tipo Element a CicloFormativo */
    private CicloFormativo elementToCicloFormativo(Element cicloDOM) {

        // 1º Obtener atributos y nodos hijo del Element CicloFormativo
        // Atributos:
        int codigoCiclo = Integer.parseInt(cicloDOM.getAttribute("Codigo"));

        // Nodos hijo:
        Element eNombre = (Element) cicloDOM.getElementsByTagName("Nombre").item(0);
        Element eFamilia = (Element) cicloDOM.getElementsByTagName("FamiliaProfesional").item(0);
        Element eHoras = (Element) cicloDOM.getElementsByTagName("Horas").item(0);
        // Nodo hijo de CicloFormativo, que contiene otros atributos y nodos hijos
        Element eGrado = (Element) cicloDOM.getElementsByTagName("Grado").item(0);
        String aGrado = eGrado.getAttribute("Tipo");

        // Se comprueba que los Element obtenidos del XML no contienen nulos:
        if (eNombre == null || eFamilia == null || eHoras == null ||  eGrado == null ) {
            return null;
        }


        // 2º Pasar los nodos hijo de tipo Element a su tipo correspondiente:
        String familiaCiclo = eFamilia.getTextContent();
        String nombreCiclo = eNombre.getTextContent();
        int horasCiclo = Integer.parseInt(eHoras.getTextContent());

        Grado gradoCiclo = null;
        // nodos hijos, de Element Grado:
        Element eNombreGrado = (Element) eGrado.getElementsByTagName("Nombre").item(0);
        String nombreGrado = eNombreGrado.getTextContent();
        Element eNumAnios = (Element) eGrado.getElementsByTagName("NumAnios").item(0);
        int numAniosGrado = Integer.parseInt(eNumAnios.getTextContent());
        // nodos hijos, de Element Grado, opcionales:
        // ... Modalidad
        Element eModalidad = (Element) eGrado.getElementsByTagName("Modalidad").item(0);
        String modalidadGrado = null;
        if (eModalidad != null){
            modalidadGrado = eModalidad.getTextContent();
        }
        Modalidad modalidadGDCiclo = null;
        // ...NumEdiciones
        Element eNumEdiciones = (Element) eGrado.getElementsByTagName("NumEdiciones").item(0);
        int numEdicionesGECiclo = 0;
        if (eNumEdiciones != null){
            numEdicionesGECiclo = Integer.parseInt(eNumEdiciones.getTextContent());
        }

        if (aGrado.equalsIgnoreCase("GradoD")){
            // Manejo de modalidad
            if (modalidadGrado.equalsIgnoreCase("Presencial")){
                modalidadGDCiclo = Modalidad.PRESENCIAL;
            } else
                modalidadGDCiclo = Modalidad.SEMIPRESENCIAL;
            // Asigna tipo de grado
            gradoCiclo = new GradoD(nombreGrado, numAniosGrado, modalidadGDCiclo);

        } else if (aGrado.equalsIgnoreCase("GradoE")){
            gradoCiclo = new GradoE(nombreGrado, numAniosGrado, numEdicionesGECiclo);
        }


        // 3º Crear obj CicloFormativo
        return new CicloFormativo(codigoCiclo, familiaCiclo, gradoCiclo, nombreCiclo, horasCiclo);
    }



    /* Método que leerá el fichero XML de ciclos, y almacenará los nodos hijos CicloFormativo
    en coleccionCiclosFormativos. Llamado desde método comenzar() */
    private void leerXML(String rutaXml) {
        // 1º Pasar el XML a Document (DOM):
        Document doc = UtilidadesXML.xmlToDom(rutaXml);
        // si doc es null, es porque no se obtuvo rutaXml hacia un fichero
        if (doc == null) {
            System.out.println("No se ha podido leer el fichero ");
            return;
        }


        // 2º Recorrer Document (DOM) para pasar sus nodos a objetos:
        // Obtiene el elemento raíz del DOM (CiclosFormativos)
        Element raizDOM = doc.getDocumentElement();
        // Obtiene la lista de nodos (hijos de nodo raíz) del DOM
        NodeList listaNodos = raizDOM.getElementsByTagName("CicloFormativo");

        // Si hay nodos hijo...
        if (listaNodos.getLength() > 0) {

            // Recorre lista de nodos hijo
            for (int i = 0; i < listaNodos.getLength(); i++) {
                // Obtiene cada nodo hijo
                Node nodo = listaNodos.item(i);

                // Comprueba que el nodo hijo es un nodo del elemento (nodo padre),
                // y no un atributo del elemento (nodo padre).
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    // pasa el nodo hijo de tipo Node a Element
                    Element cicloDOM = (Element) nodo;
                    // pasa el nodo hijo de tipo Element a CicloFormativo
                    CicloFormativo cicloFormativo = elementToCicloFormativo(cicloDOM);

                    // almacena el nodo hijo tipo CicloFormativo en coleccion en memoria
                    if (cicloFormativo != null) coleccionCiclosFormativos.add(cicloFormativo);
                }
            }
        }
    }



    /* Método que pasa un obj tipo CicloFormativo a Element del DOM indicado */
    private Element cicloFormativoToElement (Document DOMCiclos, CicloFormativo cicloFormativo) {

        // 1º Crea Element CicloFormativo, del ciclo  pasado, dentro del DOM pasado.
        Element cicloDOM = DOMCiclos.createElement("CicloFormativo");


        // 2º Establece atributos y nodos hijos al Element CicloFormativo.
        int codigoCiclo = cicloFormativo.getCodigo();
        String familiaCiclo = cicloFormativo.getFamiliaProfesional();
        Grado gradoCiclo = cicloFormativo.getGrado();
        String nombreCiclo = cicloFormativo.getNombre();
        int horasCiclo = cicloFormativo.getHoras();


        // Atributos de Element CicloFormativo:
        cicloDOM.setAttribute("Codigo", String.valueOf(codigoCiclo));

        // Nodos hijos de Element CicloFormativo:
        // ...crea elemento en el DOM
        Element eNombre = DOMCiclos.createElement("Nombre");
        // ...establece texto contenido entre las etiquetas del elemento creado
        eNombre.setTextContent(nombreCiclo);
        // ...establece el elemento como hijo de Element CicloFormativo
        cicloDOM.appendChild(eNombre);

        // ...y así sucesivamente...
        Element eFamilia = DOMCiclos.createElement("FamiliaProfesional");
        eFamilia.setTextContent(familiaCiclo);
        cicloDOM.appendChild(eFamilia);
        Element eHoras = DOMCiclos.createElement("Horas");
        eHoras.setTextContent(String.valueOf(horasCiclo));
        cicloDOM.appendChild(eHoras);

        // nodo hijo Grado, de Element CicloFormativo
        Element eGrado = DOMCiclos.createElement("Grado");
        cicloDOM.appendChild(eGrado);
        // nodos hijo de Grado:
        Element eNombreGrado = DOMCiclos.createElement("Nombre");
        eNombreGrado.setTextContent(gradoCiclo.getNombre());
        eGrado.appendChild(eNombreGrado);
        Element eNumAniosGrado = DOMCiclos.createElement("NumAnios");
        eNumAniosGrado.setTextContent(String.valueOf(gradoCiclo.getNumAnios()));
        eGrado.appendChild(eNumAniosGrado);
        // atributos de Grado y nodos hijo opcionales:
        String tipoGrado = null;
        String modalidadGrado = null;
        String numEdicionesGrado = null;
        if (gradoCiclo instanceof GradoD){
            tipoGrado = "GradoD";

            if (((GradoD) gradoCiclo).getModalidad() == Modalidad.PRESENCIAL){
                modalidadGrado = "Presencial";
            } else if (((GradoD) gradoCiclo).getModalidad() == Modalidad.SEMIPRESENCIAL){
                modalidadGrado = "Semipresencial";
            }
            Element eModalidadGD = DOMCiclos.createElement("Modalidad");
            eModalidadGD.setTextContent(modalidadGrado);
            eGrado.appendChild(eModalidadGD);

        } else if(gradoCiclo instanceof GradoE) {
            tipoGrado = "GradoE";

            numEdicionesGrado = String.valueOf(((GradoE) gradoCiclo).getNumEdiciones());
            Element eNumEdicionesGE = DOMCiclos.createElement("NumEdiciones");
            eNumEdicionesGE.setTextContent(numEdicionesGrado);
            eGrado.appendChild(eNumEdicionesGE);
        }
        eGrado.setAttribute("Tipo", tipoGrado);

        return cicloDOM;
    }


    /* Método que almacena en el fichero XML la coleccionCiclosFormativos (al usar FileOutputStream
    el UtilidadesXML.domToXml(), si existe el fichero XML lo sobreescribe).
    Llamado desde método terminar() */
    private void escribirXML() {

        // 1º Crea Document DOM vacío, con el nombre del elemento raíz.
        Document DOMCiclos = UtilidadesXML.crearDomVacio("CiclosFormativos");

        // Almacena el elemento raíz del DOM (CiclosFormativos).
        Element raizDOM = DOMCiclos.getDocumentElement();


        // 2º Convierte cada obj CicloFormativo en un Element Ciclo, y se añade al Document del
        // arbol DOM
        if (!coleccionCiclosFormativos.isEmpty()) {
            for (CicloFormativo ciclo : coleccionCiclosFormativos) {
                Element cicloDOM = cicloFormativoToElement(DOMCiclos, ciclo);
                raizDOM.appendChild(cicloDOM);
            }
        }


        // * Crea carpeta datos y ciclos.xml SOLO si no existe
        File fichero = new File(RUTA_FICHERO);
        fichero.getParentFile().mkdirs();

        // 3º Convierte Document DOM en un fichero XML
        UtilidadesXML.domToXml(DOMCiclos, RUTA_FICHERO);
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
        leerXML(RUTA_FICHERO);

    }
    @Override
    public void terminar() {
        escribirXML();
    }




    @Override
    public int getTamano() {
        return coleccionCiclosFormativos.size();
    }


    @Override
    public List<CicloFormativo> get(){
        return copiaProfundaCiclosFormativos();
    }


    private List<CicloFormativo> copiaProfundaCiclosFormativos(){
        List<CicloFormativo> copiaProfunda = coleccionCiclosFormativos.stream()
                .filter(ciclo -> ciclo != null)
                .map(CicloFormativo::new)
                .collect(Collectors.toList());
        return copiaProfunda;
    }


    @Override
    public void insertar (CicloFormativo cicloFormativo) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: No se puede insertar un ciclo formativo nulo.");
        }

        if (coleccionCiclosFormativos.contains(cicloFormativo)) {
            throw new OperationNotSupportedException("ERROR: Ya existe un ciclo formativo con ese código.");
        }

        coleccionCiclosFormativos.add(cicloFormativo);
    }


    @Override
    public CicloFormativo buscar(CicloFormativo cicloFormativo) {
        if (cicloFormativo == null){
            throw new NullPointerException("Ciclo nulo no puede buscarse.");
        }

        int indice;
        if (coleccionCiclosFormativos.contains(cicloFormativo)) {
            indice = coleccionCiclosFormativos.indexOf(cicloFormativo);
            cicloFormativo = coleccionCiclosFormativos.get(indice);
            return new CicloFormativo(cicloFormativo);
        }
        else return null;
    }


    @Override
    public void borrar(CicloFormativo cicloFormativo) throws OperationNotSupportedException {
        if (cicloFormativo == null){
            throw new NullPointerException("ERROR: No se puede borrar un ciclo formativo nulo.");
        }

        if (!coleccionCiclosFormativos.contains(cicloFormativo)) {
            throw new OperationNotSupportedException("ERROR:No existe ningún ciclo formativo como el indicado.");
        }
        else coleccionCiclosFormativos.remove(cicloFormativo);
    }

}
