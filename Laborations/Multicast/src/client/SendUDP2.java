package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketTimeoutException;
import java.util.HashMap;

public class SendUDP2 {
	private static final int BUFFER_IP_DATAGRAM = 65536;
	private static int port;
	private static String command;
	
	public static void main(String[] args) {
		if (args.length < 2 || args.length > 2)
			throw new IllegalArgumentException("java SendUDP2 port command");
		
		port = Integer.parseInt(args[0]);
		command = args[1];
		
		
		try {
			
			DatagramPacket dgPacketCatch;
			if ((dgPacketCatch = discoverServer()) == null) {
				throw new IOException("discoverServer() returned null instead of DatagramPacket");
			} else {
				DatagramSocket commandSocket = new DatagramSocket();
				commandSocket.send(
						new DatagramPacket(
								command.getBytes(), command.getBytes().length,
								dgPacketCatch.getAddress(), dgPacketCatch.getPort()));
				
				System.out.println("Sent command \"" + command + "\" to: " + dgPacketCatch.getPort());
				
				byte[] bufferRecv = new byte[BUFFER_IP_DATAGRAM];
				DatagramPacket dgPacketRecv =
						new DatagramPacket(bufferRecv, bufferRecv.length);
				
				commandSocket.receive(dgPacketRecv);
				
				System.out.println("Command response: " + new String(dgPacketRecv.getData()));
			}

		} catch (Exception e) { e.printStackTrace(); }
			
		
		// This is not how to do things, messy multicast instead of a broadcast.
		/* THIS CAN BE USED TO CHECK WHAT SERVERS ARE BROADCASTING ON A SPECIFIC PORT AND WHAT THEY OFFER
		HashMap<String, DatagramPacket> lookup = serverLookup();
		
		for (Entry<String, DatagramPacket> entry : lookup.entrySet()) {
			System.out.println(entry.getKey() + " from port: " + entry.getValue().getPort());
		}
		*/
	}
	
	public static DatagramPacket discoverServer() {
		try {
			DatagramSocket broadCast = new DatagramSocket();
			broadCast.setBroadcast(true);

			InetAddress msAddress =
					InetAddress.getByName("experiment.mcast.net");

			byte[] discover = "DISCOVER".getBytes();
			broadCast.send(new DatagramPacket(discover, discover.length, msAddress, port));
			
			System.out.println("Sent broadcast on " + msAddress + ":" + port);
			
			byte[] bufferRecv = new byte[BUFFER_IP_DATAGRAM];
			DatagramPacket dgPacketRecv = new DatagramPacket(bufferRecv, bufferRecv.length);

			System.out.println("I am listening on: " + broadCast.getLocalPort());

			broadCast.setSoTimeout(2000);
			broadCast.receive(dgPacketRecv);
			
			System.out.println("Received packet from: " + dgPacketRecv.getPort());

			return dgPacketRecv;

		} catch (SocketTimeoutException e) {
			System.out.println("Thaimat happened.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static HashMap<String, DatagramPacket> serverLookup() {
		try {
			HashMap<String, DatagramPacket> lookup = 
					new HashMap<String, DatagramPacket>();
			
			MulticastSocket ms = new MulticastSocket();
			ms.setTimeToLive(1);
			InetAddress msAddress = 
					InetAddress.getByName("experiment.mcast.net");
			
			byte[] bufferAsk = command.getBytes();
			DatagramPacket dgpAsk = 
					new DatagramPacket(
							bufferAsk, bufferAsk.length,
							msAddress, port);
			ms.send(dgpAsk);
			System.out.println("Sent: " + command + " to " + msAddress.toString() + ":" + port);
			
			

			int num = 0;
			while (true) {
				num++;

				byte[] bufferIp = new byte[BUFFER_IP_DATAGRAM];
				DatagramPacket recv = 
					new DatagramPacket(bufferIp, bufferIp.length);

				try {
					ms.setSoTimeout(1000);
					ms.receive(recv);
					lookup.put("Server #" + num + " " + new String(recv.getData()), recv);
				
					System.out.println("Received: " + new String(recv.getData()) + " from " + recv.getAddress() + ":" + recv.getPort());
				} catch (SocketTimeoutException e) { break; }
			}

			return lookup;
		} catch (IOException e) { e.printStackTrace(); }

		return null;
	}
}
