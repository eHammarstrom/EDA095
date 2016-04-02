package singlethread;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map.Entry;
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
			
			// Java 8 lambda expression, NICE!
			pdfs.forEach((k, v) -> downloadFile(v, "singlethread/" + k));
			
			/*
			for (Entry<String, URL> entry : pdfs.entrySet()) {
				downloadFile(entry.getValue(), "singlethread/" + entry.getKey());
			}
			*/
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		long endTime = System.nanoTime();
		
		long duration = endTime - startTime;

		System.out.println(duration / 1000000 + "ms");
	}
	
	private static void downloadFile(URL url, String fileName) {
		File file;
		FileOutputStream fout;
		BufferedInputStream bis;

		try {
			file = new File(fileName);
			file.createNewFile();
			fout = new FileOutputStream(file);
			bis = new BufferedInputStream(url.openStream());
			
			byte[] buffer = new byte[4096];
			int bytes;
			while ((bytes = bis.read(buffer)) != -1) {
				fout.write(buffer, 0, bytes);
			}
			
			bis.close();
			fout.flush();
			fout.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
