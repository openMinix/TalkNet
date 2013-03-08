
		TalkNet Project

	Requirements
    	This project was tested on Ubuntu 12.10.
    	You must to have installed:
		- JDK, JRE, Smack


	Getting Started

	To get started with TalkNet, you'll need to get familiar with SVN or use GIT.
	
	To initialize your local repository, use a command like this:
		($ sudo apt-get install subversion)
		$ mkdir SVNTalkNet
		$ cd SVNTalkNet/
		$ svn co  https://graphics.cs.pub.ro/TalkNet/

	For more info about svn commands [4]
		

	1. Download the library [1]
	2. Add smack.jar and smackx.jar into your classpath Eclipse project
	3. Import project into Eclipse and Run

	Openfire installation

	1. Download  the current version 3.6.4 [2]
	2. Extract openfire_3_6_4.tar.gz
	3. Go to openfire/bin
	4. ./openfire start
	5. Go to localhost:9090 for Setup
		Domain: 127.0.0.1
		Database Settings - Embedded Database
		Profile Settings - Default
		Administrator Account - Choose a valid email address and provide a pass
		Setup Complete!
	6. Restart openfire, enter again to localhost:9090, and login with (user:admin & pass from Setup)


	Openfire server configuration

	1. Go to Users/Groups tab and "Create New User"
	2. Create another user


	XMPP Client for Testing
	I will use Spark, IM client provided by the same company that provides Openfire.
	Download from [3]
	
	Extract, go to Spark, and run Spark (./Spark &)


[1] - http://www.igniterealtime.org/downloads/download-landing.jsp?file=smack/smack_3_1_0.tar.gz
[2] - www.igniterealtime.org/downloadServlet?filename=openfire/openfire_3_6_4.tar.gz
[3] - http://www.igniterealtime.org/downloads/download-landing.jsp?file=spark/spark_2_6_3.tar.gz
[4] - http://linux.byexamples.com/archives/255/svn-command-line-tutorial-for-beginners-1/

