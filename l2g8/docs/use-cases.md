# Use cases for `Solicitud` API

## 1. Crear una solicitud

Request:

```http
POST /api/solicitudes
Content-Type: application/json
```

Response `201 Created`:

```json
{
  "id": 1,
  "estado": "ABIERTA",
  "fechaCreacion": "2026-05-10T17:22:00.000+00:00",
  "nombreTecnico": "Pendiente"
}
```

## 2. Consultar una solicitud

Request:

```http
GET /api/solicitudes/1
```

Response `200 OK`:

```json
{
  "id": 1,
  "estado": "ABIERTA",
  "fechaCreacion": "2026-05-10T17:22:00.000+00:00",
  "nombreTecnico": "Pendiente"
}
```

## 3. Listar solicitudes

Request:

```http
GET /api/solicitudes
```

Response `200 OK`:

```json
[
  {
    "id": 1,
    "estado": "ABIERTA",
    "fechaCreacion": "2026-05-10T17:22:00.000+00:00",
    "nombreTecnico": "Pendiente"
  }
]
```

## 4. Asignar técnico

Request:

```http
PUT /api/solicitudes/1/tecnico
Content-Type: application/json

{
  "tecnicoId": 7
}
```

Response `200 OK`:

```http
```

## 5. Cambiar estado

Request:

```http
PUT /api/solicitudes/1/estado
Content-Type: application/json

{
  "nuevoEstado": "PROCESANDO"
}
```

Response `200 OK`:

```http
```

## 6. Reabrir solicitud

Request:

```http
PATCH /api/solicitudes/1/reabrir
```

Response `200 OK`:

```http
```