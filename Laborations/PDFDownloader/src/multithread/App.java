package multithread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
	
	

	public static void main(String[] args) {
		if (args.length < 1 || args.length > 1) {
			throw new IllegalArgumentException("Must provide an address");
		}

		URL target;

		try {
			target = new URL(args[0]);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Must pass URL as https://regex101.com/");
		}

		long startTime = System.nanoTime();
		ThreadStatus ts = new ThreadStatus();

		try {
			HashMap<String, URL> pdfs = Scraper.getFiles(target, "pdf");
			
			/*** Extending threads; multithread.Thread ***/
//			for (Entry<String, URL> entry : pdfs.entrySet()) {
//				new multithread.Thread.Runner(entry.getValue(), entry.getKey(), ts).start();
//			}

			/*** Runnable Runner with limited threads and URL list ***/
			SyncList list = new SyncList(pdfs);
			multithread.ListRunnable.Runner runner = new multithread.ListRunnable.Runner(list, ts);
			for (int i = 0; i < 9; i++) {
				new Thread(runner).start();
			}
			
			
			/*** Executor impl. with multithread.Runnable ***/
//			ExecutorService ex = Executors.newFixedThreadPool(10);
//			for (Entry<String, URL> entry : pdfs.entrySet()) {
//				ex.submit(new multithread.Runnable.Runner(entry.getValue(), entry.getKey(), ts));
//			}
//			ex.shutdown();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ts.waitForAllThreads();
		long endTime = System.nanoTime();
		long timeSpent = (endTime - startTime) / 1000000;
		System.out.println(timeSpent + "ms");
	}

}
