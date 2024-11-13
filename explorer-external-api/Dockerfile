FROM openjdk:17

WORKDIR /app

ARG JAR_FILE=explorer-external-api/build/libs/explorer-external-api.jar

COPY ${JAR_FILE} isshonigo-server.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "-Duser.timezone=Asia/Seoul", "isshonigo-server.jar"]