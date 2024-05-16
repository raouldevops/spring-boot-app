FROM openjdk:17-jdk-alpine
MAINTAINER devops-raoul.com
COPY target/spring-boot-app-0.0.1-SNAPSHOT.jar spring-boot-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/spring-boot-app-0.0.1-SNAPSHOT.jar"]