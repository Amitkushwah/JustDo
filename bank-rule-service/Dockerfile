FROM public.ecr.aws/gw2/jdk-11:latest
WORKDIR /
ADD target/rules-service.jar app.jar
EXPOSE 8090
CMD java -jar app.jar