package helpers;

import java.net.URL;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CacheList {
	LinkedList<String> cache;
	LinkedList<URL> targets;
	
	public CacheList() {
		cache = new LinkedList<String>();
		targets = new LinkedList<URL>();
	}
	
	public synchronized void push(URL url) {
		if (!cache.contains(url.toString())) {
			cache.push(url.toString());
			targets.push(url);
		}
	}
	
	public synchronized boolean contains(URL url) {
		return cache.contains(url.toString());
	}
	
	public synchronized URL pop() throws NoSuchElementException {
		return targets.pop();
	}
	
	public synchronized int size() {
		return cache.size();
	}
	
	public synchronized LinkedList<String> getCache() {
		return cache;
	}

}
