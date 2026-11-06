# POO en Java: modelos y repositorios en memoria

Proyecto de clase para aprender a construir una aplicación de consola con objetos. Representa **empresas, empleados y clientes**. Los repositorios guardan los datos en `ArrayList` mientras el programa está abierto; al cerrarlo, se pierden. Todavía no utiliza Spring, JDBC ni una base de datos.

El objetivo es avanzar desde las clases y los objetos hasta un CRUD en memoria. La documentación muestra las decisiones de cada etapa para que se pueda reproducir el ejercicio y explicar su funcionamiento en clase.

**Para quién es:** estudiantes que comienzan programación orientada a objetos en Java. Conviene conocer variables, tipos de datos, métodos y la ejecución básica de un programa Java.

## Ejecutar

1. Abre esta carpeta en IntelliJ IDEA y selecciona un JDK. Si hace falta, marca `src` como **Sources Root**.
2. Ejecuta `com.twdev.poo.PooApplication`.
3. Observa en la consola cómo se crean, consultan, actualizan y eliminan empleados, y cómo se muestran clientes y empresas.

Desde una terminal, en la raíz del proyecto, usa las herramientas del JDK:

```text
javac -d out "@docs/recursos/fuentes.txt"
java -cp out com.twdev.poo.PooApplication
```

En Visual Studio Code, abre la carpeta completa con un JDK y Extension Pack for Java instalados; selecciona **Run** sobre `main` en `PooApplication.java`. La lista `fuentes.txt` corresponde a las clases actuales y debe ampliarse al agregar archivos.

## Manuales

La guía explica **qué archivo crear, por qué se crea y cómo comprobarlo**. Cada práctica introduce solo los conceptos que necesita. Comienza en el [índice de manuales](docs/manuales/00-indice.md).

| Práctica | Resultado |
| --- | --- |
| [1. Proyecto y carpetas](docs/manuales/01-proyecto-y-arquitectura.md) | Ubicar la aplicación y sus responsabilidades. |
| [2. Modelos](docs/manuales/02-modelos.md) | Crear una clase y varios objetos. |
| [3. Getters y setters](docs/manuales/03-getters-y-setters.md) | Leer y cambiar atributos. |
| [4. Constructores](docs/manuales/04-constructores.md) | Crear objetos con datos iniciales. |
| [5. Herencia y polimorfismo](docs/manuales/05-herencia-y-polimorfismo.md) | Compartir datos y redefinir métodos. |
| [6. `toString` y `print`](docs/manuales/06-to-string-y-print.md) | Mostrar datos legibles. |
| [7. Referencias](docs/manuales/07-referencias.md) | Entender cómo se comparten los objetos. |
| [8. `ArrayList` y CRUD](docs/manuales/08-arraylist-y-crud.md) | Guardar y gestionar datos en memoria. |
| [9. Servicios](docs/manuales/09-servicios.md) | Aplicar reglas antes de guardar. |
| [10. Maven](docs/manuales/10-maven.md) | Preparar compilación y dependencias. |
| [11. Base de datos y SQL](docs/manuales/11-base-de-datos-y-sql.md) | Relacionar objetos con tablas. |
| [12. JDBC](docs/manuales/12-jdbc.md) | Conectar Java y consultar datos persistentes. |

Las prácticas 9 a 12 documentan los pasos siguientes del curso; aún no están implementadas en el código actual.

## Estructura actual

```text
src/com/twdev/poo/
├── PooApplication.java       ejemplo ejecutable
├── persona/model/            datos comunes de personas
├── employeer/model/          empleado y área
├── employeer/repository/     CRUD de empleados
├── customer/model/           cliente y tipo
├── customer/repository/      CRUD de clientes
├── company/model/            empresa
└── company/repository/       CRUD de empresas
```

Las carpetas separan modelos y repositorios dentro de un solo programa Java. El nombre existente `employeer` debe escribirse igual en carpetas, paquetes e imports. La aplicación aún no tiene controlador web.

## Cómo funciona hoy

`PooApplication` crea los repositorios y agrega datos de ejemplo. Cada repositorio guarda objetos del mismo tipo en su propia lista, asigna un ID consecutivo y ofrece métodos para crear, leer, actualizar y eliminar. Al imprimir una lista, Java usa el `toString()` de cada modelo para mostrar sus datos.

`Employee` y `Cliente` heredan de `Persona`: comparten ID, nombre, teléfono y correo. `Empresa` es independiente porque representa otro tipo de entidad. Los atributos privados se leen y modifican mediante getters y setters; los constructores permiten crear objetos con valores iniciales.

La aplicación es **monolítica**: todos los paquetes se ejecutan juntos. Los paquetes separan responsabilidades dentro del código; no son servicios distintos. Cuando el curso avance a JDBC o Spring Boot, los modelos y la idea de repositorio servirán como punto de partida, aunque cambie la forma de guardar los datos.
