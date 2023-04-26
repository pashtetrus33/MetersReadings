FROM maven:3.8.7-openjdk-18-slim AS MAVEN_BUILD
COPY ./ ./
RUN mvn package -Dmaven.test.skip
RUN apt-get update -y && apt-get install -y libfontconfig1
CMD ["java","-jar", "/target/pmbakanov-1.0.jar"]
