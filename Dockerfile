# ---- build stage: compile the fat JAR with Maven ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
# copy pom first so dependencies cache when only source changes
COPY pom.xml .
RUN mvn dependency:go-offline
# now copy source and build
COPY src ./src
RUN mvn clean package

# ---- run stage: slim JRE, no build tools shipped ----
FROM eclipse-temurin:21-jre
WORKDIR /app
# grab the built JAR from the build stage (name must match artifactId-version)
COPY --from=build /app/target/java-cron-job-with-kafka-1.0.jar app.jar
# your crontab input file
COPY crontab.txt .
# run the scheduler, passing the crontab file as an argument
ENTRYPOINT ["java", "-jar", "app.jar", "crontab.txt"]