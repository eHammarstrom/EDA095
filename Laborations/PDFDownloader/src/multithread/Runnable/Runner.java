package multithread.Runnable;

import java.net.URL;

import multithread.Callback;
import multithread.Downloader;

public class Runner implements Runnable {
	private URL url;
	private String fileName;
	private Callback callback;
	
	public Runner(URL url, String fileName, Callback callback) {
		this.url = url;
		this.fileName = fileName;
		this.callback = callback;
	}

	public void run() {
		callback.isRunning();
		Downloader.download(url, fileName, "Runnable");
		callback.isDone();
	}

}
