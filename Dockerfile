FROM openjdk:17
EXPOSE 8082
ADD target/postMicroserv-0.0.1-SNAPSHOT.jar postMicroserv.jar
ENTRYPOINT ["java", "-jar", "postMicroserv.jar"]
