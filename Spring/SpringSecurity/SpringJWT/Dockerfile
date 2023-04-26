FROM bellsoft/liberica-openjdk-centos:17
RUN mkdir /jwt
COPY ./target/spring-jwt-1.0.0.jar /jwt
CMD java -jar /jwt/spring-jwt-1.0.0.jar
EXPOSE 7778:7778
