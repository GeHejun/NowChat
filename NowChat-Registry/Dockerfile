FROM java:8
MAINTAINER gehejun
ADD /target/NowChat-Registry-1.0-SNAPSHOT-jar-with-dependencies.jar NowChat-Registry.jar
EXPOSE 9999
EXPOSE 9998
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m","NowChat-Registry.jar"]