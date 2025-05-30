package org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero;

import org.iesalandalus.programacion.matriculacion.modelo.dominio.*;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.IMatriculas;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.fichero.utilidades.UtilidadesXML;
import org.iesalandalus.programacion.matriculacion.modelo.negocio.memoria.CiclosFormativos;
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

public class Matriculas implements IMatriculas {
    private List<Matricula> coleccionMatriculas;
    private static final String RUTA_FICHERO = "datos/matriculas.xml";

    public Matriculas() throws OperationNotSupportedException {
        this.coleccionMatriculas = new ArrayList<>();
        comenzar();
    }



    /* Método que pasa los nodos hijo Matricula de tipo Element a Matricula */
    private Matricula elementToMatricula(Element matriculaDOM) throws OperationNotSupportedException {

        // 1º Obtener atributos y nodos hijo del Element Matricula

        // Atributos:
        int idMatr  = Integer.parseInt(matriculaDOM.getAttribute("Id"));

        String alumnoMatrDni = matriculaDOM.getAttribute("Alumno");
        Alumno alumnoMatr = null;
        // Crear obj inventado Alumno, menos el dni, solo para buscarlo y obtener
        // el obj real almacenado en la coleccionAlumnos:
        Alumno alumnoFalso = new Alumno("Alumno falso", alumnoMatrDni, "correofalso@gmail.com", "111111111", LocalDate.of(2000, 11,11));
        alumnoMatr = Alumnos.getInstancia().buscar(alumnoFalso);

        // Nodos hijo:
        Element eCursoAca = (Element) matriculaDOM.getElementsByTagName("CursoAcademico").item(0);
        Element eFechaMatriculacion = (Element) matriculaDOM.getElementsByTagName("FechaMatriculacion").item(0);
        Element eFechaAnulacion = (Element) matriculaDOM.getElementsByTagName("FechaAnulacion").item(0);

        Element eAsignaturas = (Element) matriculaDOM.getElementsByTagName("Asignaturas").item(0);
        // almacena en lista cada asignatura
        List<Asignatura> listaAsignaturasMatr = new ArrayList<>();
        if (eAsignaturas != null) {
            // listado de nodos llamados Asignatura, dentro del Element Asignaturas
            NodeList nodelAsignatura = eAsignaturas.getElementsByTagName("Asignatura");
            // recorre listado de nodos Asignatura
            for (int i = 0; i < nodelAsignatura.getLength(); i++) {
                // obtiene cada nodo Asignatura, su atributo Codigo
                Element eAsignatura = (Element) nodelAsignatura.item(i);
                String codAsignatura = eAsignatura.getAttribute("Codigo");
                // busca la asignatura, creando asignatura falsa (con un ciclo cualquiera) menos el codigo
                CicloFormativo cicloFalso = new CicloFormativo(1000, "familia prof", new GradoE("gradoe", 1, 1), "nombre ciclo", 2);
                Asignatura asignaturaFalsa = new Asignatura(codAsignatura, "asignatura falsa", 3, Curso.PRIMERO, 2, EspecialidadProfesorado.FOL, cicloFalso);
                Asignatura asignatura = Asignaturas.getInstancia().buscar(asignaturaFalsa);
                if (asignatura != null) {
                    listaAsignaturasMatr.add(asignatura);
                }
            }
        }

        // Se comprueba que los Element obtenidos del XML no contienen nulos:
        if (eCursoAca == null || eFechaMatriculacion == null ||
                alumnoMatr == null || idMatr == 0 || listaAsignaturasMatr.isEmpty()) {
            return null;
        }


        // 2º Pasar los nodos hijo de tipo Element a su tipo correspondiente:
        String cursoAcaMatr = eCursoAca.getTextContent();
        LocalDate fechaMatr = LocalDate.parse(eFechaMatriculacion.getTextContent());


        // 3º Dar valores a obj Matricula
        Matricula matricula = new Matricula(idMatr, cursoAcaMatr, fechaMatr, alumnoMatr, listaAsignaturasMatr);
        if (eFechaAnulacion != null) {
            LocalDate fechaAnulaMatr = LocalDate.parse(eFechaAnulacion.getTextContent());
            matricula.setFechaAnulacion(fechaAnulaMatr);
            return matricula;
        }
        return matricula;
    }



    /* Método que leerá el fichero XML de Matriculas, y almacenará los nodos hijos Matricula
    en coleccionMatriculas. Llamado desde método comenzar() */
    private void leerXML(String rutaXml) throws OperationNotSupportedException {
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
        NodeList listaNodos = raizDOM.getElementsByTagName("Matricula");

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
                    Element matriculaDOM = (Element) nodo;
                    // pasa el nodo hijo de tipo Element a Matricula
                    Matricula matricula = elementToMatricula(matriculaDOM);

                    // almacena el nodo hijo tipo Alumno en List<>
                    if (matricula != null) coleccionMatriculas.add(matricula);
                }
            }
        }
    }



    /* Método que pasa un obj tipo Matricula a Element del DOM indicado */
    private Element matriculaToElement(Document DOMMatriculas, Matricula matricula) {

        // 1º Crea Element Matricula, de la matricula pasada, dentro del DOM pasado.
        Element matriculaDOM = DOMMatriculas.createElement("Matricula");


        // 2º Establece atributos y nodos hijos al Element Matricula.
        /*
        (int idMatricula,
        String cursoAcademico,
        LocalDate fechaMatriculacion,
        Alumno alumno,
        List<Asignatura> coleccionAsignaturas)
        fechaAnulacion
        */
        int idMatr = matricula.getIdMatricula();
        String cursoAcaMatr = matricula.getCursoAcademico();
        LocalDate fechaMatr = matricula.getFechaMatriculacion();
        Alumno alumnoMatr = matricula.getAlumno();
        List<Asignatura> asignaturasMatr = matricula.getColeccionAsignaturas();
        LocalDate fechaAnulaMatr = matricula.getFechaAnulacion();


        // Atributos de Element Matricula:
        matriculaDOM.setAttribute("Id", String.valueOf(idMatr));
        matriculaDOM.setAttribute("Alumno", alumnoMatr.getDni());

        // Nodos hijos de Element Matricula:
        // ...crea elemento en el DOM
        Element eCursoAca = DOMMatriculas.createElement("CursoAcademico");
        // ...establece texto contenido entre las etiquetas del elemento creado
        eCursoAca.setTextContent(cursoAcaMatr);
        // ...establece el elemento como hijo de Element Alumno
        matriculaDOM.appendChild(eCursoAca);
        // ...y así sucesivamente...
        Element eFechaMatriculacion = DOMMatriculas.createElement("FechaMatriculacion");
        eCursoAca.setTextContent(String.valueOf(fechaMatr));
        matriculaDOM.appendChild(eFechaMatriculacion);
        // * fecha de anulación vacía a no ser que el valor en el obj matricula no sea nulo
        Element eFechaAnulacion = DOMMatriculas.createElement("FechaAnulacion");
        if (fechaAnulaMatr != null) {
            eFechaAnulacion.setTextContent(String.valueOf(fechaAnulaMatr));
        }
        matriculaDOM.appendChild(eFechaAnulacion);
        // * recorre lista de asignaturas del obj matricula  para insertar cada una como
        // nodo hijo Asignatura del element Asignaturas
        Element eAsignaturas = DOMMatriculas.createElement("Asignaturas");
        matriculaDOM.appendChild(eAsignaturas);
        for (Asignatura asign : asignaturasMatr){
            String codAsignMatr = asign.getCodigo();

            Element eAsignatura = DOMMatriculas.createElement("Asignatura");
            eAsignatura.setAttribute("Codigo", codAsignMatr);
            eAsignaturas.appendChild(eAsignatura);
        }

        return matriculaDOM;
    }



    /* Método que almacena en el fichero XML la coleccionMatriculas (al usar FileOutputStream
    el UtilidadesXML.domToXml(), si existe el fichero XML lo sobreescribe).
    Llamado desde método terminar() */
    private void escribirXML() {

        // 1º Crea Document DOM vacío, con el nombre del elemento raíz.
        Document DOMMatriculas = UtilidadesXML.crearDomVacio("Matriculas");

        // Almacena el elemento raíz del DOM (Matriculas).
        Element raizDOM = DOMMatriculas.getDocumentElement();


        // 2º Convierte cada obj Matricula en un Element Matricula, y se añade al Document del
        // arbol DOM
        if (!coleccionMatriculas.isEmpty()) {
            for (Matricula matr : coleccionMatriculas) {
                Element matriculaDOM = matriculaToElement(DOMMatriculas, matr);
                raizDOM.appendChild(matriculaDOM);
            }
        }


        // * Crea carpeta datos y matriculas.xml SOLO si no existe
        File fichero = new File(RUTA_FICHERO);
        fichero.getParentFile().mkdirs();

        // 3º Convierte Document DOM en un fichero XML
        UtilidadesXML.domToXml(DOMMatriculas, RUTA_FICHERO);
    }





    private static Matriculas instancia;
    // getInstancia es package, para que solo accedan las clases de mismo paquete
    static Matriculas getInstancia() throws OperationNotSupportedException {
        if (instancia == null){
            instancia = new Matriculas();
        }
        return instancia;
    }

    @Override
    public void comenzar() throws OperationNotSupportedException {
        leerXML(RUTA_FICHERO);
    }
    @Override
    public void terminar() {
        escribirXML();
    }




    @Override
    public int getTamano() {
        return coleccionMatriculas.size();
    }


    @Override
    public List<Matricula> get() throws OperationNotSupportedException {
        return this.coleccionMatriculas;
    }


    private List<Matricula> copiaProfundaMatriculas() throws OperationNotSupportedException {
        List<Matricula> copiaProfunda = new ArrayList<>();

        for (Matricula matricula : coleccionMatriculas) {
            if (matricula != null) {
                copiaProfunda.add(new Matricula(matricula));
            }
        }
        return  copiaProfunda;
    }


    @Override
    public void insertar (Matricula matricula) throws OperationNotSupportedException {                                                                //RELANZAR ?
        if (matricula == null){
            throw new NullPointerException("ERROR: No se puede insertar una matrícula nula.");
        }


        // INSERTAR EN LA MEMORIA:
        if (coleccionMatriculas.contains(matricula)) {
            throw new OperationNotSupportedException("ERROR: Ya existe una matrícula con ese código.");
        }
        coleccionMatriculas.add(matricula);
    }


    @Override
    public Matricula buscar(Matricula matricula) throws OperationNotSupportedException{
        if (matricula == null){
            throw new NullPointerException("Matrícula nula no puede buscarse.");
        }

        int indice;
        if (coleccionMatriculas.contains(matricula)) {
            indice=coleccionMatriculas.indexOf(matricula);
            matricula = coleccionMatriculas.get(indice);
            return new Matricula(matricula);
            //return matricula;
        }
        else return null;
    }


    @Override
    public void borrar(Matricula matricula) throws OperationNotSupportedException {
        if (matricula == null){
            throw new NullPointerException("ERROR: No se puede borrar una matrícula nula.");
        }


        // BORRAR EN LA MEMORIA:
        if (!coleccionMatriculas.contains(matricula)) {
            throw new OperationNotSupportedException("ERROR: No existe ninguna matrícula como la indicada.");
        }
        else coleccionMatriculas.remove(matricula);
    }



    // El método get que está sobrecargado y devolverá una colección de las matrículas realizadas por el
    // alumno pasado por parámetro o unca colección de las matrículas realizadas para el curso académico
    // indicado como parámetro o una colección de las matrículas realizadas para el ciclo formativo
    // indicado como parámetro.
    @Override
    public List<Matricula> get(Alumno alumno){
        /*int contador = 0;
        //Para contar cuántas coincidencias del alumno hay en matrículas:
        //Recorre las matrículas
        for (Matricula matricula : coleccionMatriculas) {
            //Si en la matricula actual, su alumno = al alumno pasado...
            if (matricula.getAlumno().equals(alumno)) {
                contador++;
            }
        }
        //Crear array con el número de coincidencias del alumno en las matrículas:
        Matricula[] coleccionMatriculasAlumn = new Matricula[contador];
        */

        /*int i = 0;
        //Para asignar las matrículas con coincidencias al nuevo array:
        for (Matricula matriculaAlumno : coleccionMatriculas){
            if (matriculaAlumno.getAlumno().equals(alumno)){
                coleccionMatriculasAlumn[i] = matriculaAlumno;
                i++;
            }
        }
        return coleccionMatriculasAlumn;
        */

        /* USANDO BUCLE
        // Crear lista
        List<Matricula> matriculasAlumno = new ArrayList<>();

        // Recorrer colección, si en la matrícula su alumno es el indicado,  se añade la matrícula a la
        // lista de matrículas del Alumno
        for (Matricula matricula : coleccionMatriculas) {
            if (matricula.getAlumno().equals(alumno)) {
                matriculasAlumno.add(matricula);
            }
        }
        return matriculasAlumno;
        */

        /* USANDO STREAM
           Diferencia .toList() y collect(Collectors.toList()):

           > Usa .toList() si no necesitas modificar la lista después.
           > Usa .collect(Collectors.toList()) si necesitas una lista mutable (puede añadir/borrar datos).
           > Si necesitas una lista específica como LinkedList, usa .collect(Collectors.toCollection(LinkedList::new)).
        */

        // obtiene alumno de clase Modelo getMatriculas()
        List<Matricula> matriculasAlumno = coleccionMatriculas.stream()
                .filter(matricula -> matricula.getAlumno().equals(alumno))
                .collect(Collectors.toList());
        return matriculasAlumno;
    }


    @Override
    public List<Matricula> get(String cursoAcademico){
        /*
        List<Matricula> matriculasCurso = new ArrayList<>();

        for (Matricula matricula : coleccionMatriculas) {
            if (matricula.getCursoAcademico().equals(cursoAcademico)) {
                matriculasCurso.add(matricula);
            }
        }
        return matriculasCurso;
        */

        List<Matricula> matriculasCurso = coleccionMatriculas.stream()
                .filter(matricula -> matricula.getCursoAcademico().equals(cursoAcademico))
                .collect(Collectors.toList());
        return matriculasCurso;
    }


    @Override
    public List<Matricula> get(CicloFormativo cicloFormativo) {
        /*
        List<Matricula> matriculasCiclo = new ArrayList<>();

        for (Matricula matricula : coleccionMatriculas) {
            for (Asignatura asignatura : matricula.getColeccionAsignaturas()){
                if (asignatura.getCicloFormativo().equals(cicloFormativo)) {
                    matriculasCiclo.add(matricula);
                    // break para evitar procesar más asignaturas una vez que se encuentra una
                    // coincidencia dentro de la matrícula
                    break;
                }
            }
        }
        return matriculasCiclo;
        */

        List<Matricula> matriculasCiclo = coleccionMatriculas.stream()              // listado de matrículas.
                .filter(matricula -> matricula.getColeccionAsignaturas().stream()   // listado de asignaturas de cada matricula.
                        .anyMatch(asignatura -> asignatura.getCicloFormativo().equals(cicloFormativo)))
                        // anyMatch da true si alguna asignatura pertenece al ciclo formativo
                .collect(Collectors.toList());
        return matriculasCiclo;
    }

}
