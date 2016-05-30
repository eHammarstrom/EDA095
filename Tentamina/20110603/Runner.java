package multi;

public class Runner extends Thread {
  LinkExtractor extractor;
  String path;

  public Runner(LinkExtractor extractor, String path) {
    this.extractor = extractor;
    this.path = path;
  }

  public void run() {
    try {
      while (!extractor.isEmpty()) {
        URL url = extractor.popUrl();
        urlToFile = new File(path + "/URL" + extractor.size() + ".pdf");
        urlToFile.createNewFile();

        is = url.openStream();

        out =
          new FileOutputStream(urlToFile);

        byte[] buffer = new byte[4096];
        int bytes;
        while ((bytes = is.read(buffer)) != -1) {
          fout.write(buffer, 0, bytes);
        }
      }
    } catch (Exception e) {}
  }

}
