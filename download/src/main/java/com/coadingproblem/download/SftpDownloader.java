package com.coadingproblem.download;

import java.net.URL;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SftpDownloader extends Downloader {

	protected SftpDownloader(URL url, String outputFolder, String userName, String password) {
		super(url, outputFolder, userName, password);

	}

	public void run() {

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(userName, serverURL);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			String remoteFile = FileName;
			FileName = FileName.substring(FileName.lastIndexOf('/') + 1);
			writeDate(channelSftp.get(remoteFile));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
