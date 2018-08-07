package com.coadingproblem.download;

import org.junit.* ;
import static org.junit.Assert.* ;

import java.io.File;
import java.util.Arrays;

public class HttpTest {
	
	  @Test
	   public void testJavaSourceViewer() {
	      String url = "http://www.iitk.ac.in/esc101/share/downloads/javanotes5.pdf";
	      DownloaderFactory instance=new DownloaderFactory();
	      Downloader downloader=instance.getInstance("http://www.iitk.ac.in/esc101/share/downloads/javanotes5.pdf");
	      downloader.download();
	      File file =new File(downloader.OutputFolder);
	      assertTrue((Arrays.asList( file.list())).contains(downloader.FileName));
	      
	   }

}
