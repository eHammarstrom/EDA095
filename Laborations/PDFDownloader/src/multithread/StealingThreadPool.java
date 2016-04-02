package multithread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

public class StealingThreadPool {
	private Executor exec;
	
	public StealingThreadPool() {
		exec = Executors.newWorkStealingPool();
	}
	
	public boolean add(Runnable command) {
		try {
			exec.execute(command);
			return true;
		} catch (RejectedExecutionException e) {
			return false;
		}
	}

}
