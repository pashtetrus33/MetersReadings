# select parent image
FROM maven:3.8.7-openjdk-18-slim

# copy the source tree ant the pom.xml to our new container
COPY ./ ./

# package our application code
RUN mvn clean package

# set the startup command to execute the jar
CMD ["java","-jar", "target/pmbakanov-1.0.jar"]
