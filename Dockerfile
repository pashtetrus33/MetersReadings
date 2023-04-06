#
# Build stage
#
FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /target/pmbakanov-1.0.jar /target/pmbakanov-1.0.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/target/pmbakanov-1.0.jar"]
