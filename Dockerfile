FROM maven:3.5.4-jdk-8-alpine as builder 

ADD . /home/app

RUN cd /home/app \
	&& mvn install -P prod -Dmaven.test.skip=true

FROM openjdk:8u181-jre-alpine3.8

LABEL maintainer="Oscar Toledo <osky.toledo@gmail.com>"

ARG APP_NAME=contacts
ARG APP_VERSION=0.0.1-SNAPSHOT

COPY --from=builder /home/app/target/${APP_NAME}-${APP_VERSION}.jar app.jar

COPY --from=builder /home/app/data/people.csv data/people.csv

VOLUME /tmp

EXPOSE 8080

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
