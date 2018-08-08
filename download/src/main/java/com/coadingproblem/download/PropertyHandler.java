package com.coadingproblem.download;

import java.io.IOException;
import java.util.Properties;

public class PropertyHandler {
	
	public static Properties properties() {
		Properties prop = new Properties();
		try {
			prop.load(DownloaderFactory.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

}
