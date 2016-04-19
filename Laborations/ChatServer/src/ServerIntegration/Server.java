package ServerIntegration;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

import sun.net.NetworkServer;

public class Server {
	
	public static void main(String[] args) {
		ClientManager cm = new ClientManager();
		ServerRunner sr = new ServerRunner(cm);

		try {
			ServerSocket ss = new ServerSocket(30000);
			ServerListener sl;

			for (;;) {
				System.out.println("Listening for connections in main.");
				sl = new ServerListener(ss.accept(), sr);
				sl.start();
				cm.push(sl);
				System.out.println("Pushed client to ClientManager from main.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
