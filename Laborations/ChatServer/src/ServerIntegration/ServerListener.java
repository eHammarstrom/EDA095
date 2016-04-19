package ServerIntegration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ServerListener extends Thread {
	private final static int BUFFER = 1024 * 4;

	private Socket sock;
	private ClientManager cm;
	private InputStream is;
	private OutputStream os;

	public ServerListener(Socket sock, ClientManager cm) {
		this.sock = sock;
		this.cm = cm;

		try {
			is = sock.getInputStream();
			os = sock.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void write(String message) {
		try {
			System.out.println(this.getName() + " Wrote to me: " + message);
			os.write(message.getBytes());
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		System.out.println(this.getName() + " Listening.");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String message;

		try {
			while ((message = br.readLine()) != null) {
				System.out.println(this.getName() + " Received: " + message);

				switch (message.toLowerCase().substring(0, 2)) {
				case "e:":
					os.write((message.substring(message.indexOf(":") + 1) + "\n").getBytes());
					break;
				case "m:":
					cm.write(message.substring(message.indexOf(":") + 1) + "\n");
					break;
				case "q:":
					cm.remove(this);
					sock.close();
					return;
				}
			}

			is.close();
			os.close();
			sock.close();
			System.out.println(this.getName() + " Closed.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			cm.remove(this);
		}
	}

}
