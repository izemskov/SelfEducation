FROM bellsoft/liberica-openjdk-debian:8
ADD target/eurekaserver-0.0.1-SNAPSHOT.jar ./
EXPOSE 8761
CMD java -jar eurekaserver-0.0.1-SNAPSHOT.jar --spring.profiles.active=docker
