FROM openjdk:8-jre-alpine3.9
RUN mkdir -p /opt/weather/
COPY target/weather-0.0.1-SNAPSHOT.jar /opt/weather/

CMD ["java","-jar","/opt/weather/weather-0.0.1-SNAPSHOT.jar"]
