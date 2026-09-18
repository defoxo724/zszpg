FROM eclipse-temurin:26.0.2_10-jre-alpine-3.24
WORKDIR /java_app
COPY ./target/*.jar java_application.jar
EXPOSE 8080
CMD ["java", "-jar", "java_application.jar"]