# the first stage of our build will use a maven 3.6.1 parent image
FROM maven:3.8.6-openjdk-11-slim AS MAVEN_BUILD

LABEL maintainer="Pavel Bakanov"
# copy the pom and src code to the container
COPY ./ ./
# package our application code without tests
RUN mvn clean package

# the second stage of our build will use open jdk 11
FROM openjdk:11-jre-slim

#add library for excel export
RUN apt-get update -y && apt-get install -y libfontconfig1 && \
    rm -rf /var/lib/apt/lists/*

# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /target/pmbakanov-1.0.jar /pmbakanov-1.0.jar

ARG ARG_MAIL=metersapp@mail.ru
ARG ARG_MAIL_PASSWORD=R5ZubqkRHyfLDLYGDEbn
ARG ARG_DATABASE_URL=mysql://bdce88d7f43907:3898ed38@us-cdbr-east-06.cleardb.net/heroku_d1e38e1f06f2064?reconnect=true
ENV MAIL=${ARG_MAIL}
ENV MAIL_PASSWORD=${ARG_MAIL_PASSWORD}
ENV DATABASE_URL=${ARG_DATABASE_URL}
# instruction for open port
EXPOSE 8080
# set the startup command to execute the jar
CMD ["java","-jar", "/pmbakanov-1.0.jar"]
