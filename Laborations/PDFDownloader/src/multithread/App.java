package multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
	
	public static void main(String[] args) {
		if (args.length < 1 || args.length > 1) {
			throw new IllegalArgumentException("Must provide an address");
		}
		
		long startTime = System.nanoTime();

		URL target;
		
		try {
			target = new URL(args[0]);
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Must pass URL as https://regex101.com/");
		}
		
		try {
			InputStream is = target.openStream();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			HashMap<String, URL> pdfs = new HashMap<String, URL>();
			String line;
			Pattern pattern = Pattern.compile("(?i)(href=\")(.+?)([^\\/]*.pdf)(\")");
			Matcher m;
			while ((line = br.readLine()) != null) {
				m = pattern.matcher(line);
				if (m.find()) {
					pdfs.put(m.group(3), new URL(m.group(2) + m.group(3)));
				}
			}
			
			Stack<JobFileDownload> jfds = new Stack<JobFileDownload>();
			for (Entry<String, URL> entry : pdfs.entrySet()) {
				jfds.push(new JobFileDownload(entry.getValue(), "multithread/" + entry.getKey()));
			}
			
			JobFileDownload temp = null;
			while (!jfds.isEmpty()) {
				try {
					temp = jfds.pop();
					Thread t = new Thread(temp);
					t.start();
				} catch (Exception e) {
					if (jfds != null) {
						jfds.push(temp);
					}
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long endTime = System.nanoTime();
		
		long duration = endTime - startTime;

		System.out.println(duration / 1000000 + "ms");
	}

}
