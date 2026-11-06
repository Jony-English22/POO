# 10. Organizar la compilación con Maven

[Anterior](09-servicios.md) · [Índice](00-indice.md) · [Siguiente](11-base-de-datos-y-sql.md)

En esta práctica convertirás el proyecto Java en un proyecto Maven. La conversión se realizará al seguir las instrucciones; el repositorio actual todavía utiliza `src` directamente.

## Conceptos

Maven automatiza tareas como compilar, comprobar y empaquetar el programa. El archivo `pom.xml` describe el proyecto, su versión de Java y las bibliotecas externas que necesita. Una biblioteca externa se declara como **dependencia**; un **plugin** realiza tareas de construcción.

## Por qué incorporarlo antes de JDBC

Hasta ahora el JDK puede compilar nuestras clases sin bibliotecas externas. Para conectarnos con una base de datos necesitaremos el controlador del fabricante, que se distribuye como un archivo JAR. Podemos descargarlo y configurar sus rutas manualmente, pero repetir ese trabajo en cada equipo dificulta preparar el mismo proyecto para todos.

Maven lee las dependencias declaradas en `pom.xml`, las descarga desde repositorios de artefactos y las incorpora a las tareas que las necesitan. También puede resolver las bibliotecas que una dependencia necesita a su vez, llamadas dependencias transitivas. Por eso conviene aprenderlo antes de añadir el controlador JDBC.

Un repositorio Maven es un lugar que distribuye bibliotecas. Es un uso distinto de la palabra **repositorio** respecto a `EmpleadoRepository`, que guarda los datos del negocio. Tampoco una dependencia Maven equivale a una instancia: el POM consigue la biblioteca; el código Java crea y utiliza sus objetos.

| Ruta | Contenido |
| --- | --- |
| `src/main/java` | Clases de la aplicación. |
| `src/main/resources` | Archivos que se distribuyen con la aplicación. |
| `src/test/java` | Pruebas cuando se incorporen. |
| `target` | Archivos generados por Maven. |

Estas rutas son convenciones que Maven reconoce automáticamente. `src/main/java` contiene los archivos que escribimos; `target/classes` contiene el resultado compilado. Los paquetes siguen empezando en `com.twdev.poo`: mover las carpetas bajo `src/main/java` no exige renombrarlos.

`src/main/resources` sirve para recursos que deben acompañar a las clases, como una configuración de ejemplo. `src/test/java` se reserva para pruebas y no se mezcla con las clases principales. Las carpetas se pueden crear cuando se necesiten.

## Convertir el proyecto

1. Instala Maven y comprueba `mvn -version`. Debe reconocer el JDK elegido para el curso.
2. Mueve la carpeta `src/com` a `src/main/java/com`. Conserva los nombres de paquete y los imports.
3. Crea este `pom.xml` en la raíz de `POO`. El ejemplo conserva Java 25, la versión utilizada para comprobar el proyecto actual.

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.twdev</groupId>
    <artifactId>poo</artifactId>
    <version>1.0-SNAPSHOT</version>
    <properties>
        <maven.compiler.release>25</maven.compiler.release>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.14.1</version>
            </plugin>
        </plugins>
    </build>
</project>
```

4. En IntelliJ IDEA o VS Code, importa o recarga el proyecto Maven a partir del `pom.xml`.
5. Desde la raíz, ejecuta `mvn compile`. Para esta etapa, que aún no tiene bibliotecas externas de ejecución, inicia con `java -cp target/classes com.twdev.poo.PooApplication`.

`groupId` identifica el grupo del proyecto; `artifactId`, su nombre dentro de Maven; y `version`, su versión. Maven prepara `target/classes` al compilar. Actualiza también la documentación de rutas y agrega `target/` a los archivos ignorados cuando realices la conversión.

## Entender el archivo `pom.xml`

POM significa *Project Object Model*: es el documento que describe cómo se construye el proyecto. `modelVersion` indica el formato del POM, mientras que `version` identifica la versión de nuestra aplicación. `1.0-SNAPSHOT` expresa una versión todavía en desarrollo.

`maven.compiler.release` fija la versión de Java para compilar. El JDK usado por Maven debe admitir esa versión; compruébalo con `mvn -version`, porque puede diferir del JDK seleccionado en el editor. `project.build.sourceEncoding` establece UTF-8 para los archivos fuente.

El plugin `maven-compiler-plugin` realiza la compilación usando el compilador Java. Su número de versión corresponde al plugin, no a la versión de Java. En cambio, una dependencia se declara en `<dependencies>`, como hijo de `<project>`, porque es una biblioteca que utilizará nuestro programa. El controlador de la base de datos se agregará allí cuando se elija el motor.

## Qué hace cada comando

| Comando | Qué realiza |
| --- | --- |
| `mvn compile` | Compila las clases principales y deja el resultado en `target/classes`. |
| `mvn test` | Recorre las fases necesarias para ejecutar las pruebas configuradas. Si no existen pruebas, no demuestra por sí solo que el CRUD funcione. |
| `mvn package` | Compila, ejecuta las pruebas disponibles y empaqueta el resultado; en este proyecto, por defecto, como JAR. |
| `mvn clean` | Elimina los archivos generados en `target` para reconstruirlos. |

Las fases se ejecutan en orden: pedir `package` incluye las fases anteriores necesarias. Compilar no inicia `main`, y crear un JAR no lo convierte automáticamente en un JAR ejecutable con sus dependencias. En esta etapa usaremos el IDE o el comando Java indicado para iniciar la aplicación.

Al recargar el POM, IntelliJ IDEA y VS Code pueden reconocer las dependencias y las carpetas estándar. Ante un error, distingue si falla la descarga de una biblioteca, la compilación del código o la ejecución del programa: son pasos diferentes.

**Decisión:** introducir Maven antes de JDBC facilitará incorporar el controlador de la base de datos. Maven organiza la construcción; `ArrayList` seguirá siendo la estructura que guarda los objetos hasta la migración de almacenamiento.

**Comprueba:** la aplicación debe mostrar los mismos datos que antes de mover las carpetas. Cambió la organización de construcción, no las reglas del servicio.

Consulta la [guía de inicio de Maven](https://maven.apache.org/guides/getting-started/index.html) y su [estructura estándar](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout).
