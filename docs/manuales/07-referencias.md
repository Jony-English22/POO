# 7. Entender las referencias

[Anterior](06-to-string-y-print.md) · [Índice](00-indice.md) · [Siguiente](08-arraylist-y-crud.md)

**Resultado esperado:** predecir qué ocurre cuando dos variables o una lista usan el mismo objeto.

## Conceptos

| Término | Significado |
| --- | --- |
| Referencia | Valor que permite acceder a un objeto creado con `new`. |
| `null` | Una referencia que no apunta a un objeto. |
| `final` en la lista | Impide asignar otra lista a esa variable; no congela sus elementos. |

Una variable de tipo `Employee` no contiene todos los campos del empleado dentro de la variable: conserva una referencia con la que puede acceder al objeto. Dos variables pueden conservar referencias al mismo objeto. Esta diferencia importa al guardar objetos en colecciones.

## Pasos

1. Crea un empleado y una segunda variable con la misma referencia:

   ```java
   Employee primero = new Employee();
   Employee segundo = primero;
   segundo.setNombre("Ana");
   System.out.println(primero.getNombre()); // Ana
   ```

2. Observa [`EmpleadoRepository.java`](../../src/com/twdev/poo/employeer/repository/EmpleadoRepository.java): `empleados.add(empleado)` guarda una referencia al objeto.
3. `obtenerEmpleados()` devuelve una lista nueva, pero sus elementos siguen siendo los mismos objetos. Cambiar un elemento puede verse desde el repositorio.

Si `primero` y `segundo` se refieren al mismo empleado, llamar a `segundo.setNombre("Ana")` cambia ese empleado. Por eso `primero.getNombre()` devuelve `"Ana"`. Si después escribes `segundo = new Employee()`, solo `segundo` pasa a referirse al objeto nuevo; `primero` sigue con el original.

**Decisión:** hablamos de **referencias**, no de punteros manipulables con direcciones de memoria. Conocerlas evita confundir «copiar la lista» con «copiar cada objeto».

El repositorio devuelve `new ArrayList<>(empleados)` para que otra clase no pueda hacer `clear()` sobre su lista interna. Esa copia contiene los **mismos objetos**: `lista.get(0).setNombre(...)` sí puede modificar un empleado guardado. Es una limitación conocida de este ejemplo sencillo. `final` en la declaración de la lista tampoco impide agregar o quitar elementos; solo evita asignar otra lista a la variable `empleados`.

**Comprueba:** si modificas el nombre mediante `segundo`, ¿qué nombre lee `primero`? ¿Por qué?
