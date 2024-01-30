FROM openjdk:17

WORKDIR /app

COPY ./seas-be/build/libs/seas-0.0.1-SNAPSHOT.jar /app

ENTRYPOINT ["java", "-jar", "seas-0.0.1-SNAPSHOT.jar", "--spring.config.name=seas-config.yml"]