package ServerIntegration;

import java.util.LinkedList;

public class ClientManager {
	private volatile LinkedList<ServerListener> clients;
	
	public ClientManager() {
		clients = new LinkedList<ServerListener>();
	}
	
	public void push(ServerListener sl) {
		clients.push(sl);
	}
	
	public void remove(ServerListener sl) {
		for (int i = 0; i < clients.size(); i++) {
			if (clients.get(i).getName().equals(sl.getName())) {
				clients.remove(i);
				break;
			}
		}
	}

	public void write(String message) {
		System.out.println("Client manager received: " + message + " . Now pushing to all clients.");

		for (ServerListener sl : clients) {
			sl.write(message);
		}
	}

}
