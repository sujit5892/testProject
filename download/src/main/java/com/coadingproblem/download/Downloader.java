package com.coadingproblem.download;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class Downloader implements Runnable {

	static Logger logger = Logger.getLogger(Downloader.class.getName());

	/** URL to download the file */
	protected URL URL;

	protected String serverURL;

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

		serverURL = url.getAuthority();
		OutputFolder = outputFolder;
		this.userName = userName;
		this.password = password;
		FileName = url.getPath();
		FileSize = -1;
		mDownloaded = 0;

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

	/**
	 * Start or resume download
	 */
	protected void download() {
		Thread t = new Thread(this);
		t.start();
	}

	protected synchronized void downloaded(int value) {
		mDownloaded += value;

	}

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
			logger.info("Deleting the file");
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
