package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.utilidades;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class UtilidadesXML {

    /* El método xmlToDom es el encargado de convertir un archivo XML en un árbol DOM.*/
    public static Document xmlToDom (String rutaXML) {

        // Crea instancia de DocumentBuilderFactory, la clase que crea objs DocumentBuilder
        // (constructores de DOM).
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        // Crea obj DocumentBuilder, que hará el parseo/conversión de XML a DOM
        DocumentBuilder db;

        // Crea obj Document, contendrá el DOM
        Document docDOM = null ;

        try {
            // nuevo DocumentBuilder
            db = dbf.newDocumentBuilder();

            // obj que apunta al fichero XML
            File ficheroXml = new File(rutaXML);

            // el Document, obtiene resultado de parsear con DocumentBuilder el File ficheroXml
            // a Document
            docDOM = db.parse(ficheroXml);
            return docDOM;

        } catch(ParserConfigurationException | SAXException | IOException ex) {
            System.out.println(ex.getMessage());
        }
        return docDOM;
    }



    /* El método domToXml implementa la transformación de un árbol DOM en un archivo XML. */
        // Document DOM: el objeto DOM que representa el contenido XML.
        // String rutaXml: la ruta donde se guardará el archivo XML.
    public static void domToXml (Document DOM, String rutaXml) {
        try {

            // Crea obj File, que representa la ruta del archivo.
            // * Pero NO crea fichero físicamente.
            File f = new File(rutaXml);
            // Crea obj FileOutputStream, que representa el archivo ya creado físicamente,
            // donde escribir bytes.
            FileOutputStream fos = new FileOutputStream(f);


            // Prepara de dónde se va a sacar la información para el XML (la fuente DOM):
            DOMSource source = new DOMSource(DOM);

            // Y a dónde se va a escribir la información (el destino):
            // - StreamResult es un contenedor del archivo de destino (fos), que le indica al Transformer
            //   dónde escribir un XML (fos) y con cual Writer traducir de texto a byte (OutputStreamWriter).
            // - OutputStreamWriter es un "traductor", convierte el texto a bytes usando UTF-8 y lo envía al
            //   archivo de destino (fos).
            //   * OutputStreamWriter es solo necesario si lo quiero escribir en bytes.
            //     Si no, usar solo obj File, que usa internamente un FileWriter que escribe en caracteres:
            //     StreamResult result = new StreamResult(f);
            StreamResult result = new StreamResult (new OutputStreamWriter (fos,"UTF-8"));


            // Creación de Transformer:
            // - Instancia de TransformerFactory, la clase que crea transformadores.
            TransformerFactory tFactory = TransformerFactory.newInstance();
            // * Define algunas opciones de salida (como la indentación del XML).
            tFactory.setAttribute("indent-number", Integer.valueOf(4));
            // - Crea obj Transformer, que hará la conversión de DOM a fichero XML.
            Transformer transformer = tFactory.newTransformer();
            // * Activa las opciones de salida (como la indentación)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            // Transforma el arbol DOM (contenido en source) en un fichero XML (mediante result):
            transformer.transform(source, result);


            // En resumen:
            // 1. Transformer recibe una fuente (DOMSource) y un destino (StreamResult).
            //    Él escribe lo que le llega (DOMSource) directamente en el destino (StreamResult).
            // 2. StreamResult recibe lo escrito por Transformer. Se lo pasa a su Writer (OutputStreamWriter).
            // 4. OutputStreamWriter traduce el XML de texto a bytes UTF-8, y lo escribe en el archivo destino (fos).


        } catch (TransformerException | FileNotFoundException | UnsupportedEncodingException ex) {
            System.out.println(ex.getMessage());
        }
    }



    /* El método crearDomVacio creará un DOM vacío que tenga como elemento raíz la etiqueta
    especificada como parámetro. */
    public static Document crearDomVacio(String etiquetaRaiz) {
        // Crea instancia de DocumentBuilderFactory, la clase que crea objs DocumentBuilder
        // (constructores de DOM).
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        // Crea obj DocumentBuilder, que hará el parseo/conversión de XML a DOM
        DocumentBuilder db;
        // Crea obj Document, contendrá el DOM
        Document docDOMvacio = null ;

        try {
            // nuevo DocumentBuilder
            db = dbf.newDocumentBuilder() ;

            // el Document vacío, obtiene resultado de crear Document mediante DocumentBuilder
            docDOMvacio = db.newDocument();

            // Crear el elemento raíz del Document vacío
            Element elementoRaiz = docDOMvacio.createElement(etiquetaRaiz);
            // Añadir el elemento raíz al Document vacío
            docDOMvacio.appendChild(elementoRaiz);

            return docDOMvacio;

        } catch (ParserConfigurationException ex) {
            System.out.println(ex.getMessage());
        }
        return docDOMvacio;
    }

}
