FROM public.ecr.aws/gw2/jdk-11:latest
WORKDIR /
ADD target/customer-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD java -jar app.jar