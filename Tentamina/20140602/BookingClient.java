package booking;

public class BookingClient {

    public BookingClient() {

    }

    public static void main(String[] args) {
        if (args.length <= 2)
            throw new IllegalArgumentException();

        try {
            InetAddress serverAddress = InetAddress.getByName(args[0]);
            String command = args[1];

            Socket socket = new Socket(serverAddress, 30000);
            OutputStream os = socket.getOutputStream();
            InputStream is = socket.getInputStream();

            BufferedReader br =
                new BufferedReader(new InputStreamReader(is, "UTF-8"));
            BufferedWriter bw =
                new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

            switch(command.toLowerCase()) {
                case "create":
                    //TODO create a booking resource
                    bw.write(
                        args[1] + " " +
                        args[2] + "\n");
                    break;
                case "book":
                    //TODO create a booking on a booking resource
                    bw.write(
                        args[1] + " " +
                        args[2] + " " +
                        args[3] + " " +
                        args[4] + " " +
                        args[5] + " " +
                        args[6] + "\n");
                    break;
                case "delete":
                    //TODO remove a bookings resource
                    bw.write(
                        args[1] + " " +
                        args[2] + " " +
                        args[3] + " " +
                        args[4] + "\n");
                    break;
                case "show":
                    // TODO ask for list of bookings of a bookingsresource
                    bw.write(
                        args[1] + " " +
                        args[2] + " " +
                        args[3] + "\n");
                    break;
                case "watch":
                    // TODO listen for booking changes
                    bw.write(
                        args[1] + " " +
                        args[2] + " " +
                        args[3] + "\n");
                    break;
            }

            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
