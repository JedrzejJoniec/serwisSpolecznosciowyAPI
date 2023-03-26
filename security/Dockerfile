FROM eclipse-temurin
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} security.jar
ADD key.pub home
ADD key.priv home
EXPOSE 8082
ENTRYPOINT ["java", "-Dspring.profiles.active=dev","-jar", "/security.jar"]