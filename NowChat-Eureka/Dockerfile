FROM java:8
MAINTAINER gehejun
ADD /target/NowChat-Eureka-1.0-SNAPSHOT.jar NowChat-Eureka.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","-Xms100m","-Xmx100m","NowChat-Eureka.jar"]