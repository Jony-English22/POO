# 3. Agregar getters y setters

[Anterior](02-modelos.md) · [Índice](00-indice.md) · [Siguiente](04-constructores.md)

**Resultado esperado:** leer y cambiar el nombre de una empresa sin acceder directamente a un atributo privado.

## Conceptos

| Método | Función |
| --- | --- |
| Getter | Devuelve el valor de un atributo. |
| Setter | Recibe un valor y lo asigna al atributo. |
| `this` | Señala el objeto actual; distingue su atributo del parámetro. |

Al declarar `nombre` como `private`, evitamos escribir `empresa.nombre = ...` desde otra clase. Los métodos públicos se convierten en la forma visible de acceder a ese dato. Esto se conoce como **encapsulación**: la clase conserva sus atributos y expone las operaciones que permite realizar con ellos.

## Pasos

1. Abre [`Empresa.java`](../../src/com/twdev/poo/company/model/Empresa.java) y localiza `private String nombre;`.
2. Agrega un método que lo lea y otro que lo cambie:

   ```java
   public String getNombre() { return nombre; }
   public void setNombre(String nombre) { this.nombre = nombre; }
   ```

3. Prueba `empresa.setNombre("Ejemplo SA");` y luego `System.out.println(empresa.getNombre());`.

El ejemplo completo muestra el papel de `return`, el parámetro y `this`:

```java
private String nombre;

public String getNombre() {
    return nombre;
}

public void setNombre(String nombre) {
    this.nombre = nombre;
}
```

`return nombre` entrega al código que llamó al getter el valor guardado. En el setter, el parámetro `nombre` llega desde fuera y `this.nombre` identifica el atributo de esa empresa concreta. Si el objeto se llama `empresa`, la llamada `empresa.setNombre("Ejemplo SA")` modifica solo ese objeto.

**Decisión:** los atributos son privados y los métodos públicos muestran cómo se usan. `getNombre()` devuelve un `String`; `setNombre(...)` usa `void` porque no devuelve un valor. En `this.nombre = nombre`, el lado izquierdo es el atributo y el derecho es el parámetro.

El patrón se repite con teléfono, correo y otros atributos. Para `boolean` es frecuente ver getters que empiezan con `is`, como `Employee.isActivo()`. En la clase `Empresa` actual se usa `getActivo()`. Más adelante, un setter podría validar datos antes de asignarlos; los de este ejercicio todavía hacen una asignación directa.

**Comprueba:** ¿qué devuelve `getNombre()` después de llamar a `setNombre("Ejemplo SA")`?
