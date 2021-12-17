FROM bellsoft/liberica-openjdk-debian:8
ADD target/loadbalancer-1.0.jar ./
EXPOSE 8080
CMD java -jar loadbalancer-1.0.jar --spring.profiles.active=docker
