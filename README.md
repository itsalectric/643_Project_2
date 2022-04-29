https://github.com/fefong/markdown_readme/blob/master/README.md?plain=1


# NJIT 643 Project 2  
Alec Shamsey  
Dr. Christian Borcea  
CS 643 Cloud Computing  
4/27/2022

https://github.com/itsalectric/643_Project_2/  

- [NJIT 643 Project 2](#njit-643-project-2)
- [Key Terms](#key-terms)
- [Prequisites](#prequisites)
	- [Downloads](#downloads)
	- [Create Account](#create-account) 
- [Initial Setup](#initial-setup)
	- [AWS Credentials Creation, Retrieval, and Setup](#aws-credentials-creation-retrieval-and-setup)
	- [Security Group Creation and Configuration](#security-group-creation-and-configuration)
	- [EC2 Creation, Configuration, and Connection](#ec2-creation-configuration-and-connection)
		- [Obtaining PEM/PPK keys](#obtaining-pemppk-keys)
			- [Learner Lab](#learner-lab)
			- [Traditional AWS Account](#traditional-aws-account)
			- [Pem to PPK Conversion](#pem-to-ppk-conversion)
		- [Connecting to a running EC2 instance](#connecting-to-a-running-ec2-instance)
			- [Connecting via PuTTy](#connecting-via-putty)
			- [Connecting via WinSCP](#connecting-via-winscp)
- [EC2 Instance Setup](#ec2-instance-setup)
	- [AWS CLI Setup](#aws-cli-setup)
	- [Instance Update and Package Installations](#instance-update-and-package-installations)
	- [Configuring Flintrock](#configuring-flintrock)



- [Running with Docker](#running-with-docker)
- [Running without Docker](#running-without-docker)



# Key Terms
✨✨✨DOUBLE CHECK ACCRONYMS APPEAR✨✨✨  
AWS Acronyms for quick reference

|Acronym|Term|Acronym|Term|
|:---|:---|:---|:---|
|AMI|Amazon Machine Images|AWS|Amazon Web Services (duh)|
|EBS|Elastic Block Store|EC2|Elastic Cloud Compute|
|EMR|Elastic MapReduce|CLI|Command Line Interface|
|HVM|Hardware Virtual Machine|IAM|Identity and Access Management|
|PEM|Privacy Enhanced Mail|PPK|Public Private Key
|S3|Simple Storage Service|SDK|Software Development Kit|		

# Prequisites
## Downloads
* PuTTY (https://www.chiark.greenend.org.uk/~sgtatham/PuTTY/latest.html)
* PuTTYgen (https://www.PuTTYgen.com/download-PuTTY)
* A Text Editor like Sublime Text (https://www.sublimetext.com/3)
* A GUI capable of connecting to AWS and securely transferring files such as WinSCP (https://winscp.net/eng/download.php)
* Docker (https://docs.docker.com/get-docker/)

## Create Account
* Standard AWS Account or Learner Lab Account
* Github Account
* Docker Hub Account

# Initial Setup

## AWS S3 Bucket Creation and Storage
A user can either load the project's files () to an S3 bucket or load them via WinSCP and push the files with various commands to the nodes. Both methods for loading data will be explained later, however an S3 bucket must be created in order to fetch the for the first option mentioned above. To do this:
1. Navigate to (https://s3.console.aws.amazon.com/s3/)
2. Click "Create bucket" towards the top left
3. Input an acceptable "Bucket name"
4. Deslect "Block all public access"
5. For simplicity, the rest of the options can be left as default
6. Scroll to the very bottom of the screen and click "Create Bucket"
7. Upload both TrainingDataset.csv and ValidationDataset.csv to the newly created bucket

## AWS Credentials Creation, Retrieval, and Setup
Many of the required tasks do not apply to the restrictions set by the learner lab. Usually the SDK would be generated through AWS IAM console. Those enrolled in the learner lab do not have access perform the following tasks:
* Create a user
* Create a group
* Set Parameters (resulting from the aforementioned)

The instructions to create a set of credentials for a standard AWS account can be found here (https://https://docs.aws.amazon.com/IAM/latest/UserGuide/id_users_create.html). Users must also edit permissions associated with the newly created IAM user, general instructions can be found here (https://docs.aws.amazon.com/IAM/latest/UserGuide/id_roles.html). Since Learner Lab accounts have rigid accessibility, a default "Lab Role" is already created and serves as one of the few editable account parameters. The following instructions are very similar to editing an already created role from a standard account, but are written to specifically edit the default “Lab Role” given by the AWS Learner Lab:
1. Navigate to the IAM console (https://console.aws.amazon.com/iamv2/)
2. Selecting “Roles” on the left pane and choosing “Lab Role”
3. Click “Add permissions” then “Attach policies” in the drop down
4. Select the following permissions:
	* AmazonEC2FullAccess
	* AmazonEMRFullAccessPolicy_v2 (if you choose to use EMR instead of Flintstone)
5. Then click Attach policies towards the end of the page
6. Users must attach the Lab Role profile to EC2 instances which will be discussed in the next section

## Security Group Creation and Configuration
It's good practice to create security groups to dictate who has access to aws services. Generally it's best to set credentials to your IP or IP addresses you expect to access the services. To create security groups:
1. Navigate to (https://console.aws.amazon.com/ec2/)
2. Click "Security Groups" on the left pane and click on "Create security group"
3. Type in your desired Security Group Name and Description in the associated textboxes
4. Assign your desired inbound and outbound rules
	1. Click "Add Rule" in the the secion you wish to edit
	2. Keep "All Traffic" selected in the "Type" section
	3. Set a singular IPs you expect to access the instance
		1. For simplicity, you can selection the destination as "My IP"
		2. To designate a specific IP, leave it as "Custom" desired input IP(s)

## EC2 Creation, Configuration, and Connection
AWS automatically implemented a new GUI when launching a new EC2 instance. The following instructions are the new GUI:  
1. Navigate to (http://console.aws.amazon.com/ec2/)
2. Click "Instances" on the left pane and click on "Launch Instances"
	1. Name the instance to your liking in the "Name and Tags" section
	2. Keep default "Ubuntu" selected in the "Application and OS Images (Amazon Machine Image)" section
	3. Edit the "Instance Type" as needed depending on computing needs
	4. Select an existing or create a new key within the  "Key Pair" section
		1. Select "vockey" if using Learner Lab (PEM key can be downloaded on the "AWS Detail" page)
		2. Click on "create new key" if using a standard account (or Learner Lab if you desire)
			1. Enter a name for your key within the "Key pair name" textbox
			2. Click "create pair" at the bottom of the form
			3. A .pem file with the designated name will be automatically downloaded. Store this file in a safe location as it can only be downloaded once
	5. Apply the security rules created from [Security Group Creation and Configuration](#security-group-creation-and-configuration) instructions within the "Network settings" section
		1. Click Edit on the right side of this section
		2. Click the "Select existing security group" radio button
		3. Select the name of previously created security group
	6. Ensure all the parameters in the summary section directly below the instance textbox is to your standards and click "Launch instance"


### Obtaining PEM/PPK keys
#### Learner Lab
1. Choose the "AWS Details" link on the learner lab module page
2. Click “Download PEM button” and save the labsuser.pem file
3. Also click “Download PPK” and save the labuser.ppk file

#### Traditional AWS Account
Ensure you've referenced and followed steps listed within the documentation on creating users, assigning roles to groups, and assigning groups to users within AWS standard accounts. Once everything is properly created, PEM Keys can be generated during EC2 instance creation, specifically in step 4 within [EC2 Creation, Configuration, and Connection](#ec2-creation-configuration-and-connection).

### Pem to PPK Conversion
Normally, AWS only supplies the pem file during EC2 creation. The following are the required steps to convert pem files to ppk when given the normal console through a traditional AWS account.
1. Open PuTTYGen
2. Click Load 
3. Select the PEM file you wish to convert
4. Once the file is successfully loaded, select “Save private key”
5. Designate the path for the new PPK file

### Connecting to a running EC2 instance
Be sure to perform the following on the singular instance you designated as the Main Node

#### Connecting via PuTTy
Open the PuTTY app and have the EC2 console open. A majority of the steps will include copying information from the console to the app for authentication.
1. Within the EC2 console, select the instance you wish to connect to
	1. Make sure the instance is running
	2. If it isn’t, highlight the instance, open the "Instance State" drop down and choose "Start Instance"
	3. While the instance highlighted and running, copy the URL under "Public IPv4 DNS"
		1. ex. ecX-X-X-X-X.compute-1.amazonaws.com
2. Within PuTTY:
	1. Select "Data" within the "Connection" drop down on the left pane
		1. Input "ubuntu" in the "Auto-login username" textbox
	2. Expand the "SSH" section under the "Connections" tree, and select "Auth"
		1. Click Browse and load your PPK file created from the section above
3. Navigate back to the main Session Page (optional - create a label for the Session and select save to easily connect back again later)
4. Paste the "Public IPv4 DNS" you previously copied into the "Host Name (or IP address)" textbox
5. Click open and you should be connected to the instance

#### Connecting via WinSCP
Open the WinSCP app and have the EC2 console open. Again, a majority of the steps will include copying information from the console to the app for authentication.
1. Same as the 1st step and substeps listed in [Connecting via PuTTy](#connecting-via-putty)
2. Within WinSCP:
	1. Click "New Session
	2. Paste the "Public IPv4 DNS" within the "Host name" textbox
	3. Paste "ubuntu" within the "User name" textbox

# Ubuntu EC2 Instance Setup
## Instance Update and Package Installations
Most of the required packages are already installed for the default AWS Linux 2 AMI but not for Ubuntu AMIs hosted on AWS. The following commands to update the instance along with all pre-installed packages and install AWS CLI, pip3, Boto3 (the AWS SDK for Python), and Flintrock:

	sudo apt update
	sudo apt upgrade -y
	sudo apt install unzip
	curl "https://awscli.amazonaws.com/awscli-exe-linux-x86_64.zip" -o "awscliv2.zip"
	unzip awscliv2.zip
	sudo ./aws/install
	sudo apt install python3 -y
	sudo apt install python3-pip -y
	pip3 install boto3
	pip3 install flintrock
	pip3 install pyspark


For simlicity, we'll also add a few paths to allow for easy calling of functions. To quickly add the path to allow for quick functions within the same session, paste the following in the terminal:
	
	echo 'export PATH="$PATH":/home/ubuntu/.local/bin' >> ~/.bashrc
	echo 'export SPARK_HOME=/etc/spark' >> ~/.bashrc
	echo 'export PATH=$SPARK_HOME/bin:$PATH' >> ~/.bashrc
	source ~/.bashrc

## AWS CLI Setup
AWS Learner lab uses predefined temporary credentials to access and utilize various services. Generally standard users must create their own access keys and assign security groups on their own within the IAM Console. Once a user on a standard account creates connects to an EC2 instance, they are able to use the "aws configure" command to mount their credentials. Instead, those utilizing the learner lab must use the following commands to mount their temporary credentials. The following values (Access Key, Secret Acccess Key, and Session Token) can be found on the "AWS Details" tab of the learner lab module. Individuals using the learner lab can, however, grab their information using the AWS Learner Lab following the steps below (the final command is just to ensure the credentials are mounted and working properly):
1.	Click “AWS Details” on the top right
2.	Click “Show” next to “AWS CLI”
3.	Copy/take note of the User’s Access key ID, Secret Access Key and Session Token
4.	Paste the following code with the appropriate values grabbed from the "AWS Detals" into the connected terminal

		export AWS_ACCESS_KEY_ID=[Access Key] 
		export AWS_SECRET_ACCESS_KEY=[Secret Access Key]
		export AWS_SESSION_TOKEN=[Session Token] 
		export AWS_REGION=us-east-1
		aws ec2 describe-instances --region us-east-1
		
In addition, be sure to upload the .pem key assigned during step 4 in [EC2 Creation, Configuration, and Connection](#ec2-creation-configuration-and-connection). You can upload it to a location of your choice, but keep track of the address as we will need it in the next step. 

## Configuring Flintrock
Navigate and open the following file:
		
	/home/ubuntu/.config/flintrock/config.yaml

Edit the file `config.yaml file` according to the parameters you set when creating the worker nodes. Most of the information can be found on the EC2 dashboard. Also add the path of the .pem key you just uploaded.

:rainbow::rainbow::rainbow::rainbow::rainbow::rainbow:
EDIT LATER
:rainbow::rainbow::rainbow::rainbow::rainbow::rainbow:
```yaml
services:
  spark:
    version: 3.1.2
    # git-commit: latest  # if not 'latest', provide a full commit SHA; e.g. d6dc12ef0146ae409834c78737c116050961f350
    # git-repository:  # optional; defaults to https://github.com/apache/spark
    # optional; defaults to download from a dynamically selected Apache mirror
    #   - can be http, https, or s3 URL
    #   - must contain a {v} template corresponding to the version
    #   - Spark must be pre-built
    #   - files must be named according to the release pattern shown here: https://dist.apache.org/repos/dist/release/spark/
    # download-source: "https://www.example.com/files/spark/{v}/"
    # download-source: "s3://some-bucket/spark/{v}/"
    # executor-instances: 1
  hdfs:
    version: 3.3.0
    # optional; defaults to download from a dynamically selected Apache mirror
    #   - can be http, https, or s3 URL
    #   - must contain a {v} template corresponding to the version
    #   - files must be named according to the release pattern shown here: https://dist.apache.org/repos/dist/release/hadoop/common/
    # download-source: "https://www.example.com/files/hadoop/{v}/"
    # download-source: "http://www-us.apache.org/dist/hadoop/common/hadoop-{v}/"
    # download-source: "s3://some-bucket/hadoop/{v}/"

provider: ec2

providers:
  ec2:
    key-name: key
    identity-file: /path/to/key.pem
    instance-type: m5.large
    region: us-east-1
    # availability-zone: <name>
    ami: ami-0aeeebd8d2ab47354  # Amazon Linux 2, us-east-1
    user: ec2-user
    # ami: ami-61bbf104  # CentOS 7, us-east-1
    # user: centos
    # spot-price: <price>
    # spot-request-duration: 7d  # duration a spot request is valid, supports d/h/m/s (e.g. 4d 3h 2m 1s)
    # vpc-id: <id>
    # subnet-id: <id>
    # placement-group: <name>
    # security-groups:
    #   - group-name1
    #   - group-name2
    # instance-profile-name:
    # tags:
    #   - key1,value1
    #   - key2, value2  # leading/trailing spaces are trimmed
    #   - key3,  # value will be empty
    # min-root-ebs-size-gb: <size-gb>
    tenancy: default  # default | dedicated
    ebs-optimized: no  # yes | no
    instance-initiated-shutdown-behavior: terminate  # terminate | stop
    # user-data: /path/to/userdata/script
    # authorize-access-from:
    #   - 10.0.0.42/32
    #   - sg-xyz4654564xyz

launch:
  num-slaves: 1
  # install-hdfs: True
  # install-spark: False
  # java-version: 8

debug: false

```

The following command will utilize the settings from the config.yaml code above. Once the instances have been created, run the second line to gather information of the newly created instances.
	
	
	flintrock launch [cluster name]
	flintrock describe [cluster name]

The expected output should be something similar to the following. Be sure to note the address of the master node as we will need it for future commands:

	test:
	  state: running
	  node-count: 5
	  master: ec2-XX-XX-XX-XX.compute-1.amazonaws.com
	  slaves:
	    - ec2-XX-XX-XX-XX.compute-1.amazonaws.com
	    - ec2-XX-XX-XX-XX.compute-1.amazonaws.com
	    - ec2-XX-XX-XX-XX.compute-1.amazonaws.com
	    - ec2-XX-XX-XX-XX.compute-1.amazonaws.com

Sbt was and scala were preiously installed from the very beginning in [Instance Update and Package Installations](Instance Update and Package Installations). You first will need to create an sbt project using the code below. After a few minutes, you will be prompted to give the project a name. Be sure to be within `/home/ubuntu` when running the commands.	

	sbt new scala/scala3.g8
	[project name]

Once the project is created, edit the `build.sbt` file to mirror your current package versions of packages 

```sbt
lazy val root = project
  .in(file("."))
  .settings(
    name := "project2",
    version := "0.1.0-SNAPSHOT",

    scalaVersion := "2.12.15",

    libraryDependencies ++= Seq(
    	"org.apache.spark" %% "spark-core" % "3.2.1",
	"org.apache.spark" %% "spark-mllib-local" % "3.2.1"
	)
    )
```



Run either one of the next two options of codes depending on whether you have completed the instrucitons from [AWS S3 Bucket Creation and Storage](#ec2-creation-configuration-and-connection), or are planning to load the data into the cluster from your original Ubuntu instance.

	# Ensures the AWS connection configuration is valid by checking the contents of the S3 bucket created above
	aws s3 ls s3://[S3 Bucket Name]/
	
	# Transferring both TrainingDataset.csv and ValidationData.csv from the Ubuntu instance to the cluster (make sure the datasets are loaded through WinSCP
	flintrock copy-file [cluster name] [.py file path] [remote working directory]
	flintrock copy-file [cluster name] [TrainingDataset.csv] [remote working directory]
	flintrock copy-file [cluster name] [ValidationDataset.csv] [remote working directory]



Replace the `Main.scala` file located in the `/[poject name]/src/main/scala` with :rainbow::rainbow::rainbow::rainbow::rainbow::rainbow:
`FILENAME.SCALA`.
:rainbow::rainbow::rainbow::rainbow::rainbow::rainbow:
Return back to your main `/[poject name]` directory and run `sbt [poject name]` to create the `[poject file].jar` file required to run over the cluster. The .jar file will be located at `/[poject name]/target/scala-[version]/[poject file].jar`. Replace the associated names created from the steps above and run the following commands.


Create an sbt project by making a directory and moving into the newly created empty directory, then run the following commands:

	sbt --sbt-create
	
	# you'll enter the sbt command module
	# We only wanted it to create an empty project so exit the sbt module 
	exit
	
create directories as necessary to mimic the following layout
![image](https://user-images.githubusercontent.com/103093354/165901731-e1af7dac-4e44-4d12-9da6-2b1abc7f3541.png)

Create a new file named `build.sbt` and import it into the main `[project name]` directory. The content of the file should be as follows:

```sbt
import Dependencies._

ThisBuild / scalaVersion     := "2.12.15"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"

lazy val root = (project in file("."))
  .settings(
    name := "project2",
    libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % "3.2.1",
      "org.apache.spark" %% "spark-mllib-local" % "3.2.1",
      "org.apache.hadoop" % "hadoop-aws" % "3.3.0"
    )
  )
```

	flintrock copy-file [cluster name] /home/ubuntu/[poject name]/target/scala-[version]/[poject file].jar /home/ec2-user/[poject file].jar




# Working within the Master Node

Once logged into the Master Node, run the following commands 




# Running with Docker
# Running without Docker
