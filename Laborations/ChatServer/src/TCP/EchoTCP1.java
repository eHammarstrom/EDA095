package TCP;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCP1 {
	
	public static void main(String[] args) {
		try {
			ServerSocket ss = new ServerSocket(30000);
			
			Socket sock = ss.accept();
			
			System.out.println(sock.getInetAddress());
			
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
			
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
