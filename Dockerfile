# Étape 1 : Build avec Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

# Étape 2 : Image d'exécution
FROM eclipse-temurin:17-jdk as base
VOLUME /tmp
WORKDIR /app

# Copie du .jar généré
COPY --from=build /app/target/assurance-0.0.1-SNAPSHOT.jar app.jar

# Lancement de l'application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
