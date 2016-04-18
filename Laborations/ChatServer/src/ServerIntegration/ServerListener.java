package ServerIntegration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerListener extends Thread {
	private final static int BUFFER = 1024 * 4;
	
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
		for (;;) {
			byte[] buffer = new byte[BUFFER];

			try {
				while (is.read(buffer) != -1) {
					String message = new String(buffer);
					System.out.println(this.getName() + " Received: " + message);

					System.out.println(message.toLowerCase().substring(0, 2));
					switch (message.toLowerCase().substring(0, 2)) {
					case "e:":
						os.write(message.substring(message.indexOf(":") + 1).getBytes());
						break;
					case "m:":
						sr.write(message.substring(message.indexOf(":") + 1));
						break;
					case "q:":
						sr.done(this);
						return;
					}
					
					buffer = new byte[BUFFER];
				}

				is.close();
				os.close();
				sock.close();
				System.out.println(this.getName() + " Closed.");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				sr.done(this);
			}
		}
	}

}
