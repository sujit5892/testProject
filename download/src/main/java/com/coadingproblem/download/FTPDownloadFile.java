package com.coadingproblem.download;


import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPDownloadFile extends Downloader {

	static Logger logger = Logger.getLogger(HttpDownloader.class.getName());
	protected FTPDownloadFile(URL url, String outputFolder, String userName, String password) {
		super(url, outputFolder, userName, password);
		
	}

	public void download() {

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(serverURL);
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
			logger.log(Level.SEVERE,"Error: ",ex.getStackTrace());
			
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				logger.log(Level.SEVERE,"Error: ",ex.getStackTrace());
			}
		}

	}

	
}