# select parent image
FROM maven:3.6.3-jdk-11

# copy the source tree ant the pom.xml to our new container
COPY ./ ./

# package our application code
RUN mvn package -Dmaven.test.skip

# set the startup command to execute the jar
CMD ["java","-jar", "target/pmbakanov-1.0.jar"]
