FROM maven:3.8.7-openjdk-18-slim
COPY ./ ./
RUN mvn package -Dmaven.test.skip
RUN apt-get update -y && apt-get install -y libfontconfig1
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/target/pmbakanov-1.0.jar"]
