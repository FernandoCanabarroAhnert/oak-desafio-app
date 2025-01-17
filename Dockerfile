FROM openjdk:21-jdk
WORKDIR /app
COPY target/oak-desafio-0.0.1-SNAPSHOT.jar /app/oak-desafio.jar
EXPOSE 8080
RUN --mount=type=secret,id=SENDGRID_API_KEY \
    sh -c "echo 'SENDGRID_API_KEY='$(cat /run/secrets/SENDGRID_API_KEY) > /app/.env"
CMD ["java","-jar","oak-desafio.jar"]