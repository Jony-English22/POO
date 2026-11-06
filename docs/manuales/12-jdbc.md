# 12. Guardar y consultar datos con JDBC

[Anterior](11-base-de-datos-y-sql.md) · [Índice](00-indice.md)

En esta práctica conectarás Java con la tabla introductoria `empresas`. Primero realizarás una inserción y una consulta. Después completarás el CRUD y todos los campos antes de sustituir el repositorio en memoria de la aplicación.

## Antes de comenzar

Completa la conversión a Maven, crea la tabla del manual 11 y verifica que puedes consultarla desde el gestor. Necesitas la URL JDBC de esa base, usuario y contraseña, además del controlador que publica el fabricante del motor elegido. Su documentación indica los valores de `groupId`, `artifactId` y `version` que debes agregar dentro de `<dependencies>` en `pom.xml`.

La API JDBC viene con Java en `java.sql`. El controlador es la biblioteca externa que implementa la comunicación con la base elegida. Añadir un `import java.sql.Connection` permite nombrar la API, pero no instala ese controlador.

## Qué es JDBC y por qué lo necesitamos

JDBC significa *Java Database Connectivity*. Es la API que permite a un programa Java abrir una conexión, enviar instrucciones SQL y leer sus resultados. La base de datos conserva los valores; JDBC es el medio que utiliza el programa para comunicarse con ella.

Hasta ahora, `agregarEmpresa` conserva una referencia dentro de un `ArrayList`. Con JDBC, esa operación enviará un `INSERT` para guardar valores en una tabla. Al consultar, un `SELECT` devolverá filas y el código Java construirá objetos a partir de ellas. No se guarda directamente el objeto completo ni sus métodos: se guardan los valores de las columnas que definimos.

El controlador adapta las llamadas de JDBC al motor elegido. La URL le indica qué base abrir y dónde está. Su formato depende del fabricante y del modo de conexión: por eso la práctica requiere elegir el motor y revisar su documentación antes de completar la configuración.

## Cómo se relaciona con el servicio

El servicio sigue decidiendo si una operación cumple las reglas del negocio. Por ejemplo, comprobar que el nombre sea obligatorio pertenece al registro de una empresa. Preparar un `INSERT`, establecer sus parámetros y leer las filas pertenece al repositorio JDBC.

El recorrido que buscamos es `aplicación → servicio → repositorio JDBC → base de datos`. El ejemplo de este manual trabaja primero con el repositorio de forma aislada para entender la conexión. Su integración con servicios vendrá después de completar los campos y acordar un contrato común de repositorio.

## Conceptos

| Elemento | Para qué sirve |
| --- | --- |
| `Connection` | Representa una conexión abierta con la base. |
| `PreparedStatement` | Ejecuta SQL con parámetros en los lugares marcados con `?`. |
| `ResultSet` | Permite recorrer las filas devueltas por una consulta. |
| `SQLException` | Informa de un problema al acceder a la base. |
| `try` con recursos | Cierra conexiones y consultas al terminar, incluso si ocurre un error. |

Una conexión representa una comunicación activa, así que necesita cerrarse. Lo mismo ocurre con la consulta y su resultado. La sintaxis `try (recurso...)` hace que Java los cierre al salir del bloque, incluso si se lanza una excepción; se cierran en orden inverso al de apertura. Por eso en el ejemplo el `ResultSet` se recorre dentro del bloque que mantiene abierta su conexión.

## Crear un repositorio JDBC de práctica

1. Conserva el repositorio actual para comparar ambos almacenamientos.
2. Después de migrar a Maven, crea `EmpresaJdbcRepository.java` bajo `src/main/java/com/twdev/poo/company/repository`.
3. Usa esta implementación introductoria para insertar y listar las tres columnas de la tabla:

```java
package com.twdev.poo.company.repository;

import com.twdev.poo.company.model.Empresa;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpresaJdbcRepository {
    private final String url;
    private final String usuario;
    private final String clave;

    public EmpresaJdbcRepository(String url, String usuario, String clave) {
        this.url = url;
        this.usuario = usuario;
        this.clave = clave;
    }

    public void agregarEmpresa(Empresa empresa) throws SQLException {
        String sql = "INSERT INTO empresas (id, nombre, rfc) VALUES (?, ?, ?)";
        try (Connection conexion = DriverManager.getConnection(url, usuario, clave);
             PreparedStatement consulta = conexion.prepareStatement(sql)) {
            consulta.setInt(1, empresa.getId());
            consulta.setString(2, empresa.getNombre());
            consulta.setString(3, empresa.getRfc());
            consulta.executeUpdate();
        }
    }

    public List<Empresa> obtenerEmpresas() throws SQLException {
        List<Empresa> empresas = new ArrayList<>();
        String sql = "SELECT id, nombre, rfc FROM empresas ORDER BY id";
        try (Connection conexion = DriverManager.getConnection(url, usuario, clave);
             PreparedStatement consulta = conexion.prepareStatement(sql);
             ResultSet filas = consulta.executeQuery()) {
            while (filas.next()) {
                Empresa empresa = new Empresa();
                empresa.setId(filas.getInt("id"));
                empresa.setNombre(filas.getString("nombre"));
                empresa.setRfc(filas.getString("rfc"));
                empresas.add(empresa);
            }
        }
        return empresas;
    }
}
```

`executeUpdate()` realiza una modificación; `executeQuery()` devuelve filas. El recorrido de `ResultSet` crea un objeto por fila: este paso es el **mapeo** entre valores SQL y atributos Java. La consulta utiliza parámetros para enviar los valores sin concatenarlos al SQL.

## Entender la inserción

1. `DriverManager.getConnection(...)` pide al controlador una conexión utilizando la URL y las credenciales recibidas por el constructor.
2. `prepareStatement(sql)` prepara una instrucción con tres posiciones para valores.
3. `setInt(1, ...)` asigna el ID al primer `?`. `setString(2, ...)` y `setString(3, ...)` asignan nombre y RFC. Las posiciones empiezan en **1** y siguen el orden del SQL.
4. `executeUpdate()` envía la modificación y devuelve el número de filas afectadas. En una inserción como esta normalmente esperamos una fila.
5. Al salir del bloque se cierran la consulta y la conexión.

El ejemplo requiere un ID explícito que no exista en la tabla. Si `empresa.getId()` es `null`, el desempaquetado a `int` falla antes de enviar la instrucción; el servicio o el ejemplo que construye los datos debe comprobar ese requisito. El repositorio en memoria asignaba IDs automáticamente, pero esta implementación introductoria todavía no lo hace.

Los parámetros separan el texto SQL de los datos que se envían. Un nombre con apóstrofo se entrega como valor mediante `setString`, sin tener que construir el SQL concatenando ese texto. Los `?` representan valores; no sustituyen nombres de tablas o columnas.

## Entender la consulta y el mapeo

El cursor de `ResultSet` comienza antes de la primera fila. Cada llamada a `filas.next()` avanza y devuelve `true` si hay una fila que leer. Si la consulta no encuentra registros, el ciclo no se ejecuta y el método devuelve una lista vacía.

Dentro del ciclo, `new Empresa()` crea un objeto diferente para esa fila. `getInt("id")` y los `getString(...)` leen columnas por su nombre; los setters copian esos valores al objeto. Después se agrega el objeto a la lista que recibirá quien hizo la consulta.

Este método todavía devuelve un `List<Empresa>`, igual que el repositorio en memoria. Sin embargo, ahora el `ArrayList` es solo el contenedor del resultado: la fuente persistente de los datos es la tabla. Los campos que no están en esta tabla introductoria conservan los valores por defecto del objeto, por eso el ejemplo aún no equivale al modelo completo.

## Conectar y comprobar

Desde un ejemplo de consola, construye el repositorio con la configuración de tu base. Obtén los datos de conexión de la configuración del entorno y evita escribir contraseñas en el código compartido. Crea una empresa con ID explícito, nombre y RFC; llama a `agregarEmpresa` y luego a `obtenerEmpresas`. Captura `SQLException` o declárala en el ejemplo para que Java permita compilarlo.

Ejecuta desde el IDE con las dependencias Maven cargadas. Cuando se añade un controlador externo, `java -cp target/classes ...` por sí solo no incluye su JAR. La configuración de ejecución debe incluir también las dependencias.

## Distinguir los tipos de error

`throws SQLException` avisa que la operación puede fallar por una causa de acceso a datos. El código que la llama debe capturar la excepción o declararla. Un servicio integrado podría traducir ese fallo a un error de la aplicación; no debería informar que el registro se guardó si la inserción falló.

| Problema observado | Qué revisar |
| --- | --- |
| No se encuentra un controlador adecuado. | Dependencia instalada, JAR disponible al ejecutar y formato de URL. |
| No se puede abrir la conexión. | Motor en ejecución cuando corresponda, dirección, base y credenciales. |
| No existe la tabla o una columna. | Base seleccionada y esquema creado en el manual 11. |
| ID duplicado. | Registro ya insertado y estrategia de generación de IDs. |

En este primer ejemplo se abre una conexión para cada operación. Una conexión JDBC normalmente comienza con confirmación automática: cada instrucción completada se confirma por separado. Si una operación del negocio necesita varias modificaciones como una sola unidad, habrá que estudiar transacciones, `commit` y `rollback`. Cerrar una conexión no sustituye esas decisiones de confirmación.

**Comprueba:** inserta una empresa una sola vez y, en otra ejecución, realiza solo la consulta. Debe continuar guardada. Reinsertar el mismo ID produce un conflicto de clave primaria: es una comprobación distinta de consultar datos existentes.

## Completar la migración

Agrega búsqueda por ID, actualización con `UPDATE ... WHERE id = ?` y eliminación con `DELETE ... WHERE id = ?`. Usa el número de filas afectadas para saber si se encontró el ID. Después incorpora todos los atributos de `Empresa` y decide cómo generará IDs el motor elegido.

La implementación de práctica todavía no sustituye a `EmpresaRepository`: tiene un alcance menor y expone errores SQL. Al integrar el almacenamiento con servicios, conviene definir una interfaz común y decidir cómo comunicar los fallos. Así el servicio podrá depender del contrato y elegir entre lista o JDBC. Ese será el puente hacia la gestión de dependencias de Spring.

Consulta la [referencia oficial de JDBC](https://docs.oracle.com/en/java/javase/25/docs/api/java.sql/java/sql/package-summary.html).
