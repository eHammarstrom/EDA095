package ServerIntegration;

public class ServerRunner extends Thread {
	private ClientManager cm;

	public ServerRunner(ClientManager cm) {
		this.cm = cm;
	}

	public synchronized void write(String message) {
		System.out.println(this.getName() + " I am ServerRunner, someone is writing: " + message);
		cm.write(message);
	}
	
	public void done(ServerListener sl) {
		cm.remove(sl);
	}

	public void run() {
		System.out.println(this.getName() + " I am ServerRunner, listening.");
		for (;;) { }
	}

}
