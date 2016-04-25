package multifast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashSet;
import java.util.NoSuchElementException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import helpers.ScrapeList;

public class Scraper implements Runnable {
	private static final int MAX_LINK_ACQUIRE = 2000;
	private static final int MAX_LINK_DELAY = 1200;
	private ScrapeList cache;
	private HashSet<String> emails;

	public Scraper(ScrapeList cache, HashSet<String> emails) {
		this.cache = cache;
		this.emails = emails;
	}

	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + " says: Hello world!");

		while (cache.size() <= MAX_LINK_ACQUIRE) {
			try {

				URL root = cache.pop();
				System.out.print(threadName + " CRAWLING: " + root.toString());

				Document doc;
				try {
					doc = Jsoup.connect(root.toString()).get();
				} catch (Exception e) {
					System.out.println("\t <-- NOT REACHABLE.");
					continue;
				}

				System.out.println("");

				Elements elements = doc.getElementsByTag("a");
				elements.addAll(doc.getElementsByTag("frame"));

				URL target = null;
				for (int i = 0; i < elements.size(); i++) {
					try {
						Element element = elements.get(i);

						if (element.hasAttr("href"))
							target = new URL(element.attr("abs:href"));
						else if (element.hasAttr("src"))
							target = new URL(element.attr("abs:src"));
						else continue;

						if (cache.contains(target)) continue;

						if (target.getProtocol()
								.equalsIgnoreCase("mailto")) {

							emails.add(URLDecoder.decode(target.getFile(), "UTF-8"));

						} else if (target.getProtocol()
								.equalsIgnoreCase("http")
								|| target.getProtocol()
								.equalsIgnoreCase("https")) {
							
							HttpURLConnection connection = 
									(HttpURLConnection) target.openConnection();
							
							connection.setRequestMethod("HEAD");
							connection.setReadTimeout(MAX_LINK_DELAY);
							connection.setConnectTimeout(MAX_LINK_DELAY);
							
							String contentType = connection.getContentType();
							
							if (contentType != null 
									&& contentType.contains("text/html")
									&& cache.push(target)) {
								
								System.out.println(
										"Cache: "
										+ cache.size()
										+ "\t"
										+ threadName.substring(threadName.indexOf("t"))
										+ " added URL: "
										+ target.toString());
								
								if (cache.size() >= MAX_LINK_ACQUIRE) break;

							}/* else {
								
								System.out.println(
										threadName.substring(threadName.indexOf("t"))
										+ " SKIPPED: "
										+ target.toString());
								
							}*/
						}
					} catch (IOException e) {
					}
				}
			} catch (NoSuchElementException e) {
			}
		}
	}

}
