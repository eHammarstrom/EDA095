package monoslow;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helpers.ScrapeList;

public class Main {

	public static void main(String[] args) {
		URL root;

		ScrapeList cache = new ScrapeList();
		LinkedList<String> emails = new LinkedList<String>();
		
		long startTime = System.currentTimeMillis();

		try {
			root = new URL(args[0]);


			cache.push(root);

			URL temp;
			while ((temp = cache.pop()) != null) {
				if (cache.size() >= 1000) {
					System.out.println("Reached MAX crawl of 1000 links");
					break;
				}


				Document doc = null;

				System.out.print("CRAWLING: " + temp.toString());

				try {
					doc = Jsoup.connect(temp.toString()).get();
				} catch (Exception e) {
					System.out.println("\t <-- THIS COULD NOT BE REACHED.");
					continue;
				}
				
				System.out.println("");

				Elements elements = doc.getElementsByTag("a");
				elements.addAll(doc.getElementsByTag("frame"));
				
				URL target = null;
				long lastStamp = System.currentTimeMillis();
				for (int i = 0; i < elements.size(); i++) {
					try {
						Element element = elements.get(i);
						System.out.print("Time taken: " + (System.currentTimeMillis() - lastStamp) + "ms");
						System.out.println("\t Current cache size: " + cache.size());
						lastStamp = System.currentTimeMillis();
						System.out.println("Processing: " + element.toString());

						if (element.hasAttr("href"))
							target = new URL(element.attr("abs:href"));
						else if (element.hasAttr("src"))
							target = new URL(element.attr("abs:src"));
						else
							continue;
						
						if (cache.contains(target)) continue;

						if (target.getProtocol().equalsIgnoreCase("mailto")) {

							emails.add(target.getFile());
							System.out.println("Mail address: " + target.getFile());

						} else if (target.getProtocol().equalsIgnoreCase("http") ||
								target.getProtocol().equalsIgnoreCase("https")) {

							HttpURLConnection connection = (HttpURLConnection) target.openConnection();

							connection.setRequestMethod("HEAD");
							connection.setReadTimeout(1200);
							connection.setConnectTimeout(1200);
							connection.connect();

							String contentType = connection.getContentType();

							if (contentType != null && contentType.contains("text/html")) {
								cache.push(target);
								System.out.println("URL working: " + target.toString());
							} else {
								System.out.println("URL not working: " + target.toString());
							}
						}
					} catch (IOException e) { }
				}
			}

		} catch (Exception e) {
			if (e instanceof NoSuchElementException) {
				System.out.println("Target list empty, leaving.");
			} else {
				e.printStackTrace();
			}
		} finally {
			System.out.println("Total time taken: " + (System.currentTimeMillis() - startTime) / 1000 + " seconds");
			
			File linkFile = new File("links.txt");
			File emailFile = new File("emails.txt");

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

}
