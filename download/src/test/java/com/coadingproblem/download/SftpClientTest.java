package com.coadingproblem.download;

import org.apache.sshd.SshServer;
import org.apache.sshd.common.NamedFactory;
import org.apache.sshd.server.Command;
import org.apache.sshd.server.command.ScpCommandFactory;
import org.apache.sshd.server.keyprovider.SimpleGeneratorHostKeyProvider;
import org.apache.sshd.server.sftp.SftpSubsystem;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class SftpClientTest {
    private SshServer sshd;
    private Downloader downloder;
    
    private String server = "localhost";
    private String login = "login";
    private String password = "testPassword";

    @Before
    public void setUp() throws IOException {
        sshd = SshServer.setUpDefaultServer();
        sshd.setPasswordAuthenticator(new MyPasswordAuthenticator());
        sshd.setKeyPairProvider(new SimpleGeneratorHostKeyProvider());
        sshd.setSubsystemFactories(Arrays.<NamedFactory<Command>>asList(new SftpSubsystem.Factory()));
        sshd.setCommandFactory(new ScpCommandFactory());
        sshd.start();
        uploadFile("src/test/resource/testFile", "target/uploaded");
       // cleanFiles();
    }

    @After
    public void tearDown() throws InterruptedException {
        sshd.stop();
        cleanFiles();
    }
 
    /*
     * Upload method to upload the file to mock server
     *  to test download functionality
     *  
     * */
    
    public void uploadFile(String sourceFile, String destinationFile) {
    	ChannelSftp c=null;
    	Channel channel=null;
    	Session session=null;
    	
    	
    	try {
        	JSch jsch = new JSch();
        	System.out.println(server+"  "+login+"   "+password+"   "+sshd.getPort());
             session = jsch.getSession(login, server,sshd.getPort());
            session.setPassword(password);
            Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
             channel = session.openChannel("sftp");
            channel.connect();
            c = (ChannelSftp) channel;
           if (c == null || session == null || !session.isConnected() ) {
               throw new RuntimeException("Connection to server is closed. Open it first.");
           }
         c.put(sourceFile, destinationFile);
         } catch (JSchException e) {
            e.printStackTrace();
        }catch (SftpException e) {
            e.printStackTrace();
        }finally{
        	if (c != null) {
                
                c.disconnect();
            }
            if (channel != null) {
               
                channel.disconnect();
            }
            if (session != null) {
               
                session.disconnect();
            }
        }
    }

   
    @Test
    public void testRetrieveFile() throws Exception {
    	
    	DownloaderFactory download=new DownloaderFactory();
    	downloder=download.getInstance("sftp://localhost:"+sshd.getPort()+"/target/uploaded");
    	downloder.download();
    }
    
    private void cleanFiles() {
        File uploaded = new File("target/uploaded.txt");
        if (uploaded.exists()) {
            uploaded.delete();
        }

        File downloaded = new File("target/downloaded.txt");
        if (downloaded.exists()) {
            downloaded.delete();
        }
    }
}