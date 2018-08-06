package com.coadingproblem.download;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

public class DownloaderFactory {
	static Logger logger = Logger.getLogger(DownloaderFactory.class.getName());
	
	
	public Properties properties(){
		Properties prop = new Properties();
		try {
			prop.load(DownloaderFactory.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
	
	public Downloader getInstance(String urlString){
		URL url=null;
		try {
			url = new URL(urlString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String protocol=url.getProtocol();
		logger.info("Protocol is : "+protocol);
		Properties prop=properties();
		
		if(protocol.equalsIgnoreCase("ftp")){
			Downloader downloader=new FTPDownloadFile(url,prop.getProperty("Output-Folder"),prop.getProperty("FTP-UserName"),prop.getProperty("FTP-Password"));
			return downloader;
		}else if(protocol.equalsIgnoreCase("sftp")){
			Downloader downloader=new SftpDownloader(url,prop.getProperty("Output-Folder"),prop.getProperty("FTP-UserName"),prop.getProperty("FTP-Password"));
			return downloader;
		}else if(protocol.equalsIgnoreCase("http")){
			Downloader downloader=new HttpDownloader(url,prop.getProperty("Output-Folder"));
			return downloader;
		}else{
			
		}
		
		return null;
		
	}

}
