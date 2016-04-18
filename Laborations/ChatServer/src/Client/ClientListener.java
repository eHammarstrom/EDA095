package Client;

import java.io.IOException;
import java.io.InputStream;

public class ClientListener extends Thread {
	private static final int BUFFER = 1024 * 4;
	private InputStream is;
	
	public ClientListener(InputStream is) throws IOException {
		this.is = is;
	}
	
	public void run() {
		System.out.println(this.getName() + " is listening to Server.");
		try {
			byte[] buffer = new byte[BUFFER];
			int read;
			
			while ((read = is.read(buffer)) != -1) {
				System.out.println(new String(buffer));
				buffer = new byte[BUFFER];
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
