FROM java:8
MAINTAINER gehejun
ADD /target/NowChat-Proxy-1.0-SNAPSHOT-jar-with-dependencies.jar NowChat-Proxy.jar
EXPOSE 7999
EXPOSE 7998
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m","NowChat-Proxy.jar"]