# Build Stage
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /home/app
COPY . .
# Build the project using Maven
RUN mvn -f pom.xml clean package
# Verify the JAR file is generated (Optional)
RUN ls target

# Package Stage
FROM openjdk:17-jdk-slim
WORKDIR /home/app
# Copy the JAR file from the build stage
COPY --from=build /home/app/target/BankApiCode-0.0.1-SNAPSHOT.jar app.jar
# Expose port 8080 for the application
EXPOSE 8080
# Start the application
ENTRYPOINT ["java","-jar","app.jar"]
