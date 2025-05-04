FROM gradle:jdk17-alpine AS builder
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY gradle.lockfile .
COPY src ./src

RUN chmod +x ./gradlew && ./gradlew build --no-daemon -x test

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 6001

ENTRYPOINT ["java","-jar","app.jar"]