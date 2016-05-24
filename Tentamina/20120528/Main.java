package buddy;

public class Main {

    public static void main(String[] args) {
        if (arg.length != 1)
            throw new IllegalArgumentException();

        String nickname = arg[0];

        try {
            MulticastSocket ms = new MulticastSocket(30000);
            ms.joinGroup(InetAddress.getByName("experiment.mcast.net"));
            DatagramSocket ds = new DatagramSocket(30001);

            BuddyWindow bw = new BuddyWindow();
            BuddyRequester br = new BuddyRequester(bw, ms).start();
            BuddyServer bs = new BuddyServer(ms, ds, nickname).start();
            BuddyListener bl = new BuddyListener(bw, ds).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
