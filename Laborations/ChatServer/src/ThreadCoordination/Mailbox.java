package ThreadCoordination;

public class Mailbox {
	private volatile String message;

	public Mailbox() {
	}

	public synchronized void deposit(String message) {
		while (this.message != null) {
			try { wait(); } catch (InterruptedException e) { }
		}
		this.message = message;
		notifyAll();
	}

	public synchronized String clear() {
		while (message == null) {
			try { wait(); } catch (InterruptedException e) { }
		}
		String temp = message;
		message = null;
		notifyAll();
		return temp;
	}

}
