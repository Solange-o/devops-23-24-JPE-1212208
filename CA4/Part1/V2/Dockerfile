FROM openjdk:21-jdk-slim
WORKDIR /app
COPY . /app
EXPOSE 59001
CMD ["java", "-cp", "/app/basic_demo-0.1.0.jar", "basic_demo.ChatServerApp", "59001"]