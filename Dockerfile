FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean package -DskipTests



FROM eclipse-temurin:17-jre-jammy
EXPOSE 8081
WORKDIR /app

RUN mkdir logs
COPY --from=builder /app/target/*.jar app.jar


VOLUME /app/logs
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-Dlogging.file.name=/app/logs/application.log", "-jar", "app.jar"]
