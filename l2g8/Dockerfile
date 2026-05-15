
FROM eclipse-temurin:17-jre-alpine


WORKDIR /app


COPY target/*.jar app.jar

# 4. Exponer el puerto 
EXPOSE 8080

# 5. Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]