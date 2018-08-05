package com.coadingproblem.download;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpDownloader extends Downloader {

	public HttpDownloader(URL url, String outputFolder) {
		super(url, outputFolder);
		download();
	}

	public void run() {
		HttpURLConnection conn = null;
		RandomAccessFile raf=null;
		BufferedInputStream in=null;
		try {
			// Opening connection to URL
			conn = (HttpURLConnection) URL.openConnection();
			conn.setConnectTimeout(10000);
			conn.connect();

			// Checking response code it should be withIn 200 range
			if (conn.getResponseCode() / 100 != 2) {
				System.out.println("Response code is " + conn.getResponseCode());
				System.out.println("Response code is " + conn.getResponseMessage());
			}

			// Check for valid content length.
			int contentLength = conn.getContentLength();

			if (FileSize == -1) {
				FileSize = contentLength;
			}

			 in = new BufferedInputStream(conn.getInputStream());

			// open the output file and seek to the start location
			 raf = new RandomAccessFile(OutputFolder + FileName, "rw");
			raf.seek(0);
			byte data[] = new byte[1024];
			int numRead;
			while (((numRead = in.read(data, 0, 1024)) != -1)) {
				raf.write(data, 0, numRead);
				downloaded(numRead);
			}

		} catch (Exception e) {

		} finally {
			if (conn != null)
				conn.disconnect();
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {}
			}
		}
	}

}
