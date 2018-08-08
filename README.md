

Downloader Project

This project is use to download the file from different protocols like http, ftp and sftp.

Add the values of below Properties
========================================================================
1. FTP-UserName -- username to login FTP server
2. FTP-Password -- password to login FTP server
3. SFTP-UserName -- username to login SFTP server
4. SFTP-Password -- password to login SFTP server
5. Output-Folder -- output folder where files can be downloaded

After adding mentied properties run the Main.java class

 mvn -q clean compile exec:java -Dexec.mainClass="com.coadingproblem.download.Main" -Dexec.args=arguments

For testing the download functionality I have added a JUnit test case for all three proptocols
1. HTTP ====>It will download the file from the configured location.
2. FTP  ====> It will create amoke server here I have used FakeFtpServer jar to create mock FTP.
3. SFTP ====> Created a default server and uploded the file from the resources file and downloading the same file 
             to test the download functionality. uploadFile methos is use to upload the file in default created server.
             
 Belwo is the URL format 
```javascript
  http --> http://<Host Name>/<FileName>
  ftp --> ftp://<host Name>:<port Number>/<file Name>
  sftp -->  sftp://<host Name>:<port Number>/<file Name>
 ```
