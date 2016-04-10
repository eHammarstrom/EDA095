package multithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scraper {
	public static URL createURL(String url, URL base) {
		try {
			return new URL(base, url);
		} catch (Exception e) {
			return null;
		}
	}

	public static HashMap<String, URL> getFiles(URL target, String fileType) throws IOException {
		InputStream is = target.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));

		HashMap<String, URL> fileUrls = new HashMap<String, URL>();

		String line;
		Pattern pattern = Pattern.compile("(?i)(href=\")(.+?)([^\\/]*." + fileType + ")(\")");
		Matcher m;
		while ((line = br.readLine()) != null) {
			m = pattern.matcher(line);
			while (m.find()) {
				URL temp = createURL(m.group(2) + m.group(3), target);
				if (temp != null) {
					fileUrls.put(m.group(3), temp);
				}
			}
		}
		
		return fileUrls;
	}
}
