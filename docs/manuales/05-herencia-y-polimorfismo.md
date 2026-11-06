# 5. Compartir datos con herencia

[Anterior](04-constructores.md) · [Índice](00-indice.md) · [Siguiente](06-to-string-y-print.md)

**Resultado esperado:** entender por qué `Employee` y `Cliente` heredan de `Persona` y cómo usan `super`.

## Conceptos

| Término | Significado |
| --- | --- |
| Herencia | Una clase hija recibe atributos y métodos de una clase base. |
| `extends` | Declara esa relación entre clases. |
| `super(...)` | Llama al constructor de la clase base. |
| Polimorfismo | Un objeto hijo puede usarse mediante una referencia del tipo base. |

La herencia expresa una relación «es un»: un empleado **es una** persona y un cliente **es una** persona. Los atributos comunes se escriben una sola vez en `Persona`, mientras cada clase hija conserva los propios. Esto evita repetir los mismos campos y getters en dos modelos.

## Pasos

1. Coloca `id`, `nombre`, `telefono` y `email` en [`Persona.java`](../../src/com/twdev/poo/persona/model/Persona.java). Es `abstract`: se usa como base y no se crea con `new Persona()`.
2. Declara [`Employee.java`](../../src/com/twdev/poo/employeer/model/Employee.java) y [`Cliente.java`](../../src/com/twdev/poo/customer/model/Cliente.java) con `extends Persona`. Cada uno conserva sus atributos propios.
3. En sus constructores, llama primero a `super(id, nombre, telefono, email)` para inicializar los datos comunes.

Por ejemplo, un constructor de `Cliente` comienza así:

```java
public Cliente(Integer id, String nombre, String telefono, String email) {
    super(id, nombre, telefono, email);
}
```

`super(...)` llama al constructor de `Persona`; por eso debe aparecer antes de las demás instrucciones del constructor hijo. `Persona` es abstracta porque aquí se usa como base de tipos concretos. Sí podemos declarar una variable `Persona`, pero no crear directamente `new Persona(...)`.

**Decisión:** «un empleado es una persona» y «un cliente es una persona» son relaciones útiles para heredar. Una empresa no es una persona; por eso `Empresa` sigue independiente.

Después podemos escribir `Persona persona = new Employee();`. Aunque la variable tenga tipo `Persona`, el objeto real es un empleado. Si `Employee` redefine `print()`, Java ejecuta esa versión; ese es el polimorfismo en este proyecto.

```java
Persona persona = new Employee();
persona.setNombre("Alan");
persona.print(); // Se ejecuta print() de Employee.
```

La variable permite llamar a los métodos visibles en `Persona`. El método que se ejecuta depende del objeto real cuando la clase hija lo ha redefinido con `@Override`. Un método exclusivo de `Employee`, como `getSalario()`, no se puede llamar directamente desde una variable declarada `Persona`.

**Comprueba:** ¿por qué `Cliente` usa `super(...)` y `Empresa` no?
