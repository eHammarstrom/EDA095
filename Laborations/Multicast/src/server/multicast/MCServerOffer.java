package server.multicast;

import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

public class MCServerOffer {

	public static void main(String args[]) {
		try {
			System.out.println("Starting threaded MCServerOffer");

			MulticastSocket ms = new MulticastSocket(4099);
			InetAddress ia = InetAddress.getByName("experiment.mcast.net");
			
			ExecutorService exec = Executors.newWorkStealingPool();

			ms.joinGroup(ia);
			
			while (true) {
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);

				ms.receive(dp);
				
				String s = new String(dp.getData(), 0, dp.getLength());
				System.out.println("Received: " + s);

				exec.submit(new ClientHandler(dp.getAddress(), dp.getPort()));
				
				System.out.println("And gave it a thread.");
				System.out.println("Discovery was sent by: " + dp.getPort());
			}

		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}
	}

}
