# 1. Organizar el proyecto y sus responsabilidades

[Índice](00-indice.md) · [Siguiente](02-modelos.md)

En esta práctica organizarás una aplicación de consola para manejar empresas, empleados y clientes. Antes de crear clases, conviene decidir qué representa cada archivo y dónde encontrarlo.

## Qué representa el proyecto

Los modelos describen los datos: una empresa tiene nombre y RFC; un empleado tiene nombre, puesto y salario. Los repositorios permiten guardar y consultar esos objetos. `PooApplication` inicia el programa y reúne los ejemplos que se ejecutan en clase.

Esta distribución es la arquitectura inicial: cada parte tiene una responsabilidad y colabora con las demás. Todas se ejecutan dentro del mismo programa Java, por lo que hablamos de una aplicación **monolítica**.

## Crear los paquetes

1. Dentro de `src`, crea el paquete base `com.twdev.poo`.
2. Crea los paquetes `company.model`, `employeer.model` y `customer.model` para las clases que representan cada entidad.
3. Crea `persona.model` para los datos compartidos entre empleados y clientes. La práctica de herencia explica esa relación.
4. Cuando llegues al almacenamiento, agrega `repository` dentro de cada tema que necesita guardar datos.

| Ubicación | Qué contiene | Ejemplo |
| --- | --- | --- |
| `company/model` | Datos y métodos del modelo empresa. | `Empresa.java` |
| `employeer/model` | Modelo empleado y catálogo de áreas. | `Employee.java`, `Area.java` |
| `customer/model` | Modelo cliente y catálogo de tipos. | `Cliente.java`, `TipoCliente.java` |
| `persona/model` | Datos comunes de personas. | `Persona.java` |
| `repository` de cada tema | Operaciones para guardar y consultar sus objetos. | `EmpleadoRepository.java` |
| Paquete base | Entrada y demostración del programa. | `PooApplication.java` |

Una carpeta de paquete corresponde a una declaración Java: `Employee.java` está bajo `com/twdev/poo/employeer/model` y declara `package com.twdev.poo.employeer.model;`. Para utilizarlo desde otro paquete, se importa ese nombre completo. Se conserva `employeer` porque es el nombre existente en el proyecto.

## Seguir el recorrido de los datos

Actualmente, `PooApplication` crea un empleado y lo entrega a `EmpleadoRepository`; el repositorio lo agrega a su `ArrayList`. El modelo contiene los datos y el repositorio controla el acceso a la lista. La carpeta `repository` expresa esa responsabilidad aunque todavía no haya una base de datos.

Más adelante agregaremos `service` para las reglas del negocio. Se creará al introducir esa responsabilidad, siguiendo el [manual de servicios](09-servicios.md). Los detalles de SQL, JDBC y Spring se incorporarán en sus propias prácticas.

**Comprueba:** si necesitas añadir un atributo al empleado, modifica su modelo; si necesitas buscarlo en la lista, revisa su repositorio. Localiza ambos archivos antes de continuar. Para ejecutar la aplicación, consulta el [README](../../README.md).
