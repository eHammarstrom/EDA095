package server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class TimeServerUDP2 {
	private static final int PORT = 30000;
	private static final int BUFFER_MAX = 1024 * 4;
	
	public static void main(String[] args) {
		DatagramSocket dgSocket;
		DatagramPacket dgPacketReceive;
		DatagramPacket dgPacketSend;
		byte[] bufferReceive = new byte[BUFFER_MAX];
		byte[] bufferSend = new byte[BUFFER_MAX];

		try {

			dgSocket = new DatagramSocket(PORT);
			dgPacketReceive = new DatagramPacket(bufferReceive, bufferReceive.length);
			
			while (true) {
				bufferReceive = new byte[BUFFER_MAX];

				dgSocket.receive(dgPacketReceive);
				
				System.out.println(
						"Data: " + dgPacketReceive.getData().toString() +
						"Length: " + dgPacketReceive.getLength() +
						"Address: " + dgPacketReceive.getAddress().getHostAddress() +
						":" + dgPacketReceive.getPort());
				
				bufferSend = 
						("I am " + InetAddress.getLocalHost().toString() + " and I offer the datetime server.").getBytes();

				dgPacketSend = new DatagramPacket(
						bufferSend, bufferSend.length,
						dgPacketReceive.getAddress(),
						dgPacketReceive.getPort());
				
				dgSocket.send(dgPacketSend);
			}

		} catch (Exception e) { e.printStackTrace(); }
		
		
	}

}
