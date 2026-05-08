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
- **Decisión Técnica:** En esta fase, se ha decidido **no persistir** el historial de estados en la base de datos de forma inmediata.
- **Justificación:** El requisito de mantener el histórico es fundamentalmente una regla de dominio en esta iteración. Introducir el mapeo JPA (mediante `@ElementCollection` y `@CollectionTable` en `SolicitudEntity`) añadiría riesgo y acoplamiento a nivel de infraestructura durante un parche crítico de producción. 
- **Impacto en Mantenibilidad:** Esta decisión introduce una leve *Deuda Técnica* temporal, ya que el historial solo existe en memoria durante el ciclo de vida de la transacción actual. Se debe registrar un Issue técnico para la siguiente iteración que aborde exclusivamente el mapeo y persistencia del `RegistroEstado` garantizando la coherencia de datos a largo plazo sin romper la estabilidad actual.
