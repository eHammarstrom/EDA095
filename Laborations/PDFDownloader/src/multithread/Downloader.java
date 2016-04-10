package multithread;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Downloader {
	public static void download(URL url, String fileName, String dir) {
		File file;
		FileOutputStream fout;
		BufferedInputStream bis;

		try {
			String outPath = dir + "/" + fileName;
			System.out.println(outPath + " started!");
			new File(dir).mkdirs();
			file = new File(outPath);
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

			System.out.println(outPath + " done!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
