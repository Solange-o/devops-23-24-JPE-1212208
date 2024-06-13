# Class Assignment 2 (CA2)

## Introduction

In this tutorial, we explored the functionalities of Gradle, a widely used build automation tool in software development. 
Using our own private repository on GitHub, we followed various work guidelines to create an organized folder structure and add the specific files for Part 1 of the required task.

We'll utilize the Gradle Wrapper to avoid installing Gradle locally. 
If Gradle is already installed locally, we can use the "gradle" keyword instead of "gradlew" to run the same commands.

## Outline

1. [Preparation](#Preparation)
    - [Gradle Basic Demo](#Gradle-Basic-Demo)
2. [Implementing Changes](#Implementing-Changes)
    - [Part 1: Adding the runServer task](#Part-1:-Adding-the-runServer-task)
    - [Part 2: Adding the test class and unit test](#Part-2:-Adding-the-test-class-and-unit-test)
    - [Part 3: Adding the Copy task](#Part-3:-Addding-the-Copy-task)
    - [Part 4: Adding the Zip task](#Part-4:-Adding-the-Zip-task)
3. [Notes](#Notes)


## Preparation

To execute the task commands, open a bash terminal.

1. Clone the following Git repository to your local machine, which will serve as the basis for the task.:
   **git clone https://bitbucket.org/pssmatos/gradle_basic_demo.git**


2. Remove the .git file from the cloned repository.

Removing the .git file from the cloned repository is important to unlink the cloned repository from the 
original one, thus avoiding sharing the commit history, branches, and other specific information from 
the original repository. 
This is useful when you want to start a new repository from the cloned content.


3. Creates the folders locally "CA2/Part1":
**mkdir CA2**
**cd CA2**
**mkdir Part1**
**cd part1**

- mkdir is used to create a new directory (folder) in the file system.
- cd changes the current directory.


4. Move all files inside the "gradle_basic_demo" directory to the CA2/Part1 directory:
**mv gradle_basic_demo/* CA2/Part1/**

-mv is used to move or rename files and directories.


5. Initialize the Git repository. If the Git repository is already initialized, ignore this step.
   **git init**

- "git init" is the first step to start using Git version control in a project. After executing this command, you can
begin adding files to your repository, making commits to save changes, and using other Git features to manage your
project's development.
   

6. Add all files to the staging area:
   **git add .**

- When files are modified, Git needs to be informed about these changes before they can be included in a commit. The
stage, is an intermediate area where you prepare the changes for the next commit.
With git add ., we are telling Git to.
  

7. Commit the added files:
   **git commit -m "[Initial] Add files for CA2/Part1. closes #9"**

- After using git add . (previous step 6) you can create a commit that includes all these changes using the git commit
command.
- git commit - This is the main command to create a new commit in Git.
- -m " message " - This part of the command is used to add a commit message. The -m option allows you to input a commit
message directly on the command line.
- The # in the commit message body is the association to an issue.
- "Closes" in a commit message, followed by an issue number, signifies that the commit resolves that specific issue and 
automatically closes it when merged into the main branch.


8. Push the commit to the remote repository:
  **git push**

- Pushes the local changes to a remote repository. It updates the specified remote branch with the changes from the corresponding
local branch.

###  Gradle Basic Demo

The next steps are instructions for compiling, running, and testing the Gradle Basic Demo application, a demo application of a basic
chat room server. Each step has a specific purpose, and all of them are intended to be executed in terminals such as Bash (on Linux) 
or Git Bash (on Windows).

1. To build a .jar file with the application:
**./gradlew build**

2. Run the chat server application:
**java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp 59001**

- This command executes a JAR file named 'basic_demo-0.1.0.jar' which contains the 'ChatServerApp' application from the 'basic_demo' project.
- The message 'chat server is running...' is typically displayed in the terminal when the chat server is successfully started and begins 
accepting client connections. The message will continue to be displayed until the server is terminated or until an issue occurs that interrupts
the server's execution. You can terminate the connection by pressing Ctrl + C.

3. Run the client of the Gradle Basic Demo application (in another terminal):
**./gradlew runClient**

- By default a new window opens up with the Chat. In case the server is listening on a different port then the client, an error is thrown. 



## Implementing Changes
### Part 1: Adding the runServer task


Initially the goal is to add a new task to the build.gradle file to start the server.

1. Open the build.gradle file (located in the root of the project), navigate to the end of the file and add the new task:
   ```gradle
   task runServer(type:JavaExec, dependsOn: classes){
    group = "DevOps"
    description = "Launches a chat server that listens on localhost:59001"

    classpath = sourceSets.main.runtimeClasspath

    mainClass = 'basic_demo.ChatServerApp'

    args '59001'}

    ```

-In this code, we are defining a task called "runServer" that will be executed using the JavaExec plugin of Gradle. 
It depends on the "classes" task and aims to start a chat server listening on localhost:59001. 
The main class of the server is defined as "basic_demo.ChatServerApp", and the port is passed as an argument for execution.


2. Send the changes from the local repository to the remote repository:
**git add .**
**git commit -m "[FEAT] Add new task runServer. closes #10"**
**git push**


     
### Part 2: Adding the test class and unit test

In this part, a new test class will be created and a unit test will be added.


1. Create a new test folder and a new test class:
   ```bash
   mkdir -p src/test/java/basic_demo
   touch src/test/java/basic_demo/AppTest.java
   ```
- This set of commands creates a directory named "basic_demo" inside the "src/test/java" directory, and then creates a file 
named "AppTest.java" within that directory. 
- The "mkdir -p" command creates the specified directory. 
- The "touch" command creates a new empty file.


2. Configure the test class and add the unit test:
```java
package basic_demo;


import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

   @Test public void testAppHasAGreeting() {
      App classUnderTest = new App();
      assertNotNull("app should have a greeting", classUnderTest.getGreeting());
   }
}
```


3. Add the dependencies in the build.gradle file:
```gradle
dependencies {
    // Use Apache Log4J for logging
    implementation group: 'org.apache.logging.log4j', name: 'log4j-api', version: '2.11.2'
    implementation group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.11.2'
    testImplementation 'junit:junit:4.12'
}
```


4. Open a terminal and run the task independently:
**./gradlew clean test --info**

5. Send the changes from the local repository to the remote repository:
**git add .**
**git commit -m "[FIX] Add a simple unit test and update the gradle script so that it is able to execute the test. closes #11"**
**git push**



### Part 3: Adding the Copy task

Here we will create a new task that will: back up the application source code and copy it to a folder named 'backup' in the root of the application.

1. Open the build.gradle file and add the new task:
```gradle
task copySources (type: Copy){
    group = "DevOps"
    description = "Copies source files to a backup directory"

    from 'src/'
    into 'backup/'
}
```


2. Navigate to the project folder and run the task independently:
**./gradlew copySource**


3. Send the changes from the local repository to the remote repository:
**git add .**
**git commit -m "[FIX] Add a new task of type Copy to be used to make a backup of the sources of the application. closes #12"**
**git push**



### Part 4: Adding the Zip task

The goal is to create a new task that will create a .zip backup of the source of the application and copy it into a 'backup' 
folder in the application root.

1. Open the build.gradle file and add the new task:
```gradle
task zipSources (type: Zip){
    group = "DevOps"
    description = "Zip source files to the target directory"

    from 'src/'
    archiveFileName = 'src.zip'
    destinationDir = file('backup/')
}
```


2. Navigate to the project folder and run the task independently in a terminal:
**./gradlew zip**


3. Send the changes from the local repository to the remote repository:
**git add .**
**git commit -m "[FIX] Add a new task of type Zip to be used to make an archive of the sources of the application. closes #13"**
**git push**


4. Add a tag to mark the end of the first part of this assignment:
**git tag ca2-part1**
**git push --tags**


## Notes

Regarding the addition of tasks, it is important to remember these aspects:

- Whenever a new task is added with a IDE support, it's necessary to press the "Sync" button in order to refresh or synchronize the Gradle project settings 
with the development environment.

- Instead of a task runs independently in a terminal, it is possible to compile the project with the command: **./gradlew build**
