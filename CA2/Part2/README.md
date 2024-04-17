# Class Assignment 2 (CA2)

## Introduction

In this tutorial, we explored the functionalities of Gradle, a widely used build automation tool in software development. 
Using our own private repository on GitHub, we followed various work guidelines to create an organized folder structure and add the specific files for Part 2 of the required task.

We'll utilize the Gradle Wrapper to avoid installing Gradle locally. 
If Gradle is already installed locally, we can use the "gradle" keyword instead of "gradlew" to run the same commands.

## Outline

1. [Preparation](#Preparation)
2. [Implementing Changes](#Implementing-Changes)
    - [Part 1: Add the frontend plugin](#Part-1-Add-the-frontend-plugin)
    - [Part 2: Add the copyJar task](#Part-2-Add-the-copyJar-task)
    - [Part 3: Add the deleteWebpackFiles task](#Part-3-Add-the-deleteWebpackFiles-task)
    - [Closing steps](#Closing-steps)
3. [Alternative implementation solution](#Alternative-implementation-solution)
4. [Conclusion](#Conclusion)


## Preparation

The goal of Part 2 of this assignment is to convert the basic version of the Tutorial application to Gradle (instead of Maven).

1. Open a git bash and create a new branch called "tut-basic-gradle" in the repository to work on the assignment:
**git branch tut-basic-gradle**

2. Switch to the created branch:
**git checkout tut-basic-gradle**

3. Use [this website](https://start.spring.io) to generate a new gradle spring boot project with the following dependencies: Rest
Repositories; Thymeleaf; JPA; H2.

4. Click on the "Generate" button and download the project. 

5. Extract the generated zip file inside the folder CA2/Part2/ of your repository. 

6. Open the project in IntelliJ and delete de **src** folder. We want to use the code from the basic tutorial.

7. Copy the **src** folder from Class Assignment 1 Part 1 to the project folder.

8. Copy the **webpack.config.js** and the **package.json** files from Class Assignment 1 Part 1 to the project folder.

9. Delete the **src/main/resources/static/built/** folder.

10. In the **Employee.java** class, change all *javax.persistence* imports to *jakarta.persistence*.

Note: You can now experiment with the application by using **./gradlew bootRun**
Notice that the web page http://localhost:8080 is empty! This is because gradle is missing the plugin for dealing with the frontend code!

11. Add all files and changes to the staging area:
   **git add .**

- When files are modified, Git needs to be informed about these changes before they can be included in a commit. 
The stage, is an intermediate area where you prepare the changes for the next commit.
With git add ., we are telling Git to.
  
12. Commit the added files:
   **git commit -m "Create a new branch "tut-basic-gradle", and add the basic folder of the Tutorial application to a Part2 folder. Run the bootRun task to test. closes #17"**

- After using git add . (previous step 11) you can create a commit that includes all these changes using the git commit command.
- git commit - This is the main command to create a new commit in Git.
- -m " message " - This part of the command is used to add a commit message. The -m option allows you to input a commit message directly on the command line.
- The # in the commit message body is the association to an issue.
- "Closes" in a commit message, followed by an issue number, signifies that the commit resolves that specific issue and automatically closes it when merged into the main branch.

13. Push the commit to the remote repository:
  **git push**

- Pushes the local changes to a remote repository. It updates the specified remote branch with the changes from the corresponding local branch.


## Implementing Changes

### Part 1: Adding the frontend plugin

The goal of this section is to integrate the org.siouan.frontend plugin into the project so that Gradle can manage the frontend as well. This will allow us to simplify and automate the build process, providing an efficient way to handle frontend dependencies and compilation tasks.

1. Open the build.gradle file and add the plugin according to your version of java:
```gradle
 id "org.siouan.frontend-jdk17" version "8.0.0"
```

2. Configure the plugin in the same build.gradle file:
```gradle
 frontend {
nodeVersion = "16.20.2"
assembleScript = "run build"
cleanScript = "run clean"
checkScript = "run check"
}
```

3. Update the scripts section/object in package.json to configure the execution of webpack:
```gradle
"scripts": {
"webpack": "webpack",
"build": "npm run webpack",
"check": "echo Checking frontend",
"clean": "echo Cleaning frontend",
"lint": "echo Linting frontend",
"test": "echo Testing frontend"
},
```

4. Add the package manager to the build.gradle file, before the scripts section:
```gradle
"packageManager": "npm@9.6.7",
```

5. Compile the project in the terminal (first navigate to the project folder):
**./gradlew build**

6. You may now execute the application by using: 
**./gradlew bootRun**

7. Add all files and changes to the staging area:
   **git add .**
  
8. Commit the added files:
   **git commit -m "Add gradle plugin; update plugins block in build.gradle; add code in build.gradle; and Update the scripts section/object in package.json. closes #18"**

9. Push the commit to the remote repository:
  **git push**


### Part 2: Add the copyJar task

In this section we'll add a task to gradle to copy the generated jar to a folder called "dist" located at the root folder level of the project.

1. Open the build.gradle file and add the task:
```gradle
task copyJar(type: Copy, dependsOn: build) {
	from 'build/libs/'
	into 'dist'
	include '*.jar'
}
```

2. Navigate to the project folder and compile the project in the terminal:
**./gradlew build**

3. Add all files and changes to the staging area:
   **git add .**

4. Commit the added files:
**git commit -m "Add a task to gradle to copy the generated jar to a folder named ”dist” located a the project root folder level. closes #19"**

5.  Push the commit to the remote repository:
  **git push**


### Part 3: Add the deleteWebpackFiles task

The aim of this part is to add a task to gradle to delete all the files generated by webpack (normally located in src/resources/main/static/built/). This new task should be run automatically by gradle before the clean task.

1. Open the build.gradle file and add the task:
```gradle
task deleteWebpackFiles(type: Delete) {
	delete 'src/main/resources/static/built'
}
```

2. After add the following command to make sure this task is executed automatically by the task *clean*: 
```gradle
clean.dependsOn(deleteWebpackFiles)
```

3. Navigate to the project folder and compile the project in the terminal:
**./gradlew build**

4. Add all te files to the staging area:
**git add .**

5. Commit the changes:
***git commit -m "Add a task to gradle to delete all the files generated by webpack. closes #20"**

6. Push the changes to the repository:
**git push**

### Closing steps

Now we need to merge into the master branch and mark the repository with the tag 'ca2-part2':

1. Change to the master branch:
**git checkout master**

2. Merge the tut-basic-gradle branch into the master branch:
**git merge --no-ff tut-basic-gradle**

3. Push the changes to the remote repository:
**git push origin master**

4. Creates a new tag named "v1.1.0" in the local repository:
**git tag ca2-part2**

5. Send the locally created tag to the remote repository on GitHub:
**git push origin ca2-part2**


## Alternative implementation solution

Besides Gradle, there are other alternative technological solutions for build automation, such as Apache Maven, Apache Ant, Bazel, among others. 
In this section, we will perform an implementation approach using Maven. This is a build automation tool based on XML, which utilizes a project model and plugins to manage the software development lifecycle.

Let's analyze how the alternative solution, in Maven, compares to our base solution in Gradle. They differ in several areas, including how they handle extensibility and the addition of new functionalities through plugins or custom tasks:
- Maven has a vast library of plugins that can be easily added to the project's POM. However, creating custom plugins can be more restrictive and requires the use of Maven's specific API. 
- On the other hand, Gradle not only supports a wide range of ready-to-use plugins but also makes the creation of custom plugins more accessible and flexible.

The alternative tool - Maven - could be used to achieve the same objectives presented for this task as follows:

1. Open a git bash and create a Maven project:
**mvn archetype:generate -DgroupId=com.mycompany.app -DartifactId=my-app -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false**

2. In the pom.xml file, add the frontend plugin:
```xml
<plugin>
<groupId>com.github.eirslett</groupId>
<artifactId>frontend-maven-plugin</artifactId>
<version>1.11.0</version>
<configuration>
<nodeVersion>v16.20.2</nodeVersion>
<workingDirectory>src/main/resources/static</workingDirectory>
</configuration>
<executions>
<execution>
<id>install node and npm</id>
<goals>
<goal>install-node-and-npm</goal>
</goals>
</execution>
<execution>
<id>npm install</id>
<goals>
<goal>npm</goal>
<configuration>
<arguments>install</arguments>
</configuration>
</goals>
</execution>
<execution>
<id>npm run build</id>
<goals>
<goal>npm</goal>
<configuration>
<arguments>run build</arguments>
</configuration>
</goals>
</execution>
</executions>
</plugin>
```

3. In the pom.xml file, add the copyJar task:
```xml
<plugin>
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-resources-plugin</artifactId>
<version>3.2.0</version>
<executions>
<execution>
<id>copy-jar</id>
<phase>package</phase>
<goals>
<goal>copy-resources</goal>
</goals>
<configuration>
<outputDirectory>${project.build.directory}/dist</outputDirectory>
<resources>
<resource>
<directory>${project.build.directory}</directory>
<includes>
<include>*.jar</include>
</includes>
</resource>
</resources>
</configuration>
</execution>
</executions>
</plugin>
```

4. In the pom.xml file, add the deleteWebpackFiles task:
```xml
<plugin>
<groupId>org.apache.maven.plugins</groupId>
<artifactId>maven-clean-plugin</artifactId>
<version>3.1.0</version>
<executions>
<execution>
<id>delete-webpack-files</id>
<phase>clean</phase>
<goals>
<goal>clean</goal>
</goals>
<configuration>
<filesets>
<fileset>
<directory>src/main/resources/static/built</directory>
<includes>
<include>*</include>
</includes>
</fileset>
</filesets> 
</configuration>
</execution>
</executions>
</plugin>
```

5. Compile the project in the terminal:
**mvn clean install**

6. Add all files and changes to the staging area:
**git add .**

7. Commit the added files:
**git commit -m "Implementation of an alternative technological solution for the construction automation tool. closes #22."**

8. Push the commit to the remote repository:
**git push**


## Conclusion

In conclusion, in this tutorial, we explored the features of Gradle and implemented an alternative technological solution using Maven for build automation. We compared the two tools in terms of extensibility and adding new features through plugins or custom tasks. While Gradle offers greater flexibility and ease in creating custom plugins, Maven offers a vast library of readily available plugins that can be easily integrated into projects. By following the steps described, we were able to achieve the same objectives using Maven, demonstrating its capacity as a build automation tool. Both Gradle and Maven have their strengths and weaknesses, and choosing between them depends on specific task requirements and preferences.

