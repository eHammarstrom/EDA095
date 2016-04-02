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

		int threadsActive = 0;
		ThreadStatus ts = new ThreadStatus();

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


			Stack<DownloadRunner> jfds = new Stack<DownloadRunner>();
			for (Entry<String, URL> entry : pdfs.entrySet()) {
				jfds.push(new DownloadRunner(entry.getValue(), "multithread/" + entry.getKey(), ts));
			}

			StealingThreadPool stp = new StealingThreadPool();
			DownloadRunner temp = null;
			while (!jfds.isEmpty()) {
				temp = jfds.pop();
				if (!stp.add(temp)) {
					jfds.push(temp);
				} else {
					threadsActive++;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (true) {
			if (ts.getFinished() == threadsActive) {
				long endTime = System.nanoTime();
				
				long timeSpent = (endTime - startTime) / 1000000;
				
				System.out.println(timeSpent + "ms");
				return;
			}
		}
	}

}
