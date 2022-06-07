FROM public.ecr.aws/gw2/jdk-11:latest
WORKDIR /
ADD target/eureka-server-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8761
CMD java -jar app.jar