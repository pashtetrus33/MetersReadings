# the first stage of our build will use a maven 3.6.3 parent image
FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD

# copy the source tree and the pom.xml to our new container
COPY ./ ./

# package our application code
RUN mvn package -Dmaven.test.skip

# the second stage of our build will use openjdk
FROM openjdk:19-jdk-slim

# copy only teh artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD /target/pmbakanov-1.0.jar /pmbakanov-1.0.jar

RUN apk add --update fontconfig freetype
# set the startup command to execute the jar
CMD ["java","-jar", "/pmbakanov-1.0.jar"]
