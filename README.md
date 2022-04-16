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
- [Innitial Setup](#innitial-setup)
	- [EC2 Creation](#ec2-creation)

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

# Prequisite
## Downloads:
* PuTTY (https://www.chiark.greenend.org.uk/~sgtatham/PuTTY/latest.html)
* PuTTYgen (https://www.PuTTYgen.com/download-PuTTY)
* A Text Editor like Sublime Text (https://www.sublimetext.com/3)
* A GUI capable of connecting to AWS and securely transferring files such as WinSCP (https://winscp.net/eng/download.php)

## Create Accounts:
* Standard AWS Account or Learner Lab Account
* Github Account
* Docker Hub Account

# Initial Setup
## EC2 Creation
AWS automatically opted me for a new GUI when launching a new EC2 instance. The following instructions are the new GUI:  
1. Navigate to [console.aws.amazon.com/ec2/](http://console.aws.amazon.com/ec2/)
2. Click "Instances" and click on "Launch Instances"
	1. Name the instance to your liking in the "Name and Tags" section
	2. All other sections can remain the same
	3. 🌈🌈🌈🌈🌈🌈 COME BACK LATER ABOUT NUM OF INSTANCES


# EC2 Setup
AWS Learner lab uses predefined credentials temporary credentials to access and utilize various services. Generally standard users must create their own access keys and assign security groups on their own. Once a user on a standard account creates connects to an EC2 instance, they are able to use the "aws configure" command to mount their credentials. Instead, those utilizing the learner lab must use the following commands to mount their temporary credentials. The following values (Access Key, Secret Acccess Key, and Session Token) can be found on the "AWS Details" tab of the learner lab.

		export AWS_ACCESS_KEY_ID= [Access Key] 
		export AWS_SECRET_ACCESS_KEY= [Secret Access Key]
		export AWS_SESSION_TOKEN= [Session Token] 
		export AWS_REGION= us-east-1
		aws ec2 describe-instances --region us-east-1


