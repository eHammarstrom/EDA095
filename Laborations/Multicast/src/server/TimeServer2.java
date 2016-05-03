package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeServer2 {

	public static void main(String[] args) {

		String command;
		while (true) {
			command = receive();
			
			Date date = new Date();
			switch (command.toLowerCase()) {
			case "datetime":
				System.out.println(date.toString());
				break;
			case "usdate":
				DateFormat dfdate = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.US);
				System.out.println(dfdate.format(date));
				break;
			case "ustime":
				DateFormat dftime = DateFormat.getTimeInstance(DateFormat.DEFAULT, Locale.US);
				System.out.println(dftime.format(date));
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
