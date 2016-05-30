package multi;

public class MainMulti {

  public static void main(String[] args) {
    String url = args[0];
    String path = args[1];
    Integer threads = Integer.parseInt(args[2]);

    ExecutorService es = Executors.newFixedThreadPool(threads);

    LinkExtractor extractor = new LinkExtractor(url);
    extractor.scrape();

    for (int i = 0; i < threads; i++) {
      es.submit(new Runner(extractor, path));
    }
    
    es.shutdown();
  }

}
