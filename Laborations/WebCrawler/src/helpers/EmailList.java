package helpers;

import java.util.HashSet;

public class EmailList<E> extends HashSet<E> {
	
	public synchronized boolean add(E e) {
		return super.add(e);
	}
	
}
