package server.multicast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Date;

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
		this.run();
	}
	
	public void run() {
		try {
			byte[] bufferSend = ("I am offering a time service and my name is: " + 
					InetAddress.getLocalHost().getHostName()).getBytes();
	
			DatagramPacket dgPacketSend = 
					new DatagramPacket(
							bufferSend, 
							bufferSend.length,
							targetAddr, targetPort);

			dgSocket.send(dgPacketSend);
		
			System.out.println("Sent our service response to: " + dgPacketSend.getPort());
			System.out.println("This is the port used by this thread to listen: " + dgSocket.getLocalPort());

			byte[] bufferRecv = new byte[65536];
			DatagramPacket dgPacketReceive =
					new DatagramPacket(bufferRecv, bufferRecv.length);
	
			dgSocket.setSoTimeout(2000);
			dgSocket.receive(dgPacketReceive);
			
			String command = new String(
					dgPacketReceive.getData(), 
					dgPacketReceive.getOffset(), 
					dgPacketReceive.getLength(), "UTF-8");

			System.out.println("Received from " + targetAddr + " command: \"" + command + "\"");
			
			byte[] answer;
			switch (command.toLowerCase()) {
			case "datetime":
				answer = new Date().toString().getBytes();
				break;
			default:
				answer = "INVALID COMMAND".getBytes();
			}

			dgSocket.send(new DatagramPacket(
					answer, answer.length, dgPacketReceive.getAddress(), dgPacketReceive.getPort()));

			System.out.println("Sent: " + new String(answer) + " to " + dgPacketReceive.getPort());
		} catch (SocketTimeoutException e) {
			System.out.println("Thaimat happened.");
		} catch (Exception e) { e.printStackTrace(); }
	}

}
