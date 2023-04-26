FROM bellsoft/liberica-openjdk-centos:17
RUN mkdir /zk
COPY ./target/zkspringboot-security-demo-0.0.1-SNAPSHOT.jar /zk
CMD java -jar /zk/zkspringboot-security-demo-0.0.1-SNAPSHOT.jar
EXPOSE 7777:7777
