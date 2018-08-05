package com.coadingproblem.download;

import java.io.BufferedOutputStream;
import java.io.File;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPDownloadFile extends Downloader {

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
			String remoteFile2 = FileName;
			File downloadFile2 = new File(OutputFolder + FileName.substring(FileName.lastIndexOf('/') + 1));
			OutputStream outputStream2 = new BufferedOutputStream(new FileOutputStream(downloadFile2));
			InputStream inputStream = ftpClient.retrieveFileStream(remoteFile2);
			byte[] bytesArray = new byte[4096];
			int bytesRead = -1;
			while ((bytesRead = inputStream.read(bytesArray)) != -1) {
				outputStream2.write(bytesArray, 0, bytesRead);
			}

			success = ftpClient.completePendingCommand();
			if (success) {
				System.out.println("downloaded successfully.");
			}
			outputStream2.close();
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