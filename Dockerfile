FROM eclipse-temurin:21-jdk
WORKDIR /opt/app
COPY target/springboot-images-new.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
