FROM public.ecr.aws/gw2/jdk-11:latest
WORKDIR /
ADD target/account-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8081
CMD java -jar app.jar