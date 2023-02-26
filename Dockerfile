FROM amazoncorretto:17
#COPY build/libs/*.jar app.jar
WORKDIR /app
ENTRYPOINT ["bash", "entrypoint.sh"]
#ENTRYPOINT ["java", "-jar", "/app.jar"]