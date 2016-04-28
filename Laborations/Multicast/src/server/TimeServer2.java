package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

public class TimeServer2 {

	public static void main(String[] args) {

		String command;
		while (true) {
			command = receive();
			
			switch (command.toLowerCase()) {
			case "datetime":
				System.out.println(new Date().toString());
				break;
			case "error":
				System.out.println("Something went wrong.");
				break;
			default:
				System.out.println("Somebody wrote!");
			}
		}
	}

	public static String receive() {
		BufferedReader br = new BufferedReader(
				new InputStreamReader(System.in));

		try {
			return br.readLine();
		} catch (Exception e) {
			return "error";
		}
	}

}
