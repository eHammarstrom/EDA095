package multithread;

public class ThreadStatus implements Callback {
	private volatile int threadsDone = 0;

	public void isDone() {
		threadsDone++;
	}

	public int getFinished() {
		return threadsDone;
	}
}
