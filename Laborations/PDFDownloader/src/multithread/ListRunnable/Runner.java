package multithread.ListRunnable;

import java.net.URL;

import multithread.Callback;
import multithread.Downloader;
import multithread.SyncList;

public class Runner implements Runnable {
	private SyncList list;
	private Callback callback;

	public Runner(SyncList list, Callback callback) {
		this.list = list;
		this.callback = callback;
	}

	public void run() {
		callback.isRunning();
		while (!list.isEmpty()) {
			URL url = list.pop();
			int lastOccurance = url.toString().lastIndexOf("/") + 1;
			String fileName = url.toString().substring(lastOccurance);

			Downloader.download(url, fileName, "Runnable");
		}
		callback.isDone();
	}

}
