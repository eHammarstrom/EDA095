package multithread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class DownloadRunner implements Runnable {
	private URL url;
	private String fileName;
	private Callback callback;
	
	public DownloadRunner(URL url, String fileName, Callback callback) {
		this.url = url;
		this.fileName = fileName;
		this.callback = callback;
	}

	public void run() {
		File file;
		FileOutputStream fout;
		BufferedInputStream bis;

		try {
			System.out.println(fileName + " started!");
			file = new File(fileName);
			file.createNewFile();
			fout = new FileOutputStream(file);
			bis = new BufferedInputStream(url.openStream());
			
			byte[] buffer = new byte[4096];
			int bytes;
			while ((bytes = bis.read(buffer)) != -1) {
				fout.write(buffer, 0, bytes);
			}

			bis.close();
			fout.flush();
			fout.close();

			System.out.println(fileName + " done!");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			callback.isDone();
		}
	}

}
