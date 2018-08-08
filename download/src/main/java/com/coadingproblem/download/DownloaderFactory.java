package com.coadingproblem.download;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class DownloaderFactory {
	static Logger logger = Logger.getLogger(DownloaderFactory.class.getName());

	public Downloader getInstance(String urlString) {
		Properties properties = PropertyHandler.properties();
		if (("ftp").equalsIgnoreCase(URLHandler.protocol(urlString))) {
			Downloader downloader = new FTPDownloadFile(URLHandler.url(urlString),
					properties.getProperty("Output-Folder"), properties.getProperty("FTP-UserName"),
					properties.getProperty("FTP-Password"));
			return downloader;
		} else if (("http").equalsIgnoreCase(URLHandler.protocol(urlString))) {
			Downloader downloader = new HttpDownloader(URLHandler.url(urlString),
					properties.getProperty("Output-Folder"));
			return downloader;
		} else {
			Downloader downloader = new SftpDownloader(urlString, properties.getProperty("Output-Folder"),
					properties.getProperty("SFTP-UserName"), properties.getProperty("SFTP-Password"));
			return downloader;
		}

	}

}
