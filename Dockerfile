FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app

# Copy jar
COPY target/GharSeva-0.0.1-SNAPSHOT.jar app.jar

# Render uses dynamic PORT
ENV PORT=8080

# Expose port
EXPOSE 8080

# Run app with dynamic port binding
ENTRYPOINT ["sh", "-c", "java -Dserver.port=${PORT} -jar app.jar"]