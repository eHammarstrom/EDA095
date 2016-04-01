package multithread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class JobFileDownload implements Runnable {
	private URL url;
	private String fileName;
	
	public JobFileDownload(URL url, String fileName) {
		this.url = url;
		this.fileName = fileName;
	}

	public void run() {
		File file;
		FileOutputStream fout;
		BufferedInputStream bis;

		try {
			file = new File(fileName);
			file.createNewFile();
			fout = new FileOutputStream(file);
			bis = new BufferedInputStream(url.openStream());
			
			int b;
			while ((b = bis.read()) != -1) {
				fout.write(b);
			}
			
			bis.close();
			fout.flush();
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
