FROM maven:3.8.7-openjdk-18-slim AS MAVEN_BUILD

# copy the source tree and the pom.xml to our new container
COPY ./ ./

# package our application code
RUN mvn package -Dmaven.test.skip

# the second stage of our build will use openjdk
#FROM openjdk:19-jdk-slim
# copy only teh artifacts we need from the first stage and discard the rest
#COPY --from=MAVEN_BUILD /target/pmbakanov-1.0.jar /pmbakanov-1.0.jar
# set the startup command to execute the jar
#CMD ["java","-jar", "/pmbakanov-1.0.jar"]

#RUN apt-get update -y && apt-get install -y libfreetype6
RUN apt-get update -y && apt-get install -y libfontconfig1

CMD ["java","-jar", "/target/pmbakanov-1.0.jar"]
