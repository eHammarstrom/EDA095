package client;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendUDP {
	private static final int BUFFER_MAX = 1024 * 4;
	
	public static void main(String[] args) {
		if (args.length < 3 || args.length > 3)
			throw new IllegalArgumentException("java SendUDP machine port command");
		
		InetAddress address;
		int port = Integer.parseInt(args[1]);
		String command = args[2];
		
		DatagramSocket dgSocket;
		DatagramPacket dgPacketSend;
		DatagramPacket dgPacketReceive;
		byte[] bufferReceive = new byte[BUFFER_MAX];
		
		try {
			address = InetAddress.getByName(args[0]);

			dgSocket = new DatagramSocket(30001);
			
			dgPacketSend = new DatagramPacket(
					command.getBytes(),
					command.getBytes().length,
					address, port);
			
			dgPacketReceive = new DatagramPacket(
					bufferReceive, bufferReceive.length);
			
			dgSocket.send(dgPacketSend);
			
			dgSocket.receive(dgPacketReceive);

			System.out.println(
					"Received Data: " + new String(dgPacketReceive.getData()));

		} catch (Exception e) { e.printStackTrace(); }
		
	}

}
