# Dockerfile for wordapp (单词大冒险)
# Expects JAR to be built before docker build

FROM eclipse-temurin:17-jre-alpine

# Install curl for health checks
RUN apk add --no-cache curl tzdata

# Create app user for security
RUN addgroup -S spring && adduser -S spring -G spring

# Set working directory
WORKDIR /app

# Create data directory for H2 file storage (volume mount target)
RUN mkdir -p /app/data /app/logs && chown -R spring:spring /app

# Copy the pre-built JAR (version-agnostic using wildcard)
COPY target/*.jar app.jar

# Change ownership to non-root user
USER spring:spring

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD curl -f http://localhost:8080/actuator/health || exit 1

# JVM options for production
ENV JAVA_OPTS="-Xmx512m -Xms256m -XX:+UseG1GC -XX:MaxGCPauseMillis=200 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/app/logs -Duser.timezone=Asia/Shanghai"

# Default profile (h2 with file-based persistence under /app/data)
ENV SPRING_PROFILES_ACTIVE=h2
ENV TZ=Asia/Shanghai

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
