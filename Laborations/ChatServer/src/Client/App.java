package Client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class App {

	public static void main(String[] args) {
		if (args.length < 2 || args.length > 2)
			throw new IllegalArgumentException("java App host port");
		
		try {
			Socket sock = new Socket(args[0], Integer.parseInt(args[1]));
			OutputStream os = sock.getOutputStream();
			ClientListener cl = new ClientListener(sock.getInputStream());
			cl.start();
			Scanner sc = new Scanner(System.in);

			while (sc.hasNext()) {
				String message = sc.nextLine();
				os.write(message.getBytes());
				os.flush();
				if (message.substring(0, 2).equalsIgnoreCase("q:")) return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
