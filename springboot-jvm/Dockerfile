# Etapa 1: Compilación
FROM maven:3.9.4-eclipse-temurin-17 AS build

WORKDIR /app

# Copiamos el archivo de definición primero para aprovechar cache de dependencias
COPY pom.xml .

# Descargamos las dependencias antes de copiar el código
RUN mvn dependency:go-offline

# Ahora sí copiamos el código fuente
COPY src ./src

# Empaquetamos la aplicación
RUN mvn clean package -DskipTests

# Etapa 2: Ejecución
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiamos el .jar generado desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto por defecto
EXPOSE 8083

# Comando para ejecutar el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]

