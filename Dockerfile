FROM openjdk:17-jdk-alpine

WORKDIR /app

EXPOSE 8080

# ENV DATABASE_URL=${DATABASE_URL} \
#     DATABASE_USERNAME=${DATABASE_USERNAME} \
#     DATABASE_PASSWORD=${DATABASE_PASSWORD}

ADD ./target/monitoreo-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]