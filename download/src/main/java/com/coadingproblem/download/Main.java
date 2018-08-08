package com.coadingproblem.download;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		final DownloaderFactory downloadFactory = new DownloaderFactory();
		List<String> list = new ArrayList<String>(Arrays.asList(args));
		list.parallelStream().forEach(e -> downloadFactory.getInstance(e).download());

	}

}