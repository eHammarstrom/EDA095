package monoscan;

public class MonoScan {
    private ServerStats servers;

    public MonoScan() {
        servers = new ServerStats();
    }

    // Scans a subnetAddress xxx.xxx.xxx
    // Done by adding .[0-255] to address
    // Adding the server info and address to ServerStats
    // Printing ServerStats
    public void scan(String subnetAddress) {
        List<String> travAddr = new List<String>();

        for (int i = 0; i < 256; i++) {
            travAddr.add(subnetAddress + "." + Integer.toString(i));
        }

        while (!travAddr.isEmpty()) {
            String addr;
            String serverType;

            try {
                addr = travAddr.pop(); // Throws exception if empty, but we are checking it beforehand
            } catch (Exception e) {
                continue; // This is merely done for safety, this should never happen;
            }

            if ((serverType = dotAddress2Server(addr)) != null) {
                servers.addServer(serverType, addr);
            }

            servers.print();
        }
    }

    // Gets the header field 'Server' from a HTTP-request from given target address
    private String dotAddress2Server(String address) {
        try {
            URL url = new URL(address);
            URLConnection urlConn = url.openConnection();

            urlConn.setConnectTime(200);
            urlConn.setReadTime(200);

            return urlConn.getHeaderField("Server"); // Returns null if not found
        } catch (Exception e) { // General catch because of multiple exceptions, and laziness
            return null; // Returns null if timeout or any other problems
        }
    }

}
