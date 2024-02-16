FROM bellsoft/liberica-openjdk-debian:17
ADD target/worker-ms-1.0.0.jar ./
CMD java -jar worker-ms-1.0.0.jar --spring.profiles.active=docker
