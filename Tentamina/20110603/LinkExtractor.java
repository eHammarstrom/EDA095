package scraper;

public class LinkExtractor {
  private Stack<URL> urlList;
  private String url;

  public LinkExtractor(String url) {
    this.urlList = new Stack<URL>();
    this.url = url;
  }

  public void scrape() {
    try {
      URL target = new URL(url);
      InputStream is = target.openStream();
      Document doc =
        Jsoup.parse(is, "UTF-8", target.toString());

      Elements links = doc.select("a[href]");

      for (Element link : links) {
        try {
          String targetLink = link.attr("abs:href");
          if (targetLink.contains(".pdf")) {
            urlList.push(new URL(targetLink));
          }
        } catch (URLException e) { continue; }
      }

      is.close();
    } catch (Exception e) { e.printStackTrace(); }
  }

  public synchronized popUrl() {
    return urlList.pop();
  }

  public synchronized isEmpty() {
    return urlList.isEmpty();
  }

  public synchronized size() {
    return urlList.size();
  }

}
