package buddy;

public class BuddyServer extends Thread {
    private MulticastSocket ms;
    private DatagramSocket ds;
    private String nickname;

    public BuddyServer(MulticastSocket ms, DatagramSocket ds, String nickname) {
        this.ms = ms;
        this.ds = ds;
        this.nickname = nickname;
    }

    public void run() {
        try {
            byte[] bufListen = new byte[1024];
            byte[] bufMessage = nickname.getBytes();

            DatagramPacket dpMessage;
            DatagramPacket dpListen;
            while(true) {
                dpListen = new DatagramPacket(bufListen, bufListen.length);
                ms.receive(dpListen);
                dpMessage = new DatagramPacket(
                    bufMessage,
                    bufMessage.length,
                    dpListen.getAddress(),
                    dpListen.getPort() + 1
                );
                ds.send(dpMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
