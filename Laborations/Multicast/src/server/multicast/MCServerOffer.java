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
			
			TimeService timeServer = new TimeService();
			timeServer.start();
			
			ms.joinGroup(ia);
			
			while (true) {
				byte[] buf = new byte[65536];
				DatagramPacket dp = new DatagramPacket(buf, buf.length);

				ms.receive(dp);
				
				String serviceQuestion = 
						new String(dp.getData(), 0, dp.getLength(), "UTF-8");

				System.out.println("Received: " + serviceQuestion +
						" from " + dp.getAddress() + ":" + dp.getPort());
				
				//DatagramPacket answer;
				switch (serviceQuestion.toLowerCase()) {
				case "datetime_hello":
					timeServer.notifyClient(dp);
					/*
					byte[] bufAnswer = 
						("I am " + 
						InetAddress.getLocalHost().getHostName() +
						" and I have the datetime service.").getBytes();

					answer = 
						new DatagramPacket(
								bufAnswer, bufAnswer.length,
								dp.getAddress(), dp.getPort());

					ms.send(answer);
					*/
					break;
				}
			}

		} catch (IOException e) {
			System.out.println("Exception:" + e);
		}
	}

}
