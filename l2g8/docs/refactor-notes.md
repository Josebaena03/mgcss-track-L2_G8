# Notas de Refactorización - Sesión 8

## Paso 2: Selección del objetivo de refactorización

*1. Problema identificado:*
En la clase de dominio Solicitud.java, los métodos principales (asignarTecnico y cambiarEstado) contienen múltiples condicionales (if) apilados. Además, se detectaron varios "Code Smells" en las clases de test debido al uso innecesario del modificador public (herencia de JUnit 4 en un proyecto JUnit 5).

*2. Métrica asociada:*
Complejidad ciclomática por método elevada y múltiples "Code Smells" que afectan a la métrica de Mantenibilidad (Maintainability).

*3. Riesgo potencial si no se corrige:*
A medida que el sistema crezca, los métodos principales se convertirán en "clases Dios" muy difíciles de leer, mantener y probar. El "ruido" en los tests dificulta su legibilidad.

---

## Paso 5: Comparación de métricas (Antes y Después)

*1. MÉTRICAS QUE HAN MEJORADO*
* *CODE SMELLS:* Se han eliminado por completo las advertencias de SonarCloud relacionadas con el uso innecesario del modificador 'public' en las clases y métodos de test de JUnit 5.
* *COMPLEJIDAD CICLOMÁTICA POR MÉTODO:* Al aplicar la técnica de extracción, la complejidad de los métodos principales en Solicitud.java (como asignarTecnico y cambiarEstado) ha disminuido significativamente.
* *DEUDA TÉCNICA (TECHNICAL DEBT):* La estimación de tiempo de mantenimiento en SonarCloud se ha reducido al simplificar la lógica de dominio y limpiar el ruido visual en los tests.
* *MAINTAINABILITY RATING:* Se mantiene la calificación 'A', consolidando un código más limpio y profesional.

*2. TÉCNICAS DE REFACTORIZACIÓN APLICADAS*
* *EXTRACT METHOD (Extraer Método):* Se ha movido la lógica de validación de Solicitud.java a métodos privados independientes (ej. validarQueSePuedeCerrar, validarTecnicoActivo) para separar las reglas de negocio de la acción principal.
* *DELEGACIÓN A ENUMS:* Se ha trasladado la responsabilidad de comprobar estados a los propios Enums (ej. estado.permiteCerrar()), siguiendo principios de alta cohesión.
* *LIMPIEZA DE SINTAXIS:* Se han eliminado modificadores de acceso redundantes en la capa de test para cumplir con los estándares modernos de JUnit 5 sugeridos por SonarCloud.

*3. BENEFICIOS PARA EL MANTENIMIENTO FUTURO*
* *LEGIBILIDAD:* El código ahora se lee de forma secuencial y clara. La intención de cada método es obvia gracias a los nombres de los métodos extraídos.
* *TESTABILIDAD Y ROBUSTEZ:* Al tener validaciones atómicas, es más fácil identificar dónde falla una regla de negocio sin navegar por largos bloques de código anidado.
* *FACILIDAD DE CAMBIO:* Si en la Sesión 9 el cliente pide cambiar una regla de transición de estados, solo necesitaremos modificar el método específico o el Enum, minimizando el riesgo de efectos colaterales en el resto del sistema.
