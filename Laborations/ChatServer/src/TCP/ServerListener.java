package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerListener extends Thread {
	Socket sock;

	public ServerListener(Socket sock) {
		this.sock = sock;
	}

	public void run() {
		System.out.println(sock.getInetAddress());

		try {
			InputStream is = sock.getInputStream();
			OutputStream os = sock.getOutputStream();

			byte[] buffer = new byte[4096];
			String recv;
			while (is.read(buffer) != -1) {
				recv = new String(buffer);
				System.out.println(recv);
				os.write(recv.toUpperCase().getBytes());
				os.flush();
			}

			sock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
