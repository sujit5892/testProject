package com.coadingproblem.download;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpDownloader extends Downloader {

	static Logger logger = Logger.getLogger(HttpDownloader.class.getName());

	public HttpDownloader(URL url, String outputFolder) {
		super(url, outputFolder);
	}

	public void run() {
		HttpURLConnection conn = null;

		try {
			// Opening connection to URL
			conn = (HttpURLConnection) URL.openConnection();
			conn.setConnectTimeout(10000);
			conn.connect();

			// Checking response code it should be withIn 200 range
			if (conn.getResponseCode() / 100 != 2) {
				logger.log(Level.SEVERE, "Response code is " + conn.getResponseCode());
				logger.log(Level.SEVERE, "Response code is " + conn.getResponseMessage());
			}
			writeDate(conn.getInputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(conn!=null){
				conn.disconnect();
			}
		}

	}
}
