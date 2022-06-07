FROM public.ecr.aws/gw2/jdk-11:latest
WORKDIR /
ADD target/api-gateway-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 5000
CMD java -jar app.jar