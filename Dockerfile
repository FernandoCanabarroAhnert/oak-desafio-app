FROM openjdk:21-jdk
WORKDIR /app
COPY target/oak-desafio-0.0.1-SNAPSHOT.jar /app/oak-desafio.jar
EXPOSE 8080
RUN --mount=type=secret,id=SENDGRID_API_KEY,env=SENDGRID_API_KEY
CMD ["java","-jar","oak-desafio.jar"]