package com.coadingproblem.download;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

public class DownloaderFactory {
	static Logger logger = Logger.getLogger(DownloaderFactory.class.getName());

	public Properties properties() {
		Properties prop = new Properties();
		try {
			prop.load(DownloaderFactory.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public Downloader getInstance(String urlString) {
		URL url = null;
		String protocol = null;
		try {
			if (!urlString.startsWith("sftp")) {
				url = new URL(urlString);
				protocol = url.getProtocol();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		Properties prop = properties();

		if (("ftp").equalsIgnoreCase(protocol)) {
			Downloader downloader = new FTPDownloadFile(url, prop.getProperty("Output-Folder"),
					prop.getProperty("FTP-UserName"), prop.getProperty("FTP-Password"));
			return downloader;
		} else if (("http").equalsIgnoreCase(protocol)) {
			Downloader downloader = new HttpDownloader(url, prop.getProperty("Output-Folder"));
			return downloader;
		} else {
			Downloader downloader = new SftpDownloader(urlString, prop.getProperty("Output-Folder"),
					prop.getProperty("SFTP-UserName"), prop.getProperty("SFTP-Password"));
			return downloader;
		}

	}

}
