FROM openjdk:17-jdk-slim-buster
ADD target/EventRegister-0.0.1-SNAPSHOT.jar .
EXPOSE 8080
CMD java -D"spring.profiles.active=dev" -jar EventRegister-0.0.1-SNAPSHOT.jar "-web -webAllowOthers -tcp -tcpAllowOthers -browser"


