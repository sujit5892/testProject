package com.coadingproblem.download;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockftpserver.fake.FakeFtpServer;
import org.mockftpserver.fake.UserAccount;
import org.mockftpserver.fake.filesystem.FileEntry;
import org.mockftpserver.fake.filesystem.FileSystem;
import org.mockftpserver.fake.filesystem.UnixFakeFileSystem;

public class FTPTest {

	protected static final String HOME_DIR = "/";
	protected static final String FILE = "/dir/sample.txt";
	protected static final String CONTENTS = "abcdef 1234567890";

	protected FakeFtpServer fakeFtpServer;

	@Before
	public void setUp() throws Exception {
		fakeFtpServer = new FakeFtpServer();

		fakeFtpServer.setServerControlPort(8088); // use any free port

		FileSystem fileSystem = new UnixFakeFileSystem();
		UserAccount userAccount = new UserAccount("demo", "password", HOME_DIR);
		fakeFtpServer.addUserAccount(userAccount);
		fileSystem.add(new FileEntry(FILE, CONTENTS));
		fakeFtpServer.setFileSystem(fileSystem);

		fakeFtpServer.start();
	}

	@After
	public void tearDown() throws Exception {
		fakeFtpServer.stop();
	}

	@Test
	public void testFtpDownload() throws Exception {

		DownloaderFactory instance = new DownloaderFactory();
		Downloader downloader = instance.getInstance("ftp://localhost:8088/dir/sample.txt");
		downloader.download();
		File file = new File(downloader.OutputFolder);
		assertTrue((Arrays.asList(file.list())).contains(downloader.FileName));

	}
}