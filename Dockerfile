FROM maven:latest AS build
# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and the source code to the container
COPY pom.xml ./
COPY src ./src

# Build the Maven project
RUN mvn clean package

# Use the official OpenJDK image for running the application
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app


## Environment variables
#ENV SPRING_DATASOURCE_URL="jdbc:mysql://db:3306/defaultdb?useSSL=true&serverTimezone=UTC&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true"
#ENV SPRING_DATASOURCE_USERNAME="doadmin"
#ENV SPRING_DATASOURCE_PASSWORD="AVNS_RD3pUrMTZXNQz4IKpVF"


# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port (change the port number if your application uses a different port)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
