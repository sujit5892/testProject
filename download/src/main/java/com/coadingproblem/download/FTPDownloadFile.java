package com.coadingproblem.download;


import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FTPDownloadFile extends Downloader {

	static Logger logger = Logger.getLogger(HttpDownloader.class.getName());
	protected FTPDownloadFile(URL url, String outputFolder, String userName, String password) {
		super(url, outputFolder, userName, password);
		
	}

	public void download() {

		FTPClient ftpClient = new FTPClient();
		try {
			
			if(port<0){
			ftpClient.connect(serverURL);
			}else{
				ftpClient.connect(serverURL, port);
			}
			int reply = ftpClient.getReplyCode();
	        if (!FTPReply.isPositiveCompletion(reply)) {
	        	ftpClient.disconnect();
	            throw new IOException("Exception in connecting to FTP Server");
	        }
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			boolean success;
			String remoteFile = FileName;
			FileName= FileName.substring(FileName.lastIndexOf('/') + 1);
			writeDate(ftpClient.retrieveFileStream(remoteFile));
			success = ftpClient.completePendingCommand();
			if (success) {
				logger.info("downloaded successfully.");
			}
			} catch (Exception ex) {
			logger.log(Level.SEVERE,"Error: ");
			ex.printStackTrace();
			
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				logger.log(Level.SEVERE,"Error: ");
				ex.printStackTrace();
			}
		}

	}

	
}