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