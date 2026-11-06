# 4. Agregar constructores

[Anterior](03-getters-y-setters.md) · [Índice](00-indice.md) · [Siguiente](05-herencia-y-polimorfismo.md)

**Resultado esperado:** crear una empresa con datos iniciales en una sola instrucción.

## Conceptos

| Término | Significado |
| --- | --- |
| Constructor | Código de la clase que se ejecuta al crear un objeto con `new`. |
| Firma | Nombre y parámetros del constructor: indican qué argumentos se deben entregar. |
| Sobrecarga | Varios constructores de la misma clase con parámetros diferentes. |

Si la clase es el modelo y el objeto es el ejemplar, el constructor indica cómo empieza la vida de ese ejemplar. Se ejecuta al escribir `new Empresa(...)`. Se llama igual que la clase y no declara tipo de retorno, ni siquiera `void`.

## Pasos

1. Abre [`Empresa.java`](../../src/com/twdev/poo/company/model/Empresa.java). Observa el constructor vacío `Empresa()` y el que recibe `logo`, `direccion`, `email` y `telefono`.
2. En el constructor con parámetros, asigna cada dato al atributo correspondiente; por ejemplo, `this.email = email;`.
3. Úsalo con `new Empresa("logo.png", "Centro", "contacto@ejemplo.com", "5551001000")`.

En el código, la asignación se ve así:

```java
public Empresa(String logo, String direccion, String email, String telefono) {
    this.logo = logo;
    this.direccion = direccion;
    this.email = email;
    this.telefono = telefono;
}
```

El constructor vacío `public Empresa() {}` es otra opción. Permite crear primero el objeto y llamar después a `setNombre`, `setRfc` y otros setters. En el constructor de cuatro argumentos también se necesitan setters para datos que no recibe, como nombre y RFC.

**Decisión:** la firma funciona como un **contrato de creación**: indica la cantidad, el tipo y el orden de los argumentos. El constructor vacío no pide datos y permite completarlos después con setters. Ninguno de estos constructores valida todavía si los valores tienen sentido.

Como varios parámetros son `String`, Java acepta cualquier texto en esas posiciones. Por eso hay que respetar el orden: pasar el teléfono donde va el email puede compilar, pero dejará los datos intercambiados. El contrato expresa los tipos requeridos; las reglas de validación serían un paso adicional.

[`Employee.java`](../../src/com/twdev/poo/employeer/model/Employee.java) ofrece varios constructores. Todos producen objetos `Employee`, pero piden datos diferentes. El repositorio asignará el ID al guardar; por eso el ejemplo de `PooApplication` crea empleados sin ID.

**Comprueba:** ¿qué debes entregar al constructor de `Empresa` que recibe cuatro parámetros? ¿Qué pasa si usas `new Empresa()`?
