FROM java:8
MAINTAINER gehejun
ADD /target/NowChat-Web-1.0-SNAPSHOT.jar NowChat-Web.jar
EXPOSE 80
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m","NowChat-Web.jar"]

