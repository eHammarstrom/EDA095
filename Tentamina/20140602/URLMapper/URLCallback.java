package urlmapper;

public class URLRunner implements Callable<String> {
    private String urlName;
    private String urlAddress;

    public URLRunner(String urlName) {
        this.urlName;
    }

    private String processUrl(String procUrl) {
        try {
            return InetAddress.getByName(procUrl).getHostAddress();
        } catch (Exception e) {
            return null;
        }
    }

    public String call() {
        return processUrl();
    }
}
