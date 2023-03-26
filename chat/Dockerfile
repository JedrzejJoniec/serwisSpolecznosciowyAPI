FROM eclipse-temurin
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} chat.jar
ADD src/main/resources/static/key.pub home
EXPOSE 8083
ENTRYPOINT ["java", "-Dspring.profiles.active=dev","-jar", "/chat.jar"]