FROM openjdk:11-jre

ARG API_USERNAME=****
ARG API_PASSWORD=****

COPY target/*.jar /opt/webapp.jar

CMD java -Dserver.port=$PORT $JAVA_OPTS -jar /opt/webapp.jar