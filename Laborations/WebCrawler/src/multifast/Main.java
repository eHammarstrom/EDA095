package multifast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import helpers.EmailList;
import helpers.ScrapeList;

public class Main {

	public static void main(String[] args) {
		if (args.length < 1 || args.length > 1)
			throw new IllegalArgumentException("Must pass URL as argument.");

		URL start = null;
		try {
			start = new URL(args[0]);
		} catch (MalformedURLException e) {
			System.err.println("Invalid URL passed as argument.");
		}

		ScrapeList cache = new ScrapeList();
		EmailList<String> emails = new EmailList<String>();

		ExecutorService executor = Executors.newFixedThreadPool(10);

		long startTime = System.currentTimeMillis();

		cache.push(start);

		for (int i = 0; i < 9; i++) {
			executor.execute(new Scraper(cache, emails));
		}
		
		while (cache.remainingScrapes() <= 50) { }
		executor.shutdown();
		while (!executor.isShutdown()) { }

		System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) / 1000 + "s");

		File linkFile = new File("multi_links.txt");
		File emailFile = new File("multi_emails.txt");

		try {
			linkFile.createNewFile();
			emailFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {

			PrintWriter pwLink = new PrintWriter(linkFile);
			PrintWriter pwEmail = new PrintWriter(emailFile);

			for (URL url : cache.getCache()) pwLink.println(url.toString());
			pwLink.close();

			for (String s : emails) pwEmail.println(s);
			pwEmail.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
