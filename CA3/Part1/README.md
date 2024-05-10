# Class Assignment 3 (CA3) - Part1

## Introduction

Virtualization is an important technique for creating and managing virtual machines on a physical system, providing flexibility and security in software development. Tools like VirtualBox (for x86 systems) and UTM (for Apple with arm64) allow us to run different operating systems on a physical machine without interfering with the host system.

The goal of this part of the assignment is for us to create a virtual machine using VirtualBox or UTM and set up Ubuntu as the operating system. We will use this virtual machine to run projects from previous assignments, gaining experience in configuring virtual development environments and solving related issues.

## Outline

1. [Create a Virtual Machine](#Create-a-Virtual-Machine)
2. [Virtual Machine Initial Configuration](#Virtual-Machine-Initial-Configuration)
3. [Additional Utilities](#Additional-Utilities)
4. [Software Installation](#Software-Installation)
5. [Project Migration](#Project-Migration)
6. [Possible Issues](#Possible-Issues)
7. [Conclusion](Conclusion)


## Create a Virtual Machine

Let's start by creating our virtual machine (VM).

1. Download and Install VirtualBox from [Oracle's website](https://www.virtualbox.org/).

2. Setting Up the VM:
  - Open VirtualBox and click on **New** to create a new virtual machine.
  - Name your VM and choose "Linux" as the type and "Ubuntu (64-bit)" as the version.
  - Allocate memory (RAM): Recommended to set at least 2048 MB for adequate performance.
  - Create a virtual hard disk. Choose VDI (VirtualBox Disk Image) and allocate at least 20 GB of space. Set the storage on the physical hard disk as dynamically allocated.

3. Install Ubuntu:
- Download the Ubuntu Server ISO file from [Ubuntu's official site](https://ubuntu.com/download/server).
- Mount the ISO file to your VM: In VirtualBox, select your VM, click on "Settings," go to "Storage," click on "Empty" under Controller: IDE, then click on the disk icon next to "Optical Drive" and select "Choose a disk file..." Locate and select your downloaded ISO file.
- Start the VM and follow the on-screen instructions to install Ubuntu. During installation, select the standard utilities for a server and, if prompted, install OpenSSH server to enable remote connections.

4. Create a Host-Only Network:
 -  Open your VM application and navigate to the **Host Network Manager**.
 - Click on **Create** to add a new host-only network.
 - Name and configure this network within my VM's network settings.

5. Setup Networking for the VM:
 - In VM's settings, set "Network Adapter 2" to be a "Host-only Adapter".
 - Check the IP range for the host-only network (e.g., 192.168.56.1/24).
 - Assign an appropriate IP from this range to the adapter, such as 192.168.56.5.


## Virtual Machine Initial Configuration

1. Start your VM and log in.

2. Update the package repositories with the command:
  **sudo apt update**
  
3. Install network tools:
  **sudo apt install net-tools**
  
4. Configure the network interface by editing the Netplan configuration file:
  **sudo nano /etc/netplan/01-netcfg.yaml**

5. Apply the changes with:
  **sudo netplan apply**


## Additional Utilities

1. SSH Setup:
    - Install OpenSSH Server:**sudo apt install openssh-server**
    - Enable password authentication in the SSH configuration.
    - Restart SSH service: **sudo systemctl restart ssh**

2. FTP Setup:
    - Install `vsftpd`:**sudo apt install vsftpd**
    - Enable write access in the FTP configuration.
    - Restart FTP service:**sudo systemctl restart vsftpd**


## Software Installation

With the Ubuntu server up and running, we'll proceed to install the software needed for the development environment.

1. Open a terminal bash and update your system:
   - **sudo apt update**
   - **sudo apt upgrade**

2. Install Git tools to clone and manage project repositories:
   - **sudo apt install git**

3. Install Java Development Kit (JDK) tools, for running Java applications:
   - **sudo apt install openjdk-17-jdk openjdk-17-jre**

4. Install Maven tools, for building and managing Java-based projects:
   - **sudo apt install maven**

5. Install Gradle tools, for building and automating Java projects:
   - **wget https://services.gradle.org/distributions/gradle-8.6-bin.zip**
   - **sudo mkdir /opt/gradle**
   - **sudo unzip -d /opt/gradle gradle-8.6-bin.zip**
   - **echo "export GRADLE_HOME=/opt/gradle/gradle-8.6" >> ~/.bashrc**
   - **echo "export PATH=$PATH:$GRADLE_HOME/bin" >> ~/.bashrc**
   - **source ~/.bashrc**

6. Ensure all installations are correct by checking the versions of the installed software:
   - **git --version**
   - **java -version**
   - **mvn -version**
   - **gradle -version**


## Project Migration

Considering the "Spring Boot tutorial basic project" and the "gradle_basic_demo" project from previous assignments, let's look at the steps to clone their repositories into the virtual machine and set up the environment for running the same projects.

1. Clone Your Repository:
   - Open a terminal in your VM
   - Navigate to a directory where you want to store your projects, such as `/home/username/projects`.
   - Use the git command to clone your repository. Replace `<repository-url>` with the URL of your GitHub repository:
   **git clone <repository-url>**
   - Enter your directory containing the projects: **cd dirctory/holding/projects**

2. Configure Maven Wrapper and Gradle Wrapper to give executing permissions:
   - **chmod +x mvnw**
   - **chmod +x gradlew**

3. Run the application from CA1 project :
   - Navigate to the project directory: **cd directory/with/spring-boot-tutorial-basic**
    - Build the project using Maven: **./mvnw clean install**
    - Run the project: **./mvnw spring-boot:run**
    - Check that the application is running correctly by accessing it from your host machine’s web browser using the VM’s IP address and the port configured in the project:
      **ip addr**
   - put the IP and the port 8080 on the browser address.


4. Run the application from CA2 Part1 project:
    - Navigate to the Gradle project directory:**cd directory/holding/gradle_basic_demo**
    - Build the project using Gradle and run the server: **./gradlew build** and **./gradlew runServer**
    - Build the project in your computer terminal and run the client:**gradle runClient --args="192.168.56.5 59001**   


5. Run the application from CA2 Part2 project:
   - Navigate to the basic folder: **cd directory/of/basic/project**
   - Run with gradle: **./gradlew build** and **./gradlew bootRun**
   - Check your VM's IP: **ip addr**
   - Write `VM'sIP.8080` on your browser address.


## Possible Issues

In this section we'll reflect on possible problems that we may encounter when working with a virtual machine.

1. VirtualBox Installation Issues:
   - It's important to make sure that you download the correct version of VirtualBox for your operating system. 

2. VM Crashes or Fails to Start:
   - This can occur due to insufficient memory allocation or configuration issues.
   - Increase the memory allocated to the VM and double-check other VM settings. 
   If needed, delete the VM and recreate it, ensuring all steps are followed correctly.

3. Ubuntu Installation Problems - ISO File Not Recognized:
   - VirtualBox might not detect the Ubuntu ISO file, preventing the VM from booting.
   - Check if the ISO file is corrupted. Re-download it from the official website and try again. 
   Ensure it is properly mounted in VirtualBox's storage settings.

4. Ubuntu Installation Errors:
   - Errors during installation can be due to incorrect settings or hardware compatibility issues.
   - Restart the VM and ensure all installation steps are followed. 
   If the problem persists, consider using a different Ubuntu version or checking VirtualBox's compatibility.

5. Unable to Access VM from Host:
   - This could be due to misconfigured network settings.
   - Double-check the network configuration in VirtualBox. Ensure the "Host-only Adapter" is properly set up, and the correct IP range is used. 
   Use the ip addr command in the VM to get the IP and ensure it aligns with the expected range.

6. SSH or FTP Connection Issues:
   - These can be caused by firewall restrictions or incorrect configurations.
   - Ensure SSH or FTP services are running (systemctl status ssh or systemctl status vsftpd). 
   If needed, adjust firewall rules to allow the necessary ports. Check SSH and FTP configuration files for any errors.

7. Maven or Gradle Build Errors:
   - Build errors often occur due to missing dependencies or misconfigurations.
   - Ensure Maven and Gradle are properly installed and up to date. Try running **./mvnw clean install** or **./gradlew build** again. 
   Check the project’s pom.xml (for Maven) or build.gradle (for Gradle) for dependency issues.

8. Application Not Running Correctly:
   - This can occur if the project setup is incomplete or there are runtime errors.
   - Check the project logs for error messages. Verify all required services and dependencies are running. Double-check the project's IP and port configuration, ensuring they align with the expected values.


## Conclusion

In this tutorial, we learned how to create and set up a virtual machine using VirtualBox or UTM, installing the Ubuntu operating system. We also installed essential development software and migrated projects into this virtual machine.
This experience taught us the importance of virtualization for creating safe development environments, while also allowing us to identify and solve common issues.
Despite the challenges, virtualization proved to be a powerful tool for flexibility and security in software development. This work provided us with a solid foundation for using virtual machines in future projects.



