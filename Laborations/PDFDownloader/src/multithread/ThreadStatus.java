package multithread;

public class ThreadStatus implements Callback {
	private volatile int threadsDone = 0;
	private volatile int threadsStarted = 0;

	public synchronized void isDone() {
		threadsDone++;
	}
	
	public synchronized void isRunning() {
		threadsStarted++;
	}

	private boolean noActiveThreads() {
		return (threadsStarted - threadsDone == 0) ? true : false;
	}
	
	public void waitForAllThreads() {
		for (;;) {
			if (noActiveThreads()) {
				return;
			}
		}
	}
}
