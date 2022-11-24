FROM openjdk:11-jre

COPY build/libs/specific-extension-blocking-1.0.0-SNAPSHOT.jar app.jar

ENV	LOCATION classpath:/application-dep.yml,/home/ec2-user/application-dep.yml
ENV USE_PROFILE dep

ENV JAVA_TOOL_OPTIONS "-Dspring.config.location=${LOCATION} -Dspring.profiles.active=${USE_PROFILE}"

ENTRYPOINT ["java","-jar","app.jar"]
