package TCP;

import java.io.IOException;
import java.net.ServerSocket;

public class EchoTCP2 {

	public static void main(String[] args) {

		try {
			ServerSocket ss = new ServerSocket(30000);
			for (;;) new ServerListener(ss.accept()).start();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
