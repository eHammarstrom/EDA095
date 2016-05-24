package buddy;

public class BuddyRequester extends Thread {
    private MulticastSocket ms;
    private String nickname;
    private BuddyWindow bw;

    public BuddyRequester(BuddyWindow bw, MulticastSocket ms) {
        this.ms = ms;
        this.bw = bw;
    }

    public void run() {
        try {
            byte[] bufMessage = "buddy_hello".getBytes();
            InetAddress ia = InetAddress.getByName("experiment.mcast.net");
            int port = 30000;

            DatagramPacket dp = new DatagramPacket(
                bufMessage,
                bufMessage.length,
                ia,
                port
            );

            while(true) {
                Thread.sleep(60 * 1000);
                bw.clear();
                ms.send(dp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
