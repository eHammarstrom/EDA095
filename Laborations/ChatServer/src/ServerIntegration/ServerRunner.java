package ServerIntegration;

public class ServerRunner {
	private ClientManager cm;

	public ServerRunner(ClientManager cm) {
		this.cm = cm;
	}

	public synchronized void write(String message) {
		cm.write(message);
	}
	
	public void done(ServerListener sl) {
		cm.remove(sl);
	}

}
