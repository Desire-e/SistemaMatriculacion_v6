# Tarea: Sistema de Matriculación v.6
## Profesor: Andrés Rubio del Río
## Alumno: Desirée Rueda Torres

Tu tarea va a consistir en modelar la gestión del **sistema de matriculación del instituto IES Al-Ándalus**. 
Después de todas las especificaciones y requerimientos anotados, en este **primer spring** de la aplicación 
el equipo directivo del instituto quiere que gestionemos matriculación del alumnado del que se quiere guardar 
el **nombre**, un número de **teléfono**, el **email**, el **dni**, la **fecha de nacimiento** y el 
**Número de Identificación del Alumnado (NIA)**. Hay que tener presente que en el instituto se imparten **Ciclos Formativos**
de diferentes familias profesionales y de diversos **Grados  (Grado D Ciclo Formativo de Grado Básico, Grado D Ciclo formativo 
de Grado Medio y Grado D Ciclo Formativo de Grado Superior)**. En referencia a las asignaturas, en este **primer spring** se ha 
decidido que se almacene la información referente al **código** que la identifica, su **nombre**, el **número de horas anuales** 
que se imparten de la asignatura en el ciclo formativo, el **número de horas de desdoble** (horas  en las que hay dos profesores 
simultáneamente en clase), el **curso** en el que se imparte la asignatura (**Primero** o **Segundo**), la **especialidad**  que 
debe tener el profesorado que imparte la asignatura y el **Ciclo Formativo** al que pertenece.  Respecto a este último, el equipo 
directivo quiere almacenar un **código** que lo identifique, una cadena que proporcione el nombre de la **familia profesional** a
la que pertenece el Ciclo, el **Grado** que le corresponde, su **nombre** y el total de **horas** del ciclo. Por último, en los 
ciclos formativos del **IES Al-Ándalus** solo pueden impartir docencia aquel profesorado que pertenezca a la especialidades de **Informática, 
Sistemas y Aplicaciones Informáticas y Formación y Orientación Laboral**.

Cuando a través de la aplicación se realice la matrícula de un alumno/a no solo se deberá almacenar la **lista de asignaturas** que constituyen 
la matrícula del alumno/a, también se deberá guardar el **identificador** de la matrícula, una cadena que represente el **curso académico** (debe 
tener formato dd-dd, por ejemplo, 23-24), la **fecha** en la que se realiza la matrícula, y en caso de que en un futuro se anule la matrícula, 
deberá quedar almacenada la **fecha de su anulación**.

He subido a GitHub un esqueleto de proyecto gradle que ya lleva incluidos todos los test necesarios que el programa debe pasar. La URL del repositorio 
es la siguiente: [repositorio GitHub con el esqueleto del proyecto](https://github.com/andresrubiodelrio/SistemaMatriculacion_v0).

Para ello te muestro un diagrama de clases para el mismo y poco a poco te iré explicando los diferentes pasos a realizar:

![](https://github.com/andresrubiodelrio/SistemaMatriculacion_v0/blob/master/src/main/resources/SistemaMatriculacion_v0.png)


#### Primeros Pasos
1. Lo primero que debes hacer es un **fork** del repositorio donde he colocado el esqueleto de este proyecto.
2. Clona tu repositorio remoto recién copiado en GitHub a un repositorio local que será donde irás realizando lo que a continuación se te pide. Modifica el archivo `README.md` para que incluya tu nombre en el apartado "Alumno". Realiza tu **primer commit**.

#### Alumno
1. Crea la clase `Alumno` en el paquete indicado en el diagrama.
2. Crea los diferentes atributos que se indican en el diagrama de clases con su visibilidad adecuada, teniendo en cuenta que la constante **FORMATO_FECHA** tiene como valor el formato de una fecha en España: **dd/MM/YYYY**.
3. Crea el método `formateaNombre`. Este método debe normalizar un nombre eliminando caracteres en blanco de sobra y poniendo en mayúsculas la primera letra de cada palabra y en minúsculas las demás.
4. Crea el método `comprobarLetraDni`. Este método hará uso de los grupos de las expresiones regulares (para ello has debido definir la expresión regular con grupos) para quedarse con el número por un lado y con la letra por otro. Para saber si la letra es válida puedes seguir las instrucciones del siguiente enlace: [Comprobar letra dni](https://calculadorasonline.com/calcular-la-letra-del-dni-validar-un-dni/).
5. Crea los métodos de acceso y modificación de cada atributo con la visibilidad adecuada, teniendo en cuenta que un nombre estará compuesto de palabras separadas por un espacio y cada palabra comenzará con una mayúscula y continuará con minúsculas. El **NIA** está formado por los cuatro primeros caracteres del nombre en minúsculas y los tres últimos dígitos del **DNI**. Además, ten presente que el **DNI**, **teléfono** y el **correo** deben también tener un formato válido, debiendo comprobar que la letra del **DNI** sea correcta. Debes crear las constantes para las expresiones regulares que luego utilizarás en los métodos de modificación. Los métodos de modificación lanzarán las excepciones adecuadas en caso de que el valor que se pretenda asignar al atributo no sea adecuado. 
6. Crea el método `getIniciales`. Este método devolverá las iniciales del nombre.
7. Ten presente que para que se pueda registrar el alumnado en el sistema, este debe tener como mínimo 16 años (**MIN_EDAD_ALUMNADO**).
8. Crea el **constructor con parámetros** que hará uso de los métodos de modificación.
8. Crea el **constructor copia**.
9. Crea los métodos `equals` y `hashCode`, sabiendo que dos alumnos se considerarán iguales si su DNI es el mismo.
10. Crea el método `imprimir` para que devuelva la cadena que esperan los tests.
11. Crea el método `toString` que devuelva la cadena que esperan los tests.
11. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Curso
1. Crea el enumerado `Curso` teniendo en cuenta que sus posibles valores son **PRIMERO** y **SEGUNDO**. 
2. Crea el atributo `cadenaAMostrar` (String).
3. Crea el **constructor** con la visibilidad adecuada, que almacenará en el atributo anterior el dato pasado por parámetro.
4. Crea el método `imprimir` indicado en el diagrama de clases que deberá devolver una cadena con el formato `dígito.-cadenaAMostrar`, para que se use en las opciones de menú. (0.-Primero y 1.-Segundo).
5. Crea el método **toString** que devuelva la cadena que esperan los tests.
6. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Grado
1. Crea el enumerado `Grado` teniendo en cuenta que sus posibles valores son los indicados con anterioridad.
2. Crea el atributo `cadenaAMostrar` (String).
3. Crea el **constructor** con la visibilidad adecuada, que almacenará en el atributo anterior el dato pasado por parámetro.
4. Crea el método `imprimir` indicado en el diagrama de clases que deberá devolver una cadena con el formato `dígito.-cadenaAMostrar`, para que se use en las opciones de menú.
5. Crea el método **toString** que devuelva la cadena que esperan los tests.
6. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### CicloFormativo
1. Crea la clase `CicloFormativo` con los atributos y visibilidad adecuados.
2. Crea los métodos de acceso y modificación de cada atributo con la visibilidad adecuada, teniendo en cuenta que el **identificador** de un ciclo formativo será un número de cuatro dígitos.  Además, debes crear las constantes indicadas en el diagrama de clases y que luego utilizarás en los métodos de modificación, sabiendo que el número máximo de horas de un ciclo formativo son 2000 horas. Los métodos de modificación lanzarán las excepciones adecuadas en caso de que el valor que se pretenda asignar al atributo no sea adecuado.
3. Crea el **constructor con parámetros** que hará uso de los métodos de modificación.
4. Crea el **constructor copia**. 
5. Un ciclo formativo será igual a otro si su identificador es el mismo. Basándote en ello crea los métodos `equals` y `hashCode`.
6. Crea el método `imprimir` que devuelva la cadena que esperan los tests.
7. Crea el método `toString` que devuelva la cadena que esperan los tests.
8. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### EspecialidadProfesorado
1. Crea el enumerado `EspecialidadProfesorado`teniendo en cuenta que cada instancia del enumerado puede ser el indicado en el diagrama de clases.
2. Crea el atributo con la visibilidad adecuada.
3. Crea el **constructor** con la visibilidad adecuada, que almacenará en el atributo anterior el dato pasado por parámetro.
4. Crea el método `imprimir` indicado en el diagrama de clases que deberá devolver una cadena con el formato `dígito.-cadenaAMostrar`, para que se use en las opciones de menú.
5. Crea el método `toString` que devuelva la cadena que esperan los tests.
6. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un commit.

#### Asignatura
1. Crea la clase `Asignatura` con los atributos y visibilidad adecuados.
2. Crea los métodos de acceso y modificación de cada atributo, teniendo en cuenta que el identificador de una ciclo formativo será un número de cuatro dígitos.  Además, debes crear las constantes indicadas en el diagrama de clases y que luego utilizarás en los métodos de modificación, sabiendo que el número máximo de horas anuales de una asignatura son 300 horas y que el número máximo de horas de desdoble son 6. Los métodos de modificación lanzarán las excepciones adecuadas en caso de que el valor que se pretenda asignar al atributo no sea adecuado.
3. Crea el **constructor con parámetros** que hará uso de los métodos de modificación.
4. Crea el **constructor copia**.
5. Una asignatura será igual a otra si si su identificador es el mismo. Basándote en ello crea los métodos `equals` y `hashCode`.
6. Crea el método `imprimir` que devuelva la cadena que esperan los tests. 
7. Crea el método `toString` que devuelva la cadena que esperan los tests. 
8. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Matrícula
1. Crea la clase `Matricula` con los atributos y visibilidad adecuados.
2. Crea los métodos de acceso y modificación de cada atributo, teniendo en cuenta que:
   1. El número máximo de horas de las asignaturas de una matrícula son 1000.
   2. El número máximo de asignaturas de una matrícula son 10.
   3. Como máximo, una matrícula se debe registrar con 15 días de retraso.
   4. La fecha de anulación de una matrícula no puede superar los 6 meses.
   5. El identificador de una matrícula debe ser un número positivo.
   6. El curso académico debe tener el formato dd-dd, por ejemplo, 23-24.
3. Crea el método `superaMaximoNumeroHorasMatricula` que deberá chequear si el total de horas de las asignaturas de la matrícula supera o no el máximo permitido.
4. Crea el **constructor con parámetros** que hará uso de los métodos de modificación.
4. Crea el **constructor copia**.
5. Una matrícula será igual a otra si su identificador es el mismo. Basándote en ello crea los métodos `equals` y `hashCode`.
6. Crea el método `imprimir` que devuelva la cadena que esperan los tests.
7. Crea el método `toString` que devuelva la cadena que esperan los tests.
8. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Alumnos
1. Crea la clase `Alumno`, en el paquete adecuado, con los atributos y métodos especificados en el diagrama y la visibilidad adecuada.
    1. Crea el **constructor con parámetros** que creará una lista de la capacidad indicada en el parámetro e inicializará los atributos de la clase a los valores correspondientes.
    2. Crea el método `get` que devolverá una copia profunda de la colección haciendo uso del método `copiaProfundaHuespedes`.
    3. Se permitirán `insertar` huéspedes no nulos al final de la colección sin admitir repetidos.
    4. El método `buscar` devolverá un huésped si éste se encuentra en la colección y `null` en caso contrario.
    5. El método `borrar`, si el huésped se encuentra en la colección, lo borrará y desplazará los elementos hacia la izquierda para dejar el array compactado.
2. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### CiclosFormativos
1. Crea la clase `CiclosFormativos`, en el paquete adecuado, con los atributos y métodos especificados en el diagrama y la visibilidad adecuada.
    1. Crea el **constructor con parámetros** que creará una lista de la capacidad indicada en el parámetro e inicializará los atributos de la clase a los valores correspondientes.
    2. Crea el método `get` que devolverá una copia profunda de la colección haciendo uso del método `copiaProfundaHabitaciones`.
    3. Se permitirán `insertar` habitaciones no nulas al final de la colección sin admitir repetidos.
    4. El método `buscar` devolverá una habitación si ésta se encuentra en la colección y null en caso contrario.
    5. El método `borrar`, si la habitación se encuentra en la colección, la borrará y desplazará los elementos hacia la izquierda para dejar el array compactado.
2. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Asignaturas
1. Crea la clase `Asignaturas`, en el paquete adecuado, con los atributos y métodos especificados en el diagrama y la visibilidad adecuada.
    1. Crea el **constructor con parámetros** que creará una lista de la capacidad indicada en el parámetro e inicializará los atributos de la clase a los valores correspondientes.
    2. Crea el método `get` que devolverá una copia profunda de la colección haciendo uso del método `copiaProfundaHabitaciones`.
    3. Se permitirán `insertar` habitaciones no nulas al final de la colección sin admitir repetidos.
    4. El método `buscar` devolverá una habitación si ésta se encuentra en la colección y null en caso contrario.
    5. El método `borrar`, si la habitación se encuentra en la colección, la borrará y desplazará los elementos hacia la izquierda para dejar el array compactado.
2. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Matriculas
1. Crea la clase `Matriculas`, en el paquete adecuado, con los atributos y métodos especificados en el diagrama y la visibilidad adecuada.
    1. Crea el **constructor con parámetros** que creará una lista de la capacidad indicada en el parámetro e inicializará los atributos de la clase a los valores correspondientes.
    2. Crea el método `get` que devolverá una copia profunda de la colección haciendo uso del método `copiaProfundaReservas`.
    3. Se permitirán `insertar` reservas no nulas al final de la colección sin admitir repetidos.
    4. El método `buscar` que devolverá el alquiler si éste se encuentra en la lista y null en caso contrario.
    5. El método `borrar`, si la reserva se encuentra en la colección, la borrará y desplazará los elementos hacia la izquierda para dejar el array compactado.
2. El método `get` que está sobrecargado y devolverá una colección de las matrículas realizadas por el alumno pasado por parámetro o una colección de las matrículas realizadas para el curso académico indicado como parámetro o una colección de las matrículas realizadas para el ciclo formativo indicado como parámetro.
3. Comprueba que la **clase pasa los test** para la misma y cuando lo haga realiza un **commit**.

#### Opcion
1. Crea el enumerado `Opcion` en el paquete indicado en el diagrama cuyas instancias corresponderán con cada una de las opciones del menú de nuestra aplicación. Cada instancia aceptará en su constructor una cadena correspondiente al mensaje que se mostrará por pantalla para dicha opción. Debes crear los atributos y métodos que se especifican en el diagrama, teniendo en cuenta que el método toString devolverá la cadena correspondiente a la concatenación del ordinal de la opción, la cadena " .- " y el mensaje a mostrar por la opción en cuestión.
2. Realiza un **commit** con la creación de este enumerado.

#### Consola
1. Crea la clase `Consola` en el paquete indicado en el diagrama.
2. Crea su **constructor** teniendo en cuenta que es una clase de utilidades y que no tiene sentido instanciar objetos de la misma.
3. Crea el método `mostrarMenu` que mostrará las diferentes opciones de nuestro programa de forma automática teniendo en cuenta cada uno de los objetos instancias del Enumerado Opcion (insertar, buscar, borrar, ...).
4. Crea el método `elegirOpcion` que pedirá que elijamos la opción y devolverá la instancia del enumerado `Opcion` correspondiente.
5. Crea el método `leerAlumno` que nos pedirá los datos correspondientes a un alumno y devolverá un objeto instancia de dicha clase en el caso que los datos introducidos sean correctos o propague la excepción correspondiente en caso contrario.
6. Crea el método `leerAlumnoPorDni` que nos pedirá el dni de un alumno y devolverá un objeto instancia de dicha clase con el dni introducido y con el resto de datos ficticios cumpliendo las restricciones de creación de un alumno. En caso contrario, deberá propagar la excepción correspondiente.
7. Crea el método `leerFecha` que nos pedirá que introduzcamos una cadena correspondiente a una fecha en el formato adecuado y devolverá el objeto `LocalDate` correspondiente. Esto se repetirá mientras la fecha introducida no sea válida.
8. Crea el método `leerGrado` que mostrará la lista de grados existentes y devolverá un objeto instancia de dicha clase.
9. Crea el método `leerCicloFormativo` que nos pedirá los datos correspondientes a un ciclo formativo y devolverá un objeto instancia de dicha clase en el caso que los datos introducidos sean correctos o propague la excepción correspondiente en caso contrario.
10. Crea el método `mostrarCiclosFormativos` que mostrará la lista de ciclos formativos que en ese momento estén registrados en el sistema.
11. Crea el método `getCicloFormativoPorCodigo` que nos pedirá el código de un ciclo formativo y devolverá una instancia de dicha clase con el código introducido y con el resto de datos ficticios cumpliendo las restricciones de creación de un ciclo formativo. En caso contrario, deberá propagar la excepción correspondiente.
12. Crea el método `leerCurso` que mostrará la lista de cursos existentes y devolverá un objeto instancia de dicha clase.
13. Crea el método `leerEspecialidadProfesorado` que mostrará la lista de especialidades existentes y devolverá un objeto instancia de dicha clase.
14. Crea el método `leerAsignatura` que nos pedirá los datos correspondientes a una asignatura y devolverá un objeto instancia de dicha clase en el caso que los datos introducidos sean correctos o propague la excepción correspondiente en caso contrario.
15. Crea el método `getAsignaturaPorCodigo` que nos pedirá el código de una asignatura y devolverá una instancia de dicha clase con el código introducido y con el resto de datos ficticios cumpliendo las restricciones de creación de una asignatura. En caso contrario, deberá propagar la excepción correspondiente.
16. Crea el método `mostrarAsignaturas` que mostrará la lista de asignaturas que en ese momento estén registrados en el sistema.
17. Crea el método `asignaturaYaMatriculada` que indicará si en la lista de asignaturas pasada como parámetro, existe la asignatura también pasada como parámetro.
18. Crea el método `leerMatricula` que nos pedirá los datos correspondientes a una matrícula y devolverá un objeto instancia de dicha clase en el caso que los datos introducidos sean correctos o propague la excepción correspondiente en caso contrario. Hay que tener en cuenta, que en la misma matrícula no puede aparecer más de una vez la misma asignatura.
19. Crea el método `getMatriculaPorIdentificador` que nos pedirá el identificador de una matrícula y devolverá una instancia de dicha clase con el código introducido y con el resto de datos ficticios cumpliendo las restricciones de creación de una matrícula. En caso contrario, deberá propagar la excepción correspondiente.
20. Realiza el **commit** correspondiente.

#### MainApp
1. Crea la clase `MainApp` en el paquete indicado en el diagrama.
    1. Crea los diferentes atributos que se indican en el diagrama de clases con su visibilidad adecuada.
    2. Crea el método `ejecutarOpcion` que dada una opción, la ejecute.
    3. Crea el método `insertarAlumno` que nos pedirá los datos de un alumno, haciendo uso de la clase Consola, y lo insertará en la colección correspondiente si es posible o informará del problema en caso contrario.
    4. Crea el método `buscarAlumno` que nos pedirá solo el dni de un alumno haciendo uso de la clase Consola, y nos mostrárá a dicho alumno o informará de que no existe o informará del problema de acontecido.
    5. Crea el método `borrarAlumno` que nos pedirá solo el dni de un alumno haciendo uso de la clase Consola, y lo borrará si es posible de la lista de alumnos o informará del problema en caso contrario.
    6. Crea el método `mostrarAlumnos` que mostrará todos los alumnos almacenados, si es que hay, o si no, nos informará de que no hay alumnos.
    7. Crea el método `insertarAsignatura` que nos pedirá los datos de una asignatura, haciendo uso de la clase Consola, y lo insertará en la colección correspondiente si es posible o informará del problema en caso contrario.
    8. Crea el método `buscarAsignatura` que nos pedirá **solo** el código de una asignatura haciendo uso de la clase Consola, y nos mostrará dicha asignatura o informará de que no existe o informará del problema acontecido.
    9. Crea el método `borrarAsginatura` que nos pedirá **solo** el código de una asignatura haciendo uso de la clase Consola, y lo borrará si es posible o informará del problema en caso contrario.
    10. Crea el método `mostrarAsignaturas` que mostrará todos las asignaturas almacenadas, si es que hay, o si no, nos informará de que no hay asignaturas.
    11. Crea el método `insertarCicloFormativo` que nos pedirá los datos de un ciclo formativo, haciendo uso de la clase Consola, y lo insertará en la colección correspondiente si es posible o informará del problema en caso contrario.
    12. Crea el método `buscarCicloFormativo` que nos pedirá **solo** el código del ciclo formativo haciendo uso de la clase Consola, y nos mostrará dicho ciclo formativo o informará de que no existe o informará del problema acontecido.
    13. Crea el método `borrarCicloFormativo` que nos pedirá **solo** el código de un ciclo formativo haciendo uso de la clase Consola, y lo borrará si es posible o informará del problema en caso contrario.
    14. Crea el método `mostrarCiclosFormativos` que mostrará todos los ciclos formativos almacenados, si es que hay, o si no, nos informará de que no hay ciclos formativos. 
    15. Crea el método `insertarMatricula` que nos pedirá los datos de una matrícula haciendo uso de la clase Consola, y lo insertará en la colección correspondiente si es posible o informará del problema en caso contrario.
    16. Crea el método `buscarMatricula` que nos pedirá **solo** el identificador de una matrícula haciendo uso de la clase Consola, y nos mostrará dicha matrícula o informará de que no existe o informará del problema acontecido.
    17. Crea el método `anularMatrícula` que mostrará la lista de matrículas existentes, debiéndose elegir la que se desea anular y una vez elegida, deberá establecer como fecha de anulación de dicha matrícula, la introducida por el usuario. En el caso de que no pueda ser anulable deberá de informarse de dicha circunstancia. 
    18. Crea el método `mostrarMatriculas` que mostrará todas las matrículas almacenadas, si es que hay o si no, nos informará de que no hay matrículas.
    19. Crea el método `mostrarMatriculasPorAlumno` que mostrará todas las matrículas que el alumno indicado tiene registradas en el sistema. Si no tuviese nos informará de que no hay matrículas. Para indicar el alumno del que se desea obtener sus matrículas se deberá indicar **solo** el dni del alumno. Se hará uso de los métodos correspondientes de la clase `Consola`. 
    20. Crea el método `mostrarMatriculasPorCicloFormativo` que mostrará todas las matrículas que el ciclo formativo indicado tiene registradas en el sistema. Si no tuviese nos informará de que no hay matrículas. Para indicar el ciclo formativo del que se desea obtener sus matrículas se deberá mostrar la lista de ciclos formativos existentes e indicar solo el código del ciclo. Se hará de los métodos correspondientes de la clase `Consola`.
    21. Crea el método `mostrarMatriculasPorCursoAcademico` que mostrará todas las matrículas del curso académico indicado. Si no tuviese nos informará de que no hay matrículas. 
    22. Crea el método `main` que nos mostrará el menú de la aplicación, nos pedirá una opción y la ejecutará mientras no elijamos la opción salir. En caso de salir, la aplicación mostrará un mensaje de despedida. 
    23. Realiza un **commit** con la creación de esta clase.
    19. Finalmente, realiza el **push** hacia tu repositorio remoto en GitHub.

#### Se valorará:

- La indentación debe ser correcta en cada uno de los apartados.
- El nombre de las variables debe ser adecuado.
- Toda la implementación realizada deberá ajustarse a lo indicado en este apartado y a lo mostrado en el diagrama de clase. En caso contrario, lo que se use que no esté indicado en el diagrama de clase no será considerado y por tanto, la parte correspondiente no será evaluada.
- Se debe utilizar la clase `Entrada` para realizar la entrada por teclado.
- El programa debe pasar todas las pruebas que van en el esqueleto del proyecto y toda entrada del programa será validada, para evitar que el programa termine abruptamente debido a una excepción. Además, que ni decir tiene, el programa no debe contener ningún error léxico, sintáctico, de dependencias, etc.
- La corrección ortográfica tanto en los comentarios como en los mensajes que se muestren al usuario.
- **Para calificar cada uno de los criterios de evaluación asociados a la tarea será imprescindible que el código compile correctamente y se pueda ejecutar. En caso contrario, los criterios de evaluación serán calificados con un 0.**

