# 6. Mostrar los objetos

[Anterior](05-herencia-y-polimorfismo.md) · [Índice](00-indice.md) · [Siguiente](07-referencias.md)

**Resultado esperado:** que la consola muestre datos como nombre, puesto y salario en lugar de una representación poco útil del objeto.

## Conceptos

| Método | Función |
| --- | --- |
| `toString()` | Devuelve una descripción del objeto como texto. |
| `print()` | Envía ese texto a la consola. |
| `@Override` | Indica que se redefine un método heredado. |

Cuando se imprime un objeto, necesitamos una representación que permita reconocerlo. `toString()` construye esa descripción. En `Employee`, combina el ID, los datos heredados de `Persona` y los datos propios, como puesto, salario y área. Así, cada modelo decide qué información mostrar.

## Pasos

1. Abre [`Employee.java`](../../src/com/twdev/poo/employeer/model/Employee.java). Su `toString()` reúne los campos propios y los heredados, estos últimos mediante getters.
2. Haz que `print()` llame a `System.out.println(toString())`.
3. Prueba `empleado.print()` y `System.out.println(empleado)`; la segunda forma usa `toString()` automáticamente.

El método `print()` de `Employee` contiene:

```java
@Override
public void print() {
    System.out.println(toString());
}
```

`@Override` ayuda a detectar errores: si el nombre o los parámetros no coincidieran con el método de `Persona`, Java señalaría el problema al compilar. `toString()` devuelve un `String`; `print()` tiene retorno `void` porque solo muestra el resultado. Por ejemplo, `String descripcion = empleado.toString();` permite guardar el texto sin imprimirlo todavía.

**Decisión:** `toString()` prepara el texto; `print()` decide mostrarlo. Así también se ven los datos al imprimir una lista de empleados. `Cliente` redefine los mismos métodos para incluir tipo y dirección.

En `PooApplication`, `System.out.println(empleados.obtenerEmpleados())` muestra una lista. La lista usa el `toString()` de cada empleado para presentar sus elementos. Por eso el resultado es legible sin recorrer e imprimir cada atributo a mano.

**Comprueba:** ¿qué método usarías si necesitas guardar la descripción en una variable sin imprimirla todavía?
