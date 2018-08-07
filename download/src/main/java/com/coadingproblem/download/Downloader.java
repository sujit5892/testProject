package com.coadingproblem.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Downloader {

	static Logger logger = Logger.getLogger(Downloader.class.getName());

	/** URL to download the file */
	protected URL URL;

	protected String serverURL;

	protected int port;

	/** Output folder for downloaded file */
	protected String OutputFolder;

	/** The file name, extracted from URL */
	protected String FileName;

	protected String userName;

	protected String password;

	/** Size of the downloaded file (in bytes) */
	protected int FileSize;

	/** The state of the download */
	protected int State;

	/** downloaded size of the file (in bytes) */
	protected int mDownloaded;

	/**
	 * Constructor
	 * 
	 * @param fileURL
	 * @param outputFolder
	 * @param numConnections
	 */
	protected Downloader(URL url, String outputFolder) {
		URL = url;
		OutputFolder = outputFolder;
		String fileURL = url.getFile();
		FileName = fileURL.substring(fileURL.lastIndexOf('/') + 1);
		FileSize = -1;

		mDownloaded = 0;

	}

	protected Downloader(URL url, String outputFolder, String userName, String password) {

		serverURL = url.getHost();
		port = url.getPort();
		OutputFolder = outputFolder;
		this.userName = userName;
		this.password = password;
		FileName = url.getPath();
		FileSize = -1;
		mDownloaded = 0;

	}

	public Downloader(String url, String outputFolder, String userName, String password) {

		String s_URL[] = url.split("//");
		System.out.println(s_URL);
		String s1 = s_URL[1];
		String s2[] = s1.split(":");
		serverURL = s2[0];
		String portNumber[] = url.split(":");
		String portNum[] = (portNumber[2].split("/"));
		port = Integer.valueOf(portNum[0]);
		FileName = url.replace(("sftp://" + serverURL + ":" + port), "");
		OutputFolder = outputFolder;
		this.userName = userName;
		this.password = password;

	}

	/**
	 * Get the URL (in String)
	 */
	public String getURL() {
		return URL.toString();
	}

	/**
	 * Get the downloaded file's size
	 */
	public int getFileSize() {
		return FileSize;
	}

	/**
	 * Get the current progress of the download
	 */
	public float getProgress() {
		return ((float) mDownloaded / FileSize) * 100;
	}

	/**
	 * Get current state of the downloader
	 */
	public int getState() {
		return State;
	}

	public abstract void download();

	public void writeDate(InputStream inputstream) throws IOException {
		BufferedInputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {

			inputStream = new BufferedInputStream(inputstream);
			outputStream = new FileOutputStream(OutputFolder + FileName);
			byte[] buffer = new byte[1024];
			int count = 0;
			while ((count = inputStream.read(buffer, 0, 1024)) != -1) {
				outputStream.write(buffer, 0, count);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Error:", e.getStackTrace());
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			File check = new File(OutputFolder + FileName);
			check.delete();

		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

}
