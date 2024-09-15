# Build Package
#
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /home/app
COPY . .
RUN mvn -f pom.xml clean package -DskipTests

#
# Package Stage
#
FROM openjdk:17-jdk-slim
WORKDIR /home/app
COPY --from=build target/BankApiCode-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
