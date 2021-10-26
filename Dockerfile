FROM openjdk:11-jre

ARG  API_USERNAME=xxx
ARG  API_PASSWORD=xxx

COPY target/*.jar /opt/webapp.jar

CMD java -Dserver.port=$PORT $JAVA_OPTS -jar /opt/webapp.jar