package buddy;

public class BuddyListener extends Thread {
    private BuddyWindow bw;
    private DatagramSocket ds;

    public BuddyListener(BuddyWindow bw, DatagramSocket ds) {
        this.bw = bw;
        this.ds = ds;
    }

    public void run() {
        try {
            byte[] bufListen = new byte[1024];

            DatagramPacket dp;
            while(true) {
                dp = new DatagramPacket(bufListen, bufListen.length);
                ds.receive(dp);
                String message = new String(dp.getData(), "UTF-8");
                bw.addBuddy(message + " (" + dp.getAddress().getHostName() + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
