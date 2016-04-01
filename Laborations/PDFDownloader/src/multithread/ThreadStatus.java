package multithread;

public class ThreadStatus implements Callback {
	private int threadsDone = 0;

	public void isDone() {
		threadsDone++;
	}

	public int getFinished() {
		return threadsDone;
	}
}
