FROM openjdk:21-jdk
WORKDIR /app
COPY target/oak-desafio-0.0.1-SNAPSHOT.jar /app/oak-desafio.jar
EXPOSE 8080
CMD ["java","-jar","oak-desafio.jar"]