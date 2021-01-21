# --- Build stage ---
FROM adoptopenjdk:11.0.9.1_1-jdk-hotspot AS build

WORKDIR /app

COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY src src

RUN ./gradlew build -x test
RUN cd build/libs && java -Djarmode=layertools -jar app-*.jar extract

# --- Release stage ---
FROM adoptopenjdk:11.0.9.1_1-jre-hotspot AS release

ARG APPLICATION=/app/build/libs

WORKDIR /app

COPY --from=build ${APPLICATION}/dependencies/ ./
COPY --from=build ${APPLICATION}/spring-boot-loader/ ./
COPY --from=build ${APPLICATION}/snapshot-dependencies/ ./
COPY --from=build ${APPLICATION}/application/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
