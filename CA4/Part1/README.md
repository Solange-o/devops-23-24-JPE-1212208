# Class Assignment 4 (CA4) - Part1

## Introduction

The objective of this first part of the assignment is to practice using Docker by creating Docker images and running containers using the CA2 chat application available at https://bitbucket.org/pssmatos/gradle_basic_demo/. 

Docker is a platform that allows developers to create, deploy, and run applications in containers. Containers are lightweight, portable, and include everything an application needs to run: code, runtime, system tools, libraries, and settings. This ensures that the application runs consistently across different environments, from development machines to production servers.

To explore the concept of docker images, let's create two versions of the solution (version 1 and version 2).

## Outline

1. [Version 1](#Version-1)
    - [Other commands](#Other-commands)
2. [Version 2](#Version-2)
3. [Publish the image](#Publish-the-image)
4. [Conclusion](Conclusion)


## Version 1

In this first version we're going to build the chat server inside the Dockerfile.

**1.** Inside a CA4/V1 folder create a Dockerfile to build the chat server inside the Docker image with the following content:

```Dockerfile
FROM ubuntu:22.04
RUN apt-get update
RUN apt-get install -y openjdk-17-jdk
RUN apt-get install -y git
RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo
WORKDIR /gradle_basic_demo
RUN chmod +x gradlew
RUN ./gradlew build
EXPOSE 59001
CMD ["java", "-cp", "build/libs/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
```

**1.1** Let's explore what each line does:

FROM ubuntu:22.04                       - The container will be based on the Ubuntu 22.04 operating system.
RUN apt-get update                      - Ensure that the container has access to the latest versions of the packages available in the Ubuntu repositories.
RUN apt-get install -y openjdk-17-jdk   - Installs the JDK (Java Development Kit) version 17. Required to compile and run Java applications, such as the chat application.
RUN apt-get install -y git              - Installs Git. Required to clone the Bitbucket chat application repository.
RUN git clone https://bitbucket.org/pssmatos/gradle_basic_demo - Clone the repository.
WORKDIR /gradle_basic_demo              - Defines the directory where subsequent commands will be executed. This is the directory where the repository was cloned.
RUN chmod +x gradlew                    - Gives execution permission to the gradlew script that is used to build the application with Gradle.
RUN ./gradlew build                     - Runs the gradlew script and compiles the chat application, producing the JAR file that will be executed.
EXPOSE 59001                            - Tells Docker that the container will listen on port 59001 at runtime.
CMD ["java", "-cp", "build/libs/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]  - Start the chat application by specifying the compiled JAR file and port 59001 for the chat server.

**2.** Now enter a terminal inside the root folder of the project and run the following command:

```bash 
docker build -t chat-server:v1 .
```

It is used to create a Docker image from a Dockerfile in the current directory.

After, if you are using Windows you can open Docker Desktop and check the Images tab to see the image that is being created.

**3.** Run the container with the following command:

```bash
docker run -p 59001:59001 chat-server:v1
```

- It is used to start a container from a specific Docker image and map ports between the host and the container.

Note: If you have both Dockerfiles in the same folder (e.g. with names Dockerfile and Dockerfile-v2), instead of creating a separate folder for version 1 (v1) and another for version 2 (v2), you need to add the -f flag to the command, for example: **docker build -f Dockerfile-v2 -t chat-server-v2 .**

**4.** Go to the CA2 Part1 folder and run the chat client to connect to the server:

```bash
./gradlew runClient
```


### Other commands

1. To see the containers running:

```bash
docker ps
```

2. To stop the container:

```bash
docker stop <container_id>
```

3. To remove the container:

```bash
docker rm <container_id>
```

* To remove the image:

```bash
docker rmi chat-server
```


## Version 2

In this version, we'll build the chat server on the host computer and copy the jar file to the Dockerfile.

**1.** Inside a CA4/V2 folder create a Dockerfile with the following content:

```Dockerfile
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY . /app
EXPOSE 59001
CMD ["java", "-cp", "/app/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]
```

**1.1** Let's explore what each line does:

FROM openjdk:21-jdk-slim    - Specifies the base image for the container.
WORKDIR /app                - Set /app as the working directory. All subsequent commands in the Dockerfile will be executed from this directory.
COPY . /app                 - Copies files from the current directory on the host to the /app directory in the container.
EXPOSE 59001                - Tells Docker that the container will listen on port 59001 at runtime.
CMD ["java", "-cp", "/app/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]  - Defines the default command to be executed when the container is started.

**2.** Now enter a terminal inside the root folder of the project and run the following command:

```bash 
docker build -t chat-server:v2 .
```

**3.** Run the container with the following command:

```bash
docker run -p 59001:59001 chat-server:v2
```

**4.** Go to the CA2 Part1 folder and run the chat client to connect to the server:

```bash
./gradlew runClient
```


## Publish the image

1. To login to Docker Hub:

```bash
docker login
```

2. Tag the images

The command is: **docker tag (id_imagem_origem) (username in Docker Hub)/(name_imagem_destino)**

```bash
docker tag cf62490d55d4 solangeoliveira/ca4-part1-v1
```

```bash
docker tag f3bbecc77d4a solangeoliveira/ca4-part1-v2
```

3. Finally we need to push the image

The command is: **docker push (username in Docker Hub)/(name_imagem_destino)**

```bash
docker push solangeoliveira/ca4-part1-v1
```

```bash
docker docker push solangeoliveira/ca4-part1-v2
```

* The image should now be available in Docker Hub


## Conclusion

Overall, this assignment provided hands-on experience with Docker, enhancing our understanding of containerization, image creation, and container orchestration. These skills are essential for modern software development and deployment, enabling more reliable and scalable application delivery. The completion of this task marks a significant step in mastering the use of Docker for application development and deployment.




