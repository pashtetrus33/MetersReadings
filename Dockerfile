# select parent image
FROM maven:3.8.5-openjdk-17-slim

# copy the source tree ant the pom.xml to our new container
COPY ./ ./

# package our application code
RUN mvn package -am -o -Dmaven.test.skip -T 1C

# set the startup command to execute the jar
CMD ["java","-jar", "target/pmbakanov-1.0.jar"]
