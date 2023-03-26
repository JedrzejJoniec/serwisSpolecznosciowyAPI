FROM eclipse-temurin
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} broker.jar
ADD src/main/resources/static/key.pub home
EXPOSE 8081
ENTRYPOINT ["java", "-Dspring.profiles.active=dev","-jar", "/broker.jar"]