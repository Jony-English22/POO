# 9. Agregar servicios y reglas del negocio

[Anterior](08-arraylist-y-crud.md) · [Índice](00-indice.md) · [Siguiente](10-maven.md)

En esta práctica agregarás un servicio que valida los datos antes de guardar un empleado. Es una ampliación propuesta: la carpeta `service` aún no forma parte del código actual.

## Conceptos

| Elemento | Responsabilidad |
| --- | --- |
| Modelo | Representa los datos del empleado. |
| Servicio | Aplica las reglas de una operación, por ejemplo registrar un empleado. |
| Repositorio | Guarda y consulta objetos. |
| Dependencia | Objeto que una clase necesita para trabajar. |

Un servicio reúne acciones del negocio. Registrar un empleado puede exigir nombre y salario no negativo antes de guardar. Esas reglas se colocan en el servicio para reutilizarlas desde la consola y, más adelante, desde un controlador web. El repositorio conserva la responsabilidad de almacenamiento.

## Por qué agregamos esta parte ahora

Hasta este punto, `PooApplication` crea los objetos y llama directamente al repositorio. Eso permite practicar el CRUD, pero al agregar reglas aparece una pregunta: ¿quién debe decidir si se puede registrar un empleado?

Podríamos poner las validaciones en `main`, pero tendríamos que repetirlas si otra pantalla o un controlador quisiera registrar empleados. También podríamos ponerlas en el repositorio, pero entonces el código que guarda datos tendría que conocer todas las decisiones del negocio. El servicio reúne esas decisiones en un lugar al que pueden acudir los distintos puntos de entrada del programa.

Por ejemplo, «el salario no puede ser negativo» es una regla del registro de empleados. «Agregar el objeto al `ArrayList` y asignarle un ID» es una operación del repositorio. El servicio comprueba la regla y, si se cumple, pide al repositorio que guarde. Así cada parte responde una pregunta concreta:

| Parte | Pregunta que responde |
| --- | --- |
| `PooApplication` | ¿Qué ejemplo vamos a ejecutar y qué mostraremos? |
| `Employee` | ¿Qué datos tiene el empleado? |
| `EmpleadoService` | ¿Se permite realizar esta operación con estos datos? |
| `EmpleadoRepository` | ¿Cómo guardamos o buscamos al empleado? |

## Qué significa la carpeta `service`

Dentro de `employeer`, el paquete `service` agrupa las clases que coordinan operaciones de empleados. Se coloca junto a `model` y `repository` porque las tres partes trabajan sobre el mismo tema, con responsabilidades diferentes. Para una operación de clientes, el equivalente se ubicaría en `customer.service`.

Aquí un servicio es una clase Java dentro de la misma aplicación. Al crear `EmpleadoService`, seguimos ejecutando un solo programa. Su nombre describe su trabajo: ofrecer operaciones del negocio al resto del código.

## Crear el servicio

1. Crea `EmpleadoService.java` en el paquete `com.twdev.poo.employeer.service`.
2. Recibe un `EmpleadoRepository` en el constructor. Así el programa que inicia la aplicación elige qué repositorio compartir.
3. Valida los datos en `registrarEmpleado`. Si son válidos, delega el guardado al repositorio.

```java
package com.twdev.poo.employeer.service;

import com.twdev.poo.employeer.model.Employee;
import com.twdev.poo.employeer.repository.EmpleadoRepository;
import java.util.List;
import java.util.Objects;

public class EmpleadoService {
    private final EmpleadoRepository repositorio;

    public EmpleadoService(EmpleadoRepository repositorio) {
        this.repositorio = Objects.requireNonNull(repositorio);
    }

    public Employee registrarEmpleado(Employee empleado) {
        Objects.requireNonNull(empleado, "El empleado es obligatorio");
        if (empleado.getNombre() == null || empleado.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (empleado.getSalario() == null || empleado.getSalario().signum() < 0) {
            throw new IllegalArgumentException("El salario debe ser cero o mayor");
        }
        return repositorio.agregarEmpleado(empleado);
    }

    public List<Employee> listarEmpleados() {
        return repositorio.obtenerEmpleados();
    }
}
```

`IllegalArgumentException` comunica que se entregaron datos que incumplen una regla. El constructor recibe la dependencia manualmente: este principio también se aprovechará cuando llegue Spring. `signum()` devuelve un número negativo, cero o positivo según el valor de `BigDecimal`.

## Leer el método de registro paso a paso

Primero, `Objects.requireNonNull` comprueba que exista un objeto. Después se revisa su nombre: `isBlank()` detecta tanto una cadena vacía como una formada solo por espacios. Java evalúa `||` de izquierda a derecha y se detiene si la primera condición es verdadera; así no llama a `isBlank()` sobre un nombre `null`.

La siguiente condición comprueba que haya salario y que no sea negativo. Cuando cualquiera de las reglas falla, `throw` termina la ejecución del método con una excepción. La línea `repositorio.agregarEmpleado(empleado)` solo se alcanza cuando las validaciones anteriores pasaron.

El valor devuelto es el empleado que guardó el repositorio, con el ID ya asignado. El servicio no necesita saber en qué posición de la lista quedó: ese detalle pertenece al almacenamiento.

`listarEmpleados()` delega directamente porque en este ejercicio no hay una regla adicional para listar. No todos los métodos de un servicio requieren validaciones; lo que importa es que represente las operaciones que ofrece la aplicación.

## Conectarlo con la aplicación

En `PooApplication`, importa el servicio y crea una sola instancia del repositorio para este flujo:

```java
EmpleadoRepository repositorio = new EmpleadoRepository();
EmpleadoService servicio = new EmpleadoService(repositorio);
Employee empleado = new Employee();
empleado.setNombre("Ana");
servicio.registrarEmpleado(empleado);
System.out.println(servicio.listarEmpleados());
```

El constructor vacío de `Employee` ya establece salario cero. El recorrido será `PooApplication → EmpleadoService → EmpleadoRepository → ArrayList`. Repite la validación en las futuras operaciones de actualización; de otro modo una modificación podría saltarse las reglas. Las referencias mutables siguen teniendo los límites explicados en el manual 7.

## Por qué el constructor recibe el repositorio

La asignación `this.repositorio = repositorio` conserva el objeto que la aplicación entregó al servicio. Esto es **inyección de dependencias por constructor**: quien crea el servicio le entrega lo que necesita para funcionar. Podemos aprender este mecanismo usando Java antes de introducir anotaciones de Spring.

Conviene compartir la misma instancia durante el ejemplo. Si creamos un repositorio nuevo para registrar y otro distinto para listar, cada uno tendrá su propio `ArrayList`; la consulta del segundo aparecerá vacía. Recibir la dependencia también permite ver explícitamente cómo se conectan las clases.

## Seguir una operación completa

1. `main` crea el objeto `Employee` y lo entrega al servicio.
2. El servicio revisa nombre y salario.
3. El repositorio asigna el ID y agrega el objeto a la lista.
4. El objeto guardado regresa al servicio y de ahí al código que llamó la operación.

Cuando agreguemos JDBC, el paso de almacenamiento cambiará. La intención del servicio —validar y coordinar el registro— seguirá siendo útil. Para intercambiar implementaciones sin depender de una clase concreta, más adelante se definirá un contrato mediante una interfaz.

**Decisión:** el servicio representa una operación del negocio. Algunos métodos delegan directamente; otros comprueban reglas antes de guardar. El almacenamiento continúa en memoria durante esta práctica.

**Comprueba:** registra un empleado con nombre y después prueba uno con nombre vacío. El segundo debe provocar la excepción antes de entrar al repositorio. Haz la prueba inválida por separado o captura la excepción para que el programa pueda continuar.
