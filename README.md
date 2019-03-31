# Dockerized SpringBoot HelloWorld

## Goal

The goal of this project is to create a simple Hello World application in Spring Boot, and 
build this application into a Docker container.

Additionally:
- how to build the Docker image with a remote JAR
- how to make the image available via Docker Hub

## Note on Simplicity

There are more advanced ways to build a docker image from a JAR, but the following method has
the benefit of being simple and it works and is intended for the uninitiated.

For more complex scenarios, see:
- [Spring IO Spring Boot Docker Guide](https://spring.io/guides/gs/spring-boot-docker/)
- [g00glen00b Spring Boot Docker Guide](https://g00glen00b.be/docker-spring-boot/)
- [Running Java in a Docker container without getting killed](https://blog.csanchez.org/2017/05/31/running-a-jvm-in-a-container-without-getting-killed/)

Notice that the Dockerfile does not use the +UseCGroupMemoryLimitForHeap options linked above, 
because that requires Java 8 u131 minimum.

### Motivation: Building from a remote JAR

Demonstrate how a remote Spring Boot JAR (available on the Web or in an artifact 
repository such as Maven) can be built into a Docker image. This is useful for building Docker 
images on remote machines using only a Dockerfile, or for building working installations with
Docker Compose using 'build'. Thus, only a Dockerfile need be shipped to build the image, not 
the JAR (as the JAR is pulled locally during the build.) In this scenario, it is possible to start
an empty server instance, run a startup script to pull the Dockerfile (or Docker Compose file), and
run the Docker image build and start the container to run the app. This is a likely scenario in 
production where the application being built is private (not published to Docker hub).

### Motivation: Publish to Docker Hub

Demonstrate how to tag an image and publish it to Docker Hub to easily distribute an application publicly. 
This is a convenient way to publish an application.

## Publish the JAR

After building the jar, push it to an S3 bucket so that it's available on the web to be pulled into a Docker build: https://s3.amazonaws.com/acg-public.kevinbaynes.com/springboot-helloworld/hello-0.0.1-SNAPSHOT.jar

## Build Docker image with local JAR

Run this command, from the command line within this project dirctory, specifying the 
relative location of the local Spring Boot Jar file, where 'hello_local_v1' is the tag name of the image.
*See Dockerfile.local*
To build your own JAR, replace the tag appropriately and the name of the JAR in the target dir.
```
docker build -f Dockerfile.local -t hello-local-v1 --build-arg JAR_FILE=target/hello-0.0.1-SNAPSHOT.jar .
```

## Build Docker image with remote JAR

Run this command, from the command line within this project directory, to build the image 
from a remote JAR URL. In this example the tag name is 'kbaynes/springboot-helloworld' where we follow
the Docker Hub convention of tagging the image with [docker_hub_user_name]/[image_name]. If you 
plan to push this image to Docker Hub, then replace 'kbaynes' with your Docker Hub user name.

```
docker build -f Dockerfile -t kbaynes/springboot-helloworld --build-arg JAR_URL=https://s3.amazonaws.com/acg-public.kevinbaynes.com/springboot-helloworld/hello-0.0.1-SNAPSHOT.jar .
```

## Run the Docker image

To run the image and expose Tomcat's 8080 port on the local host port 80. If you do not expose the port,
then the container will not receive the calls from the browser.
```
docker run -p 80:8080 kbaynes/springboot-helloworld
```

## Publish the image to Docker Hub

From the command line, run the following and make note that 'docker login' requires a _username_ and **not an email address**. Once the image is pushed, login to hub.docker.com to check that the image exists.
```
docker login [your_docker_hub_username]
docker push [your_docker_hub_username]/springboot-helloworld
```

