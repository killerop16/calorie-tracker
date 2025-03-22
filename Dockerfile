FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY .mvn .mvn
COPY mvnw .
COPY ./pom.xml ./pom.xml
COPY ./src ./src
RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/calorie-tracker.jar /app/calorie-tracker.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/calorie-tracker.jar"]
