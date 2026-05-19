
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Exponer variables de configuración por defecto
ENV APP_PORT=8080
ENV SPRING_PROFILES_ACTIVE=prod

COPY target/*.jar app.jar

#Exponer el puerto
EXPOSE ${APP_PORT}

ENTRYPOINT ["sh", "-c", "java -jar app.jar --server.port=${APP_PORT}"]