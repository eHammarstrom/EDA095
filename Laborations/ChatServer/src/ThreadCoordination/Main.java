package ThreadCoordination;

import java.lang.Thread;

public class Main {
	
	public static void main(String[] args) {
		Mailbox mb = new Mailbox();

		for (int i = 0; i < 10; i++) {
			new ThreadWrite(mb).start();;
		}
		
		new ThreadRead(mb).start();
	}
	
	private static class ThreadRead extends Thread {
		Mailbox mb;
		
		public ThreadRead(Mailbox mb) {
			this.mb = mb;
		}
		
		public void run() {
			try {
				for (;;) { 
					String out = mb.clear();
					System.out.println(out);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static class ThreadWrite extends Thread {
		Mailbox mb;
		
		public ThreadWrite(Mailbox mb) {
			this.mb = mb;
		}
		
		public void run() {
			try {
				for (int i = 0; i < 5; i++) {
					Thread.sleep((long) (Math.random() * 1000));
					mb.deposit(this.getName() + " my " + (i + 1) + " time.");
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
