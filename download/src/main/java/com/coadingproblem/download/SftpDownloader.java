package com.coadingproblem.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SftpDownloader extends Downloader {

	protected SftpDownloader(URL url, String outputFolder, String userName, String password) {
		super(url, outputFolder, userName, password);
		download();
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
            //channelSftp.cd(SFTPWORKINGDIR);
            byte[] buffer = new byte[1024];
            BufferedInputStream bis = new BufferedInputStream(channelSftp.get(FileName));
            File newFile = new File(OutputFolder+FileName.substring(FileName.lastIndexOf('/') + 1));
            OutputStream os = new FileOutputStream(newFile);
            BufferedOutputStream bos = new BufferedOutputStream(os);
            int readCount;
            while ((readCount = bis.read(buffer)) > 0) {
                System.out.println("Writing: ");
                bos.write(buffer, 0, readCount);
            }
            bis.close();
            bos.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    } 	

}
