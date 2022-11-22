FROM openjdk:11-jre

COPY build/libs/specific-extension-blocking-0.0.1-SNAPSHOT.jar app.jar

ENV	USE_PROFILE dep

ENV JAVA_TOOL_OPTIONS "-Dspring.profiles.active=${USE_PROFILE}"

ENTRYPOINT ["java","-jar","app.jar"]
