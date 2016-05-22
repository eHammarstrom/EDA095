package monoscan;

public class ServerStats {
    private volatile Map<String, List<String>> serverCount;

    public ServerStats() {
        serverCount = new HashMap<String, List<String>>();
    }

    public synchronized void addServer(String serverKey, String addressVal) {
        List<String> tempList;

        if ((tempList = serverCount.get(serverKey)) != null) {
            tempList.add(addressVal);
        } else {
            tempList = new List<String>();
            tempList.add(addressVal);
            serverCount.put(serverKey, tempList);
        }
    }

    public synchronized void print() {
        for (Entry<String, List<String>> e : serverCount.entrySet()) {
            System.out.println(e.getKey() + ": " + e.getValue().size());
        }
    }
}
