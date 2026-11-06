# 11. Pasar de objetos a tablas

[Anterior](10-maven.md) · [Índice](00-indice.md) · [Siguiente](12-jdbc.md)

En esta práctica crearás una tabla y ejecutarás operaciones SQL antes de conectarla con Java. Necesitas un gestor de base de datos y una herramienta para ejecutar SQL. El motor se elegirá para el curso; los ejemplos usan instrucciones comunes de bases relacionales.

## Conceptos

| En Java | En una base relacional |
| --- | --- |
| Clase `Empresa` | Define los datos que queremos representar en una tabla. |
| Objeto empresa | Sus valores pueden guardarse en una fila. |
| Atributo | Se relaciona con una columna. |
| ID del objeto | Identifica la fila mediante una clave primaria. |

Una lista conserva referencias a objetos dentro del programa. Una base de datos guarda valores que pueden consultarse en otra ejecución. Al recuperarlos, Java construye objetos nuevos con esos valores; no recupera las mismas referencias de memoria.

## Crear y consultar una tabla

1. En la herramienta del gestor elegido, crea una base de práctica y abre su editor SQL.
2. Ejecuta esta tabla introductoria. Representa solo ID, nombre y RFC de `Empresa` para estudiar el primer intercambio; el modelo Java actual tiene más campos.

```sql
CREATE TABLE empresas (
    id INTEGER PRIMARY KEY,
    nombre VARCHAR(120) NOT NULL,
    rfc VARCHAR(20)
);
```

La clave primaria impide repetir un ID. `NOT NULL` exige que la columna tenga un valor, aunque por sí solo no rechaza un texto vacío. Las reglas de negocio continúan en el servicio y las restricciones de la tabla protegen la integridad de los datos.

3. Ejecuta las operaciones una por una y observa cada cambio:

```sql
INSERT INTO empresas (id, nombre, rfc)
VALUES (1, 'Ejemplo SA', 'EJE010101ABC');

SELECT id, nombre, rfc FROM empresas ORDER BY id;

UPDATE empresas SET nombre = 'Ejemplo Actualizado' WHERE id = 1;

SELECT id, nombre, rfc FROM empresas WHERE id = 1;

DELETE FROM empresas WHERE id = 1;
```

`WHERE` limita la operación a una fila identificada. En una actualización o eliminación, omitirlo puede afectar todas las filas. Después de eliminar, vuelve a ejecutar el `SELECT` para comprobar que ya no aparece la empresa.

**Decisión:** en este primer ejercicio asignamos el ID manualmente para estudiar las operaciones. La generación automática de IDs depende del motor y se incorporará al ampliar el ejemplo. Antes de sustituir el repositorio actual, habrá que añadir también teléfono, correo, dirección, logo y activo a la tabla y al mapeo Java.

**Comprueba:** guarda una empresa, confirma la transacción si tu herramienta lo requiere, cierra la herramienta y vuelve a consultarla. El dato debe seguir allí. Compara ese comportamiento con el `ArrayList` que se reinicia en cada ejecución.
