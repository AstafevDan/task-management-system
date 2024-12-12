FROM gradle:8.10.2-jdk17-alpine AS builder

WORKDIR /app

COPY build.gradle gradlew gradlew.bat lombok.config settings.gradle version.gradle ./
COPY gradle ./gradle/
COPY src ./src/

RUN chmod +x gradlew
RUN ./gradlew bootJar

FROM openjdk:17-alpine

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar task-manager-sys.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "task-manager-sys.jar"]