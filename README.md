# testProject

testProject
This project is use to download the file from different protocols like http, ftp and sftp.

In pom.xml we need to add the values of below Properties
========================================================================
FTP-UserName -- username to login FTP server
FTP-Password -- password to login FTP server
SFTP-UserName -- username to login SFTP server
SFTP-Password -- password to login SFTP server
URL-FileLocation -- location of text file containing multile URLs
Output-Folder -- output folder where files can be downloaded
===========================================================================
Before running add the above mentioned property and run the testing.java class


For testing the download functionality I have added a JUnit test case for all three proptocols
1) HTTP ====>It will download the file from the configured location.
2) FTP  ====> It will create amoke server here I have used FakeFtpServer jar to create mock FTP.
2) SFTP ===> Created a default server and uploded the file from the resources file and downloading the same file 
             to test the download functionality. uploadFile methos is use to upload the file in default created server.

