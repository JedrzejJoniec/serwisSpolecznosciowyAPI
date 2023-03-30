FROM eclipse-temurin
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} serwis.jar
ADD src/main/resources/static/key.pub home
EXPOSE 8080
ENTRYPOINT ["java", "-Dspring.profiles.active=dev","-jar", "/serwis.jar"]