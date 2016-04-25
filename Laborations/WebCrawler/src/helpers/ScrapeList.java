package helpers;

import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class ScrapeList {
	private volatile HashSet<URL> cache;
	private volatile LinkedList<URL> targets;
	
	public ScrapeList() {
		cache = new HashSet<URL>();
		targets = new LinkedList<URL>();
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
	
	public synchronized HashSet<URL> getCache() {
		return cache;
	}

}
