package ThreadCoordination;

public class Mailbox {
	private volatile String message;
	
	public Mailbox() {  }
	
	public synchronized void deposit(String message) {
		this.message = message;
		try {
			wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized String clear() {
		String temp = message;
		message = null;
		notifyAll();
		return temp;
	}

}
