package multithread;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;

public class SyncList extends LinkedList<URL> {
	private volatile LinkedList<URL> list;
	private volatile int size;
	private volatile int popped;

	public SyncList(HashMap<String, URL> scrapedList) {
		list = new LinkedList<URL>();
		list.addAll(scrapedList.values());
		size = list.size();
		popped = 0;
	}
	
	@Override
	public URL pop() {
		popped++;
		return list.pop();
	}
	
	@Override
	public int size() {
		return size - popped;
	}
}
