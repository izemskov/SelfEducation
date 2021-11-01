FROM bellsoft/liberica-openjdk-debian:8
ADD target/serviceapp-0.0.1-SNAPSHOT.jar ./
CMD java -jar serviceapp-0.0.1-SNAPSHOT.jar --spring.profiles.active=docker
