FROM openjdk:17

WORKDIR /app

ARG JAR_FILE="build/libs/*.jar"
ARG PROFILE=dev

COPY ${JAR_FILE} isshonigo-server.jar
EXPOSE 8080


ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "isshonigo-server.jar"]