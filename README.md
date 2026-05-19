# mgcss-track-L2_G8

## Despliegue con Docker

### 1. Construir la imagen ejecutable
Para construir la imagen de Docker, nos ubicamos en la carpeta `l2g8` (donde está el `Dockerfile`) y ejecutamos:

```bash

# Compilar el .jar
./mvnw clean package

# Construir la imagen
docker build -t mgcss-track .
```

### 2. Ejecutar el contenedor correctamente y exponer variables
Podemos levantar el contenedor pasando variables de entorno para la configuración:

```bash
docker run \
  -p 8080:8080 \
  -e APP_PORT=8080 \
  --name mgcss-track-container \ 
   mgcss-track
```