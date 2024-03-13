# Class Assignment 1

## Introduction

This project focuses on the development of an application using the React.js language and the Spring Data REST
framework, aiming to demonstrate version control skills using Git and explore technological alternatives for source code
management.
The application is based on an existing tutorial, which has been adapted and enhanced to meet the project's
requirements.
The development of the application is divided into two parts.
Additionally, at the end of each part, comparative analyses are conducted between Git and a technological alternative
for version control, exploring their features, advantages, and disadvantages.
The goal is to promote a comprehensive understanding of the available tools and stimulate reflection on the best
practices in software development.

The final result of the assignment can be found [here](https://github.com/Solange-o/devops-23-24-JPE-1212208).

## Outline

1. [Part I](#Part-I)
    - [Preparation](#Preparation)
    - [Set the initial environment](#Set-the-initial-environment)
    - [Initial Changes: Add the job years field on the main branch](#Initial-Changes:-Add-the-job-years-field-on-the-main-branch)
2. [Part II](#Part-II)
    - [Second Changes: Add the email field with a new branch](#Second-Changes:-Add-the-email-field-with-a-new-branch)
    - [Third Changes: Add fix for invalid email on another branch](#Third-Changes:-Add-fix-for-invalid-email-on-another-branch)
3. [Other Git commands](#Other-Git-commands)
4. [Issues](#Issues)
5. [Alternative to Git: Apache Subversion](#Alternative-to-Git:-Apache-Subversion)
    - [Analysis of the alternative](#Analysis-of-the-alternative)
    - [Implementation of the alternative](#Implementation-of-the-alternative)
        - [Part I](#Part-I)
        - [Part II](#Part-II)
6. [Conclusion](#Conclusion)

### Part I

In the first part, the focus is on the initial setup of the development environment, organizing the source code in a Git
repository, and implementing a new feature in the application.
Version control is exclusively carried out on the master branch, without the use of additional branches.

#### Preparation:

Before starting the application development it's important to consider the following:

A) To carry out the exercise you need to clone the application to a local folder on your computer. You can do it by
executing in a terminal (or console in Windows) the following command:
git clone https://github.com/spring-guides/tut-react-and-spring-data-rest
B) Create your own individual repository in gitHub.
c) The React.js and Spring Data REST Application Tutorial should already be available locally.
c) To meet the requirements, you can open a bash terminal and run the commands we're about to explore.

#### Set the initial environment

These steps are for setting up the initial environment and version control for the project.
They include navigating to the project directory, copying the application to a new folder, initializing the Git
repository, adding files to the staging area, committing the changes, pushing the commit to the remote repository, and
adding a tag to the commit.

1. Navigate to the project directory:

Commands:
```bash
cd path/to/TutorialReactSpringDataREST
```

Explanation:
cd changes the current directory

2. Copy the application into a new CA1 folder:

Commands:
```bash
cp -r . ../CA1
cd ../CA1
```

Explanation:  
cp command is used to copy directories and files.
The dot (.) represents the current directory, meaning all files and subdirectories present in the current directory will
be copied.
The -r option indicates that the copy will be performed recursively, which means all contents of the current directory,
including subdirectories and their contents, will be copied.
../CA1 is the path to the destination directory where the files will be copied. Here, .. represents the parent directory
of the current directory, and CA1 is the name of the destination directory.

3. Initialize the Git repository in the local directory:

Commands:
```bash
git init
```

Explanation:
"git init" is the first step to start using Git version control in a project. After executing this command, you can
begin adding files to your repository, making commits to save changes, and using other Git features to manage your
project's development.

4. Add all files to the staging area:

Commands:
```bash
git add .
```

Explanation:
When files are modified, Git needs to be informed about these changes before they can be included in a commit. The
stage, is an intermediate area where you prepare the changes for the next commit.
With git add ., we are telling Git to.

5. Commit the added files:

Commands:
```bash
git commit -m "[Initial] #1 Copy the code of the Tutorial React.js and Spring Data REST Application into a new folder named CA1"
```

Explanation:
After using git add . (previous step 4) you can create a commit that includes all these changes using the git commit
command.
git commit - This is the main command to create a new commit in Git.
-m " message " - This part of the command is used to add a commit message. The -m option allows you to input a commit
message directly on the command line.
The # in the commit message body is the association to an issue.

6. Push the commit to the remote repository:

Commands:
```bash
git remote add origin <repository-URL>
git push -u origin master
```

Explanation:
"git remote add origin <repository-URL>": This command adds a new remote named "origin" to the local repository. The <
repository-URL> is the URL of the remote repository you want to connect your local repository to. The name "origin" is
often used as an alias for the main remote repository where you want to send your changes.
"git push -u origin master": This command sends local changes to the remote repository in the "master" branch. The -u
option sets the "master" branch on the "origin" remote as the upstream branch, meaning that future git push commands
without specifying a branch and a remote will automatically use the "master" branch on "origin". This simplifies the
process of sending changes to the remote repository.

7. Add a tag to the commit:

Commands:
```bash
git tag v1.1.0
git push origin v1.1.0
```

Explanation:
git tag v1.1.0 - This command creates a new tag named "v1.1.0" in the local repository.
git push origin v1.1.0 - This command is used to send the locally created tag to the remote repository on GitHub.


#### Initial Changes: Add the job years field on the master branch

From point 1 to 7, I'll provide examples of possible changes in Java to meet the requirements.

1. Add the new field to the _Employee_ class:
   ```java
   private int jobYears;
   ```

2. Add the new field in constructor:
   ```java
    public Employee(String name, String role, int jobYears) {
         this.name = name;
         this.role = role;
         this.jobYears = jobYears;
    }
    ```

3. Add validations:
   ```java
   public Employee(String firstName, String lastName, String description, int jobYears) throws InstantiationException {
		 if(firstName == null || lastName == null || description == null || email == null || jobYears < 0 || firstName.isEmpty() || lastName.isEmpty() || description.isEmpty() || email.isEmpty())
			throw new InstantiationException("Please enter a valid first name, last name, description and job years.");
		 this.firstName = firstName;
		 this.lastName = lastName;
		 this.description = description;
		 this.jobYears = jobYears;
	}
   ```

4. Add the new field to the getters and setters methods:
   ```java
   public int getJobYears() {
       return jobYears;
   }

   public void setJobYears(int jobYears) {
       this.jobYears = jobYears;
   }
   ```

5. Add the new field to the toString method:
   ```java
   return "Employee{" + "id=" + this.id + ", name='" + this.name + '\'' + ", role='" + this.role + '\'' + ", jobYears=" + this.jobYears + '}';
   ```

6. Add the new field to the equals method:
    ```java
    public boolean equals(Object o) {
		 if (this == o) return true;
		 if (o == null || getClass() != o.getClass()) return false;
		 Employee employee = (Employee) o;
		 return Objects.equals(id, employee.id) &&
			Objects.equals(firstName, employee.firstName) &&
			Objects.equals(lastName, employee.lastName) &&
			Objects.equals(description, employee.description) &&
			Objects.equals(jobYears, employee.jobYears)
	}
    ```

7. Add the new field to hashCode method:
    ```java
    public int hashCode() {
		 return Objects.hash(id, firstName, lastName, description, jobYears);
	}
    ```

8. Add the new field to the methods in the Javascript file app.js.

9. Add the new field to the run method in the DatabaseLoader class.

10. Generate an EmployeeTest class and incorporate unit tests for the additional field, guaranteeing that all methods
    and validations are encompassed.

11. Open a bash in the basic folder of the app and start a Spring Boot application:

Commands:
```bash
./mvnw spring-boot:run
```

12. Open a browser and navigate to [http://localhost:8080/employees](http://localhost:8080/employees) to see the changes.

13. Commit the changes to the master branch:

```bash
git commit -m "[Feat] #2 - Add a new field to record the years of the employee in the company (jobYears)"
```

14. Push the changes to the remote repository:

```bash
git push
```

15. Add a tag to the commit:

```bash
git tag v1.2.0
git push origin v1.2.0
```

16. Add a tag to mark the finish of this first part of the assignment:

```bash
git tag ca1-part1
git push origin ca1-part1
```

### Part II

In the second part, the usage of branches for developing new features and fixing bugs is introduced.
Specific branches are created for each task.
The aim is to demonstrate a more structured and controlled workflow, utilizing best practices in development and version
control.

#### Second Changes: Add the email field with a new branch.

1. Create a new branch:

Commands:
```bash
git branch email-field
```

Explanation:
When executing this command, a new branch is created where you can work specifically on implementing the email field or
any other related task.

2. Change to the new created branch:

Commands:
```bash
git checkout email-field
```

Explanation:
With this command, Git changes the state of your repository to the development line represented by that branch.
Any changes made to the files will be made in that specific branch, allowing you to work on changes related to the email
field or any other task associated with the "email-field" branch.

3. Push the new branch to the remote repository:

Commands:
```bash
git push -u origin email-field
```

Explanation:
The -u option sets the "email-field" branch as the upstream branch in the remote repository.

4. Make the necessary changes to the code of all classes in order to add the new field.
   Realize too unit tests for the new field, ensuring that all methods and validations are covered.

5. Add the changes to the staging area of Git:

```bash
git add .
```

6. Commit the changes to the master branch:

```bash
git commit -m "[Feat] #3 - Add a new field to record the title of the employee in the company (jobTitle)"
```

7. Change to the master branch:

```bash
git checkout master
```

8. Merge the email-field branch into the master branch:

Commands:
 ```bash
 git merge --no-ff email-field
 ```

Explanation:
It performs a merge of the "email-field" branch into the current branch.
With --no-ff, Git creates a new commit even if the merge could be done directly (fast-forward), thus maintaining a
clearer and more informative history of the merge. This is useful for easily identifying where merges were performed in
the repository history.

9. Push the changes to the remote repository:

```bash
git push origin master
```

10. Add a new tag to the commit:

```bash
git tag v1.3.0
git push origin v1.3.0
```

#### Third Changes: Add fix for invalid email on another branch.

1. Create a new branch:

```bash
git branch fix-invalid-email
```

2. Change to the new created branch:

```bash
git checkout fix-invalid-email
```

3. Push the new branch to the remote repository:

```bash
git push -u origin fix-invalid-email
```

4. Change the Employee and EmployeeTest class.
   The server should only accept Employees with a valid email (e.g., an email must have the ”@” sign).
   Look at this example:

```java
public Employee(String firstName, String lastName, String description, int jobYears, String email) throws InstantiationException {
    if (firstName == null || lastName == null || description == null || email == null || jobYears < 0 || firstName.isEmpty() || lastName.isEmpty() || description.isEmpty() || !email.contains("@"))
        throw new InstantiationException("Please enter a valid first name, last name, description and job years.");
    this.firstName = firstName;
    this.lastName = lastName;
    this.description = description;
    this.jobYears = jobYears;
    this.email = email;
}
```

5. Add the changes to the staging area of Git:

```bash
git add .
```

6. Commit the changes to the email-field branch:

```bash
git commit -m "[FIX] Close #7 Added further email validations to the application"
```

7. Change to the master branch:

```bash
git checkout master
```

8. Merge the email-field branch into the master branch:

```bash
git merge --no-ff fix-invalid-email
```

9. Push the changes to the remote repository:

```bash
git push origin master
```

10. Add a new tag to the commit:

```bash
git tag v1.3.1
git push origin v1.3.1
```

11. Mark the completion of this initial part of the assignment by adding a tag:

```bash
git tag ca1-part2
git push origin ca1-part2
```


### Other Git commands

Git has many more commands that you can try throughout the tutorial and that can be useful to you. 
These commands can be helpful for managing and navigating through the Git repository during the development process:

``` git status ```: This command displays the state of the working directory and the staging area. It shows which files are modified, untracked, or staged for commit.
``` git diff ```: This command shows the differences between the changes made in the working directory and the last commit.
``` git log ```: This command displays a history of commits in the repository, including commit messages, authors, dates, and commit hashes.
``` git reset ```: This command is used to undo changes in the working directory or staging area.
``` git revert ```: This command is used to create a new commit that undoes the changes made in a previous commit.
``` git stash ```: This command temporarily stores changes that are not ready to be committed, allowing you to switch branches without committing them.
``` git fetch ```: This command downloads objects and references from another repository, updating the local repository with changes made in the remote repository.


### Issues

In previous commits there are links to specific issues. This allows for more efficient traceability, as each commit can
be associated with a particular task or problem.
By including the issue number in the commit body or title, it becomes clear what work is being done and what problem is
being addressed with that commit.

To create issues:

1. Go to the repository's main page;
2. Click on the "Issues" tab;
3. Click on the "New issue" button;
4. Fill in the issue title and description and click on the "Submit new issue" button;
5. The issue will be created and assigned a number and can be seen by anyone who has access to the repository;
6. The issue can be assigned to someone, labeled, and commented on.

To close issues:
Incorporate the issue number in a commit with the command git commit -m "Close #1"
or
In the GitHub repository, on the "Issues" tab, click on the "Close issue" button.



### Alternative to Git: Apache Subversion

#### Analysis of the alternative.

Overview:
The Apache Subversion, also known as SVN, is a widely used centralized version control system in software development
projects. It is designed to store and manage different versions of files and directories, allowing multiple people to
collaborate on a project efficiently.

Advantages:
The Apache Subversion has some advantages compared to Git. Its centralized approach facilitates source code management
in projects where centralized control is preferred.
Other advantages include its stability and reliability, making it a solid choice for long-term projects.

Disadvantages:
Despite its advantages, the Apache Subversion also has some limitations. For example, it does not support offline
operations, which can be a disadvantage for teams that need to work in disconnected network environments.
Furthermore, the Apache Subversion has a more restricted branching model compared to Git, which can make it more
difficult for teams to work with multiple development lines. It may also face challenges in handling large repositories
and managing binary files.

Comparison with Git:
Compared to Git, the Apache Subversion offers a more centralized approach to version control. While Git is better suited
for distributed projects and offers greater flexibility in terms of branching model and offline operations, the Apache
Subversion is preferred in environments where centralized control and simplicity of use are more important.
In terms of performance, Git may be faster for branching and merging operations due to its distributed nature, but the
Apache Subversion can be more stable and reliable in specific scenarios.


##### Implementation of the alternative.

This implementation demonstrates some examples of how to accomplish the same tasks using Apache Subversion instead of Git.

###### Part I :

Just like git, Subversion needs to be installed on your system. Link for download https://subversion.apache.org/download.cgi

Clone the application to a local folder on your computer: svn checkout https://github.com/spring-guides/tut-react-and-spring-data-rest

Navigate to the project directory: ``` cd path/to/TutorialReactSpringDataREST ```

Copy the application into a new folder named CA1: ``` svn copy . ../CA1 ``` and ``` cd ../CA1 ```

Initialize the Subversion repository in the local directory: ``` svnadmin create . ```

Add all files to the staging area: ``` svn add * ```

Commit the added files: ``` svn commit -m "Initial commit with the Tutorial application" ```

Add a tag to the commit: ``` svn copy . tags/v1.1.0 ```

###### Part II :

Create a new branch for the email field: ``` svn copy . branches/email-field ```

Switch to the newly created branch: ``` svn switch branches/email-field ```

Add the email field to the Employee class: ``` svn add Employee.java ```

Commit the changes to the email-field branch: ``` svn commit -m "Added the email field to the Employee class" ```

Switch back to the main branch: ``` svn switch trunk ```

Merge the email-field branch into the main branch: ``` svn merge ^/branches/email-field ```

Commit the changes to the main branch: ``` svn commit -m "Merged email-field branch into main" ```

Add a tag to mark the completion of this part of the assignment: ``` svn copy . tags/ca1-part1 ```

Repeat the above steps for Third Changes: Add fix for invalid email on another branch.


### Conclusion

In conclusion this project serves as a testament to the importance of effective version control in modern software development workflows. It not only showcases the use of Git for managing code changes and collaborating on projects but also provides insights into alternative version control systems like Apache Subversion. Through structured development processes and comparative analyses, the project promotes a deeper understanding of version control tools and encourages the adoption of best practices in software development.