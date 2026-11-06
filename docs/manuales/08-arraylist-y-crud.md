# 8. Crear repositorios con `ArrayList`

[Anterior](07-referencias.md) · [Índice](00-indice.md) · [Siguiente](09-servicios.md)

**Resultado esperado:** crear, consultar, actualizar y eliminar empleados mientras el programa está abierto. Ese conjunto de operaciones se llama **CRUD**.

## Conceptos

| Elemento | Función |
| --- | --- |
| `List<Employee>` | Declara una colección de empleados. |
| `ArrayList<>` | Implementa una lista que puede crecer. |
| Repositorio | Clase que reúne las operaciones sobre la lista. |
| ID | Número que identifica a cada objeto guardado. |

Hasta ahora creamos objetos y los mostramos. Para conservar varios durante la ejecución necesitamos una estructura de datos. `ArrayList` es adecuada para esta etapa porque permite agregar elementos y recorrerlos sin definir un tamaño fijo al principio. El repositorio mantiene esa lista fuera del modelo.

## ¿Por qué se llama repositorio?

Un repositorio es el lugar del programa al que le pedimos **guardar, buscar, actualizar o eliminar** objetos. El nombre describe su responsabilidad, no la tecnología que usa por dentro. Hoy `EmpleadoRepository` trabaja con un `ArrayList`; cuando el curso llegue a JDBC, un repositorio podrá realizar esas operaciones con SQL. No hace falta una base de datos para empezar a practicar esta separación.

`Employee` describe **qué datos tiene un empleado**. `EmpleadoRepository` describe **cómo se conservan y consultan empleados**. Por eso están en carpetas distintas: `employeer/model` y `employeer/repository`. Se repite la misma organización en `customer` y `company` para encontrar cada pieza sin mezclar los datos del objeto con el código que los almacena.

## Paso 1. Preparar el repositorio

Crea [`EmpleadoRepository.java`](../../src/com/twdev/poo/employeer/repository/EmpleadoRepository.java) en `com.twdev.poo.employeer.repository`. Comienza con esta estructura. Si el archivo ya existe, compara el código; no dupliques la clase ni sus métodos.

```java
package com.twdev.poo.employeer.repository;

import com.twdev.poo.employeer.model.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EmpleadoRepository {
    private final List<Employee> empleados = new ArrayList<>();
    private int siguienteId = 1;
}
```

`List<Employee>` limita los elementos a empleados. `ArrayList` crea la lista inicialmente vacía y `siguienteId` guarda el próximo identificador que asignaremos. Cada instancia del repositorio tiene su propia lista y contador.

En los siguientes pasos, agrega los métodos **dentro de esta clase**, antes de su última llave de cierre.

## Paso 2. Crear: agregar un empleado

La **C** de CRUD viene de *Create*. Recibimos un empleado, le asignamos un ID y lo guardamos con `add`:

```java
public Employee agregarEmpleado(Employee empleado) {
    Objects.requireNonNull(empleado, "El empleado no puede ser null");
    empleado.setId(siguienteId++);
    empleados.add(empleado);
    return empleado;
}
```

`Objects.requireNonNull` evita agregar una referencia `null`. `siguienteId++` usa el número actual y después incrementa el contador: el primer empleado recibe 1, el segundo 2. El método devuelve el mismo objeto con su ID asignado. Para crear otro registro, construye otro objeto `Employee`; agregar de nuevo el mismo objeto cambiaría su ID y guardaría otra referencia al mismo objeto.

## Paso 3. Leer: listar y buscar por ID

La **R** viene de *Read*. Hay dos consultas habituales: obtener todos los empleados o encontrar uno concreto.

### Obtener todos

```java
public List<Employee> obtenerEmpleados() {
    return new ArrayList<>(empleados);
}
```

El método devuelve otra lista con los elementos actuales. Si todavía no agregamos empleados, devuelve una lista vacía. Hacer `clear()` sobre esa copia no vacía la lista interna del repositorio. Los objetos contenidos siguen siendo los mismos: no se realiza una copia de cada empleado, como se explica en el [manual de referencias](07-referencias.md).

### Buscar uno

```java
public Employee buscarPorId(int id) {
    for (Employee empleado : empleados) {
        if (empleado.getId() == id) {
            return empleado;
        }
    }
    return null;
}
```

El `for` recorre los empleados y compara su ID con el solicitado. Como el parámetro `id` es `int`, la comparación usa el valor numérico del `Integer` devuelto por `getId()`. Los objetos guardados por este repositorio reciben un ID al agregarse; evita cambiar ese ID manualmente después.

Cuando encuentra una coincidencia, `return` termina el método y entrega el objeto. Si termina el recorrido sin encontrarlo, devuelve `null`. Antes de usar el resultado, comprueba que sea distinto de `null` para evitar llamar métodos sobre una referencia vacía.

## Paso 4. Actualizar: reemplazar los datos

La **U** viene de *Update*. Recibimos el ID que queremos modificar y un objeto con los nuevos datos:

```java
public boolean actualizarEmpleado(int id, Employee datosActualizados) {
    Objects.requireNonNull(datosActualizados, "Los datos no pueden ser null");
    for (int i = 0; i < empleados.size(); i++) {
        if (empleados.get(i).getId() == id) {
            datosActualizados.setId(id);
            empleados.set(i, datosActualizados);
            return true;
        }
    }
    return false;
}
```

Aquí usamos un `for` con índice porque necesitamos saber **en qué posición** reemplazar el elemento. `get(i)` lee el empleado de esa posición y `set(i, datosActualizados)` lo sustituye. El método conserva el ID del registro y devuelve `true` cuando realizó el cambio; devuelve `false` si no encontró el ID.

El ID y la posición son conceptos diferentes: el primer elemento tiene posición 0, aunque su ID puede ser 1 u otro número. Por eso buscamos la coincidencia antes de usar `set`; no usamos el ID directamente como posición.

La actualización reemplaza el objeto completo. Incluye también los campos que deseas conservar: si el objeto nuevo tiene un teléfono `null`, ese será el teléfono guardado. Una variable que apuntaba al objeto anterior seguirá apuntando a ese objeto; para ver el registro actualizado, vuelve a consultar al repositorio.

## Paso 5. Eliminar: quitar un empleado

La **D** viene de *Delete*. Buscamos el ID y quitamos el elemento encontrado:

```java
public boolean eliminarEmpleado(int id) {
    for (int i = 0; i < empleados.size(); i++) {
        if (empleados.get(i).getId() == id) {
            empleados.remove(i);
            return true;
        }
    }
    return false;
}
```

`remove(i)` elimina la posición encontrada y los elementos posteriores cambian de posición. Sus IDs se conservan. `return true` termina el método inmediatamente después de eliminar; si no hay coincidencia, devuelve `false`.

Eliminar un empleado no reinicia el contador ni reutiliza automáticamente su ID. Después de agregar el ID 1 y eliminarlo, un nuevo empleado recibe el ID 2 dentro de esa misma instancia del repositorio.

## Paso 6. Usar las cuatro operaciones desde Java

Este ejemplo completo muestra el CRUD con un solo empleado. Para probarlo por separado, crea `CrudArrayListEjemplo.java` en `src/com/twdev/poo` y ejecútalo desde el IDE. Si compilas con la lista `fuentes.txt` del README, agrega también la ruta de ese archivo nuevo.

```java
package com.twdev.poo;

import com.twdev.poo.employeer.model.Area;
import com.twdev.poo.employeer.model.Employee;
import com.twdev.poo.employeer.repository.EmpleadoRepository;
import java.math.BigDecimal;

public class CrudArrayListEjemplo {
    public static void main(String[] args) {
        EmpleadoRepository repositorio = new EmpleadoRepository();

        // Crear: guardar un objeto nuevo.
        Employee ana = new Employee("Ana", "5551001000", "ana@ejemplo.com",
                "E-001", "Auxiliar", new BigDecimal("12000.00"),
                Area.CONTABILIDAD, true);
        repositorio.agregarEmpleado(ana);
        int id = ana.getId();

        // Leer: mostrar todos y buscar uno.
        System.out.println(repositorio.obtenerEmpleados());
        Employee encontrado = repositorio.buscarPorId(id);
        if (encontrado != null) {
            encontrado.print();
        }

        // Actualizar: entregar todos los datos que deben quedar guardados.
        Employee cambio = new Employee("Ana", "5551001000", "ana@ejemplo.com",
                "E-001", "Jefa de contabilidad", new BigDecimal("18000.00"),
                Area.CONTABILIDAD, true);
        System.out.println("Actualizar: " + repositorio.actualizarEmpleado(id, cambio));
        System.out.println(repositorio.buscarPorId(id));

        // Eliminar: quitar el objeto y comprobar que la lista queda vacía.
        System.out.println("Eliminar: " + repositorio.eliminarEmpleado(id));
        System.out.println(repositorio.obtenerEmpleados());
        System.out.println("Buscar eliminado: " + repositorio.buscarPorId(id));
    }
}
```

Primero verás a Ana con puesto de auxiliar. Después aparecerá `Actualizar: true`, el puesto nuevo y el salario de `18000.00`. Al final, `Eliminar: true`, `[]` y `Buscar eliminado: null` muestran que el empleado ya no está en el repositorio.

## Resumen de las operaciones

| CRUD | Métodos en este proyecto | Si no existe el ID |
| --- | --- | --- |
| Crear | `agregarEmpleado` | No aplica. |
| Leer | `obtenerEmpleados`, `buscarPorId` | La búsqueda devuelve `null`. |
| Actualizar | `actualizarEmpleado` | Devuelve `false`. |
| Eliminar | `eliminarEmpleado` | Devuelve `false`. |

**Decisión:** usamos una lista para practicar estructuras de datos antes de JDBC o una base de datos. Cada repositorio tiene su lista y contador de IDs. Los datos se pierden al terminar el programa. La actualización reemplaza el objeto entero, así que el nuevo debe incluir los datos que quieres conservar.

`obtenerEmpleados()` devuelve `new ArrayList<>(empleados)`: quien recibe la lista puede recorrerla, pero no puede quitar elementos de la lista interna mediante esa copia. `actualizarEmpleado` conserva el ID y usa `set(i, datosActualizados)`; `eliminarEmpleado` usa `remove(i)`. Ambos devuelven `false` si no encuentran el ID, de modo que el programa que los llama puede saber si la operación ocurrió.

Ejecuta [`PooApplication.java`](../../src/com/twdev/poo/PooApplication.java) y sigue sus llamadas al repositorio. [`ClienteRepository.java`](../../src/com/twdev/poo/customer/repository/ClienteRepository.java) y [`EmpresaRepository.java`](../../src/com/twdev/poo/company/repository/EmpresaRepository.java) repiten el patrón para sus modelos.

**Resultado visible:** la consola muestra dos empleados creados, `Actualizar empleado 1: true`, `Eliminar empleado 2: true` y, al final, solo el empleado con ID 1 en la lista. También muestra un cliente y una empresa. El [README](../../README.md) indica cómo ejecutar la aplicación.

Para repetir el ejercicio con otra entidad, cambia el tipo de la lista, el nombre de la clase repositorio y los métodos, pero conserva la secuencia: agregar, listar, buscar, actualizar y eliminar. Cuando se introduzca una base de datos, estas operaciones podrán realizarse con consultas; la idea de separar el acceso a datos seguirá siendo útil.

**Comprueba:** si ejecutas la aplicación dos veces, ¿los empleados creados en la primera ejecución siguen guardados en la segunda?
