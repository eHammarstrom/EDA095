package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendUDP2 {
	private static final int BUFFER_MAX = 1024 * 4;
	
	public static void main(String[] args) {
		if (args.length < 2 || args.length > 2)
			throw new IllegalArgumentException("java SendUDP2 port command");
		
		int port = Integer.parseInt(args[0]);
		String command = args[1];
		
		DatagramSocket dgSocket;
		DatagramPacket dgPacketSend;
		DatagramPacket dgPacketReceive;
		byte[] bufferReceive = new byte[BUFFER_MAX];
		
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

}
