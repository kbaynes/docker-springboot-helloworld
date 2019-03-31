FROM openjdk:8-jdk-alpine
LABEL "author"="Kevin Baynes k9@k9b9.com"
# Tomcat needs this dir
VOLUME /tmp
# Install prerequisites
RUN apk add --update curl && rm -rf /var/cache/apk/*
ARG JAR_URL
RUN curl -o app.jar ${JAR_URL}
# See +UseCGroupMemoryLimitForHeap options to make sure Java doesn't get killed in prod
# See g00glen00b link in the README
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]