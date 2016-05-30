package cli_serv;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class DataStreamClient {
	
	public static void main(String[] args) {
		try {
			Socket socket = new Socket(InetAddress.getLoopbackAddress(), 3000);
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

			while (true) {
				Thread.sleep(1000);
				dos.writeUTF("!World Hello");
				dos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
