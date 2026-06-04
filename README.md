# mgcss-track · L2 G8

API REST para la gestión del ciclo de vida de solicitudes de soporte técnico. Permite crear solicitudes, asignarles un técnico, controlar sus transiciones de estado (ABIERTA → PROCESANDO → CERRADA) y reabrirlas cuando sea necesario. El sistema mantiene un historial de cambios de estado en memoria por cada solicitud.

Desarrollado como práctica de la asignatura MGCSS aplicando TDD, arquitectura por capas y pipeline CI con análisis estático.

[![CI Pipeline](https://github.com/Josebaena03/mgcss-track-L2_G8/actions/workflows/ci.yml/badge.svg)](https://github.com/Josebaena03/mgcss-track-L2_G8/actions/workflows/ci.yml)
[![Release](https://github.com/Josebaena03/mgcss-track-L2_G8/actions/workflows/release.yml/badge.svg)](https://github.com/Josebaena03/mgcss-track-L2_G8/actions/workflows/release.yml)
[![Última versión](https://img.shields.io/github/v/release/Josebaena03/mgcss-track-L2_G8)](https://github.com/Josebaena03/mgcss-track-L2_G8/releases)

---

## Arquitectura y Tecnologías

El proyecto sigue una separación estricta en capas:

```
com.mgcss.l2g8
├── domain/          # Entidades de negocio (Solicitud, Tecnico, RegistroEstado) y enums
├── service/         # Casos de uso (SolicitudService, TecnicoService)
├── infraestructure/ # Entidades JPA (SolicitudEntity, TecnicoEntity) e interfaces de repositorio
└── controller/      # Controladores REST y DTOs (Request/Response)
```

La lógica de negocio (validaciones, transiciones de estado, reglas de reapertura) vive exclusivamente en el dominio. Los servicios orquestan sin contener reglas complejas. Las entidades JPA están aisladas en la capa de infraestructura y no se exponen directamente al exterior.

| Componente            | Detalle                                  |
|-----------------------|------------------------------------------|
| Java                  | 17                                       |
| Spring Boot           | 4.0.3                                    |
| Build tool            | Maven (con wrapper `./mvnw`)             |
| Base de datos         | H2 (en memoria, arranque automático)     |
| Persistencia          | Spring Data JPA / Jakarta Persistence    |
| Reducción de boilerplate | Lombok                                |
| Documentación API     | springdoc-openapi 2.5.0 (Swagger UI)     |
| Cobertura             | JaCoCo 0.8.10                            |
| Análisis estático     | SonarCloud (`josebaena03` organization)  |
| CI                    | GitHub Actions                           |
| Containerización      | Docker (`eclipse-temurin:17-jre-alpine`) |
| Entrega / Release     | GitHub Actions + GHCR (imagen versionada)|

---

## Requisitos previos

Para ejecutar el proyecto localmente necesitas tener instalado:

- **Java 17** (JDK, no solo JRE)
- **Maven 3.8+** — o usar el wrapper incluido (`./mvnw`) sin instalación adicional
- **Docker** — solo si quieres ejecutar la imagen en contenedor

No necesitas configurar ninguna base de datos. H2 arranca en memoria automáticamente y se pre-carga con datos iniciales desde `src/main/resources/data.sql`.

---

## Cómo ejecutar el proyecto

### Opción 1: Maven en local

```bash
# Desde la raíz del módulo
cd l2g8

# Compilar y ejecutar
./mvnw spring-boot:run
```

La aplicación arranca en `http://localhost:8080`.

Swagger UI disponible en: `http://localhost:8080/swagger-ui/index.html`

### Opción 2: Docker (build local)

```bash
cd l2g8

# 1. Generar el JAR
./mvnw clean package -DskipTests

# 2. Construir la imagen
docker build -t mgcss-track .

# 3. Levantar el contenedor
docker run -p 8080:8080 -e APP_PORT=8080 --name mgcss-track-container mgcss-track
```

### Opción 3: Imagen publicada en GHCR

Cada release publica automáticamente la imagen versionada en GitHub Container Registry:

```bash
docker pull ghcr.io/josebaena03/mgcss-track:v1.1.0
docker run -p 8080:8080 ghcr.io/josebaena03/mgcss-track:v1.1.0
```

Variables de entorno disponibles:

| Variable                  | Valor por defecto | Descripción                        |
|---------------------------|-------------------|------------------------------------|
| `APP_PORT`                | `8080`            | Puerto en el que escucha la app    |
| `SPRING_PROFILES_ACTIVE`  | `prod`            | Perfil de Spring activo            |

---

## Pruebas y Calidad

### Ejecutar los tests

```bash
cd l2g8

# Ejecutar todos los tests y generar informe de cobertura
./mvnw verify
```

El informe de JaCoCo se genera en `target/site/jacoco/index.html`.

Los tests están divididos en tres niveles:

- **Unitarios de dominio**: prueban `Solicitud` y sus reglas de negocio sin ningún mock ni framework externo.
- **Unitarios de servicio**: usan Mockito para aislar los repositorios y verificar que los servicios orquestan correctamente.
- **Integración JPA**: anotados con `@DataJpaTest`, comprueban las entidades y repositorios contra H2 real.

### CI y SonarCloud

El workflow `.github/workflows/ci.yml` se ejecuta en cada push a `main` y en cada Pull Request. Lanza `mvn verify` junto con el análisis de Sonar en un solo paso.

El Quality Gate configurado en SonarCloud exige:
- Cobertura ≥ 80%
- 0 Bugs
- 0 Vulnerabilidades
- 0 Code Smells críticos

El merge a `main` queda bloqueado si el pipeline falla.

---

## Release Management y Versionado

El proyecto sigue un ciclo de **entrega continua (Continuous Delivery)** reproducible: cada versión publicada es trazable hasta el commit que la generó.

### Versionado semántico (SemVer)

Las versiones siguen el formato `MAJOR.MINOR.PATCH`:

| Segmento  | Cuándo se incrementa                            |
|-----------|-------------------------------------------------|
| **MAJOR** | Cambios incompatibles con versiones anteriores  |
| **MINOR** | Nuevas funcionalidades retrocompatibles         |
| **PATCH** | Correcciones de errores                         |

El número de versión se decide analizando los **Conventional Commits** desde la última release: `feat:` → MINOR, `fix:` → PATCH, y un cambio con `BREAKING CHANGE` → MAJOR. La justificación de cada salto se documenta en `l2g8/docs/release-notes.md`.

### Cómo se publica una versión

Las releases **no se crean a mano**: se disparan automáticamente al pushear un tag `v*`.

```bash
# 1. Trabajar en una rama y abrir PR (nunca taggear sin pasar por PR)
# 2. Merge a main tras pasar el CI
# 3. Crear y pushear el tag de versión
git tag v1.2.0
git push origin v1.2.0
```

El push del tag dispara `.github/workflows/release.yml`, que de forma automática:

| Paso | Acción                                                                   |
|------|--------------------------------------------------------------------------|
| 1    | Checkout del repositorio                                                 |
| 2    | Fija la versión Maven a partir del tag (`v1.2.0` → `1.2.0`)              |
| 3    | Compila, ejecuta tests y análisis Sonar con **Quality Gate bloqueante**  |
| 4    | Genera el artefacto `l2g8-<versión>.jar`                                 |
| 5    | Construye y publica la imagen Docker versionada en GHCR                   |
| 6    | Crea la Release en GitHub con notas automáticas + `release-notes.md` y adjunta el `.jar` |

Si el Quality Gate de Sonar falla, **la release se aborta**: nunca se libera una versión con métricas en rojo.

### Trazabilidad commit → artefacto

```
commit → PR → merge a main → tag vX.Y.Z → release.yml → Release en GitHub + .jar + imagen Docker
```

No hay ambigüedad sobre qué commit generó qué versión: el tag apunta a un commit exacto y tanto el `.jar` como la imagen Docker llevan ese mismo número de versión.

---

## Endpoints principales

### Solicitudes — `/api/solicitudes`

| Método   | Ruta                          | Descripción                                              |
|----------|-------------------------------|----------------------------------------------------------|
| `POST`   | `/api/solicitudes`            | Crea una nueva solicitud en estado `ABIERTA`             |
| `GET`    | `/api/solicitudes`            | Lista todas las solicitudes                              |
| `GET`    | `/api/solicitudes/{id}`       | Obtiene una solicitud por su ID                          |
| `PUT`    | `/api/solicitudes/{id}/tecnico` | Asigna un técnico activo a la solicitud (pasa a `PROCESANDO`) |
| `PUT`    | `/api/solicitudes/{id}/estado`  | Cambia el estado de la solicitud                        |
| `PATCH`  | `/api/solicitudes/{id}/reabrir` | Reabre una solicitud cerrada (vuelve a `PROCESANDO`)    |

**Body para asignar técnico** (`PUT /tecnico`):
```json
{ "tecnicoId": 1 }
```

**Body para cambiar estado** (`PUT /estado`):
```json
{ "nuevoEstado": "CERRADA" }
```

Los estados válidos son: `ABIERTA`, `PROCESANDO`, `CERRADA`.

---

### Técnicos — `/api/tecnicos`

| Método | Ruta             | Descripción                  |
|--------|------------------|------------------------------|
| `POST` | `/api/tecnicos`  | Crea un nuevo técnico        |
| `GET`  | `/api/tecnicos`  | Lista todos los técnicos     |

**Body para crear técnico** (`POST /tecnicos`):
```json
{
  "nombre": "María García",
  "estado": "ACTIVO"
}
```

Los estados válidos para un técnico son: `ACTIVO`, `INACTIVO`. Solo se puede asignar un técnico en estado `ACTIVO` a una solicitud.

---

## Documentación adicional

En la carpeta `l2g8/docs/` se encuentra documentación generada durante el desarrollo:

- `refactor-notes.md` — análisis de deuda técnica y técnicas de refactorización aplicadas (Extract Method, delegación a enums, limpieza de tests JUnit 5).
- `change-analysis.md` — análisis de impacto previo a la implementación del requisito de reapertura de solicitudes y mantenimiento del histórico de estados.
- `release-notes.md` — justificación del versionado semántico (SemVer) y notas de cada versión publicada.
- `memoria-tecnica.pdf` — memoria técnica de la práctica: decisiones de diseño, patrones aplicados, comparativa de métricas de calidad y análisis de deuda técnica.