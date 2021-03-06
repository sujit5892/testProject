package com.coadingproblem.download;


import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SftpDownloader extends Downloader {

	protected SftpDownloader(String url, String outputFolder, String userName, String password) {
		super(url, outputFolder, userName, password);

	}

	public void download() {

		Session session = null;
		Channel channel = null;
		ChannelSftp channelSftp = null;

		try {
			JSch jsch = new JSch();
			session = jsch.getSession(userName,serverURL,port);
			session.setPassword(password);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.connect();
			channel = session.openChannel("sftp");
			channel.connect();
			channelSftp = (ChannelSftp) channel;
			String remoteFile = FileName.substring(1);
			FileName = FileName.substring(FileName.lastIndexOf('/') + 1);
			writeDate(channelSftp.get(remoteFile));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
