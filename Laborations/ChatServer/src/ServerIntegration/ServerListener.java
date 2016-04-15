package ServerIntegration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerListener extends Thread {
	private Socket sock;
	private ServerRunner sr;
	private InputStream is;
	private OutputStream os;
	
	public ServerListener(Socket sock, ServerRunner sr) {
		this.sock = sock;
		this.sr = sr;

		try {
			is = sock.getInputStream();
			os = sock.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void write(String message) {
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
		for (;;) {
			byte[] buffer = new byte[4096];
			
			try {
				while (is.read(buffer) != -1) { 
					String message = new String(buffer);
					System.out.println(this.getName() + " Received: " + message);
					if (message.startsWith("E:")) {
						os.write(message.substring(
								message.indexOf(":") + 1).getBytes());
					} else if (message.startsWith("M:")) {
						sr.write(message.substring(
								message.indexOf(":") + 1));
					}
				}
				
				sock.close();
				System.out.println(this.getName() + " Closed.");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
