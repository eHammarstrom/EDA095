package server.multicast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ClientHandler implements Runnable {
	private DatagramSocket dgSocket;
	private InetAddress targetAddr;
	private int targetPort;

	public ClientHandler(InetAddress targetAddr, int targetPort) {
		try {
			dgSocket = new DatagramSocket();
		} catch (SocketException e) { e.printStackTrace(); }
		
		this.targetAddr = targetAddr;
		this.targetPort = targetPort;
	}
	
	public void run() {
		try {
			byte[] bufferRecv = new byte[65536];
	
			DatagramPacket dgPacketSend;
			DatagramPacket dgPacketReceive =
					new DatagramPacket(bufferRecv, bufferRecv.length);
			
			byte[] bufferSend = ("I am offering a time service and my name is: " + 
					InetAddress.getLocalHost().getHostName()).getBytes();
	
			dgPacketSend = 
					new DatagramPacket(
							bufferSend, 
							bufferSend.length,
							targetAddr, targetPort);

			dgSocket.send(dgPacketSend);
	
			while (true) {
				dgSocket.receive(dgPacketReceive);
				
				System.out.println("Received " + targetAddr + ": " + 
						new String(dgPacketReceive.getData()));
			}
		} catch (Exception e) { e.printStackTrace(); }
	}

}
