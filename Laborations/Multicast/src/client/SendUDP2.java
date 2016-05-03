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
	private static DatagramSocket sock;
	
	public static void main(String[] args) {
		if (args.length < 2 || args.length > 2)
			throw new IllegalArgumentException("java SendUDP2 port command");
		
		port = Integer.parseInt(args[0]);
		command = args[1];
		
		try {
			
			DatagramPacket dgPacketCatch;
			if ((dgPacketCatch = discoverServer()) == null) {
				System.out.println("No servers discovered running a service with the command: " + command);
			} else {
				DatagramSocket commandSocket = new DatagramSocket();
				
				byte[] bufCommand = (command + "_request").getBytes();
				commandSocket.send(
						new DatagramPacket(
								bufCommand, bufCommand.length,
								dgPacketCatch.getAddress(), dgPacketCatch.getPort()));
				
				System.out.println("Sent command \"" + new String(bufCommand) + "\" to: " + dgPacketCatch.getAddress() + ":" + dgPacketCatch.getPort());
				
				byte[] bufferRecv = new byte[BUFFER_IP_DATAGRAM];
				DatagramPacket dgPacketRecv =
						new DatagramPacket(bufferRecv, bufferRecv.length);
				
				commandSocket.setSoTimeout(2000);
				commandSocket.receive(dgPacketRecv);
				
				System.out.println("Command response: " + new String(dgPacketRecv.getData()));
			}

		} catch (Exception e) { e.printStackTrace(); }
			
	}
	
	public static DatagramPacket discoverServer() {
		try {
			DatagramSocket broadCast = new DatagramSocket();

			InetAddress msAddress =
					InetAddress.getByName("experiment.mcast.net");

			byte[] discover = (command + "_hello").getBytes();
			broadCast.send(new DatagramPacket(discover, discover.length, msAddress, port));
			
			System.out.println("Sent broadcast on: " + msAddress + ":" + port);
			
			byte[] bufferRecv = new byte[BUFFER_IP_DATAGRAM];
			DatagramPacket dgPacketRecv = new DatagramPacket(bufferRecv, bufferRecv.length);

			System.out.println("I am listening on: " + broadCast.getLocalPort());

			broadCast.setSoTimeout(2000);
			broadCast.receive(dgPacketRecv);
			
			System.out.println("Received packet from: " + dgPacketRecv.getAddress() + ":" + dgPacketRecv.getPort());
			System.out.println("Which said: " + new String(dgPacketRecv.getData(), "UTF-8"));
			
			broadCast.close();

			return dgPacketRecv;

		} catch (SocketTimeoutException e) {
			System.out.println("Thaimat happened.");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
