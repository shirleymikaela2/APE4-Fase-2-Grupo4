# Informe APE 4 Fase 2 Conexión Ambato

**Universidad Técnica de Ambato · Estructura de Datos · Tercero B · Grupo 4**
Docente: Ing. José Caiza, Mg.
Participantes: completar en orden alfabético.

## Objetivos específicos

1. Representar personas y relaciones mediante listas de adyacencia enlazadas con nodos propios.
2. Implementar el alta y la baja de personas, las amistades recíprocas, los seguimientos dirigidos y sus consultas.
3. Verificar el estado inicial, las ocho operaciones de la guía y el manejo de entradas inválidas con pruebas automatizadas.

## Equipos y recursos

Computador con JDK 21, compilación para Java 17, consola, Git y guías de la práctica. Se empleó asistencia de IA para preparar la implementación y las pruebas. La guía general APE 4 pide presentar el informe en la plataforma educativa.

## Datos iniciales y discrepancia entre fases

La guía de Fase 2 atribuye seis personas y cinco amistades al estado previo. El PDF de Fase 1 y los archivos `modelo/personas.csv` y `modelo/amistades.csv` de su repositorio describen el caso base P01–P05, con Ana, Bruno, Carla, Diego y Elena. Sus cuatro enlaces son P01–P02, P01–P03, P02–P04 y P03–P04. Elena queda aislada. La actividad colaborativa de aquel PDF menciona seis usuarios y cinco amistades como requisitos de un ejercicio, pero su ejemplo desarrollado presenta ocho relaciones dirigidas. Para esta implementación se aplican los datos explícitos de la tabla 1 de Fase 2.

| Código | Persona | Amigos iniciales | Grado |
| --- | --- | --- | ---: |
| A01 | Sofía | Mateo, Andrés | 2 |
| A02 | Mateo | Sofía, Valeria | 2 |
| A03 | Valeria | Mateo, Andrés | 2 |
| A04 | Andrés | Valeria, Sofía, Camila | 3 |
| A05 | Camila | Andrés | 1 |
| A06 | Diego | Ninguno | 0 |

Las amistades son A01–A02, A02–A03, A03–A04, A04–A01 y A04–A05. Al inicio hay diez aristas internas de amistad y cero seguimientos.

## Preguntas de la Fase 2

1. **¿Cómo se representa dinámicamente una persona y una relación?** Cada persona guarda un identificador normalizado y un nombre en un nodo vértice. El nodo apunta al siguiente vértice y a su lista de aristas salientes. Cada arista guarda el vértice destino, el tipo de relación y la siguiente arista.
2. **¿Por qué la amistad necesita dos aristas y seguir solo una?** La amistad es recíproca y debe aparecer en la lista de cada persona. Se almacena A→B y B→A. El seguimiento expresa una decisión del origen y solo agrega A→B.
3. **¿Qué validaciones se realizan antes de registrar una relación?** Deben existir ambos usuarios, cuyos identificadores se buscan en minúsculas. Los vértices deben ser distintos y el enlace de ese tipo debe estar ausente en la lista de origen. Una amistad y un seguimiento pueden coexistir entre las mismas cuentas.
4. **¿Por qué eliminar una persona exige recorrer toda la red?** Las aristas entrantes están distribuidas en las listas de otros vértices. Se recorre la red para retirar todas las referencias al objetivo antes de desenlazarlo. Al perder acceso al propio vértice también desaparecen sus aristas salientes del grafo.
5. **¿Por qué listarSeguidores es más costoso que listarSeguidos?** Listar seguidos inspecciona las aristas salientes del usuario localizado. Listar seguidores inspecciona las listas de toda la red, pues pueden contener seguimientos hacia ese usuario. Los costos máximos, incluida la búsqueda, son O(V + grado saliente) y O(V + A), respectivamente.
6. **¿Qué aporta la lista de adyacencia frente a una matriz?** Usa O(V + A) de espacio, almacena enlaces existentes y permite agregar un vértice enlazándolo a la lista. Una matriz requiere O(V²) de espacio y su capacidad puede tener que ampliarse al agregar usuarios. A cambio, una matriz permite consultar una celda directamente cuando se conocen los índices de los extremos.

## Resultados obtenidos

| Paso | Acción | Resultado observado |
| ---: | --- | --- |
| 1 | Cargar el caso inicial | Seis usuarios y grados 2, 2, 2, 3, 1, 0. La suma de grados es 10, correspondiente a cinco amistades. |
| 2 | `seguir(A02,A01)` | Mateo figura como seguidor de Sofía. |
| 3 | `seguir(A06,A01)` | Diego sigue a Sofía y conserva grado de amistad cero. |
| 4 | `listarAmigos(A04)` | Camila, Sofía y Valeria. La inserción al inicio invierte el orden de carga de estos enlaces, permitido por la tabla 3. |
| 5 | `eliminarAmistad(A04,A05)` | Andrés queda con dos amigos. Camila queda con cero. |
| 6 | `eliminarPersona(A01)` | Sofía desaparece. Se retiran sus amistades y los seguimientos entrantes de Mateo y Diego. |
| 7 | `registrarPersona(A02, otro nombre)` | Devuelve `false` por duplicado. |
| 8 | `agregarAmistad(A03,A03)` | Devuelve `false` por lazo. |

Pasaron 98 verificaciones, incluidas las ocho comprobaciones, las consultas a cuentas inexistentes, la normalización de identificadores y la eliminación independiente de cada tipo de relación.

## Conclusiones

El grafo conserva la reciprocidad de la amistad y la dirección del seguimiento, incluso cuando ambos tipos unen a las mismas cuentas. Las pruebas de baja verificaron la limpieza de referencias entrantes y salientes. Las consultas de seguidores y las bajas revisan la red completa porque solo se almacenan listas de aristas salientes.

## Recomendaciones

Repetir la secuencia de comprobación después de cambiar la carga inicial o las operaciones de baja. Registrar en las evidencias los grados, las relaciones retiradas y el resultado de las entradas inválidas.

## Referencias

[1] Universidad Técnica de Ambato, «APE 4 · Fase 2: representación dinámica y operaciones sobre personas/relaciones», guía práctica, `APE 4. Fase 2.docx`.

[2] Universidad Técnica de Ambato, «Grafos», formulario de guías prácticas, `APE4 (3).pdf`, jul.–dic. 2026.

[3] Grupo 4, «APE 4 · Fase 1: Requisitos, especificación y modelo de red social mediante grafos», informe de práctica, PDF.

[4] Grupo 4, «APE4-Fase-1-Grupo4», GitHub, revisión `8dbc92a2286706e2d7ea746489240d0a2ef90776`. [Repositorio de Fase 1](https://github.com/shirleymikaela2/APE4-Fase-1-Grupo4).

## Anexos

1. Registro y `mostrarRed`: paso 1 de la [salida de consola](../evidencias/demostracion.txt).
2. Amistades y seguimientos: pasos 1–4 de la [salida de consola](../evidencias/demostracion.txt).
3. Bajas de amistad y persona: pasos 5–6 de la [salida de consola](../evidencias/demostracion.txt).
4. Consultas: pasos 1–4 de la [salida de consola](../evidencias/demostracion.txt).
5. [Salida de pruebas automatizadas](../evidencias/pruebas.txt).
6. [Repositorio de Fase 2](https://github.com/shirleymikaela2/APE4-Fase-2-Grupo4).
