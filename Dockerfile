# Use a lightweight OpenJDK 11 runtime image
FROM openjdk:11-jre-slim

# Create a volume to store temporary files
VOLUME /tmp

# Copy the built JAR file from the target directory into the container
COPY target/ob-item-service-0.0.1-SNAPSHOT.jar app.jar

# Specify the command to run the application
ENTRYPOINT ["java","-jar","/app.jar"]