package com.coadingproblem.download;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class testing {
	
	public static void main(String[] args) {
		BufferedReader reader;
		List<String> list=new ArrayList<String>();
		final DownloaderFactory downloadFactory=new DownloaderFactory();
		try {
			reader = new BufferedReader(new FileReader(downloadFactory.properties().getProperty("URL-FileLocation")));
			String line = reader.readLine();
			while (line != null) {
				list.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		list.parallelStream().forEach(e->downloadFactory.getInstance(e));
			
		
	}
	
	
	
	 
  
}