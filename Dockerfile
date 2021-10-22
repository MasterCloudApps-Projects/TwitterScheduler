FROM openjdk:11-jre

COPY target/*.jar /opt/webapp.jar

CMD java -Dserver.port=$PORT $JAVA_OPTS -jar /opt/webapp.jar