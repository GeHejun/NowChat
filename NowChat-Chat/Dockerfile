FROM java:8
MAINTAINER gehejun
ADD /target/NowChat-Chat-1.0-SNAPSHOT-jar-with-dependencies.jar NowChat-Chat.jar
EXPOSE 8999
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m","NowChat-Chat.jar"]