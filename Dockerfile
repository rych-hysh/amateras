FROM amazoncorretto:17
WORKDIR /app
ENTRYPOINT ["bash", "entrypoint.sh"]