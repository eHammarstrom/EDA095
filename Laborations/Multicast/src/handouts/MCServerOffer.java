package handouts;

import java.net.*;
import java.io.*;

public class MCServerOffer {

	public static void main(String args[]) {
		try {

			MulticastSocket ms = new MulticastSocket(4099);
			InetAddress ia = InetAddress.getByName("experiment.mcast.net");
			DatagramSocket dgSocket = new DatagramSocket(30000);

			ms.joinGroup(ia);

			while (true) {
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);
				
				byte[] bufferSend = 
						("My name is: " + InetAddress.getLocalHost().getHostName()).getBytes();

				ms.receive(dp);

				DatagramPacket dgPacketSend = new DatagramPacket(
						bufferSend,
						bufferSend.length,
						dp.getAddress(),
						dp.getPort());


				String s = new String(dp.getData(), 0, dp.getLength());
				System.out.println("Received: " + s);
				
				dgSocket.send(dgPacketSend);
			}

		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}
	}

}
