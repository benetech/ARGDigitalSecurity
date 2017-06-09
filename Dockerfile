FROM 814633283276.dkr.ecr.us-east-1.amazonaws.com/java:jdk8

MAINTAINER Ron Ellis <rone@benetech.org>

RUN mkdir /usr/local/arg
COPY target/Example-0.0.1-SNAPSHOT.war /usr/local/arg/ROOT.war

ENTRYPOINT ["java", "-jar", "/usr/local/arg/ROOT.war"]
