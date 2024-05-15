# Class Assignment 3 (CA3) - Part2

## Introduction

The goal of this assignment is to use Vagrant to set up a virtual environment that will run a Spring Boot application. 
The aim is to replicate the development environment needed to execute the Spring Boot tutorial application, which was developed in Part 2 of your previous assignment.

## Outline

1. [Install Vagrant](#Install-Vagrant)
2. [Use Vagrant](#Use-Vagrant)
3. [Explore Vagrantfile](#Explore-Vagrantfile)
4. [Alternative solution](#Alternative-solution)
5. [Conclusion](Conclusion)


## Install Vagrant

1. Download and Install Vagrant from https://developer.hashicorp.com/vagrant/install.

## Use Vagrant

1 - Make the personal repository public.
    In order to carry out this work it may be important to ensure that our personal repository is public.
    When we execute a git clone of a private repository, git will ask for authentication using a prompt for reading the password.
    This prompt for reading the password will break the provision script, since it should execute without any user interaction.

2 - Access Vagrantefile.
  The Vagrantfile is the main configuration file used by Vagrant to define and configure virtual environments. 

  Copy the supplied Vagrantefile from https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo/ to the desired area. Open a bash terminal and use the commands:
  **mkdir CA3/Part2**
  **cd CA3/Part2**
  **curl -o Vagrantfile https://bitbucket.org/pssmatos/vagrant-multi-spring-tut-demo/raw/master/Vagrantfile && cp Vagrantfile ./Vagrantfile**

3 - Change the Vagrantfile.

   In the context of this assignment, the Vagrantfile will be modified to specify the necessary settings to run the Spring Boot application within the virtual environment created by Vagrant.
   Therefore, the Vagrantfile will be updated to include instructions on how to set up the virtual machines needed to run the Spring Boot application. These changes will allow Vagrant to create a consistent and reproducible virtual environment suitable for running the Spring Boot application.

   3.1 - Opens a bash terminal and uses the command: 
   **nano Vagrantfile**
       - nano is a simple text editor that allows you to view and edit text files directly from the command line.
   
   3.2 - Edit Vagrantfile

   Makes the necessary changes to the Vagrantfile. 
   When you've finished, press Ctrl + X and then type Y (yes) to save the changes you've made.

   3.3 - Start the virtual machine with Vagrant with the command:
   **vagrant up**
       - The command "vagrant up" reads the Vagrantfile, creates and initializes the virtual machines, configures the networks as specified, and executes provisioning scripts to install software packages and configure the VMs as defined.
       After running this command, the virtual machine will be ready and configured as specified, with all networks and software installed, allowing you to start working in the configured environment.

   3.4 - See the result:

   Open a browser and type localhost:8080

4 - Commit the changes to the repository with the commands:

**git add .**
**git commit -m "Updated the Vagrantfile configuration for gradle version of the spring application. closes #25"**
**git push**

      
## Explore Vagrantfile

This configuration file automates the setup of two virtual machines, one for a database and another for a web server, with specific network configurations, 
software installations, and port forwarding settings.

1 - See the final Vagrantfile after the appropriate changes.

In order to solve various problems that have arisen, Vagrantfile has ended up with this body: 

```ruby
Vagrant.configure("2") do |config|
  config.vm.box = "ubuntu/focal64"
  config.ssh.insert_key = false

  # This provision is common for both VMs
  config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
        openjdk-17-jdk-headless
    # ifconfig
  SHELL

  #============
  # Configurations specific to the database VM
  config.vm.define "db" do |db|                
    db.vm.box = "ubuntu/focal64"                
    db.vm.hostname = "db"                        
    db.vm.network "private_network", ip: "192.168.56.11"     

    # We want to access H2 console from the host using port 8082
    # We want to connet to the H2 server using port 9092
    db.vm.network "forwarded_port", guest: 8082, host: 8082     
    db.vm.network "forwarded_port", guest: 9092, host: 9092

    # We need to download H2
    db.vm.provision "shell", inline: <<-SHELL                   
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    # The following provision shell will run ALWAYS so that we can execute the H2 server process
    # This could be done in a different way, for instance, setting H2 as as service, like in the following link:
    # How to setup java as a service in ubuntu: http://www.jcgonzalez.com/ubuntu-16-java-service-wrapper-example
    #
    # To connect to H2 use: jdbc:h2:tcp://192.168.33.11:9092/./jpadb
    db.vm.provision "shell", :run => 'always', inline: <<-SHELL        
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end                                                          

  #============
  # Configurations specific to the webserver VM
  config.vm.define "web" do |web|                             
    web.vm.box = "ubuntu/focal64"                             
    web.vm.hostname = "web"                                   
    web.vm.network "private_network", ip: "192.168.56.10"

    # We set more ram memmory for this VM
    web.vm.provider "virtualbox" do |v|                      
      v.memory = 1024
    end

    # We want to access tomcat from the host using port 8080
    web.vm.network "forwarded_port", guest: 8080, host: 8080   

    web.vm.provision "shell", inline: <<-SHELL, privileged: false 
      # sudo apt-get install git -y
      # sudo apt-get install nodejs -y
      # sudo apt-get install npm -y
      # sudo ln -s /usr/bin/nodejs /usr/bin/node
      sudo apt install -y tomcat9 tomcat9-admin
      # If you want to access Tomcat admin web page do the following:
      # Edit /etc/tomcat9/tomcat-users.xml
      # uncomment tomcat-users and add manager-gui to tomcat user

      # Change the following command to clone your own repository!
      git clone https://github.com/Solange-o/devops-23-24-JPE-1212208.git
      cd devops-23-24-JPE-1212208/CA2/Part2/
      chmod u+x gradlew
      ./gradlew clean build
      nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
      # To deploy the war file to tomcat9 do the following command:
      # sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
    SHELL
  end
end                                                                
```

2 - Let's analyse what's happening:

This configuration file is used with Vagrant to set up two virtual machines, one for a database ("db") and another for a web server ("web").

- Vagrant.configure("2") do |config|: Starts the Vagrant configuration block.
- config.vm.box = "ubuntu/focal64": Sets the base box for both virtual machines to Ubuntu Focal Fossa 64-bit.
- config.ssh.insert_key = false: Disables inserting SSH keys into the virtual machines.
- config.vm.provision "shell", inline: <<-SHELL ... SHELL: Defines a shell provisioner to run inline commands during provisioning. 
In this case, it updates the package index and installs required packages on both virtual machines.

- Defines configuration for the "db" virtual machine.
- Sets its base box to Ubuntu Focal Fossa.
- Assigns the hostname "db" to the virtual machine.
- Configures a private network interface with the IP address "192.168.56.11".
- Sets up port forwarding to allow access to H2 console (port 8082) and H2 server (port 9092) from the host.
- Downloads the H2 Database JAR file during provisioning.
- Starts the H2 server on every provision using a shell script.

- Defines configuration for the "web" virtual machine.
- Sets its base box to Ubuntu Focal Fossa.
- Assigns the hostname "web" to the virtual machine.
- Configures a private network interface with the IP address "192.168.56.10".
- Specifies the provider-specific configuration (in this case, VirtualBox) to allocate 1024 MB of memory to the virtual machine.
- Sets up port forwarding to allow access to Tomcat (port 8080) from the host.
- Installs Tomcat and other required packages, clones a Git repository, compiles a Spring Boot application, and starts it using Gradle during provisioning. 

3 - Main changes from the original Vagrantfile:

    3.1 - Change the versions of Ubuunto and Java.

    3.2 - Acrescentar a linha: “config.ssh.insert_key = false” 
      This command disables the automatic insertion of SSH keys into the virtual machine's SSH configuration file. By default, when you create a new virtual machine with Vagrant, it generates and inserts a new SSH key for each machine. Disabling this option causes Vagrant to use the default key that comes with the Vagrant box.

    3.3 - Acrescentar a linha: “ nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &” 

      - The nohup ("no hang up") command is used to run another command in such a way that it continues running in the background even if the terminal or session that started it is closed. This is useful to ensure that the command keeps executing even after the SSH session or provisioning script exits.
      - This command "./gradlew bootRun" uses the gradlew (Gradle Wrapper) script to execute the bootRun task.
      - The part "> /home/vagrant/spring-boot-app.log" redirects the standard output (stdout) of the command to the file /home/vagrant/spring-boot-app.log. Instead of displaying the output in the terminal, it will be written to this file.
      - 2>&1: This part redirects the standard error output (stderr) to the same location as the standard output (stdout). This means that both normal output and errors will be written to the same log file, /home/vagrant/spring-boot-app.log.
      - The '&' character at the end of the line puts the command in the background. This allows the script to continue executing subsequently without waiting for the ./gradlew bootRun command to finish.

    3.4 - Comment on the line: “ # sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps”.

      By commenting out this line,  the deployment of the WAR file won't occur automatically during the provisioning of the "web" virtual machine. As a result, after the successful build of the project, the WAR file won't be copied to the directory /var/lib/tomcat9/webapps/, where Tomcat typically reads and deploys web applications. 

      With this line commented, after the successful build of the project, the WAR file will be automatically copied to the directory /var/lib/tomcat9/webapps/. Consequently, Tomcat will detect and automatically deploy the web application contained in the WAR file, making it accessible through the web browser.


## Alternative solution

For this work, we will also present an alternative technological solution for the virtualisation tool. 

Let's explore VMware and see how we can make use of it:

1 - Install VMware Workstation Player. 
    Download it from the https://www.vmware.com/content/vmware/vmware-published-sites/us/products/workstation-player/workstation-player-evaluation.html.html

2 - Install and activate the VMware Vagrant Plugin with the commands:
**vagrant plugin install vagrant-vmware-workstation**
**vagrant plugin license vagrant-vmware-workstation ~/path/to/license.lic**

3 - Change the Vagrantfile for VMware.
    We need to update the Vagrantfile to specify the provider as VMware:

```ruby
    Vagrant.configure("2") do |config|
      config.vm.provider "vmware_workstation" do |v|
      v.vmx["memsize"] = "2048" # Memory alocated to the VM / Adjust as needed
      v.vmx["numvcpus"] = "2"   # Number of CPUs / Adjust as needed
    end
    
    config.vm.box = "hashicorp/bionic64"
    config.ssh.insert_key = false
    
    # This provision is common for both VMs
    config.vm.provision "shell", inline: <<-SHELL
    sudo apt-get update -y
    sudo apt-get install -y iputils-ping avahi-daemon libnss-mdns unzip \
    openjdk-17-jdk-headless
    # ifconfig
    SHELL
    
    #============
    # Configurations specific to the database VM
      config.vm.define "db" do |db|
         db.vm.provider "vmware_workstation" do |v|
            v.vmx["memsize"] = "2048" # Memory alocated to the VM / Adjust as needed
            v.vmx["numvcpus"] = "1"   # Number of CPUs / Adjust as needed
         end

         db.vm.box = "hashicorp/bionic64"
         db.vm.hostname = "db"
         db.vm.network "private_network", ip: "192.168.56.11"

         # We want to access H2 console from the host using port 8082
         # We want to connet to the H2 server using port 9092
         db.vm.network "forwarded_port", guest: 8082, host: 8082
         db.vm.network "forwarded_port", guest: 9092, host: 9092

         # We need to download H2
         db.vm.provision "shell", inline: <<-SHELL
            wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
         SHELL

         # The following provision shell will run ALWAYS so that we can execute the H2 server process
         # This could be done in a different way, for instance, setting H2 as as service, like in the following link:
         # How to setup java as a service in ubuntu: http://www.jcgonzalez.com/ubuntu-16-java-service-wrapper-example
         #
         # To connect to H2 use: jdbc:h2:tcp://192.168.33.11:9092/./jpadb
         db.vm.provision "shell", :run => 'always', inline: <<-SHELL
            java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
         SHELL
      end

      #============
      # Configurations specific to the webserver VM
      config.vm.define "web" do |web|
         web.vm.provider "vmware_workstation" do |v|
            v.vmx["memsize"] = "2048" # Memory alocated to the VM / Adjust as needed
            v.vmx["numvcpus"] = "2"   # Number of CPUs / Adjust as needed
         end

         web.vm.box = "hashicorp/bionic64"
         web.vm.hostname = "web"
         web.vm.network "private_network", ip: "192.168.56.10"

         # We want to access tomcat from the host using port 8080
         web.vm.network "forwarded_port", guest: 8080, host: 8080

         web.vm.provision "shell", inline: <<-SHELL, privileged: false
            # sudo apt-get install git -y
            # sudo apt-get install nodejs -y
            # sudo apt-get install npm -y
            # sudo ln -s /usr/bin/nodejs /usr/bin/node
            sudo apt install -y tomcat9 tomcat9-admin
            # If you want to access Tomcat admin web page do the following:
            # Edit /etc/tomcat9/tomcat-users.xml
            # uncomment tomcat-users and add manager-gui to tomcat user

            # Change the following command to clone your own repository!
            git clone https://github.com/AnaSilvaSwitch/devops-23-24-JPE-1231821.git
            cd devops-23-24-JPE-1231821/ca2/part2
            chmod u+x gradlew
            ./gradlew clean build
            nohup ./gradlew bootRun > /home/vagrant/spring-boot-app.log 2>&1 &
            # To deploy the war file to tomcat9 do the following command:
            # sudo cp ./build/libs/basic-0.0.1-SNAPSHOT.war /var/lib/tomcat9/webapps
         SHELL
      end
    end
```  

4- Running the VM's with VMware
To create and provision the virtual machines using VMware, we can use the command:
**vagrant up --provider=vmware_workstation**

5- Commit the changes to the repository with the commands:
**git add .**
**git commit -m "[FEAT] Updated the Vagrantfile configuration for VMware (alternative technological solution for the virtualization tool). closes #26"**
**git push**

6- Tag the assignment:
**git tag -a CA3-Part2 -m "ca3-part2"**
**git push origin CA3-Part2**

## Conclusion

In conclusion, this assignment aimed to utilize Vagrant to set up a virtual environment capable of running a Spring Boot application, mirroring the development environment required for executing the application developed in Part 2 of the previous assignment. Through a step-by-step guide, we covered the installation and usage of Vagrant, explored and modified the Vagrantfile to configure the virtual machines, and provided an alternative solution using VMware Workstation Player. The modifications made to the Vagrantfile included setting up network configurations, installing necessary software packages, and automating the deployment of the Spring Boot application. By leveraging Vagrant, users can easily create reproducible development environments, facilitating software development and testing processes. Additionally, the alternative solution using VMware Workstation Player demonstrated the flexibility of virtualization tools in providing options for configuring and managing virtual environments. Overall, this assignment provided valuable insights into utilizing Vagrant and alternative virtualization solutions to streamline the development and deployment of software applications.


