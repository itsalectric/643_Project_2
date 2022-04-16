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
	- [Create Accounts](#create-accounts) 
- [Initial Setup](#innitial-setup)
	- [AWS Credentials Creation, Retrieval, and Setup](#aws-credentials-creation-retrieval-and-setup)
	- [Security Group Creation and Configuration](#security-group-creation-and-configuration)
	- [EC2 Creation, Configuration, and Connection](#ec2-creation-configuration-and-connection)
		- [Obtaining PEM/PPK keys](#obtaining-pemppk-keys)
			- [Learner Lab](#learner-lab)
			- [Traditional AWS Account](#traditional-aws-account)
			- [Pem to PPK Conversion](#pem-to-ppk-conversion)





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

## Create Accounts
* Standard AWS Account or Learner Lab Account
* Github Account
* Docker Hub Account

# Initial Setup
## AWS Credentials Creation, Retrieval, and Setup
Many of the required tasks do not apply to the restrictions set by the learner lab. Usually the SDK would be generated through AWS IAM console. Those enrolled in the learner lab do not have access perform the following tasks:
* Create a user
* Create a group
* Set Parameters (resulting from the aforementioned)

The instructions to create a set of credentials for a standard AWS account can be found here (https://https://docs.aws.amazon.com/IAM/latest/UserGuide/id_users_create.html). Users must also edit permissions associated with the newly created IAM user, general instructions can be found here (https://docs.aws.amazon.com/IAM/latest/UserGuide/id_roles.html). Since Learner Lab accounts have rigid accessibility, a default "Lab Role" is already created and serves as one of the few editable account parameters. The following instructions are very similar to editing an already created role from a standard account, but are written to specifically edit the default “Lab Role” given by the AWS Learner Lab:
1. Navigating to the IAM console (https://console.aws.amazon.com/iamv2/)
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
AWS automatically opted me for a new GUI when launching a new EC2 instance. The following instructions are the new GUI:  
1. Navigate to (http://console.aws.amazon.com/ec2/)
2. Click "Instances" on the left pane and click on "Launch Instances"
	1. Name the instance to your liking in the "Name and Tags" section
	2. Keep default "Amazon Linux 2 Kernel" selected in the "Application and OS Images (Amazon Machine Image)" section
	3. Edit the "Instance Type" as needed depending on computing needs
	4. Select an existing or create a new key within the  "Key Pair" section
		1. Select "Vockey" if using Learner Lab (PEM key can be downloaded on the "AWS Detail" page)
		2. Click on "create new key" if using a standard account (or Learner Lab if you desire)
			1. Enter a name for your key within the "Key pair name" textbox
			2. Click "create pair" at the bottom of the form
			3. A .pem file with the designated name will be automatically downloaded. Store this file in a safe location as it can only be downloaded once
	5. Apply the security rules created from [Security Group Creation and Configuration](#security-group-creation-and-configuration) instructions within the "Network settings" section
		1. Click Edit on the right side of this section
		2. Click the "Select existing security group" radio button
		3. Select the name of previously created security group
	7. 🌈🌈🌈🌈🌈🌈 COME BACK LATER ABOUT NUM OF INSTANCES
	8. On the top right, you can select the number of instances you wish to run. For this project, change this value to "4"
	9. Ensure all the parameters in the summary section directly below the instance textbox is to your standards and click "Launch instance"

### Obtaining PEM/PPK keys
#### Learner Lab
1. Choose the AWS Details link on the learner lab module page.
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
#### Connecting via PuTTy
Open the PuTTY app and have the EC2 console open. A majority of the steps will include copying information from the console to the app for authentication.
1. Within the EC2 console, select the instance you wish to connect to
	1. Make sure the instance is running
	2. If it isn’t, highlight the instance, open the "Instance State" drop down and choose "Start Instance"
	3. While the instance highlighted and running, copy the URL under "Public IPv4 DNS"
		1. ex. ecX-X-X-X-X.compute-1.amazonaws.com
2. Within PuTTY:
	1. Select "Data" within the "Connection" drop down on the left pane
		1. Input "ec2-user" in the "Auto-login username" textbox
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
	3. Paste "ec2-user" within the "User name" textbox

# EC2 Instance Setup
AWS Learner lab uses predefined temporary credentials to access and utilize various services. Generally standard users must create their own access keys and assign security groups on their own within the IAM Console. Once a user on a standard account creates connects to an EC2 instance, they are able to use the "aws configure" command to mount their credentials. Instead, those utilizing the learner lab must use the following commands to mount their temporary credentials. The following values (Access Key, Secret Acccess Key, and Session Token) can be found on the "AWS Details" tab of the learner lab. The final command is just to ensure the credentials are mounted and work properly.
Individuals using the learner lab can, however, grab their SDK information using the AWS Learner Lab following the steps below:
1.	Do not download or install AWS CLI if using the Learner Lab. The online Learner Lab module has the CLI and can easily retrieve necessary account.
2.	Click “AWS Details” on the top right
3.	Click “Show” next to “AWS CLI”
4.	Copy/take note of the User’s Access key ID and Secret Access Key

		export AWS_ACCESS_KEY_ID=[Access Key] 
		export AWS_SECRET_ACCESS_KEY=[Secret Access Key]
		export AWS_SESSION_TOKEN=[Session Token] 
		export AWS_REGION=us-east-1
		aws ec2 describe-instances --region us-east-1

