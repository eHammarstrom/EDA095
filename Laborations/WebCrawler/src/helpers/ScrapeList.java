package helpers;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class ScrapeList {
	private volatile HashSet<URL> cache;
	private volatile LinkedList<URL> targets;
	private volatile int scraped;
	
	public ScrapeList() {
		cache = new HashSet<URL>();
		targets = new LinkedList<URL>();
		scraped = 0;
	}
	
	public synchronized boolean push(URL url) {
		if (cache.add(url))
			return targets.add(url);
		else
			return false;
	}
	
	public synchronized boolean contains(URL url) {
		return cache.contains(url);
	}
	
	public synchronized URL pop() throws NoSuchElementException {
		return targets.pop();
	}
	
	public synchronized int size() {
		return cache.size();
	}
	
	public synchronized int remainingScrapes() {
		return scraped;
	}
	
	public synchronized void successfulScrape() {
		scraped++;
	}
	
	public synchronized HashSet<URL> getCache() {
		return cache;
	}

}
