# Análisis de Impacto y Gestión del Cambio

**Contexto del cambio**: Se requiere permitir que una solicitud cerrada pueda reabrirse (pasar a `EN_PROCESO`/`PROCESANDO`) y mantener un histórico de los cambios de estado.

### 1. ¿Qué métodos del dominio se ven afectados?
- `Solicitud.cambiarEstado(EstadoSolicitud)`: Debe permitir la transición de `CERRADA` a `PROCESANDO` y además registrar el cambio en el histórico.
- `Solicitud.cerrarSolicitud()`: Deberá registrar el cambio en el histórico.
- Nuevo método en `Solicitud`: `reabrir()`, el cual validará que el estado sea `CERRADA` y lo cambiará a `PROCESANDO`.
- `EstadoSolicitud.puedeCambiarA(EstadoSolicitud)`: Se debe flexibilizar para permitir la transición a `PROCESANDO` cuando el estado actual es `CERRADA`.

### 2. ¿Qué reglas actuales cambian?
- **Regla actual:** Una solicitud cerrada no puede cambiar de estado (es un estado final).
- **Nueva regla:** Una solicitud cerrada SÍ puede cambiar de estado, pero únicamente a `PROCESANDO` (reapertura).
- **Nueva regla:** Toda transición de estado exitosa debe quedar registrada ordenadamente junto con su marca de tiempo (histórico de estados).

### 3. ¿Qué tests deberían romperse?
- En `SolicitudTest.java`, el test `noDebeCambiarEstadoSiSolicitudYaEstaCerrada()` se romperá si intentamos pasarle `PROCESANDO` (cosa que ahora sí será válida). Tendremos que ajustar el test para verificar que falla solo si se intenta pasar de `CERRADA` a `ABIERTA`, pero que permite pasar a `PROCESANDO`.

### 4. ¿Qué parte del modelo debe extenderse?
- Se requiere extender `Solicitud` añadiendo una estructura de datos `List<RegistroEstado>` que almacene el historial.
- Se debe crear un **Value Object** llamado `RegistroEstado` (que contenga el `EstadoSolicitud` y un `Date` o `LocalDateTime`). Hemos elegido un Value Object interno en lugar de una Entidad separada porque el histórico de estados carece de identidad propia fuera del contexto de una `Solicitud`; simplemente documenta el ciclo de vida de la entidad raíz (`Solicitud`).

### 5. ¿Qué impacto tiene en persistencia?
- El `Value Object` requerirá un mapeo mediante `@ElementCollection` (y `@CollectionTable`) en `SolicitudEntity` para que JPA lo maneje como una tabla dependiente sin necesidad de crear una entidad `EstadoChangeEntity` con un ID propio. Alternativamente, podemos documentar que la persistencia se abordará en una fase posterior (según elija el equipo técnico).
