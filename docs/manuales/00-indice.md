# Manuales: construir el proyecto paso a paso

Estas prácticas siguen una ruta de aprendizaje. Usa el código actual como referencia y agrega cada pieza en el orden indicado. La secuencia explica **cómo construir un proyecto como este**; no afirma el orden histórico exacto en que se escribió.

Cada práctica responde cuatro preguntas: **qué queremos conseguir, qué significa el código nuevo, por qué se organiza así y cómo verificamos que funciona**. Los ejemplos usan las clases existentes; se puede seguir el recorrido sin instalar una base de datos.

| Orden | Práctica | Lo que queda listo |
| --- | --- | --- |
| 1 | [Preparar el proyecto](01-proyecto-y-arquitectura.md) | Carpetas y punto de entrada. |
| 2 | [Crear modelos](02-modelos.md) | Clase `Empresa` y objetos distintos. |
| 3 | [Agregar getters y setters](03-getters-y-setters.md) | Acceso a los atributos. |
| 4 | [Agregar constructores](04-constructores.md) | Datos iniciales al usar `new`. |
| 5 | [Aplicar herencia](05-herencia-y-polimorfismo.md) | `Persona`, `Employee` y `Cliente`. |
| 6 | [Mostrar objetos](06-to-string-y-print.md) | `toString()` y `print()`. |
| 7 | [Entender referencias](07-referencias.md) | Qué pasa al compartir objetos. |
| 8 | [Crear repositorios](08-arraylist-y-crud.md) | CRUD con `ArrayList`. |
| 9 | [Agregar servicios](09-servicios.md) | Validar una operación antes de guardar en la lista. |
| 10 | [Introducir Maven](10-maven.md) | Organizar compilación y dependencias. |
| 11 | [Crear tablas y practicar SQL](11-base-de-datos-y-sql.md) | Relacionar objetos y filas. |
| 12 | [Conectar con JDBC](12-jdbc.md) | Insertar y consultar datos persistentes. |

**Cómo leerlos:** cada manual indica el resultado esperado, define los conceptos necesarios, explica la decisión de diseño, da pasos concretos y termina con una comprobación.

Si estás reconstruyendo el proyecto desde cero, crea primero los modelos y después los repositorios. Si ya tienes esta carpeta, usa los enlaces a los archivos Java para localizar cada fragmento y comparar tu explicación con la implementación real.

**Antes de comenzar:** necesitas un JDK e IntelliJ IDEA o una terminal. Se recomienda conocer variables, tipos y métodos básicos de Java. No necesitas Spring ni una base de datos para estas prácticas.

[Volver al README](../../README.md)

Los manuales 9 a 12 son ampliaciones para realizar en clase. Sus clases, conversión a Maven y conexión de base de datos todavía no están aplicadas al código de este repositorio. La base de datos y su controlador se elegirán al preparar esas prácticas.
