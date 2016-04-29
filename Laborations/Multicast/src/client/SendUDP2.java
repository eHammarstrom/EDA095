package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.HashMap;

public class SendUDP2 {
	private static final int BUFFER_IP_DATAGRAM = 65536;
	
	public static void main(String[] args) {
		if (args.length < 2 || args.length > 2)
			throw new IllegalArgumentException("java SendUDP2 port command");
		
		int port = Integer.parseInt(args[0]);
		String command = args[1];
		
		DatagramSocket dgSocket;
		DatagramPacket dgPacketSend;
		DatagramPacket dgPacketReceive;
		byte[] bufferReceive = new byte[BUFFER_IP_DATAGRAM];
		
		try {
			dgSocket = new DatagramSocket(30001);
			
			/*
			dgPacketSend = new DatagramPacket(
					command.getBytes(),
					command.getBytes().length,
					address, port);
			 */
			
			dgPacketReceive = new DatagramPacket(
					bufferReceive, bufferReceive.length);
			
			//dgSocket.send(dgPacketSend);
			
			dgSocket.receive(dgPacketReceive);

			System.out.println(
					"Received Data: " + new String(dgPacketReceive.getData()));

		} catch (Exception e) { e.printStackTrace(); }
		
	}

	public static HashMap<String, DatagramPacket> serverLookup() {
		try {
			HashMap<String, DatagramPacket> lookup = 
					new HashMap<String, DatagramPacket>();
			
			MulticastSocket ms = new MulticastSocket();
			ms.setTimeToLive(1);
			InetAddress msAddress = 
					InetAddress.getByName("experiment.mcast.net");
			
			byte[] bufferAsk = "datetime".getBytes();
			DatagramPacket dgpAsk = 
					new DatagramPacket(bufferAsk, bufferAsk.length);
			ms.send(dgpAsk);
			
			byte[] bufferIp = new byte[BUFFER_IP_DATAGRAM];
			DatagramPacket recv = 
					new DatagramPacket(bufferIp, bufferIp.length);
			long start = System.currentTimeMillis();
			while ((System.currentTimeMillis()) < 1000) {
				ms.receive(recv);
				lookup.put(new String(recv.getData()), recv);
			}

			return lookup;
		} catch (IOException e) { e.printStackTrace(); }

		return null;
	}
}
