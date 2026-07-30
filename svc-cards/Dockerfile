FROM maven:3.9-eclipse-temurin-25 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline -q
COPY src src
RUN mvn package -DskipTests -q

FROM eclipse-temurin:25-jre-jammy
WORKDIR /app
RUN groupadd -r aurix && useradd -r -g aurix aurix
USER aurix
COPY --from=builder /build/target/*.jar app.jar
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError -Djava.security.egd=file:/dev/./urandom"
EXPOSE 8094
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
