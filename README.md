# APE 4 Fase 2 Grupo 4

Red social dinámica en Java con seis cuentas iniciales, cinco amistades recíprocas y seguimientos dirigidos. Las personas y aristas se almacenan en listas enlazadas de nodos propios.

## Ejecutar

Se necesita un **JDK 17 o posterior**. Desde la carpeta raíz del proyecto:

```text
javac --release 17 -encoding UTF-8 -d build -sourcepath src src/ambato/app/Main.java
java -Dfile.encoding=UTF-8 -cp build ambato.app.Main
javac --release 17 -encoding UTF-8 -cp build -d build pruebas/ambato/pruebas/PruebasGrafo.java
java -Dfile.encoding=UTF-8 -cp build ambato.pruebas.PruebasGrafo
```

En PowerShell, si se divide el argumento de codificación, escribe `"-Dfile.encoding=UTF-8"`. Las pruebas se ejecutan sin dependencias y terminan con error si falla una comprobación.

| Carpeta | Contenido |
| --- | --- |
| `src/ambato/modelo` | Grafo, nodos propios y tipos de relación |
| `src/ambato/app` | Carga de la red inicial y demostración de los ocho pasos |
| `pruebas` | Pruebas de la secuencia, validaciones y bajas |
| `docs` | Informe, reglas y discrepancias entre fases |
| `evidencias` | Salidas reales de la demostración y las pruebas |

## Consultas

`listarAmigos`, `listarSeguidos` y `listarSeguidores` devuelven nombres separados por comas, texto vacío para una cuenta sin resultados y `null` para una cuenta inexistente. `contarAmigos` devuelve el grado de amistad o `-1` si la cuenta no existe. La búsqueda ignora mayúsculas. El registro rechaza identificadores con espacios o comas y nombres vacíos.

Ejemplo: después de `seguir("A06", "A01")`, `listarSeguidos("A06")` devuelve `Sofía` y `contarAmigos("A06")` devuelve `0`. `dejarDeSeguir("A06", "A01")` elimina ese seguimiento.

El caso de Fase 2 toma sus datos de la tabla 1 de la guía. El [informe](docs/informe.md) explica la diferencia con la [Fase 1](https://github.com/shirleymikaela2/APE4-Fase-1-Grupo4).
