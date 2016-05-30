package scraper;

public class Main {

  public static void main(String[] args) {
    try {


      LinkExtractor extractor = new LinkExtractor(args[0]);

      ArrayList<URL> urls = extractor.scrape();

      File urlToFile = null;
      OutputStream out = null;
      InputStream is = null;
      //PrintWriter pw = null;
      //BufferedReader br = null;
      int count = 0;
      for (URL url : urls) {
        count++;
        if (url.toString().contains(".pdf")) {
          urlToFile = new File("URL" + Integer.toString(count) + ".pdf");
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
      }

      is.close();
      out.close();

    }
  }

}
