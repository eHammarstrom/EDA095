package booking;

public class BookingServer {

    public static void main(String[] args) {
        try {
            BookingManager bm = new BookingManager();
            BookingManagerSync bms = new BookingManagerSync(bm);

            ServerSocket socket = new ServerSocket(30000);

            while (true) {
                new Thread(
                    new BookingServerHandler(bms, socket.accept())).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
