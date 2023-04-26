FROM bellsoft/liberica-openjdk-centos:17
RUN mkdir /zk
COPY ./target/sprint-oauth2-1.0.0.jar /zk
CMD java -jar /zk/sprint-oauth2-1.0.0.jar
EXPOSE 7777:7777
