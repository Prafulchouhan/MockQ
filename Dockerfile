FROM openjdk:17
EXPOSE 8080
ADD target/MockQ.jar MockQ.jar
ENTRYPOINT ["java","-jar","/MockQ.jar"]