package cli_serv;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class DataStreamServer {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(3000);
			Socket socket = serverSocket.accept();
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			
			while (true) {
				System.out.println(dis.readUTF());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
