# 2. Crear la primera clase de modelo

[Anterior](01-proyecto-y-arquitectura.md) · [Índice](00-indice.md) · [Siguiente](03-getters-y-setters.md)

**Resultado esperado:** entender por qué una clase `Empresa` permite crear varias empresas con datos diferentes.

## Conceptos

| Término | Significado |
| --- | --- |
| Clase | Modelo que define los datos y acciones de sus objetos. |
| Atributo | Dato que guarda cada objeto, como `nombre`. |
| Objeto | Ejemplar concreto creado a partir de una clase. También se llama instancia. |

La clase es como el modelo de un producto: define lo que tendrán todos los ejemplares, pero no es ninguno de ellos. `Empresa` es una clase; «Ejemplo SA» es el valor que puede tener un objeto concreto de esa clase. Dos objetos pueden seguir el mismo modelo y conservar nombres, RFC y teléfonos diferentes.

## Pasos

1. Crea el paquete `com.twdev.poo.company.model` y dentro la clase [`Empresa.java`](../../src/com/twdev/poo/company/model/Empresa.java).
2. Declara atributos como `private String nombre;`, `private String rfc;` y `private boolean activo;`. `private` evita que otra clase los cambie directamente.
3. Crea dos objetos del mismo modelo: `Empresa primera = new Empresa();` y `Empresa segunda = new Empresa();`. Más adelante les darás valores con setters.

La forma básica de la clase es:

```java
package com.twdev.poo.company.model;

public class Empresa {
    private String nombre;
    private String rfc;
    private boolean activo;
}
```

[`Empresa.java`](../../src/com/twdev/poo/company/model/Empresa.java) contiene más atributos, entre ellos `id`, dirección y correo. `String` representa texto; `boolean` admite `true` o `false`. El archivo debe llamarse `Empresa.java` porque contiene la clase pública `Empresa`.

**Decisión:** comenzamos con `Empresa` porque es un modelo independiente. Después agregaremos `Persona` para los datos que comparten empleados y clientes.

Una clase puede producir muchos objetos; crear otro objeto **no** requiere copiar `Empresa.java`. Cada objeto guarda sus propios valores. La palabra `new` crea el objeto mediante un constructor, que veremos en la práctica 4.

Cuando agreguemos los setters, podremos hacer `primera.setNombre("Empresa Uno")` y `segunda.setNombre("Empresa Dos")`. Leer el nombre de `primera` no cambiará el de `segunda`, porque son instancias distintas.

**Comprueba:** para registrar tres empresas, ¿cuántas clases `Empresa` y cuántos objetos necesitas?
