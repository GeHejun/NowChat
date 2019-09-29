FROM java:8
MAINTAINER gehejun
ADD /target/NowChat-Rest-1.0-SNAPSHOT.jar NowChat-Rest.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m","NowChat-Rest.jar"]