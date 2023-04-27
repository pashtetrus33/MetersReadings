# the first stage of our build will use a maven 3.6.1 parent image
#FROM maven:3.8.7-openjdk-18-slim AS MAVEN_BUILD
FROM maven:3.8.3-openjdk-16-slim AS MAVEN_BUILD
# copy the pom and src code to the container
COPY ./ ./
# package our application code without tests
#RUN mvn package -Dmaven.test.skip
RUN mvn clean package
#add library for excel export
RUN apt-get update -y && apt-get install -y libfontconfig1

# the second stage of our build will use open jdk 8 on alpine 3.9
FROM openjdk:11-jre-slim-bullseye

# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /target/pmbakanov-1.0.jar /pmbakanov-1.0.jar
# instruction for open port
EXPOSE 8080
# set the startup command to execute the jar
CMD ["java","-jar", "/pmbakanov-1.0.jar"]
