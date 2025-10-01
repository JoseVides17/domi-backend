# ----------------------------
# Etapa 1: Compilación con Maven
# ----------------------------
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app

# Copiamos pom.xml y descargamos dependencias primero (caché más eficiente)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiamos el resto del código
COPY src ./src

# Compilamos y generamos el JAR
RUN mvn clean package -DskipTests

# ----------------------------
# Etapa 2: Imagen final para correr la app
# ----------------------------
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiamos el JAR desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Render asigna dinámicamente el puerto
EXPOSE 8080

# Comando para arrancar la app
CMD ["java", "-jar", "app.jar"]
