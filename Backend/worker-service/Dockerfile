FROM eclipse-temurin:21-jdk AS build
WORKDIR /src
COPY . .
RUN sed -i 's/\r$//' ./gradlew && chmod +x ./gradlew
RUN ./gradlew :worker-service:bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /src/worker-service/build/libs/*.jar /app/app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","/app/app.jar"]