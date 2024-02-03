FROM openjdk:17

WORKDIR /app

COPY ./seas-be/build/libs/seas-0.0.1-SNAPSHOT.jar /app
#COPY ./seas-be/src/main/resources/seas-config.yml /app/seas-config.yml
#COPY ./seas-be/src/main/resources/application.yml /app/application.y

ENTRYPOINT ["java", "-jar", "seas-0.0.1-SNAPSHOT.jar", "--spring.config.additional-location=classpath:/seas-config.yml"]

EXPOSE 8081