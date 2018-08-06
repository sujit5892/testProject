package com.coadingproblem.download;


import java.net.URL;


public abstract class Downloader  implements Runnable {

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

	
}
