package server.multicast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;

public class TimeService extends Thread {
	private static final int BUFFER_IP = 65536;
	private DatagramSocket dgSocket;
	
	public TimeService() {
		try {
			dgSocket = new DatagramSocket();
		} catch (SocketException e) { e.printStackTrace(); }
	}
	
	public void notifyClient(DatagramPacket dgPacketRecv) {
		try {
			byte[] bufferNotify = 
					("I am " + 
					InetAddress.getLocalHost().getHostName() + 
					" and I have the timeservice.").getBytes();

			DatagramPacket dgPacketNotify
				= new DatagramPacket(
						bufferNotify, bufferNotify.length, 
						dgPacketRecv.getAddress(), dgPacketRecv.getPort());
			
			dgSocket.send(dgPacketNotify);
		} catch (Exception e) { e.printStackTrace(); }
	}

	public void run() {
		try {
			
			byte[] bufferRecv = new byte[BUFFER_IP];

			DatagramPacket dgPacketRecv =
					new DatagramPacket(bufferRecv, bufferRecv.length);
			
			DatagramPacket dgPacketSend;
			
			String request;
			while (true) {
				dgSocket.receive(dgPacketRecv);
				
				request = new String(
						dgPacketRecv.getData(),
						dgPacketRecv.getOffset(),
						dgPacketRecv.getLength(),
						"UTF-8");
				
				if (request.equalsIgnoreCase("datetime_request")) {
					byte[] bufferSend = new Date().toString().getBytes();

					dgPacketSend = 
							new DatagramPacket(
									bufferSend, bufferSend.length,
									dgPacketRecv.getAddress(),
									dgPacketRecv.getPort());

					dgSocket.send(dgPacketSend);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
