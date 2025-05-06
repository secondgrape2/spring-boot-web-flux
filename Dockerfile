FROM gradle:jdk17-alpine AS app_base
WORKDIR /app
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src ./src
RUN chmod +x ./gradlew

FROM app_base AS app_builder
RUN ./gradlew build --no-daemon -x test

FROM app_base AS app_tester
CMD ["./gradlew", "test"]

FROM eclipse-temurin:17-jdk-alpine AS app_runner
WORKDIR /app
COPY --from=app_builder /app/build/libs/*.jar app.jar
EXPOSE 6001
EXPOSE 8090
ENTRYPOINT ["java","-jar","app.jar"]