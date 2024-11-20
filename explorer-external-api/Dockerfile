FROM openjdk:17

WORKDIR /app

ARG JAR_FILE=explorer-external-api/build/libs/explorer-external-api.jar

COPY ${JAR_FILE} isshonigo-server.jar

EXPOSE 8080

ARG PROFILE=dev
ENV PROFILE=${PROFILE}

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=${PROFILE}", "-Duser.timezone=Asia/Seoul", "isshonigo-server.jar"]