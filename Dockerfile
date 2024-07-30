# the first stage of our build will use a maven 3.6.1 parent image
FROM maven:3.8.6-openjdk-11-slim AS MAVEN_BUILD

LABEL maintainer="Pavel Bakanov"
# copy the pom and src code to the container
COPY ./ ./
# package our application code without tests
#RUN mvn clean package -DskipTests=true
RUN mvn clean package

# the second stage of our build will use open jdk 11
FROM openjdk:11-jre-slim

#add library for excel export
RUN apt-get update -y && apt-get install -y fontconfig && \
    rm -rf /var/lib/apt/lists/*

# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /target/pmbakanov-1.0.jar /pmbakanov-1.0.jar
ARG MAIL
ARG MAIL_PASSWORD
ARG DATABASE_URL
ARG DATABASE_USER
ARG DATABASE_PASSWORD
ENV MAIL=${MAIL}
ENV MAIL_PASSWORD=${MAIL_PASSWORD}
ENV DATABASE_URL=${DATABASE_URL}
ENV DATABASE_USER=${DATABASE_USER}
ENV DATABASE_PASSWORD=${DATABASE_PASSWORD}
# instruction for open port
EXPOSE 443
EXPOSE 80
# set the startup command to execute the jar
CMD ["java","-jar", "/pmbakanov-1.0.jar"]
