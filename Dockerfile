FROM eclipse-temurin:17-jdk-alpine
RUN mkdir /opt/app
COPY target/ZephyrWork-0.1.jar /opt/app
CMD ["java", "-jar", "/opt/app/ZephyrWork-0.1.jar"]