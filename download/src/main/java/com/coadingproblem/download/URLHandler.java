package com.coadingproblem.download;

import java.net.MalformedURLException;
import java.net.URL;

public class URLHandler {
	public static URL url(String urlString) {
		try {
			return new URL(urlString);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String protocol(String urlString) {
		/*
		 * java.net.url doesn't supported protocols
		 * */
		try {
			if (!urlString.startsWith("sftp")) {
			return new URL(urlString).getProtocol();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
