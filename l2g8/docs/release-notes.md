# Release Notes

## Justificación de Versionado Semántico (FASE 1)

Basándonos en el historial reciente de commits:
- `feat: implementar workflow de release y notas de la version`
- `feat: añadir comprobacion de Quality Gate de Sonar en el workflow de release`
- `fix: add fetch-depth 0 to checkout for proper sonar analysis`

**Versión elegida:** `v1.1.0` (o superior si la 1.1.0 ya existe).
**Justificación:** Se han añadido nuevas funcionalidades en la infraestructura (indicado por los commits tipo `feat:`), como el pipeline de GitHub Actions y la integración del Quality Gate de SonarQube. Según SemVer (MAJOR.MINOR.PATCH), al añadir nueva funcionalidad que es retrocompatible, se debe incrementar el número **MINOR** (el número de en medio). Las correcciones menores (`fix:`) se incluyen sin alterar este salto.

---

## Versión v1.1.0

### Novedades
- Automatización completa del flujo de Release mediante GitHub Actions (`release.yml`).
- Empaquetado automático en imagen de Docker y publicación en GitHub Container Registry (GHCR).
- **Quality Gate:** Integración obligatoria con SonarCloud para no permitir releases si el código no cumple las métricas mínimas.

### Correcciones
- Solucionado el problema de visibilidad en SonarQube añadiendo `fetch-depth: 0` al clonar el repositorio, garantizando un análisis de código exacto.
