package com.coadingproblem.download;

import java.io.BufferedOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPDownloadFile extends Downloader {

	static Logger logger = Logger.getLogger(HttpDownloader.class.getName());
	protected FTPDownloadFile(URL url, String outputFolder, String userName, String password) {
		super(url, outputFolder, userName, password);
		download();
	}

	public void run() {

		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(serverURL);
			ftpClient.login(userName, password);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			boolean success;
			String remoteFile = FileName;
			File downloadFile = new File(OutputFolder + FileName.substring(FileName.lastIndexOf('/') + 1));
			OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(downloadFile));
			InputStream inputStream = ftpClient.retrieveFileStream(remoteFile);
			byte[] bytesArray = new byte[4028];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(bytesArray)) != -1) {
				outputStream.write(bytesArray, 0, bytesRead);
			}

			success = ftpClient.completePendingCommand();
			if (success) {
				logger.info("downloaded successfully.");
			}
			outputStream.close();
			inputStream.close();

		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}