# Class Assignment 4 (CA4) - Part2

## Introduction

The aim of this task is to use Docker to set up a containerised environment to run the gradle version of the Spring Basic tutorial application. 
We're going to produce a solution similar to the one in part 2 of the previous CA but now using Docker instead of Vagrant.

Note: For the development of this assignment I am using *Docker Desktop* as a tool.

## Outline

1. [Web Dockerfile](#Web-Dockerfile)
2. [Database Dockerfile](#Database-Dockerfile)
3. [Docker-compose file](#Docker-compose-file)
4. [Publish the image](#Publish-the-image)
5. [Alternative Solution](#Alternative-Solution)
6. [Conclusion](Conclusion)


## Web Dockerfile

The first Dockerfile created was the web Dockerfile. 
This Dockerfile is responsible for creating an image that runs the app. 
The Dockerfile is shown below:

```Dockerfile
# It uses a base image of OpenJDK version 17 with a minimalist (slim) configuration.
FROM openjdk:17-jdk-slim

# Install additional utilities
RUN apt-get update -y && apt-get install -y git unzip

# Create a directory called /app. This directory will be used as the entry point point for the application in the container.
RUN mkdir /app

# Set /app as the current working directory. All subsequent commands will be executed from this directory.
WORKDIR /app/

# Clone your Spring Boot application repository
 RUN git clone https://github.com/Solange-o/devops-23-24-JPE-1212208.git

# Set the working directory
WORKDIR /app/devops-23-24-JPE-1212208/CA2/Part2

# Ensure the gradlew script is executable and build the application
 RUN chmod +x ./gradlew && ./gradlew clean build

# Configure the container to run as an executable
 ENTRYPOINT ["./gradlew"]
 CMD ["bootRun"]
```

## Database Dockerfile

This Dockerfile is responsible for creating an image that runs the H2 database. It first copies the contents of the h2 folder and then runs the H2 database. 
The Dockerfile is shown below:


```Dockerfile
# Use the base Ubuntu image for the container.
FROM ubuntu

# Update package lists and install necessary packages including OpenJDK 17, unzip, and wget.
RUN apt-get update && \
  apt-get install -y openjdk-17-jdk-headless && \
  apt-get install unzip -y && \
  apt-get install wget -y

# Create a directory named /usr/src/app in the container.
RUN mkdir -p /usr/src/app

# Set the working directory to /usr/src/app. All subsequent commands will be executed from this directory.
WORKDIR /usr/src/app/

# Download H2 Database and run it
RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar -O /opt/h2.jar
# Download the H2 Database JAR file from the specified URL and save it as /opt/h2.jar.

# Expose ports 8082 and 9092. These ports will be accessible from outside the container.
EXPOSE 8082
EXPOSE 9092

# Start H2 Server
CMD java -cp /opt/h2.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists
```

## Docker-compose file

**1.** This file is responsible for creating the two images and running the containers. The file is shown below:

```yaml
services:
  db:
    build:
      context: .
      dockerfile: Dockerfile-db
    container_name: h2-db
    ports:
      - "8082:8082"
      - "9092:9092"
    volumes:
      - h2-data:/opt/h2-data

  web:
    build:
      context: .
      dockerfile: Dockerfile-web
    container_name: spring-web
    ports:
      - "8080:8080"
    depends_on:
      - db

volumes:
  h2-data:
    driver: local
```

**1.1** This Docker Compose file defines two services: "db" and "web".

For the "db" service:
It builds an image using the Dockerfile specified (Dockerfile.db) in the current context.
Exposes ports 8082 and 9092.
Mounts the directory ./h2-data from the host to /opt/h2-data inside the container.
Assigns a container name to the service as "h2-db".

For the "web" service:
It builds an image using the specified Dockerfile (Dockerfile-web) in the current context.
Exposes port 8080.
Assigns a container name to the service as "spring-web".
Specifies a dependency on the "db" service, ensuring that "db" is started before "web".

Additionally, the file defines a volume named "h2-data" to persist the H2 Database data, using a local driver.


**1.2** After the docker-compose file is created open *Docker Desktop* 

**1.3** Now we can start the images as containers by running the following command:

```bash
docker-compose up
```

Note: The --build flag is used to build the images before starting the services. This is optional but preferable if you're making changes to the docker-compose.yml file or the Dockerfiles. In this case the following command is **docker-compose up --build**.

**2.** After starting the containers, the web app can be accessible:

Spring Boot Application: http://localhost:8080

H2 Database Console: http://localhost:8082


## Publish the image

To publish the project at the Docker Hub, you need to create an account at the Docker Hub and login.

**1.** To login to Docker Hub:

```bash
docker login
```

2. Tag the images

The command is: **docker tag (id_imagem_origem) (username in Docker Hub)/(name_imagem_destino)**

```bash
docker tag c2d021ff40ab solangeoliveira/ca4-part2-web
```

```bash
docker tag 0cc7a2b03780 solangeoliveira/ca4-part2-db
```

3. Finally we need to push the image

The command is: **docker push (username in Docker Hub)/(name_imagem_destino)**

```bash
docker push solangeoliveira/ca4-part2-web
```

```bash
docker push solangeoliveira/ca4-part2-db
```

* The image should now be available in Docker Hub


## Alternative Solution with Kubernetes

Kubernetes is an open-source container orchestration platform developed by Google. It automates the deployment, scaling, and management of containerized applications. Kubernetes manages clusters of virtual machines and runs containers on these nodes, making it easier to operate large-scale workloads with high availability, load balancing, monitoring, and security.

The Kubernetes is more complex to set up and manage than Docker, especially for beginners and small teams. Additionally, it consumes more system resources compared to Docker due to its complexity and additional features. Transitioning from Docker Compose to Kubernetes involves creating multiple YAML files to describe the different components of the system. However, with Kubernetes, we gain greater flexibility and scalability while maintaining automated container management and orchestration. It is important to note that Kubernetes offers advanced container orchestration features that can be essential for operating applications at scale and in complex production environments.

The differences between the manifests used in Docker (Docker Compose) and Kubernetes are mainly in the level of detail, flexibility and control that each offers.
Kubernetes uses multiple YAML files to describe various resources such as Pods, Services, Deployments, ConfigMaps, Secrets, etc.

**1.** Adapting to Kubernetes would require the following documents to be configured:

**1.1** Web Deployment (web-deployment.yaml)
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web-deployment
spec:
  replicas: 1
  selector:
    matchLabels:
      app: web
  template:
    metadata:
      labels:
        app: web
    spec:
      containers:
        - name: web
          image: <your-dockerhub-username>/devops-23-24:CA4_Part2_web
          ports:
            - containerPort: 8080
```

**1.2** Web Service (web-service.yaml)
```yaml
apiVersion: v1
kind: Service
metadata:
  name: web-service
spec:
  selector:
    app: web
  ports:
    - protocol: TCP
      port: 8080
      targetPort: 8080
  type: NodePort
```

**1.3** Database Deployment (db-deployment.yaml)
```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: db-deployment
spec:
  replicas: 1
  selector:
    matchLabels:
      app: db
  template:
    metadata:
      labels:
        app: db
    spec:
      containers:
        - name: db
          image: <your-dockerhub-username>/devops-23-24:CA4_Part2_db
          ports:
            - containerPort: 8082
            - containerPort: 9092
          volumeMounts:
            - mountPath: /usr/src/data-backup
              name: data-volume
      volumes:
        - name: data-volume
          hostPath:
            path: /data
            type: Directory
```

**1.4** Database Service (db-service.yaml)
```yaml
apiVersion: v1
kind: Service
metadata:
  name: db-service
spec:
  selector:
    app: db
  ports:
    - protocol: TCP
      port: 8082
      targetPort: 8082
    - protocol: TCP
      port: 9092
      targetPort: 9092
  type: NodePort
```

**2** Deploying to Kubernetes

**2.1** Deploy the database:

```bash
kubectl apply -f db-deployment.yaml
```

```bash
kubectl apply -f db-service.yaml
```

**2.2** Deploy the web application:

```bash
kubectl apply -f web-deployment.yaml
```

```bash
kubectl apply -f web-service.yaml
```

**3** Access Services
After deploying the services, you can access the web application and the H2 database through the node IP and assigned port:

Web Application: Access via http://<NodeIP>:<NodePort>
H2 Database Console: Access via http://<NodeIP>:<NodePort>


## Conclusion

In conclusion, this assignment provided valuable hands-on experience with Docker, allowing us to containerize and deploy a Spring Boot application and an H2 database. By leveraging Docker and Docker Compose, we were able to streamline the setup and deployment process, ensuring consistency and portability across different environments.






